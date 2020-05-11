import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Klasa odpowiedzialna za odtwarzanie dzwieku z pliku
 */
public class Audio {

    /**
     * Konstruktor - odtwarzania dzwieku z pliku
     * @param f obiekt klasy File reprezentujacy sciezke do pliku MP3
     */

    public static synchronized void playSound(final File f) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    /**
     * Konstruktor - odtwarzania muzyki z pliku
     * @param f obiekt klasy File reprezentujacy sciezke do pliku MP3
     */
    public static synchronized void playMusic(final File f) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
                clip.open(inputStream);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
