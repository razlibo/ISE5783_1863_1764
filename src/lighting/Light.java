package lighting;

import primitives.Color;

abstract class Light {
    private Color intensity;

    public Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
