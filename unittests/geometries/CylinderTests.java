package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import static org.junit.jupiter.api.Assertions.*;

/** Testing Cylinder */
class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        Cylinder cyl = new Cylinder(new Ray(new Point(0, 0, 1),
                new Vector(0, 1, 0)),1.0, 1d);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Point at a side of the cylinder
        assertEquals(new Vector(0, 0, 1), cyl.getNormal(new Point(0, 0.5, 2)),
                "Bad normal to cylinder");

        // TC02: Point at a 1st base of the cylinder
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 0, 1.5)),
                "Bad normal to lower base of cylinder");

        // TC03: Point at a 2nd base of the cylinder
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 1, 0.5)),
                "Bad normal to upper base of cylinder");

        // =============== Boundary Values Tests ==================
        //  TC11: Point at the center of 1st base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 0, 1)),
                "Bad normal to center of lower base");

        // TC12: Point at the center of 2nd base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 1, 1)),
                "Bad normal to center of upper base");

        // TC13: Point at the edge with 1st base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 0, 2)),
                "Bad normal to edge with lower base");

        // TC14: Point at the edge with 2nd base
        assertEquals(new Vector(0, 1, 0), cyl.getNormal(new Point(0, 1, 2)),
                "Bad normal to edge with upper base");
    }
}