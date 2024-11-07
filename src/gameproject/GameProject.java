package gameproject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import levelPanel.EasyLevel;
import levelPanel.HardLevel;
import levelPanel.ImpossibleLevel;
import levelPanel.NormalLevel;

public class GameProject extends JFrame {

    MenuPanel menuPanel;
    Listening listen = new Listening();
    EasyLevel lv1;
    NormalLevel lv2;
    HardLevel lv3;
    ImpossibleLevel lv4;
    Thread currentLevelThread;
    int currentLevel;

    public GameProject() {
        setTitle("Protect Planet");
        setSize(1100, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPanel = new MenuPanel(this);
        add(menuPanel);

        addKeyListener(listen);
        setFocusable(true);
        System.out.println(requestFocusInWindow());

        lv1 = new EasyLevel();
        lv2 = new NormalLevel();
        lv3 = new HardLevel();
        lv4 = new ImpossibleLevel();
        currentLevel = 0;

        setVisible(true);

    }

    public void startGame(int level) {

        if (currentLevelThread != null && currentLevelThread.isAlive()) {
            stopCurrentLevel();
        }

        getContentPane().removeAll();
        currentLevel = level;

        switch (level) {
            case 1 -> {
                add(lv1);
                currentLevelThread = new Thread(lv1);
            }
            case 2 -> {
                add(lv2);
                currentLevelThread = new Thread(lv2);
            }
            case 3 -> {
                add(lv3);
                currentLevelThread = new Thread(lv3);
            }
            case 4 -> {
                add(lv4);
                currentLevelThread = new Thread(lv4);
            }
            default -> {
                System.out.println("Invalid level selection.");
                return;
            }
        }
        currentLevelThread.start();  // Start the level thread

        revalidate(); // Refresh the layout
        repaint();
    }

    public void stopCurrentLevel() {
        if (currentLevelThread != null && currentLevelThread.isAlive()) {
            switch (currentLevel) {
                case 1 -> lv1.stop();
                case 2 -> lv2.stop();
                case 3 -> lv3.stop();
                case 4 -> lv4.stop();
                default -> {
                }
            }
        }
    }

    class Listening implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            //nothing
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.out.println("Return to Menu");

                stopCurrentLevel();
                getContentPane().removeAll();
                add(menuPanel);
                revalidate(); //refresh
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //nothing
        }

    }

    public static void main(String[] args) {
        new GameProject();
    }
}
