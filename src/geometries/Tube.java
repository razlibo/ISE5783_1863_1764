package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry{
    protected final Ray axisRay;

    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    public Ray getAxisRay() {
        return axisRay;
    }
}
