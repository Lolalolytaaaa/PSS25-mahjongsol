package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private Map<Position, Tile> initialTiles;
    private Position posBase;

    @BeforeEach
    void setUp() {
        initialTiles = new HashMap<>();
        posBase = new Position(0, 0, 0);
        initialTiles.put(posBase, new TileImpl(1, TileSuit.BAMBOO, "1"));
    }

    @Test
    void testInitialState() {
        BoardImpl board = new BoardImpl(initialTiles);
        assertEquals(1, board.getRemainingTilesCount());
        assertFalse(board.isEmpty());
        assertTrue(board.getTileAt(0, 0, 0).isPresent());
    }

    @Test
    void testSingleTileIsFree() {
        BoardImpl board = new BoardImpl(initialTiles);
        assertTrue(board.isTileFree(posBase));
    }

    @Test
    void testRemoveTile() {
        BoardImpl board = new BoardImpl(initialTiles);
        assertTrue(board.removeTile(posBase));
        assertEquals(0, board.getRemainingTilesCount());
        assertTrue(board.isEmpty());
    }
}


