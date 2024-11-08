package gameproject;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private final int screenWidth = 1100;
    private final int screenHeight = 600;

    private Random random;
    private ArrayList<Bird> birds;
    private int birdCount;
    private final int BIRD_COUNT_MAX = 10;

    paintBird pb;

    ButtonListen listen;
    JButton jbtEasy = new JButton("Easy");
    JButton jbtNormal = new JButton("Normal");
    JButton jbtHard = new JButton("Hard");
    JButton jbtImpossible = new JButton("Impossible");
    Font font = new Font("Arial", Font.BOLD, 16);

    public MenuPanel(GameProject game) {

        setLayout(null);
        listen = new ButtonListen(game);
        random = new Random();
        birds = new ArrayList<>();

        jbtEasy.addActionListener(listen);
        jbtNormal.addActionListener(listen);
        jbtHard.addActionListener(listen);
        jbtImpossible.addActionListener(listen);

        JLabel title = new JLabel("Protect Planet");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        title.setBounds(445, 120, 300, 60);
        jbtEasy.setBounds(445, 190, 200, 60);
        jbtNormal.setBounds(445, 260, 200, 60);
        jbtHard.setBounds(445, 330, 200, 60);
        jbtImpossible.setBounds(445, 400, 200, 60);

        jbtEasy.setFont(font);
        jbtNormal.setFont(font);
        jbtHard.setFont(font);
        jbtImpossible.setFont(font);

        add(title);
        add(jbtEasy);
        add(jbtNormal);
        add(jbtHard);
        add(jbtImpossible);

        pb = new paintBird();
        pb.setBounds(0, 0, screenWidth, screenHeight);
        pb.setOpaque(false);
        new Thread(pb).start();
        add(pb);
        birdCount = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            g2d.drawImage(ImageIO.read(getClass().getResourceAsStream("/background/bgMain.png")), 0, 0, screenWidth, screenHeight, this);
        } catch (IOException ex) {
            Logger.getLogger(MenuPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class ButtonListen implements ActionListener {

        GameProject game;

        public ButtonListen(GameProject game) {
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbtEasy) {
                game.startGame(1);
            } else if (e.getSource() == jbtNormal) {
                game.startGame(2);
            } else if (e.getSource() == jbtHard) {
                game.startGame(3);
            } else if (e.getSource() == jbtImpossible) {
                game.startGame(4);
            }
        }

    }

    class paintBird extends JPanel implements Runnable {

        private boolean panelActive;

        public paintBird() {
            this.panelActive = true;
        }

        void setPanelActive(boolean panelActive) {
            this.panelActive = panelActive;
        }

        @Override
        public void run() {
            while (panelActive) {
                if (random.nextDouble() < 0.01 && birdCount < BIRD_COUNT_MAX) { // 0.1% chance
                    birdCount++;
                    int flyHeight = random.nextInt(250) + 50; // Random height between 50-300
                    Bird newBird = new Bird(flyHeight);
                    birds.add(newBird);
                    new Thread(newBird).start(); // Start bird's movement
                }

                updateBirds();
                repaint();

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void updateBirds() {
            Iterator<Bird> iterator = birds.iterator();
            while (iterator.hasNext()) {
                Bird bird = iterator.next();
                if (bird.getX() > screenWidth) {
                    iterator.remove(); // Safely remove the bird if it’s off-screen
                    birdCount--;
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Draw each bird
            for (Bird bird : birds) {
                bird.draw(g2d);
            }

        }
    }

}
