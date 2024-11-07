package gameproject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Meteorite extends Entity {

    public Meteorite(int fallPosi) {
        super(fallPosi, 0);
        setFrameIndex(0);
        setSpeed(5);
        setIsMoving(true);
        loadImages();
    }

    @Override
    protected void loadImages() {
        frames = new BufferedImage[4];
        try {
            for (int i = 0; i < 4; i++) {
                frames[i] = ImageIO.read(getClass().getResourceAsStream("/Fire/fire0" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgWidth = frames[0].getWidth();
        imgHeight = frames[0].getHeight();
        setY(getY() - imgHeight);
    }

    @Override
    public void run() {
        while (isMoving) {
            try {
                Thread.sleep(60); // Adjust as needed for frame rate
                y += speed; // Move right by `speed` pixels
                frameIndex = (frameIndex + 1) % frames.length; // Cycle through frames

                if (y > screenHeight) { // Stop when the bird reaches the right edge of the screen
                    isMoving = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        if (isMoving && frames[frameIndex] != null) {
            g2d.drawImage(frames[frameIndex], x, y, imgWidth*2, imgHeight*2, null);
        }
    }
}
