package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.Tile;

public class TileImpl implements Tile {
    private final int id;
    private final TileSuit suit;
    private final String value;

    public TileImpl(int id, TileSuit suit, String value) {
        this.id = id;
        this.suit = suit;
        this.value = value;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getSuit() {
        return this.suit.name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}