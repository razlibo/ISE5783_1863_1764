package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class will present Ray object
 *
 * @author Raz
 */
public class Ray {

    /**
     * Delta for moving the ray
     */
    private static final double DELTA = 0.1;
    /**
     * Vector in the space
     */
    private final Vector dir;

    /**
     * Point stating point
     */
    private final Point p0;

    /**
     * constructor for Ray
     *
     * @param v Vector value
     * @param p point value
     */

    public Ray(Point p, Vector v) {
        this.dir = v.normalize();
        this.p0 = p;
    }

    /**
     * get the point
     * @param t for scale in dir
     * @return P0 + dir scale in t
     */
    public Point getPoint(double t){
        return this.p0.add(this.getDir().scale(t));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }

    @Override
    public String toString() {
        return this.p0.toString() + this.dir.toString();
    }

    public Vector getDir() {
        return dir;
    }

    public Point getP0() {
        return p0;
    }

    /**
     Finds the closest point in a list of points to p0.

     @param points A list of points to search for the closest point.

     @return The closest point in the list to p0.
     */
    public Point findClosesPoint(List<Point> points){
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        if (geoPoints.isEmpty()) return null;
        GeoPoint min = geoPoints.get(0);
        double minDistance = p0.distance(geoPoints.get(0).point);

        for (GeoPoint geoPoint : geoPoints){
            double distance = p0.distance(geoPoint.point);
            if (distance < minDistance){
                min = geoPoint;
                minDistance = distance;
            }
        }
        return min;
    }

    /**
     * Construct ray that is moved by the delta in the normal/-normal direction
     * @param head the ray head
     * @param dir the direction
     * @param n the normal
     */
    public Ray(Point head, Vector dir, Vector n){
        double nl = alignZero(n.dotProduct(dir));
        this.p0 = nl == 0 ? head : head.add(n.scale(nl < 0 ? -DELTA : DELTA));
        this.dir = dir.normalize();
    }
}
