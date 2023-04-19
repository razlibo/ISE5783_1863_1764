package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * interface for intersectable object
 *
 * @author Raz Leibovitch
 */
public interface Intersectable {

    /**
     * Function to find intersections between ray and geometry body
     * @param ray The ray to find intersections
     * @return list of points of intersections
     */
    List<Point> findIntersections(Ray ray);
}
