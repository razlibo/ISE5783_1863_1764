package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * interface for geometric object
 *
 * @author Yoni
 */
public abstract class Geometry extends Intersectable{

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    private Material material = new Material();
    /**
     * get the emission
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * set emission
     * @param emission to set
     * @return Geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    protected Color emission = Color.BLACK;

    /**
     * @param p the point that we want to get the normal from
     * @return the normal vector
     */
    public abstract Vector getNormal(Point p);
}
