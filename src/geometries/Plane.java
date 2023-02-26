package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    private final Point p0;
    private final Vector normal;

    public Plane(Point p1,Point p2,Point p3){
        p0 = p1;
        normal = null;
    }

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    public Point getP0() {
        return p0;
    }

    public Vector getNormal(){
        return normal;
    }
}
