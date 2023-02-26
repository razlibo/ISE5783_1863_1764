package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class that represent tube
 *
 * @author Yoni
 */
public class Tube extends RadialGeometry {
    /**
     * the axis ray
     */
    protected final Ray axisRay;

    /**
     * constructor that build tube from -
     *
     * @param axisRay the axis ray
     * @param radius  the radius
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    /**
     * getter for axisRay
     *
     * @return the axis ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }
}
