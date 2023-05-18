package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.alignZero;


/**

 SpotLight is a class that represents a spotlight light source in the scene.
 It extends the PointLight class and adds the capability to specify a direction for the light to shine in.
 It also allows for adjusting the narrowness of the beam of light.
 */
public class SpotLight extends PointLight{
    Vector direction;
    double narrowBean = 1;

    /**
     * Sets the narrowness of the beam of light for the spotlight.
     * @param narrowBean The narrowness of the beam of light.
     * @return The updated SpotLight instance.
     */
    public SpotLight setNarrowBeam(double narrowBean) {
        this.narrowBean = narrowBean;
        return this;
    }

    /**
     * Constructs a new instance of SpotLight with the specified intensity, position, and direction.
     * @param intensity The intensity of the SpotLight.
     * @param position The position of the SpotLight.
     * @param direction The direction the light shines in.
     */
    public SpotLight(Color intensity, Point position, Vector direction)  {
        super(intensity, position);
        this.direction=direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.pow(Math.max(0, getL(p).dotProduct(direction)),this.narrowBean));
    }

    @Override
    public Vector getL(Point p) {return super.getL(p);}

    @Override
    public SpotLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    @Override
    public SpotLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    @Override
    public SpotLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
