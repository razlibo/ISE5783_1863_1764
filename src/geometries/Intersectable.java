package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * interface for intersectable object
 *
 * @author Raz Leibovitch
 */
public abstract class Intersectable {

    /**
     * Function to find intersections between ray and geometry body
     * @param ray The ray to find intersections
     * @return list of points of intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }


    /**
     * A class representing a geographic point, with a corresponding geometry and point.
     */
    public static class GeoPoint {

        /**
         * The geometry associated with this point.
         */
        public Geometry geometry;

        /**
         * The point coordinates.
         */
        public Point point;

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

    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    //TODO implement in subclasses
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}