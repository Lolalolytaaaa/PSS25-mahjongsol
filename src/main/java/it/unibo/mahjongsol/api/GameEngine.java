package it.unibo.mahjongsol.api;

import java.util.List;
import java.util.Optional;

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

  /**
   * Cerca una coppia di tessere libere e abbinabili sulla plancia.
   *
   * @return Un Optional contenente la lista delle due posizioni suggerite,
   *         oppure vuoto se non ci sono mosse disponibili.
   */
  Optional<List<Position>> getHint();

  /**
   * Annulla l'ultima mossa effettuata, ripristinando le due tessere rimosse
   * e sottraendo i punti guadagnati.
   *
   * @return true se una mossa è stata annullata, false se non ci sono mosse nello
   *         storico.
   */
  boolean undo();
}