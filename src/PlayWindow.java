import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Klasa, w ktorej znajduje sie glowny obszar graficzny gry, dziedziczy po MouseAdapter
 */

public class PlayWindow extends MouseAdapter {

    private GameTrainBrain game;                /**Obiekt klasy GameTrainBrain*/
    private Handler handler;                    /**Obiekt klasy Handler*/
    public static long startTime;               /**Zmienna okreslajaca czas startu gry*/
    public static boolean pause=false;          /**Zmienna stanu czy wystepuje pauza*/
    public GameStatus gStatus;                  /**Obiekt obrazujacy status gry*/

    /**
     * Konstruktor klasy
     * @param game parametr odpowiedzialny za gre
     * @param handler parametr przetrzymujacy oniekty w liscie
     */
    public PlayWindow(GameTrainBrain game, Handler handler)
    {
        this.game = game;
        this.handler = handler;
        gStatus=new GameStatus();
        gStatus.reset();
        restartGame();

        Audio.playMusic(new File("menu_music.wav"));
    }

    /** Dodaj obsluge zdarzen - wcisniecie przycisku myszki*/
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //Menu
        if (game.gameState == GameTrainBrain.STATE.Menu) {                      //Czy stan gry = Menu

            if (mouseOver(mx, my, 400, 240, 200, 64)) {     //Czy wybrano przycisk Graj
                restartGame();
                game.gameState = GameTrainBrain.STATE.Level;                     //Jezeli tak to przechodzimy do stanu = Level
            }
            if (mouseOver(mx, my, 400, 330, 200, 64)) {      //Czy wybrano przycisk Wyniki
                game.gameState = GameTrainBrain.STATE.Stats;                      //Jezeli tak to przechodzimy do stanu = Statystyki
            }
            if (mouseOver(mx, my, 400, 420, 200, 64)) {       //Czy wybrano przycisk Wyjscie jezeli tak to wychodzimy z gry
                System.exit(1);
            }
        }

        // Wyjscie z statystk do glownego menu
        if (game.gameState == GameTrainBrain.STATE.Stats) {
            if (mouseOver(mx, my, 400, 615, 200, 64)) {
                game.gameState = GameTrainBrain.STATE.Menu;
            }
        }

        // Poziomy gry

        //Latwy
        if (game.gameState == GameTrainBrain.STATE.Level) {

            if (mouseOver(mx, my, 400, 330, 200, 64)) {
                restartGame();
                game.gameState = GameTrainBrain.STATE.Instruction;              //Jezeli wybierzemy poziom latwy lub trudny zostaniemy przekeirowani do instrukcji
                game.lvl = 0;
            }
            //Trudny

            if (mouseOver(mx, my, 400, 420, 200, 64)) {
                restartGame();
                game.gameState = GameTrainBrain.STATE.Instruction;
                game.lvl = 1;
            }
            if (mouseOver(mx, my, 400, 510, 200, 64)) {     //Gdy zdecydujemy się na wcisniecie powrotu do menu, zostaniemy tam przekierowani
                game.gameState = GameTrainBrain.STATE.Menu;
            }
        }

        //Instrukcja gry, ktora pojawia sie po wybraniu poziomu gry
        if (game.gameState == GameTrainBrain.STATE.Instruction) {
            if (mouseOver(mx, my, 400, 555, 200, 64)) {
                restartGame();                                                      //W tym momencie mozemy zostac przekierowani do gry lub powrocic do menu
                game.gameState = GameTrainBrain.STATE.Game;
            }
            if (mouseOver(mx, my, 400, 615, 200, 64)) {
                game.gameState = GameTrainBrain.STATE.Menu;
            }
        }

