package primitives;

import geometries.Intersectable;

import java.util.List;

/**
 * This class will present Ray object
 *
 * @author Raz
 */
public class Ray {
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
        return this.p0.add(this.getDir().Scale(t));
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
        if (points.isEmpty()) return null;
        Point min = points.get(0);
        double minDistance = p0.distance(points.get(0));

        for (Point point : points){
            double distance = p0.distance(point);
            if (distance < minDistance){
                min = point;
                minDistance = distance;
            }
        }
        return min;
    }
}
