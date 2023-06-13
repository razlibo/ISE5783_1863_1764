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

    /**
     * The AABB class represents an Axis-Aligned Bounding Box.
     * It is used to enclose objects and determine intersection in computer graphics and collision detection algorithms.
     */
    public static class AABB {
        Point min, max, center;

        /**
         * Flag indicating if the AABB is infinite.
         */
        boolean isInfinite = false;

        /**
         * Constant value used for expanding the AABB slightly.
         */
        private static final double DELTA = 0.1;

        /**
         * Checks if the AABB is infinite.
         *
         * @return true if the AABB is infinite, false otherwise.
         */
        public boolean isInfinite() {
            return isInfinite;
        }

        /**
         * Constructs an AABB with the given minimum point, maximum point, and center point.
         * Expands the AABB slightly using the DELTA constant.
         *
         * @param min    The minimum point of the AABB.
         * @param max    The maximum point of the AABB.
         * @param center The center point of the AABB.
         */
        public AABB(Point min, Point max, Point center) {
            min = min.add(new Vector(-DELTA, -DELTA, -DELTA));
            max = max.add(new Vector(DELTA, DELTA, DELTA));
            this.min = min;
            this.max = max;
            this.center = center;
        }

        /**
         * Constructs an AABB with the given minimum and maximum points.
         * Calculates the center point and expands the AABB using the DELTA constant.
         *
         * @param min The minimum point of the AABB.
         * @param max The maximum point of the AABB.
         */
        public AABB(Point min, Point max) {
            min = min.add(new Vector(-DELTA, -DELTA, -DELTA));
            max = max.add(new Vector(DELTA, DELTA, DELTA));
            this.min = min;
            this.max = max;
            center = new Point(
                    (min.getX() + max.getX()) / 2,
                    (min.getY() + max.getY()) / 2,
                    (min.getZ() + max.getZ()) / 2
            );
        }

        /**
         * Checks if the AABB intersects with the given Ray within the specified maximum distance.
         *
         * @param ray    The Ray to check for intersection.
         * @param maxDis The maximum distance at which intersection can occur.
         * @return true if the AABB intersects with the Ray, false otherwise.
         */
        public boolean intersect(Ray ray, double maxDis) {
            if (isInfinite) return true;
            var dir = ray.getDir();
            var vP0 = ray.getP0();
            var invdir = new Vector(1 / dir.getX(), 1 / dir.getY(), 1 / dir.getZ());
            int[] sign = {invdir.getX() < 0 ? 1 : 0, invdir.getY() < 0 ? 1 : 0, invdir.getZ() < 0 ? 1 : 0};
            Point[] bounds = {min, max};
            double px = vP0.getX(),py = vP0.getY(),pz = vP0.getZ();
            double inx = invdir.getX(), iny = invdir.getY(),inz = invdir.getZ();
            double tmin, tmax, tymin, tymax, tzmin, tzmax;
            tmin = (bounds[sign[0]].getX() - px) * inx;
            tmax = (bounds[1 - sign[0]].getX() - px) * inx;
            tymin = (bounds[sign[1]].getY() - py) * iny;
            tymax = (bounds[1 - sign[1]].getY() - py) * iny;
            if ((tmin > tymax) || (tymin > tmax))
                return false;

            if (tymin > tmin)
                tmin = tymin;
            if (tymax < tmax)
                tmax = tymax;
            tzmin = (bounds[sign[2]].getZ() - pz) * inz;
            tzmax = (bounds[1 - sign[2]].getZ() - pz) * inz;

            if ((tmin > tzmax) || (tzmin > tmax))
                return false;
            if (tzmax < tmax)
                tmax = tzmax;

            return tmax < maxDis;
        }

        /**
         * Calculates the surface area of the AABB.
         *
         * @return The surface area of the AABB.
         */
        public double AABBArea() {
            Point extent = max.subtract(min);
            return extent.getX() * extent.getY() + extent.getY() * extent.getZ() + extent.getZ() * extent.getX();
        }

        /**
         * Returns the minimum point of the AABB.
         *
         * @return The minimum point of the AABB.
         */
        public Point getMin() {
            return min;
        }

        /**
         * Returns the maximum point of the AABB.
         *
         * @return The maximum point of the AABB.
         */
        public Point getMax() {
            return max;
        }

        /**
         * Returns the center point of the AABB.
         *
         * @return The center point of the AABB.
         */
        public Point getCenter() {
            return center;
        }

        /**
         * Sets the infinity flag of the AABB.
         *
         * @param isInfinite true to set the AABB as infinite, false otherwise.
         * @return The updated AABB instance.
         */
        public AABB setInfinity(boolean isInfinite) {
            this.isInfinite = isInfinite;
            return this;
        }
    }

    /**
     * The box for AABB improvement
     */

    AABB bbox;

    /**
     * find geo intersection
     * @param ray To find the intersection whit
     * @return list of points the ray intersect
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
    }

    /**
     * find geo intersection limited whit max distance
     * @param ray To find the intersection whit
     * @param maxDis To limit the intersection points
     * @return List<GeoPoint> the ray intersect
     */
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