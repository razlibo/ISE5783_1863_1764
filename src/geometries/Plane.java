package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class that represent a plane
 *
 * @author Yoni
 */
public class Plane implements Geometry {
    private final Point p0;
    private final Vector normal;

    /**
     * constructor that take 3 points and calculate the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        p0 = p1;
        normal = p1.subtract(p2).crossProduct(p2.subtract(p3)).normalize();
    }

    /**
     * constructor that take existing plane by -
     *
     * @param p0     the point in the plane
     * @param normal the normal vector to the plane
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * getter for p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter for normal
     *
     * @return the normal
     */
    public Vector getNormal() {
        return normal;
    }
}
