package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosesPointTest() {
        List<Point> list = List.of(new Point(1,1,1),new Point(2,2,2),new Point(3,3,3));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Middle point is the closest
        assertEquals(new Point(2,2,2),new Ray(new Point(1.7,1.7,1.7),new Vector(1,1,1)).findClosesPoint(list),"Wrong point");


        // =============== Boundary Values Tests ==================

        // TC10: Empty list
        assertNull(new Ray(new Point(1.7,1.7,1.7),new Vector(1,1,1)).findClosesPoint(new LinkedList<>()));

        // TC11: First point is the closest
        assertEquals(new Point(1,1,1),new Ray(new Point(1.2,1.2,1.2),new Vector(1,1,1)).findClosesPoint(list),"Wrong point");

        // TC12: Last point is the closest
        assertEquals(new Point(3,3,3),new Ray(new Point(2.8,2.8,2.8),new Vector(1,1,1)).findClosesPoint(list),"Wrong point");
    }
}