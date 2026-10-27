package it.unibo.mahjongsol.api;

/**
 * Rappresenta una coordinata tridimensionale sulla plancia di gioco.
 * 
 * @param x Posizione sulla griglia orizzontale (colonne)
 * @param y Posizione sulla griglia verticale (righe)
 * @param z Livello di altezza (layer di sovrapposizione)
 */
public record Position(int x, int y, int z) {
}