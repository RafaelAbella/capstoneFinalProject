import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class tttApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("TicTakToe");
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        screenGUI screen = new screenGUI(cardLayout, cardPanel);
        GamePanel gamePanel = new GamePanel(cardLayout, cardPanel);

        cardPanel.add(screen.getScreenPanel(), "Screen");
        cardPanel.add(gamePanel, "Game");
        frame.add(cardPanel);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gamePanel.getScoreManager().saveScores();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
