package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface for geometric object
 *
 * @author Yoni
 */
public interface Geometry extends Intersectable{
    /**
     * @param p the point that we want to get the normal from
     * @return the normal vector
     */
    Vector getNormal(Point p);
}
