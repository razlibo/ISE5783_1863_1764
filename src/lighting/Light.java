package lighting;

import primitives.Color;

/**
 Light is an abstract class that represents a basic light source in the scene.
 It has a single field for the light intensity and a constructor that initializes it.
 */
abstract class Light {

    /**
     * The intensity of the light source.
     */
    private Color intensity;

    /**
     * Constructs a Light object with a given intensity.
     * @param intensity The intensity of the light source.
     */
    public Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light source.
     * @return The intensity of the light source.
     */
    public Color getIntensity() {
        return intensity;
    }
}
