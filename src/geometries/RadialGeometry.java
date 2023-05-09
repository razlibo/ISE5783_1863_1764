package geometries;

/**
 * class for radial geometric object
 *
 * @author Yoni
 */
public abstract class RadialGeometry extends Geometry {
    /**
     * the radius
     */
    protected double radius;

    /**
     * constructor to initialize RadialGeometry radius
     *
     * @param radius the radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * getter for radius
     *
     * @return radius
     */
    public double getRadius() {
        return radius;
    }
}
