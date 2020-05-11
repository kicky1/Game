import java.awt.*;

/**
 * Abstrakcyjna klasa GameObjects przechowujaca wartosci x,y oraz ID tworzonych obiektow
 * takich jak ColorBlock,ColorBockHard oraz TextBlock.
 * param x początkowa wspolrzedna x
 * param y początkowa wspolrzedna y
 * param id wartosc z enum
 */
public abstract class GameObjects{

    protected int x,y;
    protected ID id;

    /**
     * Konstruktor klasy obiektow gry, dodanie obslugi zdarzen
     * @param x poczatkowa wartosc wspolrzednej x
     * @param y poczatkowa wartosc wspolrzednej y
     * @param id numer enum
     */
    public GameObjects(int x, int y, ID id)
    {
        this.x=x;
        this.y=y;
        this.id=id;
    }
    /**
     * Metoda odpowiedzialna za uplyw czasu w programie
     */
    public abstract void tick();
    /**
     * Metoda odpowiedzialna za rysowanie poszczegolnych ptakow
     * @param g grafika
     */
    public abstract void render(Graphics g);
}
