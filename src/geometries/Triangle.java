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

    /*
        @Override
    public void findMinMaxCenter() {

    }

     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
//        Point maxBbox = new Point(Double.NEGATIVE_INFINITY), minBbox = new Point(Double.POSITIVE_INFINITY);
//        for (var i = 1; i < vertices.size(); ++i) {
//            minBbox = Point.createMinPoint(minBbox, vertices.get(i));
//            maxBbox = Point.createMaxPoint(maxBbox, vertices.get(i));
//        }
//
//        bbox = new AABB(minBbox, maxBbox);
        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;

        Point minPoint = new Point(0, 0, 0);
        Point maxPoint = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
        for (Point p : vertices) {
            minPoint = Point.createMinPoint(minPoint, p);
            maxPoint = Point.createMaxPoint(maxPoint, p);
            sumX += p.getX();
            sumY += p.getY();
            sumZ += p.getZ();
        }

        // Create and return the center point
        var centerAABB = new Point(sumX / 3, sumY / 3, sumZ / 3);
        bbox = new AABB(minPoint, maxPoint, centerAABB);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        List<GeoPoint> points = plane.findGeoIntersections(ray, maxDis);
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

//    @Override
//    public boolean isIntersectAABB(AABB bbox) {
//        return super.isIntersectAABB(bbox);
//    }
}
