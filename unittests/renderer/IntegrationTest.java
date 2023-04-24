package renderer;

import geometries.Intersectable;
import static org.junit.jupiter.api.Assertions.*;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

public class IntegrationTest {
    /**
     * Testing integration of the camera constructed rays that intersect with a sphere
     */
    @Test
    public void sphereIntegrationTest(){
        Camera camera;
        // TC01: Testing when only the center ray intersect
        camera = new Camera(new Point(0,0,0),new Vector(0,0,-1), new Vector(0,1,0));
        camera.setVPSize(3,3).setVPDistance(1);
        assertEquals(2, findAllIntersections(camera, new Sphere(1, new Point(0,0,-3))), "Wrong number of intersections");

        // TC02: Testing when the VP is inside the sphere
        camera = new Camera(new Point(0,0,0.5),new Vector(0,0,-1), new Vector(0,1,0));
        camera.setVPSize(3,3).setVPDistance(1);
        assertEquals(18, findAllIntersections(camera, new Sphere(2.5, new Point(0,0,-2.5))), "Wrong number of intersections");

        // TC03: Testing when some of the VP is inside the sphere
        assertEquals(10, findAllIntersections(camera, new Sphere(2, new Point(0,0,-2))), "Wrong number of intersections");

        // TC04: Testing when the VP and the camera are inside the sphere
        assertEquals(9, findAllIntersections(camera, new Sphere(4, new Point(0,0,-2))), "Wrong number of intersections");

        // TC04: Testing when the VP and the camera are after the sphere
        assertEquals(0, findAllIntersections(camera, new Sphere(0.5, new Point(0,0,1))), "Wrong number of intersections");
    }

    /**
     * Testing integration of the camera constructed rays that intersect with a plane
     */
    @Test
    public void planeIntegrationTest(){
        Camera camera = new Camera(new Point(0,0,0),new Vector(0,0,-1), new Vector(0,1,0));
        camera.setVPSize(3,3).setVPDistance(1);

        // TC01: When the plane and the VP are parallel
        assertEquals(9, findAllIntersections(camera, new Plane(new Point(0,0,-3), new Point(8,7,-3), new Point(9,1,-3))), "Wrong number of intersections");

        // TC02: When the plane and the VP are not parallel but still there are 9 intersection points
        assertEquals(9, findAllIntersections(camera, new Plane(new Point(0,0,-3), new Point(8,7,-3), new Point(9,-1,-4))), "Wrong number of intersections");

        // TC03: When the plane and the VP are not parallel but still there are 6 intersection points
        assertEquals(6, findAllIntersections(camera, new Plane(new Point(0,0,-3), new Point(8,2,-3), new Point(9,-1,-8))), "Wrong number of intersections");
    }

    /**
     * Testing integration of the camera constructed rays that intersect with a plane
     */
    @Test
    public void triangleIntegrationTest(){
        Camera camera = new Camera(new Point(0,0,0),new Vector(0,0,-1), new Vector(0,1,0));
        camera.setVPSize(3,3).setVPDistance(1);

        // TC01: One intersection point
        assertEquals(1, findAllIntersections(camera, new Triangle(new Point(0,1,-2), new Point(-1,-1,-2), new Point(1,-1,-2))), "Wrong number of intersections");

        // TC02: Two intersection point
        assertEquals(2, findAllIntersections(camera, new Triangle(new Point(0,20,-2), new Point(-1,-1,-2), new Point(1,-1,-2))), "Wrong number of intersections");


    }

    /**
     Finds all intersections between the rays cast by a camera and an intersectable object.
     This method assumes the image has a size of 3x3.
     @param camera the camera object used to cast rays
     @param intersectable the object that is being intersected by the rays
     @return the total number of intersections found
     */
    private int findAllIntersections(Camera camera, Intersectable intersectable){
        int counter = 0;
        for (int i = 0; i< 3; i++){
            for (int j = 0; j< 3; j++){
                var result = intersectable.findIntersections(camera.constructRay(3, 3, j, i));
                counter = result == null ? counter : counter + result.size();
            }
        }
        return counter;
    }
}


