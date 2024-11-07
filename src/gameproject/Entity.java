package gameproject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity implements Runnable {

    protected int x;
    protected int y;
    protected int speed;
    protected BufferedImage[] frames;
    protected int frameIndex;
    protected boolean isMoving;
    protected final int screenWidth = 1100;
    protected final int screenHeight = 600;
    protected int imgWidth;
    protected int imgHeight;

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

    abstract void loadImages();

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
