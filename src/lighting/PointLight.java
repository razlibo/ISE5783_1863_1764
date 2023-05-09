package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a point light source, which emits light uniformly in all directions from a single point.
 * <p>
 * Implements the LightSource interface, which specifies methods for getting the intensity and direction of the light.
 */
public class PointLight extends Light implements LightSource {

    /**
     * The position of the point light source.
     */
    protected Point position;
    /**
     * The constant attenuation coefficient.
     */
    protected double kC = 1;
    /**
     * The linear attenuation coefficient.
     */
    protected double kL = 0;
    /**
     * The quadratic attenuation coefficient.
     */
    protected double kQ = 0;

    /**
     * Creates a new PointLight object with the specified intensity and position.
     *
     * @param intensity The intensity of the point light source.
     * @param position  The position of the point light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation coefficient of the point light source.
     *
     * @param kC The new constant attenuation coefficient.
     * @return This PointLight object, for chaining.
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation coefficient of the point light source.
     *
     * @param kL The new linear attenuation coefficient.
     * @return This PointLight object, for chaining.
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation coefficient of the point light source.
     *
     * @param kQ The new quadratic attenuation coefficient.
     * @return This PointLight object, for chaining.
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Calculates the intensity of the light at a given point, taking into account the distance from the light
     * source and the attenuation coefficients.
     *
     * @param p The point at which to calculate the intensity of the light.
     * @return The intensity of the light at the given point.
     */
    @Override
    public Color getIntensity(Point p) {
        double d = this.position.distance(p);
        return this.getIntensity().scale(1.0/(kC + kL*d + kQ*d*d));
    }

    /**
     * Calculates the direction of the light from the point light source to a given point.
     *
     * @param p The point at which to calculate the direction of the light.
     * @return The direction of the light from the point light source to the given point.
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }


}
