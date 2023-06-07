package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class that represent tube
 *
 * @author Yoni
 */
public class Tube extends RadialGeometry {
    /**
     * the axis ray
     */
    protected final Ray axisRay;

    /**
     * constructor that build tube from -
     *
     * @param axisRay the axis ray
     * @param radius  the radius
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        if(isZero(p.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()))){
            return p.subtract(axisRay.getP0()).normalize();
        }
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        Point O = axisRay.getP0().add(axisRay.getDir().scale(t));
        return p.subtract(O).normalize();
    }

    /**
     * getter for axisRay
     *
     * @return the axis ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }


    @Override
    public void findMinMaxCenter() {}

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        return null;
    }
}
