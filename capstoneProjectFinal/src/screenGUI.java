import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class screenGUI {
    private JPanel screenPanel;
    private JButton playGameButton;
    private JLabel guiLabel;
    private JButton gameHistoryButton;

    public screenGUI(CardLayout cardLayout, JPanel cardPanel) {

        screenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(135, 206, 235),  // Sky blue
                        0, getHeight(), new Color(25, 25, 112)  // Midnight blue
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        screenPanel.setLayout(new BorderLayout());
        screenPanel.setOpaque(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        guiLabel = new JLabel("Tic-Tak-Toe");
        guiLabel.setFont(new Font("Tahoma", Font.BOLD, 78));
        guiLabel.setForeground(Color.WHITE);
        guiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playGameButton = new JButton("Play Game") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;


                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);


                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(60, 179, 113),
                        0, getHeight(), new Color(46, 139, 87)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                super.paintComponent(g);
            }
        };

        playGameButton.setPreferredSize(new Dimension(200, 50));
        playGameButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        playGameButton.setForeground(Color.WHITE);
        playGameButton.setContentAreaFilled(false);
        playGameButton.setBorderPainted(false);
        playGameButton.setFocusPainted(false);
        playGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Game");
            }
        });

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(guiLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(playGameButton);
        contentPanel.add(Box.createVerticalGlue());

        screenPanel.add(contentPanel, BorderLayout.CENTER);
    }

    public JPanel getScreenPanel() {
        return screenPanel;
    }
}