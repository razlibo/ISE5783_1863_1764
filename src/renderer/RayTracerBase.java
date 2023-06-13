package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The RayTracerBase class serves as the base class for all ray-tracing classes.
 * It holds a reference to the scene object and provides an abstract method for tracing a single ray.
 */
public abstract class RayTracerBase {
    /**
     * The scene object for the ray tracing.
     */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene object.
     *
     * @param scene the scene object for the ray tracing
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a single ray and returns the resulting color.
     *
     * @param ray the ray to trace
     * @return the resulting color
     */
    public abstract Color traceRay(Ray ray);


    /**
     * Traces multiple rays and calculates the average color of the resulting intersections.
     * @param rayList The list of rays to trace.
     * @return The average color of the intersections.
     */
    public Color traceMultipleRays(List<Ray> rayList) {
        int size = rayList.size();
        Color avgColor = Color.BLACK;
        for (Ray ray : rayList) {
            avgColor = avgColor.add(traceRay(ray));
        }
        return avgColor.scale(1.0 / size);
    }
}
