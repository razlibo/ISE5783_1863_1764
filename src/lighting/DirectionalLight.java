package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**

 DirectionalLight class represents a directional light source in the scene, where the light rays have a single direction.
 It extends Light abstract class and implements the LightSource interface.
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * The direction of the light source
     */
    private Vector direction;

    /**
     * Sets the direction of the light source.
     * @param direction The new direction of the light source.
     * @return This DirectionalLight object, to allow method chaining.
     */
    public DirectionalLight setDirection(Vector direction) {
        this.direction = direction.normalize();
        return this;
    }

    /**
     * Constructs a DirectionalLight object with a given intensity and direction.
     * @param intensity The intensity of the light source.
     * @param direction The direction of the light source.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }


    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction.normalize();
    }

    @Override
    public double getDistance(Point p){
        return Double.POSITIVE_INFINITY;
    }
}
