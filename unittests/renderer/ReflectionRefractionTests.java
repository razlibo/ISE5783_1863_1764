/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));


        scene.geometries.add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setkT(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setkL(0.00001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a one triangle and three spheres lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void testMultipleObjects() {
        Scene scene = new Scene("MultipleObjects");

        // Set up camera
        Camera camera = new Camera(
                new Point(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPSize(200, 200)
                .setVPDistance(850);

        // Set ambient light
        scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30), 0.1));

        // Add geometries to the scene
        // Plane
        Geometry plane = new Plane(
                new Point(0, 0, 0),
                new Vector(0, 0, 1)
        ).setEmission(new Color(0,20,20));
        plane.setMaterial(new Material()
                .setKd(0.5)
                .setKs(0.5)
                .setShininess(60)
                .setkR(0.02)
        );
        scene.geometries.add(plane);

        // Sphere 1
        Sphere sphere1 = new Sphere(50, new Point(0, -50, 50));
        sphere1.setMaterial(new Material()
                .setKd(0.6)
                .setKs(0.4)
                .setShininess(100)
                .setkR(0.3)
        );
        sphere1.setEmission(new Color(0, 0, 255));
        scene.geometries.add(sphere1);

        // Sphere 2
        Sphere sphere2 = new Sphere(30, new Point(70, 70, 30));
        sphere2.setMaterial(new Material()
                .setKd(0.7)
                .setKs(0.5)
                .setShininess(80)
                .setkR(0.1).setkT(0.35)
        );
        sphere2.setEmission(new Color(255, 0, 0));
        scene.geometries.add(sphere2);

        Sphere sphere3 = new Sphere(20, new Point(70, 70, 30));
        sphere3.setMaterial(new Material()
                .setKd(0.6)
                .setKs(0.4)
                .setShininess(100)
                .setkR(0.3)
        );
        sphere3.setEmission(new Color(0, 255, 0));
        scene.geometries.add(sphere3);

        // Triangle 1
        Triangle triangle1 = new Triangle(
                new Point(-100, -100, 0),
                new Point(100, -100, 0),
                new Point(0, 100, 0)
        );
        triangle1.setMaterial(new Material()
                .setKd(0.6)
                .setKs(0.4)
                .setShininess(50)
                .setkR(0.2)
        );
        triangle1.setEmission(new Color(0, 255, 0));
        scene.geometries.add(triangle1);

        // Triangle 2
        Triangle triangle2 = new Triangle(
                new Point(-120, -50, 30),
                new Point(-120, 50, 30),
                new Point(0, 0, 100)
        );
        triangle2.setMaterial(new Material()
                .setKd(0.8)
                .setKs(0.6)
                .setShininess(70)
                .setkR(0.2)
        );
        triangle2.setEmission(new Color(255, 255, 255));
        scene.geometries.add(triangle2);

        // Add lights to the scene
        SpotLight spotLight = new SpotLight(
                new Color(1000, 1000, 1000),
                new Point(-100, 100, 100),
                new Vector(1, -1, -1)).setkL(0.0004).setkQ(0.0001);
        scene.lights.add(spotLight);

        // Render the scene
        ImageWriter imageWriter = new ImageWriter("MultipleObjects", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

   @Test
   public void anotherMultipleObjectTest() {
      Scene scene = new Scene("anotherMultipleObjectTest");

      // Set up camera
      Camera camera = new Camera(
              new Point(0, 0, 1000),
              new Vector(0, 0, -1),
              new Vector(0, 1, 0)
      ).setVPSize(200, 200)
              .setVPDistance(850);

      // Set ambient light
      scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30), 0.1));

      // Add geometries to the scene
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

      // Spheres
      Sphere[] spheres = new Sphere[4];
      spheres[0] = new Sphere(50, new Point(0, -50, 50));
      spheres[1] = new Sphere(30, new Point(70, 70, 30));
      spheres[2] = new Sphere(20, new Point(70, 70, 30));
      spheres[3] = new Sphere(40, new Point(-70, 70, 30));

      for (int i = 0; i < spheres.length; i++) {
         Sphere sphere = spheres[i];
         sphere.setMaterial(new Material()
                 .setKd(0.6)
                 .setKs(0.4)
                 .setShininess(100)
                 .setkR(0.3)
         );
         sphere.setEmission(new Color((i + 1) * 50, i * 100, (i + 1) * 100));
         scene.geometries.add(sphere);
      }

      // Triangles
      Triangle[] triangles = new Triangle[4];
      triangles[0] = new Triangle(
              new Point(-100, -100, 0),
              new Point(100, -100, 0),
              new Point(0, 100, 0)
      );
      triangles[1] = new Triangle(
              new Point(-120, -50, 30),
              new Point(-120, 50, 30),
              new Point(0, 0, 100)
      );
      triangles[2] = new Triangle(
              new Point(-50, -50, 50),
              new Point(50, -50, 50),
              new Point(0, 50, 50)
      );
      triangles[3] = new Triangle(
              new Point(-80, -30, 30),
              new Point(-80, 30, 30),
              new Point(0, 0, 80)
      );

      for (int i = 0; i < triangles.length; i++) {
         Triangle triangle = triangles[i];
         triangle.setMaterial(new Material()
                 .setKd(0.6)
                 .setKs(0.4)
                 .setShininess(50)
                 .setkR(0.2)
         );
         triangle.setEmission(new Color(i * 100, (i + 1) * 50, (i + 1) * 100));
         scene.geometries.add(triangle);
      }

       // Polygon
       Point[] vertices = new Point[]{
               new Point(-50, 0, -50),
               new Point(-50, 0, 50),
               new Point(50, 0, 50),
               new Point(50, 0, -50)
       };
       Polygon polygon = new Polygon(vertices);
       polygon.setMaterial(new Material()
               .setKd(0.8)
               .setKs(0.6)
               .setShininess(70)
               .setkR(0.2)
       );
       polygon.setEmission(new Color(255, 255, 0));
       scene.geometries.add(polygon);


      // Add lights to the scene
      SpotLight spotLight = new SpotLight(
              new Color(1000, 1000, 1000),
              new Point(-100, 100, 100),
              new Vector(1, -1, -1)).setkL(0.0004).setkQ(0.0001);
      scene.lights.add(spotLight);

      // Render the scene
      ImageWriter imageWriter = new ImageWriter("anotherMultipleObjectTest", 600, 600);
      camera.setImageWriter(imageWriter)
              .setRayTracer(new RayTracerBasic(scene))
              .renderImage()
              .writeToImage();
   }
}
