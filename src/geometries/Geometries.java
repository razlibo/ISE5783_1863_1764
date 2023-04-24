package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    private LinkedList<Intersectable> bodies;

    public Geometries(){
        bodies = new LinkedList<>();
    }

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
    public List<Point> findIntersections(Ray ray) {
        LinkedList<Point> list = null;
        for (Intersectable body : bodies) {
            var temp = body.findIntersections(ray);
            if (temp != null){
                if (list == null) list = new LinkedList<>();
                list.addAll(temp);
            }
        }
        return list;
    }
}
