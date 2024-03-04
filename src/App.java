package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App {

    /* WINDOW SETTINGS */
    public static JFrame mainFrame;
    static int hgap = 0, vgap = 0; // for main panel
    static int titleHeight = 100;
    static String fontName = "Courier New";

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /* COLOR PALETTE */
    public static final Color[] TILE_PALETTE = {
            new Color(236, 140, 228), // 2
            new Color(233, 96, 242), // 4
            new Color(211, 59, 246), // 8
            new Color(186, 31, 247), // 16
            new Color(161, 22, 230), // 32
            new Color(134, 11, 205), // 64
            new Color(117, 5, 169), // 128
            new Color(89, 2, 153), // 256
            new Color(75, 0, 121), // 512
            new Color(56, 0, 103), // 1024
            new Color(50, 0, 87) // 2048
    };

    public static final Color[] WINDOW_PALETTE = {
            new Color(254, 253, 255), // TEXT
            new Color(255, 229, 220), // WINDOW_BACKGROUND
            new Color(255, 192, 164), // GAME_BACKGROUND
            new Color(255, 154, 83) // SQUARE_BACKGROUND
    };

    public static final int _2 = 0, _4 = 1, _8 = 2, _16 = 3, _32 = 4, _64 = 5, _128 = 6, _256 = 7, _512 = 8, _1024 = 9,
            _2048 = 10;

    public static final int TEXT = 0, WINDOW_BACKGROUND = 1, GAME_BACKGROUND = 2, SQUARE_BACKGROUND = 3;

    public static void main(String[] args) {
        mainFrame = new JFrame("2,048,000");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVaryscreen();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setMinimumSize(
                new Dimension((int) (screenSize.getWidth() / 2), (int) (screenSize.getHeight() * 2 / 3)));

        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout(hgap, vgap));
        mainPanel.setBackground(WINDOW_PALETTE[WINDOW_BACKGROUND]);

        // title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setPreferredSize(new Dimension(0, titleHeight));
        titlePanel.setBackground(WINDOW_PALETTE[WINDOW_BACKGROUND]);
        // title text
        JLabel titleLabel = new JLabel("2048,000");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension((int) (screenSize.getWidth() / 2), titleHeight));
        titleLabel.setFont(new Font(fontName, Font.BOLD, 100));
        titlePanel.add(titleLabel);

        // game panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(WINDOW_PALETTE[WINDOW_BACKGROUND]);
        GamePanel gamePanel = new GamePanel((int) (screenSize.getWidth() / 4));
        gamePanel.gameThread.start(); // starts gamethread
        centerPanel.add(gamePanel);

        // add everything to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    public static int setFontSizeToFit(String fontName, Dimension size) {
        // get current width,

        return 0;
    }

    public static void setFullscreen() {
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setUndecorated(true);
    }

    public static void setVaryscreen() {
        mainFrame.setResizable(true);
        mainFrame.setBounds(100, 100, (int) (screenSize.getWidth() / 2), (int) (screenSize.getHeight() / 2));
    }
}