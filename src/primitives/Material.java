package primitives;

/**
 * Represents the material of a geometrical object, such as its diffuse and specular coefficients and shininess.
 */
public class Material {

    /**
     * The transparency coefficient of the material.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient of the material.
     */
    public Double3 kR = Double3.ZERO;

    /**
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
    public int nShininess = 1;

    /**
     * Sets the transparency coefficient of the material.
     * @param kT to set
     * @return Material object
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     * @param kR to set
     * @return Materiel object
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets the transparency coefficient of the material.
     * @param kT to set
     * @return Material object
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     * @param kR to set
     * @return Materiel object
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

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

    public Double3 getkR() {
        return kR;
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
