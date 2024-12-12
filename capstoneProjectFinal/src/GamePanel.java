import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {

    private final GameLogic gameLogic;
    private final ScoreManager scoreManager;
    private final JButton resetButton;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private int boardWidth;
    private int boardHeight;
    private int cellSize;
    private int gridOffsetX;
    private int gridOffsetY;

    private Color backgroundColor;
    private Color gridColor;
    private Color sidebarColor;
    private Color textColor;
    private Color xColor;
    private Color oColor;

    private JComboBox<String> themeSelector;

    public GamePanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        setMinimumSize(new Dimension(400, 300));
        setPreferredSize(new Dimension(600, 400));

        gameLogic = new GameLogic();
        scoreManager = new ScoreManager();

        addMouseListener(new XOListener());

        resetButton = new JButton("Play Again?");
        resetButton.setBackground(new Color(64, 224, 228));
        resetButton.addActionListener(this);
        resetButton.setVisible(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(resetButton);

        String[] themes = { "Light", "Dark", "Colorful", "Custom" };
        themeSelector = new JComboBox<>(themes);
        themeSelector.addActionListener(this);
        buttonPanel.add(themeSelector);

        add(buttonPanel, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBoardDimensions();
            }
        });

        updateBoardDimensions();
        setTheme("Light");
    }

    private void setTheme(String theme) {
        switch (theme) {
            case "Light":
                backgroundColor = new Color(0xF1F1F1);
                gridColor = new Color(0x3f3f44);
                sidebarColor = new Color(0xB0C4DE);
                textColor = Color.BLACK;
                xColor = new Color(0x8B0000);
                oColor = new Color(0x00008B);
                break;
            case "Dark":
                backgroundColor = new Color(0x333333);
                gridColor = new Color(0xA9A9A9);
                sidebarColor = new Color(0x444444);
                textColor = Color.WHITE;
                xColor = new Color(0xFF4500);
                oColor = new Color(0x00FFFF);
                break;
            case "Colorful":
                backgroundColor = new Color(0xFFFFE0);
                gridColor = new Color(0x008B8B);
                sidebarColor = new Color(0xFFD700);
                textColor = Color.BLACK;
                xColor = new Color(0xFF6347);
                oColor = new Color(0x1E90FF);
                break;
            case "Custom":
                Color newBackground = JColorChooser.showDialog(this, "Choose Background Color", backgroundColor);
                if (newBackground != null) backgroundColor = newBackground;

                Color newGrid = JColorChooser.showDialog(this, "Choose Grid Line Color", gridColor);
                if (newGrid != null) gridColor = newGrid;

                Color newX = JColorChooser.showDialog(this, "Choose X Color", xColor);
                if (newX != null) xColor = newX;

                Color newO = JColorChooser.showDialog(this, "Choose O Color", oColor);
                if (newO != null) oColor = newO;

                Color newSidebar = JColorChooser.showDialog(this, "Choose Sidebar Color", sidebarColor);
                if (newSidebar != null) sidebarColor = newSidebar;

                textColor = Color.BLACK;

                break;
        }
        setBackground(backgroundColor);
        repaint();
    }

    private void updateBoardDimensions() {
        boardWidth = getWidth();
        boardHeight = getHeight();
        cellSize = Math.min((boardWidth - 120) / 3, (boardHeight) / 3);
        gridOffsetX = (boardWidth - 120 - cellSize * 3) / 2;
        gridOffsetY = (boardHeight - cellSize * 3) / 2;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateBoardDimensions();
        drawBoard(g);
        drawUI(g);
        drawGame(g);
    }

    private void drawBoard(Graphics g) {
        g.setColor(gridColor);
        int lineWidth = Math.max(3, cellSize / 20);
        int lineLength = cellSize * 3;

        for (int i = 1; i < 3; i++) {
            int y = gridOffsetY + i * cellSize;
            g.fillRoundRect(gridOffsetX, y, lineLength, lineWidth, 5, 30);
        }

        for (int i = 1; i < 3; i++) {
            int x = gridOffsetX + i * cellSize;
            g.fillRoundRect(x, gridOffsetY, lineWidth, lineLength, 30, 5);
        }
    }

    private void drawUI(Graphics g) {
        int sidebarWidth = 120;
        g.setColor(sidebarColor);
        g.fillRect(boardWidth - sidebarWidth, 0, sidebarWidth, boardHeight);

        g.setColor(textColor);
        g.setFont(new Font("Tahoma", Font.BOLD, Math.max(12, cellSize / 10)));

        g.drawString("Win Count", boardWidth - sidebarWidth + 10, 30);
        g.drawString("X: " + scoreManager.getPlayerXWins(), boardWidth - sidebarWidth + 10, 60);
        g.drawString("O: " + scoreManager.getPlayerOWins(), boardWidth - sidebarWidth + 10, 90);

        if (gameLogic.isGameDone()) {
            g.drawString("Winner: " + gameLogic.getWinnerText(), boardWidth - sidebarWidth + 10, 150);
        } else {
            g.drawString("Turn: " + gameLogic.getCurrentPlayerText(), boardWidth - sidebarWidth + 10, 150);
        }
    }

    private void drawGame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(Math.max(3, cellSize / 20)));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = gridOffsetX + i * cellSize + (cellSize - 50) / 2;
                int y = gridOffsetY + j * cellSize + (cellSize - 50) / 2;

                if (gameLogic.getBoardValue(i, j) == 1) { // Draw X
                    g2.setColor(xColor);
                    g2.drawLine(x, y, x + 50, y + 50);
                    g2.drawLine(x, y + 50, x + 50, y);
                } else if (gameLogic.getBoardValue(i, j) == 2) { // Draw O
                    g2.setColor(oColor);
                    g2.drawOval(x, y, 50, 50);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            gameLogic.resetGame();
            resetButton.setVisible(false);
            repaint();
        } else if (e.getSource() == themeSelector) {
            String selectedTheme = (String) themeSelector.getSelectedItem();
            setTheme(selectedTheme);
        }
    }

    private class XOListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (gameLogic.isGameDone()) return;

            int selX = (e.getX() - gridOffsetX) / cellSize;
            int selY = (e.getY() - gridOffsetY) / cellSize;

            if (selX >= 0 && selX < 3 && selY >= 0 && selY < 3 &&
                    gameLogic.makeMove(selX, selY)) {
                if (gameLogic.isGameDone()) {
                    if (gameLogic.getWinner() == 1) scoreManager.incrementPlayerXWins();
                    else if (gameLogic.getWinner() == 2) scoreManager.incrementPlayerOWins();
                    resetButton.setVisible(true);
                }
                repaint();
            }
        }

        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
    }
}
