package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.Board;
import it.unibo.mahjongsol.api.LayoutGenerator;
import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory responsabile della creazione del mazzo standard da 144 tessere
 * e del suo abbinamento casuale con le coordinate di un LayoutGenerator.
 */
public class BoardFactory {

  public Board createBoard(final LayoutGenerator layoutGenerator) {
    final List<Position> positions = layoutGenerator.generateLayout();
    final List<Tile> deck = generateStandardDeck();

    if (positions.size() != deck.size()) {
      throw new IllegalArgumentException(
          "Il layout richiede " + positions.size() + " tessere, ma il mazzo ne ha " + deck.size());
    }

    Collections.shuffle(deck);

    final Map<Position, Tile> boardMap = new HashMap<>();
    for (int i = 0; i < positions.size(); i++) {
      boardMap.put(positions.get(i), deck.get(i));
    }

    return new BoardImpl(boardMap);
  }

  private List<Tile> generateStandardDeck() {
    final List<Tile> deck = new ArrayList<>(144);
    int idCounter = 1;

    // 1. Semi numerali (3 semi x 9 valori x 4 copie = 108 tessere)
    final List<TileSuit> numberedSuits = List.of(TileSuit.BAMBOO, TileSuit.CHARACTERS, TileSuit.CIRCLES);
    for (final TileSuit suit : numberedSuits) {
      for (int value = 1; value <= 9; value++) {
        for (int copy = 0; copy < 4; copy++) {
          deck.add(new TileImpl(idCounter++, suit, String.valueOf(value)));
        }
      }
    }

    // 2. Venti (4 venti x 4 copie = 16 tessere)
    final List<String> winds = List.of("EAST", "SOUTH", "WEST", "NORTH");
    for (final String wind : winds) {
      for (int copy = 0; copy < 4; copy++) {
        deck.add(new TileImpl(idCounter++, TileSuit.WINDS, wind));
      }
    }

    // 3. Draghi (3 draghi x 4 copie = 12 tessere)
    final List<String> dragons = List.of("RED", "GREEN", "WHITE");
    for (final String dragon : dragons) {
      for (int copy = 0; copy < 4; copy++) {
        deck.add(new TileImpl(idCounter++, TileSuit.DRAGONS, dragon));
      }
    }

    // 4. Fiori (4 tessere uniche -> formano 2 coppie valide tra loro)
    for (int f = 1; f <= 4; f++) {
      deck.add(new TileImpl(idCounter++, TileSuit.FLOWERS, "FLOWER_" + f));
    }

    // 5. Stagioni (4 tessere uniche -> formano 2 coppie valide tra loro)
    for (int s = 1; s <= 4; s++) {
      deck.add(new TileImpl(idCounter++, TileSuit.SEASONS, "SEASON_" + s));
    }

    return deck;
  }
}
