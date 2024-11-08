package gameproject;

import java.awt.Graphics2D;
import java.util.Random;

public class GrayBird extends Bird {

    private boolean returning;
    private Random random;

    public GrayBird(int flyHeight) {
        super(flyHeight);
        random = new Random();
        returning = false;
        loadImages(6, "/Bird2/tile00");
        setX(getX() - imgWidth); //starting from left and spawn is hidden
    }

    @Override
    public void run() {
        while (isMoving) {
            try {
                Thread.sleep(60); // Adjust as needed for frame rate

                if (!returning) {
                    if (random.nextDouble() < 0.001) { //0.5% chance
                        returning = true;
                        speed *= -1;
                    }
                }

                if (isAlive) {
                    x += speed; // Move right by `speed` pixels
                    frameIndex = (frameIndex + 1) % frames.length; // Cycle through frames
                } else {
                    y += 20;
                }

                if (x > screenWidth || y > screenHeight) { // Stop when the bird reaches the right edge of the screen
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
            int drawWidth = (speed < 0 ? -imgWidth : imgWidth) * 2;
            g2d.drawImage(frames[frameIndex], x, y, drawWidth, imgHeight * 2, null);
        }
    }

}
