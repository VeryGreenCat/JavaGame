package gameproject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Bullet extends Entity {

    private double angle;

    public Bullet(Player player) {//player.getY()-20
        super(player.getX(), player.getMouseX() < player.getX() ? player.getY() - 20 : player.getY() + 17);
        angle = player.getAngle();
        setSpeed(10);
        setIsMoving(true);
        loadImages();
    }

    @Override
    void loadImages() {
        frames = new BufferedImage[1];
        try {
            frames[0] = ImageIO.read(getClass().getResourceAsStream("/Gun/Bullet.png"));

        } catch (IOException ex) {
            Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
        }
        imgWidth = frames[0].getWidth();
        imgHeight = frames[0].getHeight();
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isMoving && frames[0] != null) {
            g2d.drawImage(frames[0], x, y, imgWidth, imgHeight, null);
        }
    }

    @Override
    public void run() {
        while (isMoving) {
            x += Math.cos(angle) * getSpeed();
            y += Math.sin(angle) * getSpeed();

            // Stop and remove bullet if it goes off-screen
            if (x < 0 || x > screenWidth || y < 0 || y > screenHeight) {
                isMoving = false; // Mark bullet for removal
            }

            try {
                Thread.sleep(16); // Adjust for smooth movement
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
