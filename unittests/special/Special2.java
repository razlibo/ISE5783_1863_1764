package special;

import geometries.Intersectable;
import geometries.Polygon;
import geometries.Triangle;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.io.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static java.awt.Color.CYAN;
import static java.awt.Color.YELLOW;

public class Special2 {
    private final ImageWriter imageWriter = new ImageWriter("teapot", 2000, 2000);

    private final Camera camera = new Camera(new Point(-500, -500, -500), new Vector(1, 1, 1), new Vector(0, 1, -1)) //
            .setVPDistance(1000).setVPSize(200, 200) //
            .setImageWriter(imageWriter) ;//


    private final Scene scene = new Scene("Test scene");

    private static final Color color = new Color(200, 0, 0);
    private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);

    @Test
    public void Test() throws IOException {
        // Path to your STL file
        String stlFilePath = "C:\\OBJS\\Wolf_One_stl.stl";

        var o = STLParser.parseSTLFile(Path.of(stlFilePath));

        List<Intersectable> l = new ArrayList<>();

        for (Triangle i:o
             ) {
            l.add(i.setMaterial(mat));
        }

        scene.geometries.add(l);
        scene.lights.add(new PointLight(new Color(500, 500, 500), new Point(100, 0, -100)).setkQ(0.00000001));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.BLUE), new Vector(0, 0, 1)));
        scene.background = new Color(CYAN);
        scene.geometries.BuildBVH();

        camera.setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();

    }
}
