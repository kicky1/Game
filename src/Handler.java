import java.awt.*;
import java.util.LinkedList;

/**
 * Klasa odpowiadajaca za przechowywanie obiektow w grze za pomoca listy
 * w metodzie tick oraz render wyswietlane sa te obiekty,
 */
public class Handler {

    LinkedList<GameObjects> object = new LinkedList<GameObjects>();

    /**
     * Metoda tick pobierajaca obiekty i wpisujace je do listy
     */
    public void  tick()
    {
        for(int i = 0; i<object.size(); i++)
        {
            GameObjects tempObject = object.get(i);
            tempObject.tick();
        }
    }
    /**
     * Metoda render pobierajaca obiekty i wpisujace je do listy
     * @param g grafika
     */
    public void render(Graphics g)
    {
        for(int i = 0; i<object.size(); i++)
        {
            GameObjects tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    /**
     * clearTextBlock sluzy do usuwania obiektów z listy
      */
    public void clearTextBlock()
    {
        object.clear();
    }
    /**
     * dodawanie obiektow
     * @param object objekt
     */
    public void addObjects(GameObjects object)
    {
        this.object.add(object);
    }
}
