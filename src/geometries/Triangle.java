package geometries;

import primitives.Point;

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
}
