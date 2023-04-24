package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

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
    public Cylinder(Ray axisRay,double radius, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        Point point = axisRay.getP0().add(axisRay.getDir().Scale(height));

        if(p.equals(point) || isZero(axisRay.getDir().dotProduct(p.subtract(point))))
            return axisRay.getDir().normalize();

        if(p.equals(axisRay.getP0()) || isZero(axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()))))
            return axisRay.getDir().normalize();

        return super.getNormal(p);
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
