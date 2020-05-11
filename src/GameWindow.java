import javax.swing.*;
import java.awt.*;

/**
 * Okno glowne gry demonstracyjnej.
 * @author Krzysztof Wicki
 */
public class GameWindow extends Canvas {

    /**
     * Glowny konstruktor klasy - ustawienie parametrów i rozpoczęcia akcji
     * @param width szerokosc okna
     * @param height wysokosc okna
     * @param tittle tytul gry
     * @param game GameTrainBrain
     */
    public GameWindow(int width, int height, String tittle, GameTrainBrain game)
    {
        JFrame frame = new JFrame((GraphicsConfiguration) null);
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));
        //frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);                                            //Dodanie gry do ramki
        frame.setVisible(true);
        game.start();
    }
}
