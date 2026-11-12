package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.LayoutGenerator;
import it.unibo.mahjongsol.api.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementazione della disposizione classica a "Tartaruga" in 3D.
 */
public class ClassicTurtleLayout implements LayoutGenerator {

    @Override
    public List<Position> generateLayout() {
        final List<Position> positions = new ArrayList<>();

        // Livello 0 (Base)
        addTileRow(positions, 1, 12, 0, 0);
        addTileRow(positions, 3, 10, 1, 0);
        addTileRow(positions, 2, 11, 2, 0);
        addTileRow(positions, 1, 12, 3, 0);
        addTileRow(positions, 1, 12, 4, 0);
        addTileRow(positions, 2, 11, 5, 0);
        addTileRow(positions, 3, 10, 6, 0);
        addTileRow(positions, 1, 12, 7, 0);

        // Livello 1
        for (int y = 2; y <= 5; y++) {
            addTileRow(positions, 4, 9, y, 1);
        }

        // Livello 2
        for (int y = 3; y <= 4; y++) {
            addTileRow(positions, 5, 8, y, 2);
        }

        // Livello 3
        for (int y = 3; y <= 4; y++) {
            addTileRow(positions, 6, 7, y, 3);
        }

        // Livello 4 (Vertice superiore)
        positions.add(new Position(6, 3, 4));

        return Collections.unmodifiableList(positions);
    }

    private void addTileRow(List<Position> list, int startX, int endX, int y, int z) {
        for (int x = startX; x <= endX; x++) {
            list.add(new Position(x, y, z));
        }
    }
}