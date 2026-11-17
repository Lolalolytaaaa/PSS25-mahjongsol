package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.Board;
import it.unibo.mahjongsol.api.GameEngine;
import it.unibo.mahjongsol.api.LayoutGenerator;
import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del motore di gioco del Mahjong Solitaire.
 * Gestisce lo stato della partita, la selezione delle tessere, gli abbinamenti e il punteggio.
 */
public class GameEngineImpl implements GameEngine {

    private static final int MATCH_POINTS = 100;

    private final BoardFactory boardFactory;
    private final LayoutGenerator layoutGenerator;
    private Board board;
    private Position selectedPosition;
    private int score;

    public GameEngineImpl() {
        this(new BoardFactory(), new ClassicTurtleLayout());
    }

    public GameEngineImpl(final BoardFactory boardFactory, final LayoutGenerator layoutGenerator) {
        this.boardFactory = boardFactory;
        this.layoutGenerator = layoutGenerator;
        startNewGame();
    }

    @Override
    public void startNewGame() {
        this.board = this.boardFactory.createBoard(this.layoutGenerator);
        this.selectedPosition = null;
        this.score = 0;
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

        // Ignora il click se la tessera non esiste o non è libera
        if (this.board.getTileAt(x, y, z).isEmpty() || !this.board.isTileFree(clickedPos)) {
            return false;
        }

        // Caso 1: Nessuna tessera attualmente selezionata -> seleziona questa
        if (this.selectedPosition == null) {
            this.selectedPosition = clickedPos;
            return false;
        }

        // Caso 2: Ricliccata la stessa tessera -> deseleziona
        if (this.selectedPosition.equals(clickedPos)) {
            this.selectedPosition = null;
            return false;
        }

        // Caso 3: Due tessere diverse selezionate -> verifica l'abbinamento
        final Optional<Tile> firstTileOpt = this.board.getTileAt(
                this.selectedPosition.x(),
                this.selectedPosition.y(),
                this.selectedPosition.z()
        );
        final Optional<Tile> secondTileOpt = this.board.getTileAt(x, y, z);

        if (firstTileOpt.isPresent() && secondTileOpt.isPresent()
                && areTilesMatching(firstTileOpt.get(), secondTileOpt.get())) {
            this.board.removeTile(this.selectedPosition);
            this.board.removeTile(clickedPos);
            this.selectedPosition = null;
            this.score += MATCH_POINTS;
            return true;
        }

        // Se non corrispondono, sposta la selezione sulla nuova tessera cliccata
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

        final List<Tile> freeTiles = new ArrayList<>();
        for (final Position pos : this.layoutGenerator.generateLayout()) {
            final Optional<Tile> tile = this.board.getTileAt(pos.x(), pos.y(), pos.z());
            if (tile.isPresent() && this.board.isTileFree(pos)) {
                freeTiles.add(tile.get());
            }
        }

        for (int i = 0; i < freeTiles.size(); i++) {
            for (int j = i + 1; j < freeTiles.size(); j++) {
                if (areTilesMatching(freeTiles.get(i), freeTiles.get(j))) {
                    return false;
                }
            }
        }
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