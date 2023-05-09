package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.alignZero;
public class SpotLight extends PointLight{
    Vector direction;

    public SpotLight setNarrowBeam(double narrowBean) {
        this.narrowBean = narrowBean;
        return this;
    }

    double narrowBean = 1;
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
}
