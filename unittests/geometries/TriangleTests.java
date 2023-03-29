package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for the normal
        Point[] pts = new Point[]{new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle triangle = new Triangle(pts[0], pts[1], pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> triangle.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = triangle.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i <= 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Triangle's normal is not orthogonal to one of the edges");
    }
}