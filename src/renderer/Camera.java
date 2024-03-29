package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;
import java.util.stream.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represents a camera in a 3D space.
 * <p>
 * It defines the camera's position, orientation, and view plane parameters.
 */
public class Camera {

    ImageWriter imageWriter;
    /**
     * Ray tracer
     */
    RayTracerBase rayTracer;
    /**
     * The location of the camera
     */
    private Point location;
    /**
     * The direction vector towards which the camera is facing
     */
    private Vector vTo;
    /**
     * The up direction vector of the camera
     */
    private Vector vUp;
    /**
     * The right direction vector of the camera
     */
    private Vector vRight;
    /**
     * The width of the view plane
     */
    private double VPWidth;
    /**
     * The height of the view plane
     */
    private double VPHeight;
    /**
     * The distance between the camera and the view plane
     */
    private double VPDistance;

    /** Aperture radius */
    double apertureRadius = 0;

    /** Focal length */
    double focalLength = 0;

    /** DoF active */
    boolean DoFActive = false;

    /**
     * DoF points on the aperture plane
     */
    List<Point> DoFPoints = null;


    /**
     * Aperture area grid density
     */
    int gridDensity = 7;

    /**
     * Constructs a camera object with the specified position, direction, and up direction vectors.
     *
     * @param location the position of the camera
     * @param vTo      the direction vector towards which the camera is facing
     * @param vUp      the up direction vector of the camera
     * @throws IllegalArgumentException if the direction and up direction vectors are not orthogonal
     */
    public Camera(Point location, Vector vTo, Vector vUp){
        if (!isZero(vUp.dotProduct(vTo))) throw new IllegalArgumentException("Vectors must be orthogonal");
        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * check if all fields are not empty
     */
    public Camera renderImage() {
        if (location == null) {
            throw new MissingResourceException("location not given", "Camera", "location");
        }

        if (vTo == null) {
            throw new MissingResourceException("vTo not given", "Camera", "vTo");
        }

        if (vUp == null) {
            throw new MissingResourceException("vUp not given", "Camera", "vUp");
        }

        if (vRight == null) {
            throw new MissingResourceException("vRight not given", "Camera", "vRight");
        }

        if (VPHeight == 0) {
            throw new MissingResourceException("VPHeight not given", "Camera", "VPHeight");
        }

        if (VPDistance == 0) {
            throw new MissingResourceException("VPDistance not given", "Camera", "VPDistance");
        }

        if (VPWidth == 0) {
            throw new MissingResourceException("VPWidth not given", "Camera", "VPWidth");
        }

        if (imageWriter == null) {
            throw new MissingResourceException("Image writer not given", "Camera", "imageWriter");
        }

        if (rayTracer == null) {
            throw new MissingResourceException("Ray tracer base writer not given", "Camera", "rayTracerBase");
        }
        if (apertureRadius == 0 && DoFActive) {
            throw new MissingResourceException("Aperture radius not set", "Camera", "apertureRadius");
        }
        if (focalLength == 0  && DoFActive) {
            throw new MissingResourceException("Focal length not set", "Camera", "focalLength");
        }

        int width = imageWriter.getNx(), height = imageWriter.getNy();
        Pixel.initialize(height, width, 1);
        if(DoFActive) {
            this.DoFPoints = Point.generatePointsOnCircle(location, vUp, vRight, apertureRadius, gridDensity);
            IntStream.range(0, height).parallel().forEach(i -> {
                IntStream.range(0, width).parallel().forEach(j -> {
                    var focalPoint = constructRay(width, height, j, i).getPoint(focalLength);
                    imageWriter.writePixel(j, i, rayTracer.traceMultipleRays(Ray.constructRaysFromListOfPointsToPoint(focalPoint, DoFPoints)));
                    Pixel.pixelDone();
                    Pixel.printPixel();
                });
            });
        }else {

            IntStream.range(0, height).parallel().forEach(i -> {
                IntStream.range(0, width).parallel().forEach(j -> {
                    imageWriter.writePixel(j, i, rayTracer.traceRay(constructRay(width, height, j, i)));
                    Pixel.pixelDone();
                    Pixel.printPixel();
                });
            });
        }
        return this;
    }

    /**
     * Sets the width and height of the view plane.
     *
     * @param width  the width of the view plane
     * @param height the height of the view plane
     * @return the camera object with the updated view plane size
     */
    public Camera setVPSize(double width, double height) {
        this.VPHeight = height;
        this.VPWidth = width;
        return this;
    }

    /**
     * Sets the distance between the camera and the view plane.
     *
     * @param distance the distance between the camera and the view plane
     * @return the camera object with the updated view plane distance
     */
    public Camera setVPDistance(double distance) {
        VPDistance = distance;
        return this;
    }

    /**
     * Constructs a ray that passes through a specific pixel on the view plane.
     *
     * @param nX the number of pixels along the width of the view plane
     * @param nY the number of pixels along the height of the view plane
     * @param j  the x-coordinate of the pixel on the view plane
     * @param i  the y-coordinate of the pixel on the view plane
     * @return the ray that passes through the specified pixel on the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pC = location.add(vTo.scale(VPDistance));
        var rY = VPHeight / nY;
        var rX = VPWidth / nX;
        var yI = -(i - (nY - 1) / 2.0) * rY;
        var xJ = (j - (nX - 1) / 2.0) * rX;

        var pIJ = isZero(yI) ? pC : pC.add(vUp.scale(yI));
        pIJ = isZero(xJ) ? pIJ : pIJ.add(vRight.scale(xJ));
        return new Ray(location, pIJ.subtract(location));
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracer = rayTracerBase;
        return this;
    }

    public Camera setApertureRadius(double apertureRadius) {
        this.apertureRadius = apertureRadius;
        return this;
    }

    public Camera setFocalLength(double focalLength) {
        this.focalLength = focalLength;
        return this;
    }

    public Camera setDoFActive(boolean doFActive) {
        DoFActive = doFActive;
        return this;
    }

    public Camera setGridDensity(int gridDensity) {
        this.gridDensity = gridDensity;
        return this;
    }


    /**
     * This method prints a grid on the image with the given interval and color.
     * <p>
     * Throws a MissingResourceException if imageWriter is not initialized.
     *
     * @param interval The interval between each line of the grid
     * @param color    The color of the grid lines
     */
    public Camera printGrid(int interval, Color color) {

        if (imageWriter == null) {
            throw new MissingResourceException("Image writer not given", "Camera", "imageWriter");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) imageWriter.writePixel(i, j, color);
            }
        }
        return this;
    }

    /**
     * This method writes the image to file using the imageWriter.
     * Throws a MissingResourceException if imageWriter is not initialized.
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("Image writer not given", "Camera", "imageWriter");
        }
        imageWriter.writeToImage();
    }
}