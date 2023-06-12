package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Geometries extends Intersectable{

    public LinkedList<Intersectable> bodies;

    static List<Function<Intersectable, Double>> axes = null;

    /**
     * empty constructor for Geometries
     */
    public Geometries(){
        if (axes == null) {
            axes = new ArrayList<>();
            axes.add((x) -> x.bbox.center.getX());
            axes.add((x) -> x.bbox.center.getY());
            axes.add((x) -> x.bbox.center.getZ());
        }
        bodies = new LinkedList<>();
        bbox = new AABB(new Point(Double.POSITIVE_INFINITY), new Point(Double.NEGATIVE_INFINITY));
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
        add(Arrays.stream(geometries).toList());
    }

    /***
     * add geometries to te list
     * @param geometries to add
     */
    public Geometries add(List<Intersectable> geometries){
        if (geometries.size() == 0) return this;
        Point maxBbox = bbox.getMax(), minBbox = bbox.getMin();
        for (Intersectable geometry : geometries) {
            bodies.add(geometry);
            maxBbox = Point.createMaxPoint(maxBbox, geometry.bbox.getMax());
            minBbox = Point.createMinPoint(minBbox, geometry.bbox.getMin());
        }
        bbox = new AABB(minBbox, maxBbox);
        return this;
    }

//    @Override
//    public void findMinMaxCenter() {
//        minAABB = new Point(0,0,0);
//        maxAABB = new Point(0,0,0);
//        for(Intersectable i:bodies){
//            i.findMinMaxCenter();
//            minAABB = Point.createMinPoint(minAABB, i.minAABB);
//            maxAABB = Point.createMaxPoint(maxAABB, i.maxAABB);
//        }
//        this.centerAABB = new Point((maxAABB.getX() + minAABB.getX())/2,(maxAABB.getY() + minAABB.getY())/2,(maxAABB.getZ() + minAABB.getZ())/2 );
//
//    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        LinkedList<GeoPoint> list = null;
        if(!bbox.intersect(ray, maxDis)) return null;
        for (Intersectable body : bodies) {
            var temp = body.findGeoIntersections(ray, maxDis);
            if (temp != null){
                if (list == null) list = new LinkedList<>();
                list.addAll(temp);
            }
        }
        return list;
    }

    public Geometries BuildBVH(){
        if (bodies.size() <= 2) return this;
        double[] arr = decideHowToSplitBySAH();
        List<List<Intersectable>> split = splitByAxis(axes.get((int) arr[0]), arr[1]);
        bodies = new LinkedList<>();
        add(new Geometries().add(split.get(0)).BuildBVH(), new Geometries().add(split.get(1)).BuildBVH());
        add(split.get(2));

        return this;

    }

    double evaluateSAH(Function<Intersectable, Double> func, double pos){
        Geometries left = new Geometries(), right = new Geometries();
        for(Intersectable i:bodies){
            if(i.bbox.isInfinite()){
                continue;
            }
            if(func.apply(i) < pos){
                left.add(i);
            }else{
                right.add(i);
            }
        }
        double cost = left.bodies.size() * left.bbox.AABBArea() + right.bodies.size() * right.bbox.AABBArea();
        return cost > 0 ? cost : Double.POSITIVE_INFINITY;
    }

    double[] decideHowToSplitBySAH(){


        int bestAxis = -1;
        double bestPos = 0, bestCost = Double.POSITIVE_INFINITY;
        for( int axis = 0; axis < 3; axis++ ){
            for(Intersectable i:bodies){
                double candidatePos = axes.get(axis).apply(i);
                double cost = evaluateSAH(axes.get(axis), candidatePos);
                if (cost < bestCost) {
                    bestPos = candidatePos;
                    bestAxis = axis;
                    bestCost = cost;
                }
            }
        }
        return new double[]{bestAxis, bestPos};
    }

    List<List<Intersectable>> splitByAxis(Function<Intersectable, Double> func, double pos){
        List<List<Intersectable>> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());

        for(Intersectable i:bodies){
            if(i.bbox.isInfinite()){
                list.get(2).add(i);
            }
            else if(func.apply(i) < pos){
                list.get(0).add(i);
            }else{
                list.get(1).add(i);
            }
        }
        return list;
    }
}
