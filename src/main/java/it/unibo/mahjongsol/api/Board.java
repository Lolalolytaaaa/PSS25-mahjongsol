package it.unibo.mahjongsol.api;

import java.util.Optional;

/**
 * Rappresenta la plancia di gioco e la disposizione tridimensionale delle
 * tessere.
 */
public interface Board {

  /**
   * Recupera una tessera in base alle sue coordinate tridimensionali.
   * 
   * @param x Posizione orizzontale
   * @param y Posizione verticale
   * @param z Livello di altezza (layer)
   * @return Un Optional contenente la tessera se presente in quella posizione,
   *         altrimenti vuoto.
   */
  Optional<Tile> getTileAt(int x, int y, int z);

  /**
   * @return Il numero di tessere attualmente rimaste sulla plancia.
   */
  int getRemainingTilesCount();

  /**
   * @return true se non ci sono più tessere (vittoria), false altrimenti.
   */
  boolean isEmpty();
}