package primitives;

public class Ray {

    private Vector dir;
    private Point p0;
    public Ray(Vector v, Point p){
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
}
