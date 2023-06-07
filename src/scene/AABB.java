package scene;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Polygon;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AABB extends Intersectable {

    public AABB leftNode, rightNode;
    Geometries geometries;
    boolean isLeaf = false;

    static List<Function<Intersectable, Double>> axises = null;

    Polygon[] polygons;


    public AABB(Geometries geometries){
        if (axises == null) {
            axises = new ArrayList<>();
            axises.add((x) -> x.getCenterABBA().getX());
            axises.add((x) -> x.getCenterABBA().getY());
            axises.add((x) -> x.getCenterABBA().getZ());
        }
        this.geometries = geometries;
        findMinMaxCenter();
        Point[] corners = new Point[8];

        // Calculate the coordinates for each corner
        corners[0] = new Point(geometries.getMinABBA().getX(), geometries.getMinABBA().getY(), geometries.getMinABBA().getZ());
        corners[1] = new Point(geometries.getMaxABBA().getX(), geometries.getMinABBA().getY(), geometries.getMinABBA().getZ());
        corners[2] = new Point(geometries.getMinABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMinABBA().getZ());
        corners[3] = new Point(geometries.getMaxABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMinABBA().getZ());
        corners[4] = new Point(geometries.getMinABBA().getX(), geometries.getMinABBA().getY(), geometries.getMaxABBA().getZ());
        corners[5] = new Point(geometries.getMaxABBA().getX(), geometries.getMinABBA().getY(), geometries.getMaxABBA().getZ());
        corners[6] = new Point(geometries.getMinABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMaxABBA().getZ());
        corners[7] = new Point(geometries.getMaxABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMaxABBA().getZ());

        polygons = new Polygon[6];

        // Define the polygons using the corner points
        // Each polygon is defined by four corners in a counterclockwise order
        polygons[0] = new Polygon(corners[0], corners[1], corners[3], corners[2]);
        polygons[1] = new Polygon(corners[1], corners[5], corners[7], corners[3]);
        polygons[2] = new Polygon(corners[5], corners[4], corners[6], corners[7]);
        polygons[3] = new Polygon(corners[4], corners[0], corners[2], corners[6]);
        polygons[4] = new Polygon(corners[2], corners[3], corners[7], corners[6]);
        polygons[5] = new Polygon(corners[4], corners[5], corners[1], corners[0]);



        if(geometries.bodies.size() <= 2){
            isLeaf = true;
            return;
        }
        buildTree();

    }

    public boolean intersect(Ray ray, double maxDis){

        for(Polygon p:polygons){
            var l = p.findGeoIntersections(ray, maxDis);
            if(l != null) return true;
        }
        return false;




//        var xPoint = ray.getP0().getX();
//        var dir = ray.getDir();
//
//        var minPoint = geometries.getMinABBA();
//        var maxPoint = geometries.getMaxABBA();
//        double tmin = (minPoint.getX() - xPoint) * dir.getX();
//        double tmax = (maxPoint.getX() - xPoint) * dir.getX();
//
//        double tMini = Math.min(tmin,tmax);
//        double tMax = Math.max(tmin,tmax);
//
//        var yPoint = ray.getP0().getY();
//        double tymin = (minPoint.getY() - yPoint) * dir.getY();
//        double tymax = (maxPoint.getY() - yPoint) * dir.getY();
//
//        double xyMini = Math.min(tMini,Math.min(tymin,tymax));
//        double xyMax =  Math.max(tMax,Math.max(tymin,tymax));
//
//        var zPoint = ray.getP0().getZ();
//        double tzmin = (minPoint.getZ() - zPoint) * dir.getZ();
//        double tzmax = (maxPoint.getZ() - zPoint) * dir.getZ();
//
//        double xyzMini = Math.min(xyMini,Math.min(tzmin,tzmax));
//        double xyzMax =  Math.max(xyMax,Math.max(tzmin,tzmax));
//        return xyzMax >= xyzMini && xyzMax <= maxDis;
    }


    public AABB setGeometries(Geometries geometries){
        this.geometries = geometries;
        return this;
    }
    public AABB buildTree(){
        double[] arr = decideHowToSplitBySAH();
        List<List<Intersectable>> split = splitByAxis(axises.get((int) arr[0]), arr[1]);
        leftNode = new AABB(new Geometries().add(split.get(0)));
        rightNode = new AABB(new Geometries().add(split.get(1)));
        return this;
    }

    double decideHowToSplit() {
        Point minAABB = geometries.getMinABBA(), maxAABB = geometries.getMaxABBA();
        double xLength = maxAABB.getX() - minAABB.getX();
        double yLength = maxAABB.getY() - minAABB.getY();
        double zLength = maxAABB.getZ() - minAABB.getZ();

        if(xLength > yLength && xLength > zLength) {
            return 1;
        }else if(yLength > xLength && yLength > zLength){
            return 2;
        }else{
            return 3;
        }
    }

    List<List<Intersectable>> splitByAxis(Function<Intersectable, Double> func, double pos){
        List<List<Intersectable>> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());

        for(Intersectable i:geometries.bodies){
            if(func.apply(i) < pos){
                list.get(0).add(i);
            }else{
                list.get(1).add(i);
            }
        }
        return list;
    }


    @Override
    public void findMinMaxCenter() {
        geometries.findMinMaxCenter();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDis) {
        if(!intersect(ray, maxDis)){
            return null;
        }
        if(isLeaf) return geometries.findGeoIntersections(ray, maxDis);

        var leftIntersections = leftNode.findGeoIntersectionsHelper(ray, maxDis);
        var rightIntersections = rightNode.findGeoIntersectionsHelper(ray, maxDis);
        if(leftIntersections == null && rightIntersections == null){
            return null;
        }
        return leftIntersections == null ? rightIntersections : leftIntersections;
    }

    double evaluateSAH(Function<Intersectable, Double> func, double pos){
        Geometries left = new Geometries(), right = new Geometries();
        for(Intersectable i:geometries.bodies){
            if(func.apply(i) < pos){
                left.add(i);
            }else{
                right.add(i);
            }
        }
        double cost = left.bodies.size() * AABBArea(left) + right.bodies.size() * AABBArea(right);
        return cost > 0 ? cost : Double.POSITIVE_INFINITY;
    }

    double AABBArea(Geometries geometries){
        if (geometries.bodies.size() == 0)
            return Double.POSITIVE_INFINITY;
        geometries.findMinMaxCenter();
        Point extent = geometries.getMaxABBA().subtract(geometries.getMinABBA());
        return extent.getX() * extent.getY() + extent.getY() * extent.getZ() + extent.getZ() * extent.getX();
    }

    double[] decideHowToSplitBySAH(){


        int bestAxis = -1;
        double bestPos = 0, bestCost = Double.POSITIVE_INFINITY;
        for( int axis = 0; axis < 3; axis++ ){
            for(Intersectable i:geometries.bodies){
                double candidatePos = axises.get(axis).apply(i);
                double cost = evaluateSAH(axises.get(axis), candidatePos);
                if (cost < bestCost) {
                    bestPos = candidatePos;
                    bestAxis = axis;
                    bestCost = cost;
                }
            }
        }
        return new double[]{bestAxis, bestPos};
    }
}