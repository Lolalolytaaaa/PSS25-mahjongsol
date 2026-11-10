package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.Board;
import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementazione concreta della plancia di gioco tridimensionale.
 */
public class BoardImpl implements Board {

    private final Map<Position, Tile> tiles;

    public BoardImpl(Map<Position, Tile> initialTiles) {
        this.tiles = new HashMap<>(initialTiles);
    }

    @Override
    public Optional<Tile> getTileAt(int x, int y, int z) {
        return Optional.ofNullable(tiles.get(new Position(x, y, z)));
    }

    @Override
    public int getRemainingTilesCount() {
        return tiles.size();
    }

    @Override
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    /**
     * Verifica se una tessera in una determinata posizione è "libera" (cliccabile).
     * Una tessera è libera se:
     * 1. Non ha tessere direttamente sopra di essa (livello z + 1).
     * 2. Ha almeno uno dei due lati (sinistra o destra) completamente libero.
     */
    public boolean isTileFree(Position pos) {
        if (!tiles.containsKey(pos)) {
            return false;
        }

        // 1. Controlla se c'è una tessera sopra
        boolean hasTileAbove = tiles.keySet().stream()
                .anyMatch(p -> p.z() == pos.z() + 1 && Math.abs(p.x() - pos.x()) <= 1 && Math.abs(p.y() - pos.y()) <= 1);

        if (hasTileAbove) {
            return false;
        }

        // 2. Controlla i lati sinistro (x - 2) e destro (x + 2)
        boolean hasLeftNeighbor = tiles.keySet().stream()
                .anyMatch(p -> p.z() == pos.z() && p.x() == pos.x() - 2 && Math.abs(p.y() - pos.y()) <= 1);

        boolean hasRightNeighbor = tiles.keySet().stream()
                .anyMatch(p -> p.z() == pos.z() && p.x() == pos.x() + 2 && Math.abs(p.y() - pos.y()) <= 1);

        // È libera se manca il vicino a sinistra OPPURE il vicino a destra
        return !hasLeftNeighbor || !hasRightNeighbor;
    }

    /**
     * Rimuove una tessera dalla plancia.
     */
    public boolean removeTile(Position pos) {
        return tiles.remove(pos) != null;
    }

    public Map<Position, Tile> getTiles() {
        return Collections.unmodifiableMap(tiles);
    }
}