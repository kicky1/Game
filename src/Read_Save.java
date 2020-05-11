import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * W klasie Read_Save wykonywany jest odczyt oraz zapis do pliku tekstowego
 */

public class Read_Save {
    /** Imie, ktore zostaje podane podczas gry*/
    public static String userName;

    /**
     * Odczytywanie pliku tekstowego oraz zamiana go na String
     * @return text
     */
    public static String Read() {
        String text = " ";
        try {
            text = Files.readString(Paths.get("score.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * Zapisywanie imienia, punktow oraz daty do pliku tekstowego score.txt
     */
    public static void Save() {

        userName = JOptionPane.showInputDialog("Wpisz swoje imie : ");
        try {
            PrintWriter scoreSave = new PrintWriter(new FileWriter("score.txt"));
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = currentDate.format(dateFormat);
            scoreSave.println(userName + " - " + GameStatus.score + " / " + GameStatus.false_score + " Data : " +formattedDate);
            scoreSave.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}



