package geometries;

import primitives.Point;
import primitives.Ray;

import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

/**
 * class that represent triangle
 *
 * @author Yoni
 */
public class Triangle extends Polygon {
    /**
     * constructor that build the triangle from 3 points
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        List<GeoPoint> points = new Plane(vertices.get(0), vertices.get(1), vertices.get(2)).findGeoIntersections(ray, maxDis);
        if(points == null) return null;

        var v1 = this.vertices.get(0).subtract(ray.getP0());
        var v2 = this.vertices.get(1).subtract(ray.getP0());
        var v3 = this.vertices.get(2).subtract(ray.getP0());
        var n1 = v1.crossProduct(v2).normalize();
        var n2 = v2.crossProduct(v3).normalize();
        var n3 = v3.crossProduct(v1).normalize();
        var v = ray.getDir();
        if ((alignZero(v.dotProduct(n1)) > 0 && alignZero(v.dotProduct(n2)) > 0 && alignZero(v.dotProduct(n3)) > 0) ||
                (alignZero(v.dotProduct(n1)) < 0 && alignZero(v.dotProduct(n2)) < 0 && alignZero(v.dotProduct(n3)) < 0)) {
            return points.stream().map(gp -> new GeoPoint(this, gp.point)).toList();
        }
        return null;
    }
}
