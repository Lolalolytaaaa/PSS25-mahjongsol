package it.unibo.mahjongsol.api;

/**
 * Gestisce la logica principale e lo stato della partita.
 */
public interface GameEngine {

  /**
   * Inizializza e avvia una nuova partita, generando la plancia.
   */
  void startNewGame();

  /**
   * @return La plancia di gioco attuale.
   */
  Board getBoard();

  /**
   * Tenta di selezionare o abbinare una tessera cliccata dall'utente.
   * 
   * @param x Posizione orizzontale della tessera cliccata
   * @param y Posizione verticale della tessera cliccata
   * @param z Livello di altezza della tessera cliccata
   * @return true se l'azione ha prodotto un abbinamento valido, false altrimenti
   */
  boolean selectTile(int x, int y, int z);

  /**
   * @return Il punteggio attuale del giocatore.
   */
  int getScore();

  /**
   * @return true se non ci sono più mosse legali disponibili (sconfitta).
   */
  boolean isGameOver();
}