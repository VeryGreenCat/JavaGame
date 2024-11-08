package gameproject;

import java.awt.Graphics2D;

public class Meteorite extends Entity {

    public Meteorite(int fallPosi) {
        super(fallPosi, 0);
        setFrameIndex(0);
        setSpeed(5);
        setIsMoving(true);
        loadImages(4,"/Fire/fire0");
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