        //Gra - caly przebieg rozgrywki poczawszy od wyswietlenia obiektow po zliczanie punktow
        if (game.gameState == GameTrainBrain.STATE.Game) {                                              //Jezeli stan gry = Game wlacza sie etap Game w, ktorym w zaleznosci
            if(game.lvl==0) {                                                                           //Od wybranego poziomu pojawi sie 4 lub 7 ptaszkow
            handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));                         //Dodanie ptakow do gry
            handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));                       //Dodanie bloku tekstowego,na ktorym losowane sa poszczegolne kolory

                /**
                 * W zaleznosci od wylosowanego koloru w klasie TextBlock, funkcja okresla ktara odpowiedz jest dobra
                 * Na przyklad jezeli bedzie wylosowany kolor pomaranczowy, ktory jest pierwszym kolorem w tablicy kolorow
                 * utworzonej w klasie TextBlock, to jako element 0 gdy gracz nacisnie na okreslony obszar to zdobedzie punkt
                 * W calej funkcji powtarzaja sie elementy dlatego opisane zostaly po pierwszej zależności dla poziomu 0 oraz 1
                 */

                if (TextBlock.a == 0) {                                                         //Czy wylosowano 0 element tablicy
                    if (mouseOver(mx, my, 100, 270, 128, 128)) {            //Jezeli tak, to gdy nacisniemy na okreslone wspolrzedne dostaniemy punkt
                        Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));       //Po kazdym wylosowaniu elementy muszą byc dodawane i rysowane na nowo
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                        TextBlock.a = TextBlock.randomNumber();                                    //Przypisanie losowej liczby

                    } else if (mouseOver(mx, my, 300, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)) {
                       Audio.playSound(new File("false.wav"));                          //Jezeli wylosowano okreslony element a uzytkownik zaznaczy jedno z pol, ktore
                        System.out.println("Zle");                                                //sie nie zgadzaja to doliczany jest punkt do zlych odpowiedzi
                        GameStatus.false_score++;
                    }
                } else if (TextBlock.a == 1) {
                    if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)) {
                        Audio.playSound(new File("false.wav"));
                        System.out.println("Zle");
                        GameStatus.false_score++;
                    } else if (mouseOver(mx, my, 300, 270, 128, 128)) {
                       Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                        TextBlock.a = TextBlock.randomNumber();
                    }
                } else if (TextBlock.a == 2) {
                    if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 300, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)) {
                        Audio.playSound(new File("false.wav"));
                        System.out.println("Zle");
                        GameStatus.false_score++;
                    } else if (mouseOver(mx, my, 500, 270, 128, 128)) {
                        Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                        TextBlock.a = TextBlock.randomNumber();
                    }
                } else if (TextBlock.a == 3) {
                    if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 300, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128)) {
                        Audio.playSound(new File("false.wav"));
                        System.out.println("Zle");
                        GameStatus.false_score++;
                    } else if (mouseOver(mx, my, 700, 270, 128, 128)) {
                        Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                        TextBlock.a = TextBlock.randomNumber();
                    }
                }
            }
            else if(game.lvl==1){                                                           //Jezeli wybrano poziom trudny to dodawane sa kolejne ptaki
                handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));

                if (TextBlock.c == 0) {                                                     //Dla poziomu trudnego w klasie TextBlock utworzono nową zmienna przechowującą
                    if (mouseOver(mx, my, 100, 270, 128, 128)) {        //randomowe liczby z zakresu od 1 do 7
                        Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                        handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                        TextBlock.c = TextBlock.randomNumber2();
                    }else if (mouseOver(mx, my, 300, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)|| mouseOver(mx, my, 200, 400, 128, 128)|| mouseOver(mx, my, 400, 400, 128, 128)|| mouseOver(mx, my, 600, 400, 128, 128)) {
                        Audio.playSound(new File("false.wav"));
                        System.out.println("Zle");
                        GameStatus.false_score++;
                    }
                }else if (TextBlock.c == 1) {
                    if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)|| mouseOver(mx, my, 200, 400, 128, 128)|| mouseOver(mx, my, 400, 400, 128, 128)|| mouseOver(mx, my, 600, 400, 128, 128)) {
                        Audio.playSound(new File("false.wav"));
                        System.out.println("Zle");
                        GameStatus.false_score++;
                    }else if (mouseOver(mx, my, 300, 270, 128, 128)) {
                        Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                        handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                        TextBlock.c = TextBlock.randomNumber2();
                    }
                } else if (TextBlock.c == 2) {
                    if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 300, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)|| mouseOver(mx, my, 200, 400, 128, 128)|| mouseOver(mx, my, 400, 400, 128, 128)|| mouseOver(mx, my, 600, 400, 128, 128)) {
                       Audio. playSound(new File("false.wav"));
                        System.out.println("Zle");
                        GameStatus.false_score++;
                    } else if (mouseOver(mx, my, 500, 270, 128, 128)) {
                        Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                        handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                        TextBlock.c = TextBlock.randomNumber2();
                    }
                } else if (TextBlock.c == 3) {
                    if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 300, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 200, 400, 128, 128) || mouseOver(mx, my, 400, 400, 128, 128) || mouseOver(mx, my, 600, 400, 128, 128)) {
                        Audio.playSound(new File("false.wav"));
                        System.out.println("Zle");
                        GameStatus.false_score++;
                    } else if (mouseOver(mx, my, 700, 300, 128, 128)) {
                        Audio.playSound(new File("true.wav"));
                        handler.clearTextBlock();
                        GameStatus.score++;
                        System.out.println("Dobrze");
                        handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                        handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                        handler.addObjects(new TextBlock(370, 100, ID.TextBlock, game));
                        TextBlock.c = TextBlock.randomNumber2();
                    }
                }
                    else if (TextBlock.c == 4) {
                        if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)|| mouseOver(mx, my, 400, 400, 128, 128)|| mouseOver(mx, my, 600, 400, 128, 128)) {
                            Audio.playSound(new File("false.wav"));
                            System.out.println("Zle");
                            GameStatus.false_score++;
                        } else if (mouseOver(mx, my, 200, 400, 128, 128)) {
                            Audio.playSound(new File("true.wav"));
                            handler.clearTextBlock();
                            GameStatus.score++;
                            System.out.println("Dobrze");
                            handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                            handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                            handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                            TextBlock.c = TextBlock.randomNumber2();
                        }
                    }
                    else if (TextBlock.c == 5) {
                        if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)|| mouseOver(mx, my, 200, 400, 128, 128)|| mouseOver(mx, my, 600, 400, 128, 128)) {
                            Audio.playSound(new File("false.wav"));
                            System.out.println("Zle");
                            GameStatus.false_score++;
                        } else if (mouseOver(mx, my, 400, 400, 128, 128)) {
                            Audio.playSound(new File("true.wav"));
                            handler.clearTextBlock();
                            GameStatus.score++;
                            System.out.println("Dobrze");
                            handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                            handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                            handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                            TextBlock.c = TextBlock.randomNumber2();
                        }
                    }
                    else if (TextBlock.c == 6) {
                        if (mouseOver(mx, my, 100, 270, 128, 128) || mouseOver(mx, my, 500, 270, 128, 128) || mouseOver(mx, my, 700, 270, 128, 128)|| mouseOver(mx, my, 200, 400, 128, 128)|| mouseOver(mx, my, 400, 400, 128, 128)) {
                            Audio.playSound(new File("false.wav"));
                            System.out.println("Zle");
                            GameStatus.false_score++;
                        } else if (mouseOver(mx, my, 600, 400, 128, 128)) {
                            Audio.playSound(new File("true.wav"));
                            handler.clearTextBlock();
                            GameStatus.score++;
                            System.out.println("Dobrze");
                            handler.addObjects(new ColorBlock(100 , 270, ID.ColorBlock));
                            handler.addObjects(new ColorBlockHard(100 , 400, ID.ColorBlockHard));
                            handler.addObjects(new TextBlock(370, 100, ID.TextBlock,game));
                            TextBlock.c = TextBlock.randomNumber2();
                        }
                    }
                }

           if (mouseOver(mx, my, 400, 615, 200, 64))                //W kazdym momencie gry mozemy wyjsc do menu glownego ale, gre
           {                                                                             //bedziemy musieli rozpoczac od nowa!
               handler.clearTextBlock();
               game.gameState = GameTrainBrain.STATE.Menu;
           }
        }


        // Zagraj jeszcze raz
        if (game.gameState == GameTrainBrain.STATE.EndGame) {                           //Po okreslonym czasie wyswietla sie stan gry EndGame, w ktorym mozna wybrac czy
            if (mouseOver(mx, my, 310, 545, 400, 64)) {             //zapisujemy nasze statystyki czy gramy ponownie
                restartGame();
                game.gameState = GameTrainBrain.STATE.Level;
            }
            if (mouseOver(mx, my, 310, 445, 400, 64)) {
                Read_Save.Save();                                                             //Zapis punktow
            }
        }
    }
    /** Puszczenie myszy*/
    public void mouseReleased(MouseEvent e)
    {}
    /** Mysza nad*/
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height)          //Sprawdzanie czy myszka jest nad danym punktem na planszy gry
    {
        if (mx > x && mx < x + width){
            return my > y && my < y + height;
        }else  return  false;
    }
    public void tick()
    {
            startTime--;                                                                    //Odliczanie czasu startowego
    }

    /**
     * Za pomoca rendera w klasie PlayWindow wyswietlane sa wszystkie przyciski oraz opisy
     * dla poszczegolnych stanow gry
     * @param g grafika
     */
    public void render(Graphics g) {
        if(game.gameState == GameTrainBrain.STATE.Menu) {

            Font fnt = new Font("Comic Sans MS", 1, 50);                                               //Utworzenie nowego obiektu odpowiedzialnego za przetrzymywanie fonta
            g.setFont(fnt);
            g.setColor(Color.black);
            g.drawString("Menu", 435, 200);
            g.drawImage(GameTrainBrain.button_graj,400,240,200,64,null);                    //Dodanie przycisku odpwoiedzialnego za przejcie do stanu "Level"
            g.drawImage(GameTrainBrain.button_wyniki,400,330,200,64,null);
            g.drawImage(GameTrainBrain.button_wyjscie,400,420,200,64,null);

        }else if (game.gameState == GameTrainBrain.STATE.Stats)
        {
            Font fnt = new Font("Comic Sans MS", 1, 50);
            Font fnt1 = new Font("Comic Sans MS", 1, 25);
            g.drawImage(GameTrainBrain.score_board,40,61,900,800,null);
            g.setFont(fnt);
            g.setColor(Color.BLACK);
            g.drawString("Statystyki", 365, 40);
            g.drawImage(GameTrainBrain.button_powrot,400,615,200,64,null);
            g.setFont(fnt1);
            g.drawString(Read_Save.Read(), 100, 200);

        }else if (game.gameState == GameTrainBrain.STATE.Game)
        {
            Font fnt = new Font("Comic Sans MS", 1, 50);
            g.setFont(fnt);
            g.setColor(Color.black);
            g.drawImage(GameTrainBrain.button_powrot,400,615,200,64,null);
            Font fnt1 = new Font("Comic Sans MS", 1, 15);
            g.setFont(fnt1);
            g.drawString("Punkty : " + GameStatus.score,10,30);
            g.drawString("Błędy : " + GameStatus.false_score, 10,50);
            g.drawString("Czas : " + startTime,10,70);

        }
        else if (game.gameState == GameTrainBrain.STATE.EndGame)
        {
            Font fnt = new Font("Comic Sans MS", 1, 40);
            g.setFont(fnt);
            g.setColor(Color.BLACK);
            g.drawString("Koniec czasu!", 365, 100);
            g.drawString("Poprawne odpowiedzi : " + GameStatus.score, 225, 315);
            g.drawString("Nieprawidłowe odpowiedzi : " + GameStatus.false_score, 225, 365);
            g.drawImage(GameTrainBrain.button_graj,410,545,200,64,null);
            g.drawImage(GameTrainBrain.button_zapisz,410,445,200,64,null);
        }
        else if(game.gameState == GameTrainBrain.STATE.Level)
        {
            Font fnt = new Font("Comic Sans MS", 1, 50);
            g.setFont(fnt);
            g.setColor(Color.black);
            g.drawString("Wybierz poziom", 305, 260);

            g.drawImage(GameTrainBrain.button_latwy,400,330,200,64,null);
            g.drawImage(GameTrainBrain.button_trudny,400,420,200,64,null);
            g.drawImage(GameTrainBrain.button_powrot,400,510,200,64,null);

        }
        else if (game.gameState == GameTrainBrain.STATE.Instruction)
        {
            Font fnt = new Font("Comic Sans MS", 1, 50);
            Font fnt1 = new Font("Comic Sans MS", 1, 25);
            g.setFont(fnt);
            g.setColor(Color.black);
            g.drawString("Zasady gry!", 370, 200);
            g.drawImage(GameTrainBrain.button_start,400,555,200,64,null);
            g.drawImage(GameTrainBrain.button_powrot,400,615,200,64,null);
            g.setFont(fnt1);
            g.setColor(Color.black);
            g.drawString("Twoim zadaniem jest wybranie koloru ptaka na podstawie wyswietlonego napisu. ", 35, 300);
            g.drawString("Pamietaj zeby się nie pomylic koloru z napisem! ", 225, 350);
        }
    }

    /**
     * Restart wszystkich parametrow w grze
     */
    private void restartGame(){
        gStatus.resetPoints();
        gStatus.resetFalse_score();
        startTime=15;
        pause=false;
    }

}
