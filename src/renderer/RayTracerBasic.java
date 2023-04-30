package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene){
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var points = scene.geometries.findIntersections(ray);
        return points == null ? scene.background : calcColor(ray.findClosesPoint(points));

    }


    /**
     * Calculating the color in a given point
     *
     * @param point the point to calculate
     * @return the color in the point
     */
    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
}
