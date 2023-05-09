package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents ambient light in a scene.
 */
public class AmbientLight extends Light{
    /**
     * A constant representing no ambient light.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


    /**
     * Constructs a new AmbientLight object with the given intensity and reflection coefficient.
     *
     * @param IA The intensity of the ambient light.
     * @param KA The reflection coefficient of the ambient light.
     */
    public AmbientLight(Color IA, Double3 KA) {
        super(IA.scale(KA));
    }

    /**
     * Constructs a new AmbientLight object with the given intensity and reflection coefficient.
     *
     * @param IA The intensity of the ambient light.
     * @param KA The reflection coefficient of the ambient light.
     */
    public AmbientLight(Color IA, double KA) {
        super(IA.scale(KA));
    }


}