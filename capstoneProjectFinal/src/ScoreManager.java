import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreManager {
    private int playerXWins = 0;
    private int playerOWins = 0;

    public void saveScores() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pw.println("Game Date and Time: " + timestamp);
            pw.println("Player X Wins: " + playerXWins);
            pw.println("Player O Wins: " + playerOWins);
            pw.println("------");
        } catch (IOException e) {
            System.out.println("Error saving the scores.");
        }
    }

    public void incrementPlayerXWins() {
        playerXWins++;
    }

    public void incrementPlayerOWins() {
        playerOWins++;
    }

    public int getPlayerXWins() {
        return playerXWins;
    }

    public int getPlayerOWins() {
        return playerOWins;
    }
}
