package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        Double3 min, max;

        public AABB(Double3 min, Double3 max){
            this.min = min;
            this.max = max;
        }
        public boolean intersect(Ray ray, double maxDis){

//        for(Polygon p:polygons){
//            var l = p.findGeoIntersections(ray, maxDis);
//            if(l != null) return true;
//        }
//        return false;
            var dir = ray.getDir();
            var vP0 = ray.getP0();
            var invdir = new Vector(1/dir.getX(),1/dir.getY(),1/dir.getZ());
            int[] sign = {invdir.getX() < 0 ? 1 : 0,invdir.getY() < 0 ? 1 : 0, invdir.getZ() < 0 ? 1 : 0 };
            Point[] bounds = {new Point(min),new Point(max)};
            double tmin, tmax, tymin, tymax, tzmin, tzmax;
            tmin = (bounds[sign[0]].getX() - vP0.getX()) * invdir.getX();
            tmax = (bounds[1-sign[0]].getX() - vP0.getX()) * invdir.getX();
            tymin = (bounds[sign[1]].getY() - vP0.getY()) * invdir.getY();
            tymax = (bounds[1-sign[1]].getY() - vP0.getY()) * invdir.getY();
            if ((tmin > tymax) || (tymin > tmax))
                return false;

            if (tymin > tmin)
                tmin = tymin;
            if (tymax < tmax)
                tmax = tymax;
            tzmin = (bounds[sign[2]].getZ() - vP0.getZ()) * invdir.getZ();
            tzmax = (bounds[1-sign[2]].getZ() - vP0.getZ()) * invdir.getZ();

            if ((tmin > tzmax) || (tzmin > tmax))
                return false;
            if (tzmax < tmax)
                tmax = tzmax;

            return tmax <= maxDis;
        }
        public double AABBArea(){
            Point extent = new Point(max.subtract(min));
            return extent.getX() * extent.getY() + extent.getY() * extent.getZ() + extent.getZ() * extent.getX();
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
    public abstract boolean isIntersectAABB(AABB bbox);
}