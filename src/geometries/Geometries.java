package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Geometries extends Intersectable{

    private LinkedList<Intersectable> bodies;

    private AABB bbox;

    boolean isLeaf;

    Geometries leftNode, rightNode;

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
        if (bbox != null){
            if(!bbox.intersect(ray, maxDis)){
                return null;
            }

            if(!isLeaf) {
                var leftIntersections = leftNode.findGeoIntersectionsHelper(ray, maxDis);
                var rightIntersections = rightNode.findGeoIntersectionsHelper(ray, maxDis);
                if(leftIntersections == null && rightIntersections == null){
                    return null;
                }
                return leftIntersections == null ? rightIntersections : leftIntersections;
            }
        }
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

    @Override
    public boolean isIntersectAABB(AABB bbox) {
        return false;
    }
    public Geometries buildBVH(){
        return this.recBuildBVH();
    }

    private Geometries recBuildBVH(){
        if ()
    }

//    double evaluateSAH(AABB left, AABB right){
//        double leftCount = 0, rightCount = 0;
//        for(Intersectable i:bodies){
//            if(i.isIntersectAABB(left)) leftCount++;
//            if (i.isIntersectAABB(right)) rightCount++;
//        }
//        double cost = leftCount * left.AABBArea() + rightCount * right.AABBArea();
//        return cost > 0 ? cost : Double.POSITIVE_INFINITY;
//    }
//
//    double[] decideHowToSplitBySAH(){
//
//
//        int bestAxis = -1;
//        double bestPos = 0, bestCost = Double.POSITIVE_INFINITY;
//        for( int axis = 0; axis < 3; axis++ ){
//            for(Intersectable i:bodies){
//                double candidatePos = axises.get(axis).apply(i);
//                double cost = evaluateSAH(axises.get(axis), candidatePos);
//                if (cost < bestCost) {
//                    bestPos = candidatePos;
//                    bestAxis = axis;
//                    bestCost = cost;
//                }
//            }
//        }
//        return new double[]{bestAxis, bestPos};
//    }
}
