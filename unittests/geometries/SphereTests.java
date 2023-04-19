package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");

        assertEquals(new Point(2,0,0), result.get(0), "Wrong intersection point");

        // TC04: Ray starts after the sphere (0 points)

        assertNull(sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0))), "Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 point)
        result = sphere.findIntersections(new Ray(p1, new Vector(3, 1, 0)));

        assertEquals(1, result.size(),"Wrong number of points");

        assertEquals(p2, result.get(0), "Wrong intersection point");

        // TC12: Ray starts at sphere and goes outside (0 points)

        assertNull(sphere.findIntersections(new Ray(p2, new Vector(3, 1, 0))),"Wrong number of points");

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)

        result = sphere.findIntersections(new Ray(new Point(1, -3,0), new Vector(0,1,0)));

        assertEquals(2, result.size(),"Wrong number of points");

        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));

        assertEquals(List.of(new Point(1,-1,0), new Point(1,1,0)), result, "Wrong intersection points");

        // TC14: Ray starts at sphere and goes inside (1 point)

        result = sphere.findIntersections(new Ray(new Point(1, -1,0), new Vector(0,1,0)));

        assertEquals(1, result.size(),"Wrong number of points");

        assertEquals(new Point(1,1,0), result.get(0), "Wrong intersection point");

        // TC15: Ray starts inside (1 point)

        result = sphere.findIntersections(new Ray(new Point(1, -0.5,0), new Vector(0,1,0)));

        assertEquals(1, result.size(),"Wrong number of points");

        assertEquals(new Point(1,1,0), result.get(0), "Wrong intersection point");

        // TC16: Ray starts at the center (1 point)

        result = sphere.findIntersections(new Ray(new Point(1, 0,0), new Vector(0,1,0)));

        assertEquals(1, result.size(),"Wrong number of points");

        assertEquals(new Point(1,1,0), result.get(0), "Wrong intersection point");

        // TC17: Ray starts at sphere and goes outside (0 points)

        assertNull(sphere.findIntersections(new Ray(new Point(1, 1,0), new Vector(0,1,0))), "Wrong number of points");

        // TC18: Ray starts after sphere (0 points)

        assertNull(sphere.findIntersections(new Ray(new Point(1, 2,0), new Vector(0,1,0))), "Wrong number of points");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point

        assertNull(sphere.findIntersections(new Ray(new Point(2, -3,0), new Vector(0,1,0))), "Wrong number of points");

        // TC20: Ray starts at the tangent point

        assertNull(sphere.findIntersections(new Ray(new Point(2, 0,0), new Vector(0,1,0))), "Wrong number of points");

        // TC21: Ray starts after the tangent point

        assertNull(sphere.findIntersections(new Ray(new Point(2, 1,0), new Vector(0,1,0))), "Wrong number of points");

        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line

        assertNull(sphere.findIntersections(new Ray(new Point(3, 0,0), new Vector(0,1,0))), "Wrong number of points");

    }
}