package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**

 This class represents a camera in a 3D space.

 It defines the camera's position, orientation, and view plane parameters.
 */
public class Camera {

    /** The location of the camera */
    private Point location;

    /** The direction vector towards which the camera is facing */
    private Vector vTo;

    /** The up direction vector of the camera */
    private Vector vUp;

    /** The right direction vector of the camera */
    private Vector vRight;

    /** The width of the view plane */
    private double VPWidth;

    /** The height of the view plane */
    private double VPHeight;

    /** The distance between the camera and the view plane */
    private double VPDistance;

    /**

     Constructs a camera object with the specified position, direction, and up direction vectors.
     @param location the position of the camera
     @param vTo the direction vector towards which the camera is facing
     @param vUp the up direction vector of the camera
     @throws IllegalArgumentException if the direction and up direction vectors are not orthogonal
     */
    public Camera(Point location, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vUp.dotProduct(vTo))) throw new IllegalArgumentException("Vectors must be orthogonal");
        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }
    /**

     Sets the width and height of the view plane.
     @param width the width of the view plane
     @param height the height of the view plane
     @return the camera object with the updated view plane size
     */
    public Camera setVPSize(double width, double height){
        this.VPHeight = height;
        this.VPWidth = width;
        return this;
    }
    /**

     Sets the distance between the camera and the view plane.
     @param distance the distance between the camera and the view plane
     @return the camera object with the updated view plane distance
     */
    public Camera setVPDistance(double distance) {
        VPDistance = distance;
        return this;
    }
    /**

     Constructs a ray that passes through a specific pixel on the view plane.

     @param nX the number of pixels along the width of the view plane

     @param nY the number of pixels along the height of the view plane

     @param j the x-coordinate of the pixel on the view plane

     @param i the y-coordinate of the pixel on the view plane

     @return the ray that passes through the specified pixel on the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        Point pC = location.add(vTo.Scale(VPDistance));
        var rY = alignZero(VPHeight/nY);
        var rX = alignZero(VPWidth/nX);
        var yI = -(i-(nY-1)/2.0) * rY;
        var xJ = (j-(nX-1)/2.0) * rX;

        var pIJ = isZero(yI) ? pC : pC.add(vUp.Scale(yI));
        pIJ = isZero(xJ) ? pIJ : pIJ.add(vRight.Scale(xJ));
        return new Ray(location, pIJ.subtract(location));
    }
}