package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    static final int FPS = 100;
    Thread gameThread;

    int x = 0;
    int y = 0;
    int xVel = 1;
    int yVel = 1;

    public GamePanel(int size) {
        gameThread = new Thread(this);
        this.setPreferredSize(new Dimension(size, size));
        this.setBackground(App.WINDOW_PALETTE[App.GAME_BACKGROUND]);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();

                repaint();

                delta--;
            }

        }
    }

    public void update() {
        x += xVel;
        y += yVel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.dispose();
    }

}
