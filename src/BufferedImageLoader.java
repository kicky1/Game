import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Klasa odpowiadajaca za pobieranie sciezki obrazka i zwracanie obrazka
 * */

public class BufferedImageLoader {

    BufferedImage image;

    /**
     * metoda odczytuje siezke obrazka
     * @param path sciezka obrazka
     * @return image
     */
    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
