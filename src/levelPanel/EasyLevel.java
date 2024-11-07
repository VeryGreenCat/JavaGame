package levelPanel;

import gameproject.Bird;
import gameproject.Bullet;
import gameproject.KeyHandler;
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

public class EasyLevel extends JPanel implements Runnable {

    private final int screenWidth = 1100;
    private final int screenHeight = 600;
    JLabel jlbScorePoint;

    private boolean running;
    private Random random;

    private ArrayList<Bird> birds;
    private ArrayList<Meteorite> meteorites;
    private ArrayList<Bullet> bullets;

    private int birdCount;
    private int meteoritesCount;
    private int bulletCount;

    private final int birdCountMax = 2;
    private final int meteoritesCountMax = 3;
    private final int bulletCountMax = 50;

    private Player player;
    private KeyHandler keyListen;

    private int scorePoint;

    public EasyLevel() {
        setLayout(null);
        

        // Level title label
        JLabel jlbLevel = new JLabel("Easy");
        jlbLevel.setBounds(10, 5, 100, 20);
        jlbLevel.setFont(new Font("Arial", Font.BOLD, 20));
        add(jlbLevel);

        //ScorePoint
        jlbScorePoint = new JLabel("Score: " + scorePoint);
        jlbScorePoint.setBounds(975, 5, 110, 20);
        jlbScorePoint.setFont(new Font("Arial", Font.BOLD, 20));
        add(jlbScorePoint);

        //set value
        running = true;
        random = new Random();
        birds = new ArrayList<>();
        birdCount = 0;
        meteorites = new ArrayList<>();
        meteoritesCount = 0;
        bullets = new ArrayList<>();
        bulletCount = 0;
        player = new Player(500);
        keyListen = new KeyHandler();
        scorePoint = 100;

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
        running = false;
        player.setIsMoving(false);
    }

    @Override
    public void run() {
        running = true;

        while (running) {

            if (random.nextDouble() < 0.01 && birdCount < birdCountMax) { // 0.1% chance
                birdCount++;
                int flyHeight = random.nextInt(250) + 50; // Random height between 50-300
                Bird newBird = new Bird(flyHeight);
                birds.add(newBird);
                new Thread(newBird).start(); // Start bird's movement
            }

            if (random.nextDouble() < 0.01 && meteoritesCount < meteoritesCountMax) { // 0.1% chance
                meteoritesCount++;
                int fallPosi = random.nextInt(screenWidth - 20); // Random width between 0-screenWidth - 20
                Meteorite newMeteorite = new Meteorite(fallPosi);
                meteorites.add(newMeteorite);
                new Thread(newMeteorite).start(); // Start bird's movement
            }

            if (player.getClick() && bulletCount < bulletCountMax) {
                bulletCount++;
                Bullet newBullet = new Bullet(player);
                bullets.add(newBullet);
                new Thread(newBullet).start();
                player.setClick(false);
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
        updateScorePoint();
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
                    bulletIterator.remove();
                    birdIterator.remove();
                    bulletCount--;
                    birdCount--;
                    scorePoint -= 20;
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
                    meteoritesCount--;
                    scorePoint += 2;
                    break;
                }
            }
        }
    }

    private void updateScorePoint() {
        jlbScorePoint.setText("Score: " + scorePoint);
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
                meteoritesCount--;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            // Background image or color
            g2d.drawImage(ImageIO.read(getClass().getResourceAsStream("/background/Bg1.png")), 0, 0, screenWidth, screenHeight, this);

        } catch (IOException ex) {
            Logger.getLogger(EasyLevel.class.getName()).log(Level.SEVERE, null, ex);
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
