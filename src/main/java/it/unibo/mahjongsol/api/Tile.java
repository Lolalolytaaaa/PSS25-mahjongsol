package it.unibo.mahjongsol.api;

/**
 * Rappresenta una singola tessera del gioco Mahjong Solitaire.
 */
public interface Tile {
    
    /**
     * @return L'identificativo univoco della tessera (utile per l'algoritmo).
     */
    int getId();

    /**
     * @return La famiglia o il seme della tessera (es. "BAMBOO", "CHARACTERS", "DRAGONS").
     */
    String getSuit();

    /**
     * @return Il valore specifico della tessera (es. "1", "9", "RED").
     */
    String getValue();
}