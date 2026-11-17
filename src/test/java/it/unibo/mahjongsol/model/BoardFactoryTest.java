package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BoardFactoryTest {

  @Test
  void testCreateClassicBoard() {
    final BoardFactory factory = new BoardFactory();
    final Board board = factory.createBoard(new ClassicTurtleLayout());

    assertFalse(board.isEmpty());
    assertEquals(144, board.getRemainingTilesCount());
  }
}