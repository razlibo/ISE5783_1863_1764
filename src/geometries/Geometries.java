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
    public void findMinMaxCenter() {
        minAABB = new Point(0,0,0);
        maxAABB = new Point(0,0,0);
        for(Intersectable i:bodies){
            i.findMinMaxCenter();
            minAABB = Point.createMinPoint(minAABB, i.minAABB);
            maxAABB = Point.createMinPoint(maxAABB, i.maxAABB);
        }
        this.centerAABB = new Point((maxAABB.getX() + minAABB.getX())/2,(maxAABB.getY() + minAABB.getY())/2,(maxAABB.getZ() + minAABB.getZ())/2 );

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
