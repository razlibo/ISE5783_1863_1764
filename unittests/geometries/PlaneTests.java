package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Plane class
 *
 * @author Yoni
 */
class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}
     */
    @Test
    void testConstructor(){
        // =============== Boundary Values Tests ==================
        // TC10: The first two points is the same
        assertThrows(IllegalArgumentException.class,()-> new Plane(new Point(0,0,0),new Point(0,0,0),new Point(0,0,1)),"ERROR: the plane constructor accept two identical points");
        // TC11: All points on the same line
        assertThrows(IllegalArgumentException.class,()-> new Plane(new Point(0,0,1),new Point(0,0,2),new Point(0,0,3)),"ERROR: the plane constructor accept tree points on the same line");

    }
    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal
        Point[] pts = new Point[]{new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)};
        Plane plane = new Plane(pts[0], pts[1], pts[2]);
        var result = plane.getNormal();
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i <= 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Plane's normal is not orthogonal to one of the edges");

    }
}