package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable{

    private LinkedList<Intersectable> bodies;


    /**
     * empty constructor for Geometries
     */
    public Geometries(){
        bodies = new LinkedList<>();
    }

    /**
     * constructor for Geometries
     */
    public Geometries(Intersectable... geometries){
        this();

        add(geometries);
    }

    /***
     * add geometries to te list
     * @param geometries to add
     */
    public void add(Intersectable... geometries){
        for (Intersectable geometry : geometries) {
            bodies.add(geometry);
        }
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        LinkedList<GeoPoint> list = null;
        for (Intersectable body : bodies) {
            var temp = body.findGeoIntersections(ray, maxDis);
            if (temp != null){
                if (list == null) list = new LinkedList<>();
                list.addAll(temp);
            }
        }
        return list;
    }
}
