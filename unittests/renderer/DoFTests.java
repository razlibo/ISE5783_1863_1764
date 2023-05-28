package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.DirectionalLight;
import lighting.LightSource;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

public class DoFTests {

    @Test
    public void testDepthOfField() {
        // Set up the scene
        Scene scene = new Scene("DoF");

        // Set the camera
        Camera camera = new Camera(
                new Point(0, 0, 2500),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(200, 200)
                .setVPDistance(850).setDoFActive(true).setFocalLength(1600).setApertureRadius(20);

        // Set ambient light
        AmbientLight ambientLight = new AmbientLight(new Color(30, 30, 30), 0.1);
        scene.setAmbientLight(ambientLight);

        // Create and add geometries to the scene
        // Plane
        Geometry plane = new Plane(
                new Point(0, 0, 0),
                new Vector(0, 0, 1)
        ).setEmission(new Color(0, 20, 20));
        plane.setMaterial(new Material()
                .setKd(0.5)
                .setKs(0.5)
                .setShininess(60)
                .setkR(0.02)
        );
        scene.geometries.add(plane);

//        // Spheres
//        int numSpheres = 3;
//        double sphereRadius = 50;
//        double sphereSpacing = 150;
//        double spheresCenter = (numSpheres - 1) * sphereSpacing / 2.0;
//
//        for (int i = 0; i < numSpheres; i++) {
//            double x = i * sphereSpacing - spheresCenter;
//            Sphere sphere = new Sphere(sphereRadius, new Point(0, x, 50));
//            sphere.setMaterial(new Material()
//                    .setKd(0.6)
//                    .setKs(0.4)
//                    .setShininess(100)
//                    .setkR(0.3)
//            );
//
//            scene.geometries.add(sphere);
//        }
        scene.geometries.add(new Sphere(70, new Point(100, 0 ,300)).setMaterial(new Material()
                    .setKd(0.6)
                    .setKs(0.4)
                    .setShininess(100)
                    .setkR(0.3)));

        scene.geometries.add(new Sphere(70, new Point(0, 0 ,900)).setMaterial(new Material()
                .setKd(0.6)
                .setKs(0.4)
                .setShininess(100)
                .setkR(0.3)));

        scene.geometries.add(new Sphere(70, new Point(-100, 0 ,1500)).setMaterial(new Material()
                .setKd(0.6)
                .setKs(0.4)
                .setShininess(100)
                .setkR(0.3)));

//        // Add lights to the scene
//        LightSource spotLight = new SpotLight(
//                new Color(255, 1000, 1000),
//                new Point(-100, 100, 100),
//                new Vector(1, -1, -1))
//                .setkL(0.0004)
//                .setkL(0.0001);

        LightSource lightSource = new DirectionalLight(new Color(70, 172, 21 ),new Vector(-1,0,0) );
        scene.lights.add(lightSource);

        ImageWriter imageWriter = new ImageWriter("DoF", 1200, 1200);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


}
