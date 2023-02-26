package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class that represent Cylinder
 *
 * @author Yoni
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * constructor that take all 3 fields
     *
     * @param radius  the radius
     * @param axisRay the axis ray
     * @param height  the height
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    /**
     * getter for height
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }
}
