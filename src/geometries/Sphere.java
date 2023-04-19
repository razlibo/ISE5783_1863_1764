package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import static primitives.Util.*;
/**
 * class that represent sphere
 *
 * @author Yoni
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor that takes sphere by -
     *
     * @param radius the radius
     * @param center the center point
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * getter for center
     *
     * @return center
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point p) { return p.subtract(this.center).normalize(); }

    @Override
    public List<Point> findIntersections(Ray ray) {
        double tm = 0, d = 0;
        if(!this.center.equals(ray.getP0())) {
            var u = this.center.subtract(ray.getP0());
            tm = ray.getDir().dotProduct(u);
            d = Math.sqrt(u.dotProduct(u) - tm*tm);
        }

        if(d >= this.radius){
            return null;
        }
        var th = Math.sqrt(this.radius* this.radius - d*d);
        var t1 = tm + th;
        var t2 = tm - th;
        List<Point> lst = new ArrayList<>();

        if(t1 > 0 && !isZero(t1)){
            lst.add(ray.getPoint(t1));
        }

        if(t2 > 0 && !isZero(t2)){
            lst.add(ray.getPoint(t2));
        }
        if(lst.isEmpty())
            return null;
        return lst;
    }
}
