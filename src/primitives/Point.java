package primitives;

/**
 * This class will serve primitive classes based on three numbers from Double3
 *
 * @author Raz
 */
public class Point {
    /**
     * Vector in the space
     */
    final Double3 xyz;

    /**
     * constructor for point
     *
     * @param xyz Double3 value
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructor to initialize Point based object with tree numbers
     *
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return this.xyz.equals(other.xyz);
        return false;
    }

    @Override
    public String toString() {
        return this.xyz.toString();
    }

    /**
     * Sum two vectors into a new Point where each couple of numbers
     * is summarized
     *
     * @param v right handle side operand for addition
     * @return result of add
     */
    public Point add(Vector v) {
        return new Point(this.xyz.add(v.xyz));
    }

    /**
     * Subtract vector from Point into a new vector
     *
     * @param p right handle side operand for subtract
     * @return result of subtract
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract(p.xyz));
    }

    /**
     * distance squared
     *
     * @param p point to calculate the distance
     * @return the distance between thh two points
     */
    public double distanceSquared(Point p) {
        return (this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) +
                (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2) +
                (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3);
    }

    /**
     * distance sqrt
     *
     * @param p point to calculate the distance
     * @return the distance sqrt between thh two points
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }
}
