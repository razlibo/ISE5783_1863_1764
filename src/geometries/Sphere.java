package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    private final Point center;

    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
