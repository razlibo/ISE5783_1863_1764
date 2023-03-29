package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 *
 * @author Raz Leibovitch
 */

class PointTests {

    /**
     * Test method for {@link primitives.Point#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check Point + vector work correctly
        assertEquals(new Point(0, 0, 0), new Point(1, 2, 3).add(new Vector(-1, -2, -3)), "ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#subtract(Point)}
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: check Point - Point work correctly
        assertEquals(new Vector(1, 1, 1), new Point(2, 3, 4).subtract(new Point(1, 2, 3)), "ERROR: Point - Point does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}
     */
    @Test
    void testDistanceSquared() {
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}
     */
    @Test
    void testDistance() {
    }
}