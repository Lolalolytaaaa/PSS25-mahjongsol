package it.unibo.mahjongsol.controller;

import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;

import java.util.Map;
import java.util.Optional;

/**
 * Interfaccia del Controller che media tra la View (JavaFX) e il Model (GameEngine).
 */
public interface GameController {

    /**
     * Avvia una nuova partita.
     */
    void newGame();

    /**
     * Gestisce il click dell'utente su una tessera.
     *
     * @param pos La posizione della tessera cliccata
     */
    void onTileClicked(Position pos);

    /**
     * @return Mappa immutabile delle tessere attualmente presenti sulla plancia.
     */
    Map<Position, Tile> getVisibleTiles();

    /**
     * @return La posizione della tessera attualmente selezionata, se presente.
     */
    Optional<Position> getSelectedTile();

    /**
     * @return true se la tessera nella posizione indicata è cliccabile.
     */
    boolean isTileFree(Position pos);

    /**
     * @return Il punteggio attuale.
     */
    int getScore();

    /**
     * @return true se il giocatore ha vinto (plancia vuota).
     */
    boolean isVictory();

    /**
     * @return true se non ci sono più mosse disponibili.
     */
    boolean isGameOver();
}