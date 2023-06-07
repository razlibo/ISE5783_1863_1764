package scene;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Plane;
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

    public AABB(Geometries geometries){
        this.geometries = geometries;
        findMinMaxCenter();
        if(geometries.bodies.size() <= 2){
            isLeaf = true;
            return;
        }
        buildTree();

    }

    public boolean intersect(Ray ray, double maxDis){

        var xPoint = ray.getP0().getX();
        var dir = ray.getDir();

        var minPoint = geometries.getMinABBA();
        var maxPoint = geometries.getMaxABBA();
        double tmin = (minPoint.getX() - xPoint) * dir.getX();
        double tmax = (maxPoint.getX() - xPoint) * dir.getX();

        double tMini = Math.min(tmin,tmax);
        double tMax = Math.max(tmin,tmax);

        var yPoint = ray.getP0().getY();
        double tymin = (minPoint.getY() - yPoint) * dir.getY();
        double tymax = (maxPoint.getY() - yPoint) * dir.getY();

        double xyMini = Math.min(tMini,Math.min(tymin,tymax));
        double xyMax =  Math.max(tMax,Math.max(tymin,tymax));

        var zPoint = ray.getP0().getZ();
        double tzmin = (minPoint.getZ() - zPoint) * dir.getZ();
        double tzmax = (maxPoint.getZ() - zPoint) * dir.getZ();

        double xyzMini = Math.min(xyMini,Math.min(tzmin,tzmax));
        double xyzMax =  Math.max(xyMax,Math.max(tzmin,tzmax));
        return xyzMax >= xyMini;
    }


    public AABB setGeometries(Geometries geometries){
        this.geometries = geometries;
        return this;
    }
    public AABB buildTree(){
        List<List<Intersectable>> split = splitByAxis(decideHowToSplit());
        leftNode = new AABB(new Geometries().add(split.get(0)));
        rightNode = new AABB(new Geometries().add(split.get(1)));
        return this;
    }

    Function<Intersectable, Double> decideHowToSplit() {
        Point minAABB = geometries.getMinABBA(), maxAABB = geometries.getMaxABBA();
        double xLength = maxAABB.getX() - minAABB.getX();
        double yLength = maxAABB.getY() - minAABB.getY();
        double zLength = maxAABB.getZ() - minAABB.getZ();

        if(xLength > yLength && xLength > zLength) {
            return (x) -> x.getCenterABBA().getX();
        }else if(yLength > xLength && yLength > zLength){
            return (x) -> x.getCenterABBA().getY();
        }else{
            return (x) -> x.getCenterABBA().getZ();
        }
    }

    List<List<Intersectable>> splitByAxis(Function<Intersectable, Double> func){
        List<List<Intersectable>> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());

        var pos = func.apply(geometries);
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

        var intersections = leftNode.findGeoIntersectionsHelper(ray, maxDis);
        if(intersections == null){
            intersections = new ArrayList<>();
        }
        intersections.addAll(rightNode.findGeoIntersectionsHelper(ray, maxDis));
        return intersections;
    }
}
