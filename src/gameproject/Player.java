package gameproject;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Player extends Entity implements MouseMotionListener, MouseListener, KeyListener {

    private int mouseX;
    private int mouseY;
    private double angle;
    private boolean click;
    private boolean isShooting;
    private boolean rePosition;
    KeyHandler kh;

    public Player(int x) {
        super(x, 500);
        loadImages(10, "/Gun/tile00");
        setIsMoving(true);

        click = false;
        isShooting = false;
        rePosition = false;
        kh = new KeyHandler();
    }

    public void update(int mouseX, int mouseY) {
        // Rotate if not shooting
        if (!isShooting) {
            angle = Math.atan2(mouseY - y, mouseX - x);

            if ((mouseX > x && imgHeight < 0) || (mouseX < x && imgHeight > 0)) {
                imgHeight *= -1;
            }

            if (mouseX < x && !rePosition) {
                y -= imgHeight;
                rePosition = true;
            } else if (mouseX > x && rePosition) {
                y -= imgHeight;
                rePosition = false;
            }
        }

        if (kh.leftPressed) {
            x -= speed; // Move left
        }
        if (kh.rightPressed) {
            x += speed; // Move right
        }
    }

    public void shoot() {
        frameIndex = 0;
        isShooting = true;

        // Loop shooting animation
        while (isShooting) {
            frameIndex++;
            if (frameIndex >= frames.length) {
                frameIndex = 0;   // Reset frames
                isShooting = false; // Stop shooting animation
            }

            try {
                Thread.sleep(25); //animation speed
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        while (isMoving) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (isShooting) {
                shoot();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Translate to the center of the character by adding half the width and height
        g2d.translate(x + imgWidth / 2, y + imgHeight / 2);
        // Rotate around this new center point
        g2d.rotate(angle);

        // Draw the image centered at the origin (0,0) of this rotated space
        g2d.drawImage(frames[frameIndex], -imgWidth / 2, -imgHeight / 2, imgWidth * 2, imgHeight * 2, null);

        // Reset rotation and translation
        g2d.rotate(-angle);
        g2d.translate(-(x + imgWidth / 2), -(y + imgHeight / 2));
    }

    //setter getter
    public boolean getClick() {
        return this.click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public double getAngle() {
        return this.angle;
    }

    public int getMouseX() {
        return mouseX;
    }

    @Override
    public void mouseMoved(MouseEvent e) { //get angle to face the same direction as mouse
        mouseX = e.getX();
        mouseY = e.getY();
        update(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(MouseEvent e) { //shooting animation
        if (!isShooting) {
            new Thread(this::shoot).start();
            click = true;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //nothing
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //nothing
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_A) {
            x -= speed;
        }
        if (code == KeyEvent.VK_D) {
            x += speed;
        }
        if (code == KeyEvent.VK_SPACE) {
            //
        }
        //add s to slow down the bullet
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //nothing
    }

}
