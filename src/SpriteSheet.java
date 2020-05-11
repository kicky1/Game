import java.awt.image.BufferedImage;

/**
 * Publiczna klasa odpowiedzialana za tworzenie siatki spritow, ktora nastepnie jest wtkorzystywana przy
 * wybieraniu poszegolnych ptakow w klasach ColorBlock oraz ColorBlockHard
 */

public class SpriteSheet {

    private BufferedImage sprite;

    /**
     * Konstruktor pobierajacy zdjecie i przypisujacy do wartosci ss
     * @param ss sprite
     */
    public SpriteSheet(BufferedImage ss)
    {
        this.sprite = ss;
    }

    /**
     * Metoda, w ktorej opisany jest wzor na dizelenie siatki spritow
     * @param col kolumna
     * @param row rzad
     * @param width szerokosc
     * @param height wysokosc
     * @return image
     */
    public BufferedImage grabImage(int col, int row, int width, int height )
    {
        return sprite.getSubimage((row*128)-128,(col*128)-128, width, height);
    }
}
