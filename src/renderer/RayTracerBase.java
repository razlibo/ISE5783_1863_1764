package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 The RayTracerBase class serves as the base class for all ray-tracing classes.
 It holds a reference to the scene object and provides an abstract method for tracing a single ray.
 */
public abstract class RayTracerBase {
    /**
     The scene object for the ray tracing.
     */
    protected Scene scene;

    /**
     Constructs a RayTracerBase with the given scene object.
     @param scene the scene object for the ray tracing
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     Traces a single ray and returns the resulting color.
     @param ray the ray to trace
     @return the resulting color
     */
    public abstract Color traceRay(Ray ray);
}
