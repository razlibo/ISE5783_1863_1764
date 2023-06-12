package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        Point maxBbox = new Point(Double.NEGATIVE_INFINITY), minBbox = new Point(Double.POSITIVE_INFINITY);
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
            minBbox = Point.createMinPoint(minBbox, vertices[i]);
            maxBbox = Point.createMaxPoint(maxBbox, vertices[i]);
        }
//        bbox = new AABB(minBbox, maxBbox);
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {

        Vector[] vectors = new Vector[vertices.size()], dotProductVectors = new Vector[vertices.size()];

        List<GeoPoint> points = plane.findGeoIntersections(ray, maxDis);

        if(points == null) return null;

        for (int i = 0; i < vectors.length; i++) {
            vectors[i] = this.vertices.get(i).subtract(ray.getP0());
        }

        for (int i = 0; i < vectors.length; i++) {
            dotProductVectors[i] = vectors[i].crossProduct(vectors[(i + 1) % vectors.length]);
        }

        for (int i = 0; i < vectors.length; i++) {
            if (alignZero(ray.getDir().dotProduct(dotProductVectors[i])) <= 0) return null;
        }

        return points.stream().map(gp -> new GeoPoint(this, gp.point)).toList();
    }

//    @Override
//    public boolean isIntersectAABB(AABB bbox) {
//        if (axisSeparation(bbox.min.getD1(), bbox.min.getD2(), bbox.min.getD3(), bbox.min.getD1(), bbox.max.getD2(), bbox.max.getD3()))
//            return false;
//        if (axisSeparation(bbox.min.getD1(), bbox.max.getD2(), bbox.min.getD3(), bbox.max.getD1(), bbox.max.getD2(), bbox.max.getD3()))
//            return false;
//        if (axisSeparation(bbox.max.getD1(), bbox.max.getD2(), bbox.max.getD3(), bbox.max.getD1(), bbox.min.getD2(), bbox.min.getD3()))
//            return false;
//        if (axisSeparation(bbox.max.getD1(), bbox.min.getD2(), bbox.max.getD3(), bbox.min.getD1(), bbox.min.getD2(), bbox.min.getD3()))
//            return false;
//        if (axisSeparation(bbox.min.getD1(), bbox.min.getD2(), bbox.min.getD3(), bbox.min.getD1(), bbox.min.getD2(), bbox.max.getD3()))
//            return false;
//        if (axisSeparation(bbox.min.getD1(), bbox.max.getD2(), bbox.min.getD3(), bbox.min.getD1(), bbox.max.getD2(), bbox.max.getD3()))
//            return false;
//        if (axisSeparation(bbox.max.getD1(), bbox.max.getD2(), bbox.min.getD3(), bbox.max.getD1(), bbox.max.getD2(), bbox.max.getD3()))
//            return false;
//        if (axisSeparation(bbox.max.getD1(), bbox.min.getD2(), bbox.min.getD3(), bbox.max.getD1(), bbox.min.getD2(), bbox.max.getD3()))
//            return false;
//
//        // Test polygon edges
//        for (int i = 0; i < this.vertices.size(); i++) {
//            Point p1 = this.vertices.get(i);
//            Point p2 = this.vertices.get((i + 1) % this.vertices.size());
//            if (axisSeparation(p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ()))
//                return false;
//        }
//
//        return true; // Intersection detected
//    }
//
//
//    // Helper function to check axis separation
//    private boolean axisSeparation(double x1, double y1, double z1, double x2, double y2, double z2) {
//        double axisX = y1 * z2 - z1 * y2;
//        double axisY = z1 * x2 - x1 * z2;
//        double axisZ = x1 * y2 - y1 * x2;
//
//        double minA = Double.POSITIVE_INFINITY;
//        double maxA = Double.NEGATIVE_INFINITY;
//        double minB = Double.POSITIVE_INFINITY;
//        double maxB = Double.NEGATIVE_INFINITY;
//
//        for (Point p : this.vertices) {
//            double projected = axisX * p.getX() + axisY * p.getY() + axisZ * p.getZ();
//            minA = Math.min(minA, projected);
//            maxA = Math.max(maxA, projected);
//        }
//
//        double projected = axisX * x1 + axisY * y1 + axisZ * z1;
//        minB = Math.min(minB, projected);
//        maxB = Math.max(maxB, projected);
//
//        projected = axisX * x2 + axisY * y2 + axisZ * z2;
//        minB = Math.min(minB, projected);
//        maxB = Math.max(maxB, projected);
//
//        return maxA < minB || maxB < minA;
//    }

}
