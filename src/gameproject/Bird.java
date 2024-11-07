package gameproject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bird extends Entity {

    public Bird(int flyHeight) {
        super(0, flyHeight);
        setFrameIndex(0);
        setSpeed(4);
        setIsMoving(true);
        loadImages();
    }

    @Override
    protected void loadImages() {
        frames = new BufferedImage[6];
        try {
            for (int i = 0; i < 6; i++) {
                frames[i] = ImageIO.read(getClass().getResourceAsStream("/Bird1/tile00" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgWidth = frames[0].getWidth();
        imgHeight = frames[0].getHeight();
        setX(getX() - imgWidth);
    }

    @Override
    public void run() {
        while (isMoving) {
            try {
                Thread.sleep(60); // Adjust as needed for frame rate
                x += speed; // Move right by `speed` pixels
                frameIndex = (frameIndex + 1) % frames.length; // Cycle through frames

                if (x > screenWidth) { // Stop when the bird reaches the right edge of the screen
                    isMoving = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
