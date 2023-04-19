package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable{

    private List<Intersectable> bodies;

    public Geometries(){
        bodies = new ArrayList<>();
    }

    public Geometries(Intersectable... geometries){
        bodies = new ArrayList<>();

        add(geometries);
    }

    public void add(Intersectable... geometries){
        for (Intersectable geometry : geometries) {
            bodies.add(geometry);
        }
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        ArrayList<Point> list = null;
        for (Intersectable body : bodies) {
            var temp = body.findIntersections(ray);
            if (temp != null){
                if (list == null) list = new ArrayList<>();
                list.addAll(temp);
            }
        }
        return list;
    }
}
