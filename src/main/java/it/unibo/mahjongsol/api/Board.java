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

    /**
     * Verifica se la tessera nella posizione indicata è libera e selezionabile.
     *
     * @param pos Le coordinate 3D della tessera
     * @return true se la tessera è libera, false altrimenti
     */
    boolean isTileFree(Position pos);

    /**
     * Rimuove la tessera nella posizione indicata dalla plancia.
     *
     * @param pos Le coordinate 3D della tessera da rimuovere
     * @return true se la tessera era presente ed è stata rimossa, false altrimenti
     */
    boolean removeTile(Position pos);

    /**
     * Riposiziona una tessera in una specifica coordinata 3D (usato per l'Undo).
     *
     * @param pos  Le coordinate 3D in cui reinserire la tessera
     * @param tile La tessera da posizionare
     */
    void placeTile(Position pos, Tile tile);
}