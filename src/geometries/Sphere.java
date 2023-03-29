package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class that represent sphere
 *
 * @author Yoni
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor that takes sphere by -
     *
     * @param radius the radius
     * @param center the center point
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * getter for center
     *
     * @return center
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point p) { return p.subtract(this.center).normalize(); }
}
