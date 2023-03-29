package primitives;

/**
 * This class will present Vector in the space
 *
 * @author Raz
 */
public class Vector extends Point {

    /**
     * constructor for Vector
     *
     * @param xyz Double3 vector
     * @throws IllegalArgumentException if the Vector is vector 0 => (0,0,0)
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Vector can't be zero");
    }

    /**
     * Constructor to initialize Vector based object with its three number values
     *
     * @param x first number value
     * @param y second number value
     * @param z third number value
     * @throws IllegalArgumentException if the Vector is vector 0 => (0,0,0)
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Vector can't be zero");
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj instanceof Vector other)
            return super.equals(other);
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Sum two vectors into a new Vector
     *
     * @param v right handle side operand for addition
     * @return result of add
     */
    public Vector add(Vector v) {
        return new Vector(this.xyz.add(v.xyz));
    }

    /**
     * scale Vector in number
     *
     * @param d number to scale
     * @return result of scale
     */
    public Vector Scale(double d) {
        return new Vector(this.xyz.scale(d));
    }

    /**
     * cross product vectors
     *
     * @param v Vector to cross product
     * @return result is the normal vector to two vectors
     */
    public Vector crossProduct(Vector v) {
        return new Vector(this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2,
                this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3,
                this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1);
    }

    /**
     * dot product vector in ather vector
     *
     * @param v vector the dot
     * @return the float number
     */
    public double dotProduct(Vector v) {
        return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
    }

    /**
     * multiply vector in himself to get the length squared
     *
     * @return result of the multiplication
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * calculate the length of the vector
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * calculate the vector normalization
     *
     * @return the normal vector
     */
    public Vector normalize() {
        return new Vector(this.xyz.reduce(this.length()));
    }
}
