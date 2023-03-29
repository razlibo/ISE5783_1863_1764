package primitives;

/**
 * This class will present Ray object
 *
 * @author Raz
 */
public class Ray {
    /**
     * Vector in the space
     */
    private final Vector dir;

    /**
     * Point stating point
     */
    private final Point p0;

    /**
     * constructor for Ray
     *
     * @param v Vector value
     * @param p point value
     */

    public Ray(Vector v, Point p) {
        this.dir = v.normalize();
        this.p0 = p;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }

    @Override
    public String toString() {
        return this.p0.toString() + this.dir.toString();
    }

    public Vector getDir() {
        return dir;
    }

    public Point getP0() {
        return p0;
    }
}
