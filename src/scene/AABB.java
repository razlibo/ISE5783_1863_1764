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
//        Point[] corners = new Point[8];
//
//        // Calculate the coordinates for each corner
//        corners[0] = new Point(geometries.getMinABBA().getX(), geometries.getMinABBA().getY(), geometries.getMinABBA().getZ());
//        corners[1] = new Point(geometries.getMaxABBA().getX(), geometries.getMinABBA().getY(), geometries.getMinABBA().getZ());
//        corners[2] = new Point(geometries.getMinABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMinABBA().getZ());
//        corners[3] = new Point(geometries.getMaxABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMinABBA().getZ());
//        corners[4] = new Point(geometries.getMinABBA().getX(), geometries.getMinABBA().getY(), geometries.getMaxABBA().getZ());
//        corners[5] = new Point(geometries.getMaxABBA().getX(), geometries.getMinABBA().getY(), geometries.getMaxABBA().getZ());
//        corners[6] = new Point(geometries.getMinABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMaxABBA().getZ());
//        corners[7] = new Point(geometries.getMaxABBA().getX(), geometries.getMaxABBA().getY(), geometries.getMaxABBA().getZ());
//
//        polygons = new Polygon[6];
//
//        // Define the polygons using the corner points
//        // Each polygon is defined by four corners in a counterclockwise order
//        polygons[0] = new Polygon(corners[0], corners[1], corners[3], corners[2]);
//        polygons[1] = new Polygon(corners[1], corners[5], corners[7], corners[3]);
//        polygons[2] = new Polygon(corners[5], corners[4], corners[6], corners[7]);
//        polygons[3] = new Polygon(corners[4], corners[0], corners[2], corners[6]);
//        polygons[4] = new Polygon(corners[2], corners[3], corners[7], corners[6]);
//        polygons[5] = new Polygon(corners[4], corners[5], corners[1], corners[0]);



        if(geometries.bodies.size() <= 2){
            isLeaf = true;
            return;
        }
        buildTree();

    }

    public boolean intersect(Ray ray, double maxDis){

//        for(Polygon p:polygons){
//            var l = p.findGeoIntersections(ray, maxDis);
//            if(l != null) return true;
//        }
//        return false;
        var dir = ray.getDir();
        var vP0 = ray.getP0();
        var invdir = new Vector(1/dir.getX(),1/dir.getY(),1/dir.getZ());
        int[] sign = {invdir.getX() < 0 ? 1 : 0,invdir.getY() < 0 ? 1 : 0, invdir.getZ() < 0 ? 1 : 0 };
        Point[] bounds = {geometries.getMinABBA(),geometries.getMaxABBA()};
        double tmin, tmax, tymin, tymax, tzmin, tzmax;
        tmin = (bounds[sign[0]].getX() - vP0.getX()) * invdir.getX();
        tmax = (bounds[1-sign[0]].getX() - vP0.getX()) * invdir.getX();
        tymin = (bounds[sign[1]].getY() - vP0.getY()) * invdir.getY();
        tymax = (bounds[1-sign[1]].getY() - vP0.getY()) * invdir.getY();
        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;
        if (tymax < tmax)
            tmax = tymax;
        tzmin = (bounds[sign[2]].getZ() - vP0.getZ()) * invdir.getZ();
        tzmax = (bounds[1-sign[2]].getZ() - vP0.getZ()) * invdir.getZ();

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;
        if (tzmax < tmax)
            tmax = tzmax;

        return tmax <= maxDis;
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