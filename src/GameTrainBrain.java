import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Glowna klasa odpowiadajaca za wlaczenie gry
 * Znajduja sie w niej poszczegolne stany gry takie jak Menu,Game,Stats dla ktorych
 * odpowiednio inne rzeczy sa wyswietlane
 */

public class GameTrainBrain extends Canvas implements Runnable {

    public static final int WIDTH = 1024;               /** Ustawienie szerokosci*/
    public static final int  HEIGHT = 768;               /** Ustawienie wysokosci*/
    private Thread thread;                                 /** Obiekt reprezentujacy przerwanie*/
    private boolean running = false;                      /** Okreslenie czy program dziala*/
    public int lvl = 0;                                    /** Zmienna okreslajaca poziom gry*/
    private PlayWindow playWindow;                         /**Obiekt reprezentujacy okno gry*/
    private Handler handler;                               /** Obiekt reprezentujacy handler*/
    public STATE gameState = STATE.Menu;                   /** Utworzenie publicznego stanu gry dla Menu*/
    public static BufferedImage sprite_sheet;               /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage sprite_sheet2;              /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage bcg_image;                  /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_graj;                /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_latwy;               /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_powrot;              /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_start;               /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_trudny;              /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_wyjscie;             /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_wyniki;              /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage button_zapisz;              /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/
    public static BufferedImage score_board;                /** Dodanie statycznyej zmiennej, aby odwolywac sie do zdjecia*/



    /**
     * Enumeracja STATE w ktorej znajduja sie poszczegolne stany gry
     */

    public enum STATE
    {
        /** Plansza Menu**/
        Menu,
        Game,       /** Plansza Gry**/
        Level,      /** Plansza wyboru poziomu**/
        Stats,      /** Plansza wynikow**/
        EndGame,    /** Plansza konca gry**/
        Instruction,    /** Plansza instrukcji przed rozpoczeciem gry**/
    }

 /**
  * Konstruktor GamerTrainBrain przyjmujacy wielkosci gry oraz
  * ladujacy poczegolne zdjecia tla i przyciskow, ponadto okresla wielkosc
  * oraz opisuje tytul gry
 */
    public GameTrainBrain()
    {
        handler = new Handler();
        playWindow = new PlayWindow(this, handler);
        this.addMouseListener(playWindow);

        new GameWindow(WIDTH, HEIGHT, " Train Your Brain", this);
        BufferedImageLoader loader = new BufferedImageLoader();
        sprite_sheet = loader.loadImage("/spreed1.png");
        sprite_sheet2 = loader.loadImage("/spreed2.png");
        bcg_image = loader.loadImage("/background.png");
        button_graj = loader.loadImage("/button_graj.png");
        button_latwy = loader.loadImage("/button_latwy.png");
        button_powrot = loader.loadImage("/button_powrot.png");
        button_start = loader.loadImage("/button_start.png");
        button_trudny = loader.loadImage("/button_trudny.png");
        button_wyjscie= loader.loadImage("/button_wyjscie.png");
        button_wyniki= loader.loadImage("/button_wyniki.png");
        button_zapisz= loader.loadImage("/button_zapisz.png");
        score_board= loader.loadImage("/score_board.png");

    }

    /**
     * Metoda start odpowiadaja za inicjajowanie
     */

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    /**
     * Metoda stop odpowiadaja za zatrzymywanie
     */
    public synchronized void stop()
    {
        try{
            thread.join();
            running = false;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * W metodzie run sprawdzane jest wlaczenie programu, jezeli program dziala to
     * obraz sia wyswietla (render()), rowniez czas jest zliczany przez caly czas wlaczenia programu
     */
    public void run()
    {
        long lastTime = System.nanoTime();
        double ns = 1000000000;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            while(delta>=1){
                tick();
                delta--;
            }
            if(running)
                render();
             if(System.currentTimeMillis() - timer > 1000){
                 timer += 1000;
             }
        }
        stop();
    }


    private  void tick(){

        handler.tick();
        if(gameState == STATE.Menu || gameState == STATE.EndGame|| gameState == STATE.Level|| gameState == STATE.Instruction)
        {
            playWindow.tick();                  //Jezeli Stan gry jest rowyny jednemu z powyzczych to zliczany jest ten sam czas
        }
        else if(gameState == STATE.Game)
        {
            playWindow.tick();
            if (PlayWindow.startTime <= 0)      //Jezeli natomiast stan gry jest w "Grze", to czas, gdy osiągnie wartosc mniejszą od 0
            {                                   //zmieni wyswietlany stan gry na "EndGame" czyli kolejną plansze
                handler.clearTextBlock();
                gameState = STATE.EndGame;
            }
        }
    }

    private  void  render(){                                    //W funkcji render wyswietlane są obrazy oraz napisy w zaleznosci od poszczegolnych stanow
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
           this.createBufferStrategy(3);
           return;
        }

        /*
          Ustawianie tła dla calej gry oraz tła podczas lądowania programu
         */

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0,0,WIDTH, HEIGHT);
        g.drawImage(bcg_image,0,0,WIDTH,HEIGHT,null);

        /*
          Dla danych stanow program moze renderowac rozne obrazy napszyklad HUD oraz obiekty gry
          jednak w tej grze wszystkie obiekty są tworzone w PlayWindow
         */

        if(gameState == STATE.Menu || gameState == STATE.EndGame || gameState == STATE.Stats|| gameState == STATE.Level|| gameState == STATE.Instruction|| gameState == STATE.Game)
        {
            playWindow.render(g);
        }

        handler.render(g);
        g.dispose();
        bs.show();
    }

    /**
     * Prosta gra
     * @author Krzysztof Wicki
     * @param args args
     */

    public static void main (String[] args)
    {
        new GameTrainBrain();
    }
}
