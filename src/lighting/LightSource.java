package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 LightSource is an interface that represents a light source in the scene.
 It defines two methods for getting the intensity of the light source at a given point in the scene
 and for getting the direction of the light from a given point in the scene.
 */
public interface LightSource {
    /**
     * Returns the intensity of the light source at a given point in the scene.
     * @param p The point in the scene to calculate the intensity of the light source.
     * @return The color of the light source at the given point in the scene.
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction of the light from a given point in the scene.
     * @param p The point in the scene to calculate the direction of the light from.
     * @return The direction of the light from the given point in the scene.
     */
    public Vector getL(Point p);


}
