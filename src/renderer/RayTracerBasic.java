package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The RayTracerBasic class represents a basic implementation of a ray tracer.
 * It extends the RayTracerBase abstract class and implements the traceRay method
 * to calculate the color of a given ray by intersecting it with objects in the scene.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Delta for moving the ray
     */
    private static final double DELTA = 0.1;
    /**
     * Constructs a new RayTracerBasic object with a given scene.
     *
     * @param scene the scene to be rendered
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);

    }

    /**
     * Calculates the color of a given point by combining its emission color and local effects.
     *
     * @param geoPoint the intersection point to calculate the color for
     * @param ray      the ray that intersected with the point
     * @return the color of the point after taking emission and local effects into account
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Calculates the local effects (diffuse and specular) on a given point for each light source in the scene.
     *
     * @param geoPoint the intersection point to calculate the local effects for
     * @param ray      the ray that intersected with the point
     * @return the local effects on the point as a Color object
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        var color = geoPoint.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));

        if (nv == 0) {
            return color;
        }

        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(geoPoint,lightSource,l,n,nl)) {
                    Color iL = lightSource.getIntensity(geoPoint.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection of a material on a given point for a given light source.
     *
     * @param material the material of the object the point is on
     * @param nl       the dot product between the normal and light vectors
     * @return the diffuse reflection as a Double3 object
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }


    /**
     * Calculates the specular component of the color of a point on a surface
     *
     * @param material the material of the surface
     * @param n        the normal vector to the surface at the point
     * @param l        the direction vector from the light source to the point
     * @param nl       the dot product of the normal vector and the direction vector from the light source to the point
     * @param v        the direction vector from the camera to the point
     * @return the specular component of the color of the point
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        var r = n.scale(2 * nl).subtract(l);
        return material.kS.scale(Math.pow(Math.max(0, v.dotProduct(r)), material.nShininess));
    }



    /**
     Checks if a given {@link GeoPoint} is unshaded by a {@link LightSource}.
     @param gp The {@link GeoPoint} to check for shading.
     @param light The {@link LightSource} in the scene.
     @param l The direction vector from the point to the light source.
     @param n The surface normal vector at the point.
     @param nl The dot product between the surface normal and the light direction vector.
     @return {@code true} if the point is unshaded, {@code false} otherwise.
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        return intersections == null;
    }
}
