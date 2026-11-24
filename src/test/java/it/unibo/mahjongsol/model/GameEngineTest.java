package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.GameEngine;
import it.unibo.mahjongsol.api.Position;
import it.unibo.mahjongsol.api.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite di test unitari per {@link GameEngineImpl}.
 */
class GameEngineTest {

  private GameEngine engine;

  @BeforeEach
  void setUp() {
    this.engine = new GameEngineImpl();
  }

  @Test
  void testInitialState() {
    assertEquals(0, this.engine.getScore());
    assertEquals(144, this.engine.getBoard().getRemainingTilesCount());
    assertFalse(this.engine.isGameOver());
  }

  @Test
  void testGetHintReturnsValidMatchingFreePair() {
    final Optional<List<Position>> hintOpt = this.engine.getHint();
    assertTrue(hintOpt.isPresent(), "A inizio partita deve esserci almeno un suggerimento valido");

    final List<Position> pair = hintOpt.get();
    assertEquals(2, pair.size());

    final Position p1 = pair.get(0);
    final Position p2 = pair.get(1);

    assertTrue(this.engine.getBoard().isTileFree(p1));
    assertTrue(this.engine.getBoard().isTileFree(p2));

    final Tile t1 = this.engine.getBoard().getTileAt(p1.x(), p1.y(), p1.z()).orElseThrow();
    final Tile t2 = this.engine.getBoard().getTileAt(p2.x(), p2.y(), p2.z()).orElseThrow();
    assertEquals(t1.getSuit(), t2.getSuit());
  }

  @Test
  void testMatchAndUndo() {
    // A inizio partita non ci sono mosse nello storico
    assertFalse(this.engine.undo());

    // Recupera una coppia valida tramite getHint e la abbina
    final List<Position> pair = this.engine.getHint().orElseThrow();
    final Position p1 = pair.get(0);
    final Position p2 = pair.get(1);

    assertFalse(this.engine.selectTile(p1.x(), p1.y(), p1.z()), "La prima selezione non completa la coppia");
    assertTrue(this.engine.selectTile(p2.x(), p2.y(), p2.z()), "La seconda selezione deve produrre un match");

    assertEquals(100, this.engine.getScore());
    assertEquals(142, this.engine.getBoard().getRemainingTilesCount());
    assertTrue(this.engine.getBoard().getTileAt(p1.x(), p1.y(), p1.z()).isEmpty());
    assertTrue(this.engine.getBoard().getTileAt(p2.x(), p2.y(), p2.z()).isEmpty());

    // Esegue l'Undo e verifica il ripristino dello stato precedente
    assertTrue(this.engine.undo());
    assertEquals(0, this.engine.getScore());
    assertEquals(144, this.engine.getBoard().getRemainingTilesCount());
    assertTrue(this.engine.getBoard().getTileAt(p1.x(), p1.y(), p1.z()).isPresent());
    assertTrue(this.engine.getBoard().getTileAt(p2.x(), p2.y(), p2.z()).isPresent());

    // Dopo aver annullato l'unica mossa, lo storico torna vuoto
    assertFalse(this.engine.undo());
  }

  @Test
  void testStartNewGameResetsScoreAndHistory() {
    final List<Position> pair = this.engine.getHint().orElseThrow();
    this.engine.selectTile(pair.get(0).x(), pair.get(0).y(), pair.get(0).z());
    this.engine.selectTile(pair.get(1).x(), pair.get(1).y(), pair.get(1).z());

    assertEquals(100, this.engine.getScore());

    this.engine.startNewGame();

    assertEquals(0, this.engine.getScore());
    assertEquals(144, this.engine.getBoard().getRemainingTilesCount());
    assertFalse(this.engine.undo(), "Lo storico deve azzerarsi all'avvio di una nuova partita");
  }
}