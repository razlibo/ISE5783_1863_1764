package primitives;

/**
 * Represents the material of a geometrical object, such as its diffuse and specular coefficients and shininess.
 */
public class Material {
    /*

    The diffuse reflection coefficient of the material.
    */
    public Double3 kD = new Double3(0);
    /**
     * The specular reflection coefficient of the material.
     */
    public Double3 kS = new Double3(0);
    /**
     * The shininess of the material.
     */
    public int nShininess = 0;

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The new diffuse reflection coefficient.
     * @return This Material object, for chaining.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The new specular reflection coefficient.
     * @return This Material object, for chaining.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the shininess of the material.
     *
     * @param nShininess The new shininess of the material.
     * @return This Material object, for chaining.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material to a scalar value, which is converted to a Double3 object.
     *
     * @param kD The new diffuse reflection coefficient, as a scalar value.
     * @return This Material object, for chaining.
     */
    public Material setKd(Double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material to a scalar value, which is converted to a Double3 object.
     *
     * @param kS The new specular reflection coefficient, as a scalar value.
     * @return This Material object, for chaining.
     */
    public Material setKs(Double kS) {
        this.kS = new Double3(kS);
        return this;
    }
}
