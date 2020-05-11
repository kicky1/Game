import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Klasa w ktorej znajduje sie siatka spritow, majaca za zadanie
 * renderowanie poszczegolnych obrazkow ptakow na poziomie latwym
* */

public class ColorBlock extends GameObjects {

    private BufferedImage block_image,block_image1,block_image2,block_image3;

    /**
     * Konstruktor - zawiera wspolrzedne poczatkowe x oraz y i id elementu
     * @param x poczatkowa wartosc wspolrzednej x
     * @param y poczatkowa wartosc wspolrzednej y
     * @param id numer enum
     */
        public ColorBlock(int x, int y, ID id)
        {
            super(x, y, id);
            SpriteSheet ss = new SpriteSheet(GameTrainBrain.sprite_sheet);
            block_image = ss.grabImage(1,1,128,128);   // nadanie block_image wartosci z 1 kolumny i 1 rzedu na siatce spritow
            block_image1 = ss.grabImage(1,2,128,128);
            block_image2 = ss.grabImage(2,1,128,128);
            block_image3= ss.grabImage(2,2,128,128);

        }

        /**
        * Metoda odpowiedzialna za uplyw czasu w programie
        */
        public void tick(){}
        @Override

        /**
         * Metoda odpowiedzialna za rysowanie poszczegolnych ptakow
         */
        public void render(Graphics g){
            g.drawImage(block_image,(100), (270),null);
            g.drawImage(block_image1,(300), (270),null);
            g.drawImage(block_image2,(500), (270),null);
            g.drawImage(block_image3,(700),(270),null);
        }
 }


