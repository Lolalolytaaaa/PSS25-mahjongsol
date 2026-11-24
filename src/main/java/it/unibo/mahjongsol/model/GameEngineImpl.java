package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.Board;
import it.unibo.mahjongsol.api.GameEngine;
import it.unibo.mahjongsol.api.LayoutGenerator;
import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del motore di gioco del Mahjong Solitaire.
 * Gestisce lo stato della partita, la selezione delle tessere, gli abbinamenti,
 * il punteggio, i suggerimenti e lo storico per l'undo.
 */
public class GameEngineImpl implements GameEngine {

    private static final int MATCH_POINTS = 100;

    private record MoveRecord(Position pos1, Tile tile1, Position pos2, Tile tile2) {
    }

    private final BoardFactory boardFactory;
    private final LayoutGenerator layoutGenerator;
    private final Deque<MoveRecord> moveHistory;
    private Board board;
    private Position selectedPosition;
    private int score;

    public GameEngineImpl() {
        this(new BoardFactory(), new ClassicTurtleLayout());
    }

    public GameEngineImpl(final BoardFactory boardFactory, final LayoutGenerator layoutGenerator) {
        this.boardFactory = boardFactory;
        this.layoutGenerator = layoutGenerator;
        this.moveHistory = new ArrayDeque<>();
        startNewGame();
    }

    @Override
    public void startNewGame() {
        this.board = this.boardFactory.createBoard(this.layoutGenerator);
        this.selectedPosition = null;
        this.score = 0;
        if (this.moveHistory != null) {
            this.moveHistory.clear();
        }
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    public Optional<Position> getSelectedPosition() {
        return Optional.ofNullable(this.selectedPosition);
    }

    @Override
    public boolean selectTile(final int x, final int y, final int z) {
        final Position clickedPos = new Position(x, y, z);

        if (this.board.getTileAt(x, y, z).isEmpty() || !this.board.isTileFree(clickedPos)) {
            return false;
        }

        if (this.selectedPosition == null) {
            this.selectedPosition = clickedPos;
            return false;
        }

        if (this.selectedPosition.equals(clickedPos)) {
            this.selectedPosition = null;
            return false;
        }

        final Optional<Tile> firstTileOpt = this.board.getTileAt(
                this.selectedPosition.x(),
                this.selectedPosition.y(),
                this.selectedPosition.z());
        final Optional<Tile> secondTileOpt = this.board.getTileAt(x, y, z);

        if (firstTileOpt.isPresent() && secondTileOpt.isPresent()
                && areTilesMatching(firstTileOpt.get(), secondTileOpt.get())) {
            this.moveHistory.push(new MoveRecord(
                    this.selectedPosition, firstTileOpt.get(),
                    clickedPos, secondTileOpt.get()));
            this.board.removeTile(this.selectedPosition);
            this.board.removeTile(clickedPos);
            this.selectedPosition = null;
            this.score += MATCH_POINTS;
            return true;
        }

        this.selectedPosition = clickedPos;
        return false;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public boolean isGameOver() {
        if (this.board.isEmpty()) {
            return false;
        }
        return getHint().isEmpty();
    }

    @Override
    public Optional<List<Position>> getHint() {
        final List<Position> freePositions = new ArrayList<>();
        for (final Position pos : this.layoutGenerator.generateLayout()) {
            final Optional<Tile> tile = this.board.getTileAt(pos.x(), pos.y(), pos.z());
            if (tile.isPresent() && this.board.isTileFree(pos)) {
                freePositions.add(pos);
            }
        }

        for (int i = 0; i < freePositions.size(); i++) {
            for (int j = i + 1; j < freePositions.size(); j++) {
                final Position p1 = freePositions.get(i);
                final Position p2 = freePositions.get(j);
                final Tile t1 = this.board.getTileAt(p1.x(), p1.y(), p1.z()).orElseThrow();
                final Tile t2 = this.board.getTileAt(p2.x(), p2.y(), p2.z()).orElseThrow();
                if (areTilesMatching(t1, t2)) {
                    return Optional.of(List.of(p1, p2));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean undo() {
        if (this.moveHistory.isEmpty()) {
            return false;
        }
        final MoveRecord lastMove = this.moveHistory.pop();
        this.board.placeTile(lastMove.pos1(), lastMove.tile1());
        this.board.placeTile(lastMove.pos2(), lastMove.tile2());
        this.selectedPosition = null;
        this.score = Math.max(0, this.score - MATCH_POINTS);
        return true;
    }

    private boolean areTilesMatching(final Tile t1, final Tile t2) {
        if (!t1.getSuit().equals(t2.getSuit())) {
            return false;
        }
        if (TileSuit.FLOWERS.name().equals(t1.getSuit()) || TileSuit.SEASONS.name().equals(t1.getSuit())) {
            return true;
        }
        return t1.getValue().equals(t2.getValue());
    }
}