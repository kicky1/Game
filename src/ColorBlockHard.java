import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Klasa w ktorej znajduje sie siatka spritow, majaca za zadanie
 * renderowanie poszczegolnych obrazkow ptakow na poziomie trudnym
 * */

public class ColorBlockHard extends GameObjects {

    private BufferedImage block_image,block_image1,block_image2;

    /**
     * Konstruktor - zawiera wspolrzedne poczatkowe x oraz y i id elementu
     * @param x poczatkowa wartosc wspolrzednej x
     * @param y poczatkowa wartosc wspolrzednej y
     * @param id numer enum
     */
    public ColorBlockHard(int x, int y, ID id)
    {
        super(x, y, id);
        SpriteSheet ss = new SpriteSheet(GameTrainBrain.sprite_sheet2);
        block_image = ss.grabImage(1,1,128,128);
        block_image1 = ss.grabImage(1,2,128,128);
        block_image2 = ss.grabImage(2,1,128,128);
    }
    /**
     * Metoda odpowiedzialna za uplyw czasu w programie
     */
    public void tick(){
    }
    /**
     * Metoda odpowiedzialna za rysowanie poszczegolnych ptakow
     */
    @Override
    public void render(Graphics g){
        g.drawImage(block_image,(200), (400),null);
        g.drawImage(block_image1,(400), (400),null);
        g.drawImage(block_image2,(600), (400),null);
    }

}


