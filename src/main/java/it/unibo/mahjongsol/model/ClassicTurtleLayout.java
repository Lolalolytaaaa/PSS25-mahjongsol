package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.LayoutGenerator;
import it.unibo.mahjongsol.api.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementazione della disposizione classica a "Tartaruga" in 3D (144
 * tessere).
 * Usa una griglia a mezz'unità (passo 2) per permettere le sovrapposizioni
 * sfalsate.
 */
public class ClassicTurtleLayout implements LayoutGenerator {

  @Override
  public List<Position> generateLayout() {
    final List<Position> positions = new ArrayList<>();

    // Livello 0 (Base - 87 tessere)
    addTileRow(positions, 2, 24, 0, 0); // 12 tessere
    addTileRow(positions, 6, 20, 2, 0); // 8 tessere
    addTileRow(positions, 4, 22, 4, 0); // 10 tessere
    addTileRow(positions, 2, 24, 6, 0); // 12 tessere
    addTileRow(positions, 2, 24, 8, 0); // 12 tessere
    addTileRow(positions, 4, 22, 10, 0); // 10 tessere
    addTileRow(positions, 6, 20, 12, 0); // 8 tessere
    addTileRow(positions, 2, 24, 14, 0); // 12 tessere

    // Ali laterali del Livello 0 (3 tessere)
    positions.add(new Position(0, 7, 0)); // Ala sinistra (centrata tra y=6 e y=8)
    positions.add(new Position(26, 7, 0)); // Ala destra 1
    positions.add(new Position(28, 7, 0)); // Ala destra 2

    // Livello 1 (36 tessere - quadrato 6x6)
    for (int y = 2; y <= 12; y += 2) {
      addTileRow(positions, 8, 18, y, 1);
    }

    // Livello 2 (16 tessere - quadrato 4x4)
    for (int y = 4; y <= 10; y += 2) {
      addTileRow(positions, 10, 16, y, 2);
    }

    // Livello 3 (4 tessere - quadrato 2x2)
    for (int y = 6; y <= 8; y += 2) {
      addTileRow(positions, 12, 14, y, 3);
    }

    // Livello 4 (1 tessera - vertice centrale sopra il quadrato 2x2)
    positions.add(new Position(13, 7, 4));

    return Collections.unmodifiableList(positions);
  }

  private void addTileRow(final List<Position> list, final int startX, final int endX, final int y, final int z) {
    for (int x = startX; x <= endX; x += 2) {
      list.add(new Position(x, y, z));
    }
  }
}