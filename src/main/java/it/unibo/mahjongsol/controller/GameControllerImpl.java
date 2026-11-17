package it.unibo.mahjongsol.controller;

import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;
import it.unibo.mahjongsol.model.BoardImpl;
import it.unibo.mahjongsol.model.GameEngineImpl;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Implementazione concreta del Controller di gioco.
 */
public class GameControllerImpl implements GameController {

    private final GameEngineImpl engine;

    public GameControllerImpl() {
        this.engine = new GameEngineImpl();
    }

    @Override
    public void newGame() {
        this.engine.startNewGame();
    }

    @Override
    public void onTileClicked(final Position pos) {
        this.engine.selectTile(pos.x(), pos.y(), pos.z());
    }

    @Override
    public Map<Position, Tile> getVisibleTiles() {
        if (this.engine.getBoard() instanceof BoardImpl boardImpl) {
            return boardImpl.getTiles();
        }
        return Collections.emptyMap();
    }

    @Override
    public Optional<Position> getSelectedTile() {
        return this.engine.getSelectedPosition();
    }

    @Override
    public boolean isTileFree(final Position pos) {
        return this.engine.getBoard().isTileFree(pos);
    }

    @Override
    public int getScore() {
        return this.engine.getScore();
    }

    @Override
    public boolean isVictory() {
        return this.engine.getBoard().isEmpty();
    }

    @Override
    public boolean isGameOver() {
        return this.engine.isGameOver();
    }
}
