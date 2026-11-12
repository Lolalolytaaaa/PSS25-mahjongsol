package it.unibo.mahjongsol.model;

import it.unibo.mahjongsol.api.LayoutGenerator;
import it.unibo.mahjongsol.api.Position;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassicTurtleLayoutTest {

    @Test
    void testTurtleLayoutGenerates144UniqueTiles() {
        final LayoutGenerator generator = new ClassicTurtleLayout();
        final List<Position> positions = generator.generateLayout();

        // Verifica che siano esattamente 144 tessere (numero pari standard)
        assertEquals(144, positions.size());

        // Verifica che non ci siano coordinate duplicate sovrapposte sullo stesso
        // livello
        final Set<Position> uniquePositions = new HashSet<>(positions);
        assertEquals(144, uniquePositions.size());

        // Verifica che la tessera in cima (livello 4) sia presente
        assertTrue(positions.contains(new Position(13, 7, 4)));
    }
}