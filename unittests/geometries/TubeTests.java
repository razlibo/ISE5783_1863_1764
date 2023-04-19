package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Tube class
 *
 * @author Yoni
 */

class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}
     */
    @Test
    void testGetNormal() {

        Tube tube = new Tube(1, new Ray(new Point(0,0,0), new Vector(0,0,1)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: test the normal
        var result = tube.getNormal(new Point(1,0,3));
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");
        assertEquals(1, result.dotProduct(new Point(1,0,0).subtract(new Point(0,0,0))), 0.00000001, "Tube's normal is not in correct direction");

        // =============== Boundary Values Tests ==================
        // TC10: test when p-p0 orthogonal to v
        result = tube.getNormal(new Point(1,0,0));
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");
        assertEquals(1, result.dotProduct(new Point(1,0,0).subtract(new Point(0,0,0))), 0.00000001, "Tube's normal is not in correct direction");

    }
}