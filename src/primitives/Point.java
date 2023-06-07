package primitives;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

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

    public final static Point ZERO = new Point(0,0,0);

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

    public double getX(){return xyz.d1;}

    public double getY(){return xyz.d2;}

    public double getZ(){return xyz.d3;}
    /**

     Generates points on a circle given the center, up vector, right vector, radius, and grid density.

     @param center the center point of the circle

     @param vUp the up vector defining the orientation of the circle

     @param vRight the right vector defining the orientation of the circle

     @param radius the radius of the circle

     @param gridDensity the density of the grid used for generating points

     @return a list of points generated on the circle
     */
    public static List<Point> generatePointsOnCircle(Point center, Vector vUp, Vector vRight, double radius, double gridDensity){
        Random random = new Random();
        var points = new ArrayList<Point>();
        for(double i = -radius; i < radius; i+= radius/gridDensity){
            if(isZero(i)) continue;
            double jitterOffset =  random.nextDouble(-0.1,0.1);
            for(double j = -radius; j < radius; j+= radius/gridDensity){
                if(isZero(j)) continue;
                var p = center.add(vUp.scale(i).add(vRight.scale(j + jitterOffset)));
                if(center.distance(p) <= radius) points.add(p);
            }
        }
        return points;
    }

    public static Point createMaxPoint(Point p1, Point p2){
        return new Point(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()), Math.max(p1.getZ(), p2.getZ()));
    }

    public static Point createMinPoint(Point p1, Point p2){
        return new Point(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()), Math.min(p1.getZ(), p2.getZ()));
    }
}
