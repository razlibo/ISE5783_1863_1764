package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

/**
 * class that represent a plane
 *
 * @author Yoni
 */
public class Plane extends Geometry {
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

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        if(ray.getP0().equals(this.p0)) return null;
        double nv = this.normal.dotProduct(ray.getDir());
        if(isZero(nv))
            return null;

        double t = alignZero(this.normal.dotProduct(this.p0.subtract(ray.getP0()))/ nv);
        List<GeoPoint> geoPoints = null;
        if(t > 0 && alignZero(t - maxDis) <= 0){
            geoPoints = List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        return geoPoints;
    }

    @Override
    public boolean isIntersectAABB(AABB bbox) {
        Double3 c = bbox.max.add(bbox.min).scale(0.5);
        Double3 e = bbox.max.subtract(c);
        double r = e.getD1()*Math.abs(this.normal.getX()) + e.getD2()*Math.abs(this.normal.getY()) +e.getD3()*Math.abs(this.normal.getZ());
        double s = this.normal.dotProduct(new Vector(c)) - this.normal.dotProduct(new Vector(p0.getX(), p0.getY(), p0.getZ()));

        return Math.abs( s ) <= r;
    }
}
