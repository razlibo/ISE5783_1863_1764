package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometry.Geometries
 *
 * @author Yoni
 */
public class GeometriesTests {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Geometries geometries = new Geometries(new Plane(new Point(4,0,0),new Point(0,4,0), new Point(0,-4,0)),new Sphere(1,new Point(0,0,4)), new Triangle(new Point(4,0,1),new Point(0,4,1), new Point(0,-4,1)));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects shapes not every one (2 bodies)

        assertEquals(2, geometries.findIntersections(new Ray(new Point(1,-1,-1), new Vector(0,0,1))).size(), "Wrong number of points");

        // =============== Boundary Values Tests ==================

        // TC11: No bodies (0 bodies)

        assertNull(new Geometries().findIntersections(new Ray(new Point(1,-1,-1), new Vector(0,0,1))), "Wrong number of points");

        // TC12: No intersection

        assertNull(geometries.findIntersections(new Ray(new Point(0,0,0.5), new Vector(1,0,0))), "Wrong number of points");

        // TC13: One shape only

        assertEquals(2, geometries.findIntersections(new Ray(new Point(0,0,2), new Vector(0,0,1))).size(), "Wrong number of points");

        // TC14: All the shapes

        assertEquals(4, geometries.findIntersections(new Ray(new Point(0.5,0,-1), new Vector(0,0,1))).size(), "Wrong number of points");
    }
}
