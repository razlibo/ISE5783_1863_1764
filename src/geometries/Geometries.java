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

    static List<Function<Intersectable, Double>> axes = new ArrayList<>(Arrays.asList((x) -> x.bbox.center.getX(), (x) -> x.bbox.center.getY(), (x) -> x.bbox.center.getZ()));

    /**
     * empty constructor for Geometries
     */
    public Geometries(){
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
        boolean inf = bbox.isInfinite();
        for (Intersectable geometry : geometries) {
            bodies.add(geometry);
            if (geometry.bbox.isInfinite()) {
                inf = true;
            }
            maxBbox = Point.createMaxPoint(maxBbox, geometry.bbox.getMax());
            minBbox = Point.createMinPoint(minBbox, geometry.bbox.getMin());
        }
        bbox = new AABB(minBbox, maxBbox).setInfinity(inf);
        return this;
    }

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


    /**
     * Builds a Bounding Volume Hierarchy (BVH) for the geometries.
     * @return The root node of the BVH.
     */
    public Geometries BuildBVH(){
        if (bodies.size() <= 2) return this;
        double[] arr = decideHowToSplitBySAH();
        List<List<Intersectable>> split = splitByAxis(axes.get((int) arr[0]), arr[1]);
        bodies = new LinkedList<>();
        add(new Geometries().add(split.get(0)).BuildBVH(), new Geometries().add(split.get(1)).BuildBVH());
        if(split.size() == 3)add(split.get(2));

        return this;
    }

    /**
     * Evaluates the Surface Area Heuristic (SAH) cost for a given splitting position.
     * @param func The function to evaluate for each intersectable.
     * @param pos The splitting position.
     * @return The SAH cost for the given splitting position.
     */
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

    /**
     * Decides the best axis and position to split the geometries using the Surface Area Heuristic (SAH).
     * @return An array containing the best axis and position to split.
     */
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

    /**
     * Splits the geometries into three lists based on a given splitting function and position.
     * @param func The function to determine the splitting position for each intersectable.
     * @param pos The splitting position.
     * @return A list containing three lists of intersectables: left, right, and infinite.
     */
    List<List<Intersectable>> splitByAxis(Function<Intersectable, Double> func, double pos){
        List<List<Intersectable>> list = new ArrayList<>(Arrays.asList(new ArrayList<>(),new ArrayList<>()));

        for(Intersectable i:bodies){
            if(i.bbox.isInfinite()){
                if(list.size() == 2) list.add(new ArrayList<>());
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
