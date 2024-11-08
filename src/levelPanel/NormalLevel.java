package levelPanel;

import gameproject.Bird;
import gameproject.Bullet;
import gameproject.Meteorite;
import gameproject.Player;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NormalLevel extends JPanel implements Runnable {

    private final int screenWidth = 1100;
    private final int screenHeight = 600;
    private JLabel jlbScorePoint;
    private JLabel jlbTimer;
    Font font20;

    private boolean panelActive;
    private boolean isPlaying;
    private boolean isWinning;
    private Random random;

    private ArrayList<Bird> birds;
    private ArrayList<Meteorite> meteorites;
    private ArrayList<Bullet> bullets;

    private int birdCount;
    private int meteoriteCount;
    private int bulletCount;

    private int loopCounter;
    private int timePlay;

    private final int BIRD_COUNT_MAX = 3;
    private final int METEORITE_COUNT_MAX = 5;
    private final int BULLET_COUNT_MAX = 50;
    private final int GAME_TIMER = 15;

    private Player player;
    private int scorePoint;

    public NormalLevel() {
        setLayout(null);
        font20 = new Font("Arial", Font.BOLD, 20);

        // Level title label
        JLabel jlbLevel = new JLabel("Normal");
        jlbLevel.setBounds(10, 8, 100, 20);
        jlbLevel.setFont(font20);
        add(jlbLevel);

        //timer
        jlbTimer = new JLabel(String.valueOf(GAME_TIMER - timePlay));
        jlbTimer.setBounds(520, 8, 100, 20);
        jlbTimer.setFont(font20);
        add(jlbTimer);

        //ScorePoint
        jlbScorePoint = new JLabel("Score: " + scorePoint);
        jlbScorePoint.setBounds(975, 8, 110, 20);
        jlbScorePoint.setFont(font20);
        add(jlbScorePoint);

        //set value
        panelActive = true;
        random = new Random();
        birds = new ArrayList<>();
        meteorites = new ArrayList<>();
        bullets = new ArrayList<>();
        player = new Player(500);

        resetLevel();

        //player act as a event manager that manage the event passed by EasyLevel
        addMouseMotionListener(player); //handle mouse motion
        addMouseListener(player); //handle mouse click
        addKeyListener(player); //handle player walk
        setFocusable(true);

        setFocusable(true);
        System.out.println(requestFocusInWindow());
        new Thread(player).start();
    }

    public void stop() {
        panelActive = false;
        player.setIsMoving(false);
        resetLevel();
    }

    public void resetLevel() {
        isPlaying = true;
        isWinning = true;
        birds.clear();
        meteorites.clear();
        bullets.clear();

        birdCount = 0;
        meteoriteCount = 0;
        bulletCount = 0;
        loopCounter = 0;
        timePlay = 0;
        scorePoint = 100;
    }

    @Override
    public void run() {

        panelActive = true;

        while (panelActive) {

            if (isPlaying) {
                //check score point
                isPlaying = !(scorePoint <= 0 || (timePlay >= GAME_TIMER && isWinning));
                isWinning = scorePoint > 0;

                //check time
                if (loopCounter >= 60) {
                    timePlay += 1;
                    loopCounter = 0;
                    System.out.println("NormalLevel is running for " + timePlay + " seconds");

                }
                loopCounter++;

                if (random.nextDouble() < 0.01 && birdCount < BIRD_COUNT_MAX) { // 0.1% chance
                    birdCount++;
                    int flyHeight = random.nextInt(250) + 50; // Random height between 50-300
                    Bird newBird = new Bird(flyHeight);
                    birds.add(newBird);
                    new Thread(newBird).start(); // Start bird's movement
                }

                if (random.nextDouble() < 0.01 && meteoriteCount < METEORITE_COUNT_MAX) { // 0.1% chance
                    meteoriteCount++;
                    int fallPosi = random.nextInt(screenWidth - 20); // Random position between 0-screenWidth - 20
                    Meteorite newMeteorite = new Meteorite(fallPosi);
                    meteorites.add(newMeteorite);
                    new Thread(newMeteorite).start(); // Start Meteorite's movement
                }

                if (player.getClick() && bulletCount < BULLET_COUNT_MAX) {
                    bulletCount++;
                    Bullet newBullet = new Bullet(player);
                    bullets.add(newBullet);
                    new Thread(newBullet).start(); // Start Bullet's movement
                    player.setClick(false);
                }

            }
            update();
            checkCollision();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        updateBirds();
        updateMeteorites();
        updateBullet();
        updateLabel();
    }

    private void checkCollision() {
        // Check collision between bullets and birds
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            boolean bulletHit = false;

            //bird collision
            Iterator<Bird> birdIterator = birds.iterator();
            while (birdIterator.hasNext()) {
                Bird bird = birdIterator.next();
                if (bullet.getBounds().intersects(bird.getBounds())) {
                    bird.setIsAlive(false);
                    bulletIterator.remove();
                    bulletCount--;
                    birdCount--;
                    scorePoint -= 10;
                    timePlay -= 5;
                    bulletHit = true;
                    break; // Exit inner loop since this bullet is now gone
                }
            }

            if (bulletHit) {
                continue;
            }

            //meteorite collision
            Iterator<Meteorite> meteoriteIterator = meteorites.iterator();
            while (meteoriteIterator.hasNext()) {
                Meteorite meteorite = meteoriteIterator.next();
                if (bullet.getBounds().intersects(meteorite.getBounds())) {
                    bulletIterator.remove();
                    meteoriteIterator.remove();
                    bulletCount--;
                    meteoriteCount--;
                    scorePoint += 2;
                    break;
                }
            }
        }
    }

    private void updateLabel() {
        jlbScorePoint.setText("Score: " + scorePoint);
        jlbTimer.setText(String.valueOf(GAME_TIMER - timePlay));
    }

    private void updateBullet() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            //System.out.println(bullet.getX() + " " + bullet.getY());
            if (bullet.getX() > screenWidth || bullet.getY() > screenHeight || bullet.getY() < 0 || bullet.getX() < 0) {
                iterator.remove(); // Safely remove the bird if it’s off-screen
                bulletCount--;
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

    private void updateMeteorites() {
        Iterator<Meteorite> iterator = meteorites.iterator();
        while (iterator.hasNext()) {
            Meteorite meteorite = iterator.next();
            if (meteorite.getY() > screenHeight) {
                iterator.remove(); // Safely remove the bird if it’s off-screen
                meteoriteCount--;
                scorePoint -= 10;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            // Background image or color
            g2d.drawImage(ImageIO.read(getClass().getResourceAsStream("/background/Bg2.png")), 0, 0, screenWidth, screenHeight, this);

            if (isWinning && !isPlaying) {
                g2d.drawImage(ImageIO.read(getClass().getResourceAsStream("/background/Success.png")), 0, 0, screenWidth, screenHeight, this);
            }
            if (!isWinning && !isPlaying) {
                g2d.drawImage(ImageIO.read(getClass().getResourceAsStream("/background/Fail.png")), 0, 0, screenWidth, screenHeight, this);
            }
        } catch (IOException ex) {
            Logger.getLogger(NormalLevel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Draw each bird
        for (Bird bird : birds) {
            bird.draw(g2d);
        }

        for (Meteorite meteorite : meteorites) {
            meteorite.draw(g2d);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g2d);
        }

        player.draw(g2d);
    }
}
