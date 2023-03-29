package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Sphere class
 *
 * @author Yoni
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test the normal
        Sphere spr = new Sphere(1, new Point(0,0,0));
        var result = spr.getNormal(new Point(0,0,1));
        assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
        assertEquals(1, result.dotProduct(new Point(0,0,1).subtract(new Point(0,0,0))), 0.00000001, "Sphere's normal is not in correct direction");
    }
}