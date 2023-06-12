package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;


/**

 The Intersectable class represents an abstract geometric object that can be intersected by a Ray object.

 Concrete subclasses of Intersectable must implement the findGeoIntersectionsHelper method, which returns a list of

 GeoPoint objects representing the intersection points between the object and a given Ray object.
 */
public abstract class Intersectable {

    /**
     The GeoPoint class represents a single intersection point between a Ray object and a Geometry object.
     */
    public static class GeoPoint {

        /**
         The Geometry object that was intersected
         */
        public Geometry geometry;

        /**
         The Point object representing the intersection point
         */
        public Point point;

        /**
         Constructs a new GeoPoint object with the given Geometry object and Point object.
         @param geometry the Geometry object that was intersected
         @param point the Point object representing the intersection point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint other)
                return this.geometry.equals(other.geometry) && this.point.equals(other.point);
            return false;
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    public static class AABB{
        Point min, max;

        public AABB(Point min, Point max){
            this.min = min;
            this.max = max;
        }
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDis){
        return findGeoIntersectionsHelper(ray, maxDis);
    }

    /**
     Finds the intersection points between the Intersectable object and the given Ray object.
     This method is implemented by concrete subclasses of Intersectable and returns a list of GeoPoint objects
     representing the intersection points between the object and the Ray object.
     @param ray the Ray object to intersect with the Intersectable object
     @param maxDis the max distance that if the intersection distance from the ray origin greater then this we ignore it
     @return a list of GeoPoint objects representing the intersection points between the two objects, or null if no
     intersection points were found
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis);

    /**
     Finds the intersection points between the Intersectable object and the given Ray object.
     This method calls findGeoIntersections to get a list of GeoPoint objects, then maps each GeoPoint to its
     Point attribute and returns a list of those Points.
     @param ray the Ray object to intersect with the Intersectable object
     @return a list of Point objects representing the intersection points between the two objects, or null if no
     intersection points were found
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
}