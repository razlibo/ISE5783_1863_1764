package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene){
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray);

    }


    /**
     * Calculating the color in a given point
     *
     * @param geoPoint the point to calculate
     * @return the color in the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray){
        return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint,ray));
    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray){
        var color = geoPoint.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));

        if(nv == 0){return color;}

        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(geoPoint.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));

            }
        }
        return color;
    }

    private Double3 calcDiffusive(Material material, double nl){
        return material.kD.scale(Math.abs(nl));
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v){
       // var r = l.subtract(n.scale(2*(nl)));
        var r = n.scale(2*nl).subtract(l);
        return material.kS.scale(Math.pow(Math.max(0,v.dotProduct(r)),material.nShininess));
    }
}
