package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void testImageWriter(){
        ImageWriter imageWriter = new ImageWriter("MyFirstImage",800,600);
        for(int i = 0; i < 800; i++){
            for(int j = 0; j < 500; j++){
                imageWriter.writePixel(i,j,i % 50 == 0 || j % 50 == 0 ? new Color(52,100,235): new Color(235,64,52));
            }
        }

        imageWriter.writeToImage();
    }

}