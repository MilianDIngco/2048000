package src;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    public GamePanel(int size) {
        this.setPreferredSize(new Dimension(size, size));
        this.setBackground(App.WINDOW_PALETTE[App.GAME_BACKGROUND]);
    }

    @Override
    public void run() {

    }

}
