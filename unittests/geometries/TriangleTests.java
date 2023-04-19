package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
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

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(4,0,0), new Point(0,4,0), new Point(0,-4,0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray goes inside the triangle
        var result = triangle.findIntersections(new Ray(new Point(1,-1,-1), new Vector(0,0,1)));

        assertEquals(1, result.size(), "wrong number of points");

        assertEquals(new Point(1,-1,0),result.get(0), "wrong intersection point");

        // TC02: Ray goes outside the triangle against an edge

        assertNull(triangle.findIntersections(new Ray(new Point(-1,0,-1), new Vector(0,0,1))), "wrong number of points");

        // TC02: Ray goes outside the triangle against an edge

        assertNull(triangle.findIntersections(new Ray(new Point(-1,0,-1), new Vector(0,0,1))), "wrong number of points");

        // TC03: Ray goes outside the triangle against a vertex

        assertNull(triangle.findIntersections(new Ray(new Point(-1,6,-1), new Vector(0,0,1))), "wrong number of points");

        // =============== Boundary Values Tests ==================
        // TC 11: Ray goes on edge

        assertNull(triangle.findIntersections(new Ray(new Point(0,2,-1), new Vector(0,0,1))), "wrong number of points");

        // TC 12: Ray goes on vertex

        assertNull(triangle.findIntersections(new Ray(new Point(4,0,-1), new Vector(0,0,1))), "wrong number of points");

        // TC 11: Ray goes on edge's continuation

        assertNull(triangle.findIntersections(new Ray(new Point(0,6,-1), new Vector(0,0,1))), "wrong number of points");

    }

}