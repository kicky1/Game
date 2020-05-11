import java.awt.*;
import java.util.Random;

/**
 * W klasie TextBlock tworzony jest blok tekstowy wyswietlany nastepnie podczasy gry,
 * w ktory losowane sa poszczegolne kolory napisow jak i samego bloku tekstowego
 */
public class TextBlock extends GameObjects {

    String[] table = {"pomarańczowy", "fioletowy", "zielony", "niebieski"};                                     //Tablica kolorow dla poziomu latwego
    String[] table_hard = {"pomarańczowy", "fioletowy", "zielony", "niebieski","szary","czerwony","różowy"};     //Tablica kolorow dla poziomu trudnego
    Random r = new Random();                                                                                    //Utworzenie obiektu r klasy random
    private GameTrainBrain game;

    /**
     * Metoda odpowiadajaca za wylosowanie randomowej liczby z zakresu 1-4
     * @return wartosc od 1 do 4
     */
    public static int randomNumber()
    {
        Random r = new Random();
        return r.nextInt(4);
    }

    /**
     * Metoda odpowiadajaca za wylosowanie randomowej liczby z zakresu 1-7
     * @return wartosc od 1-7
     */
    public static int randomNumber2()
    {
        Random r = new Random();
        return r.nextInt(7);
    }

    public static int a = randomNumber();                                                     /**Utworzenie zmiennej przyjmującej losową wartosc*/
    public static int c = randomNumber2();                                                    /**Utworzenie zmiennej przyjmującej losową wartosc*/
    public static  Color orange = new Color(220, 121, 0);                           /**Utworzenie obiektu o kolorze pomaranczowym*/
    public static  Color purple = new Color(126, 15, 198);                          /**Utworzenie obiektu o kolorze fioletowym*/
    public static  Color green = new Color(173, 209, 39);                           /**Utworzenie obiektu o kolorze zielonym*/
    public static  Color blue = new Color(56, 182, 209);                            /**Utworzenie obiektu o kolorze niebieskim*/
    public static Color grey = new Color(101, 98, 100);                             /**Utworzenie obiektu o kolorze szarym*/
    public static Color red = new Color(209, 0, 13);                                /**Utworzenie obiektu o kolorze czerwonym*/
    public static Color pink = new Color(249, 111, 254);                            /**Utworzenie obiektu o kolorze rozowym*/


    Color[] table2 = {orange,purple,green,blue};                                                                //Tablica kolorow dla poziomu latwego
    Color[] table2_hard = {orange,purple,green,blue,grey,red,pink};                                             //Tablica kolorow dla poziomu trudnego
    int b = r.nextInt(4);
    int b_hard = r.nextInt(7);

    public TextBlock(int x, int y, ID id,GameTrainBrain game)
    {
        super(x, y, id);
        this.game = game;
    }

    public void tick(){}


    /**
     * W renderze tworzone sa tablice kolorow, w zaleznosci od wybranego poziomu gry
     * @param g grafika
     */
    @Override
    public void render(Graphics g){

        Font fnt = new Font("Comic Sans MS", 1, 20);
        g.setFont(fnt);

        if(game.lvl==0){
            if(a == 0) {                                                //Jezeli wylosowana liczba = a to przypisywana jest losowa wartosc do tablicy

            g.setColor(table2[b]);
            g.fill3DRect(x,y,200,40,true);
            g.setColor(Color.black);
            g.drawString((table[0]), 400, 125);
            g.dispose();
            }
            else if(a==1){
            g.setColor(table2[b]);
            g.fill3DRect(x,y,200,40,true);
            g.setColor(Color.black);
            g.drawString((table[1]), 430, 125);
            g.dispose();
            }
            else if(a==2){
            g.setColor(table2[b]);
            g.fill3DRect(x,y,200,40,true);
            g.setColor(Color.black);
            g.drawString(String.valueOf(table[2]), 435, 125);
            g.dispose();
            }
            else if(a==3) {
            g.setColor(table2[b]);
            g.fill3DRect(x,y,200,40,true);
            g.setColor(Color.black);
            g.drawString((table[3]), 427, 125);
            g.dispose();
            }
        }
        else if(game.lvl==1)
        {
            if(c == 0) {
                g.setColor(table2_hard[b_hard]);
                g.fill3DRect(x,y,200,40,true);
                g.setColor(Color.black);
                g.drawString((table_hard[0]), 400, 125);
                g.dispose();
            }
            else if(c==1){
                g.setColor(table2_hard[b_hard]);
                g.fill3DRect(x,y,200,40,true);
                g.setColor(Color.black);
                g.drawString((table_hard[1]), 433, 125);
                g.dispose();
            }
            else if(c==2){
                g.setColor(table2_hard[b_hard]);
                g.fill3DRect(x,y,200,40,true);
                g.setColor(Color.black);
                g.drawString((table_hard[2]), 435, 125);
                g.dispose();
            }
            else if(c==3) {
                g.setColor(table2_hard[b_hard]);
                g.fill3DRect(x,y,200,40,true);
                g.setColor(Color.black);
                g.drawString((table_hard[3]), 427, 125);
                g.dispose();
            }
            else if(c==4) {
                g.setColor(table2_hard[b_hard]);
                g.fill3DRect(x,y,200,40,true);
                g.setColor(Color.black);
                g.drawString((table_hard[4]), 447, 125);
                g.dispose();
            }
            else if(c==5) {
                g.setColor(table2_hard[b_hard]);
                g.fill3DRect(x,y,200,40,true);
                g.setColor(Color.black);
                g.drawString((table_hard[5]), 427, 125);
                g.dispose();
            }
            else if(c==6) {
                g.setColor(table2_hard[b_hard]);
                g.fill3DRect(x,y,200,40,true);
                g.setColor(Color.black);
                g.drawString((table_hard[6]), 431, 125);
                g.dispose();
            }
        }
    }
}
