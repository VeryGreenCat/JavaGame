package gameproject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Entity implements Runnable {

    protected final int screenWidth = 1100;
    protected final int screenHeight = 600;
    protected int imgWidth;
    protected int imgHeight;

    protected int x;
    protected int y;
    protected int speed;
    protected int loopCounter;

    protected int frameIndex;
    protected boolean isMoving;
    protected BufferedImage[] frames;

    Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        if (isMoving && frames[frameIndex] != null) {
            g2d.drawImage(frames[frameIndex], x, y, imgWidth, imgHeight, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, imgWidth, imgHeight);
    }

    protected void loadImages(int numberOfFrame, String path) {
        frames = new BufferedImage[numberOfFrame];
        try {
            for (int i = 0; i < numberOfFrame; i++) {
                frames[i] = ImageIO.read(getClass().getResourceAsStream(path + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgWidth = frames[0].getWidth();
        imgHeight = frames[0].getHeight();
    }

    //getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    public int getFrameIndex() {
        return frameIndex;
    }
    
    public int getImgWidth() {
        return imgWidth;
    }

    //setter
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

}
