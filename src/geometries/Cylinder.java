package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    private final double height;

    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    public double getHeight() {
        return height;
    }
}
