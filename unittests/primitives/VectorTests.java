package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Raz Leibovitch
 */
class VectorTests {

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}
     */
    @Test
    void testConstructor(){
        // =============== Boundary Values Tests ==================
        // TC10: try to create zero vector
        assertThrows(IllegalArgumentException.class, () -> new Vector(0,0,0), "ERROR: constructor allow to create zero vector");
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test correct addition
        assertEquals(new Vector(-1, -2, -3), new Vector(1, 2, 3).add(new Vector(-2, -4, -6)), "ERROR: Point + Point does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC10: test Vector + -itself
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)), "ERROR: Vector + -itself does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#Scale(double)}
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test correct scale
        assertEquals(new Vector(3,3,3), new Vector(1,1,1).Scale(3),"ERROR: Scale doesn't work properly");

        // =============== Boundary Values Tests ==================
        // TC10: Test scale by 0
        assertThrows(IllegalArgumentException.class, () -> new Vector(1,1,1).Scale(0), "ERROR: Can scale by 0");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test correct dot product length
        assertEquals(new Vector(1, 2, 3).length() * new Vector(0, 3, -2).length(), new Vector(1, 2, 3).crossProduct(new Vector(0, 3, -2)).length(), 0.000001, "ERROR: crossProduct() wrong result length");

        //TC02: test correct direction of vector from cross product (orthogonal to the 2 operands)
        assertEquals(0, new Vector(1, 2, 3).crossProduct(new Vector(0, 3, -2)).dotProduct(new Vector(1, 2, 3)), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(0, new Vector(1, 2, 3).crossProduct(new Vector(0, 3, -2)).dotProduct(new Vector(0, 3, -2)), 0.000001, "ERROR: crossProduct() result is not orthogonal to its operands");

        // =============== Boundary Values Tests ==================

        // TC10: test two parallel vectors equal to zero
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(-2, -4, -6)), "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test correct dot product
        assertEquals(-28, new Vector(1, 2, 3).dotProduct(new Vector(-2, -4, -6)), 0.000001, "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================

        // TC10: test two orthogonal vectors equal to zero
        assertEquals(0, new Vector(1, 2, 3).dotProduct(new Vector(0, 3, -2)), 0.000001, "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test correct length squared
        assertEquals(14, new Vector(1, 2, 3).lengthSquared(), 0.00001, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test correct length
        assertEquals(5, new Vector(0, 3, 4).length(), 0.00001, "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test correct normalize
        assertEquals(1, new Vector(1, 2, 3).normalize().length(), 0.000001, "ERROR: the normalized vector is not a unit vector");

        // =============== Boundary Values Tests ==================

        // TC10: test if the normalize vector is parallel to the original
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(1, 2, 3).normalize()), "ERROR: the normalized vector is not parallel to the original one");

        // TC11: test if the normalize vector is parallel in the opposite direction to the original
        assertTrue(new Vector(1, 2, 3).dotProduct(new Vector(1, 2, 3).normalize()) > 0, "ERROR: the normalized vector is opposite to the original one");
    }

    /**
     * Test method for {@link Vector#subtract(Point)} ()}
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: test correct addition
        assertEquals(new Vector(3, 6, 9), new Vector(1, 2, 3).subtract(new Vector(-2, -4, -6)), "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================

        // TC10: test Vector - itself
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).subtract(new Vector(1, 2, 3)), "ERROR: Vector - itself does not throw an exception");
    }
}