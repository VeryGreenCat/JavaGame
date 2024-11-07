package gameproject;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public final class Player extends Entity implements MouseMotionListener, MouseListener, KeyListener {

    private BufferedImage[][] frame2side;
    private double angle;
    private int mouseX;
    private int mouseY;
    private boolean isShooting;
    private boolean click;
    private int mouseOntheLeftSide;

    public Player(int x) {
        super(x, 450);
        setIsMoving(true);
        loadImages();
        isShooting = false;
        click = false;
    }

    public void update(int mouseX, int mouseY) {
        if (!isShooting) { // Rotate if not shooting
            angle = Math.atan2(mouseY - y, mouseX - x);

//            if ((mouseX > x && imgHeight < 0) || (mouseX < x && imgHeight > 0)) {
//                imgHeight *= -1;
//
//            }
            mouseOntheLeftSide = mouseX < x ? 1 : 0;

        }
    }

    public void shoot() {
        frameIndex = 0;
        isShooting = true;

        // Loop to simulate shooting animation
        while (isShooting) {
            frameIndex++;
            if (frameIndex >= frame2side[0].length) {
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

    public double getAngle() {
        return this.angle;
    }

    public boolean getClick() {
        return this.click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    @Override
    void loadImages() {
        frame2side = new BufferedImage[2][10];

        for (int i = 0; i < 10; i++) {

            try {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/Gun/tile00" + i + ".png"));
                frame2side[0][i] = temp;
                frame2side[1][i] = FlipImgHorizontally(temp); // flip

            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        imgWidth = frame2side[0][0].getWidth();
        imgHeight = frame2side[0][0].getHeight();
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

    public static BufferedImage FlipImgHorizontally(BufferedImage img) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(), 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage flippedImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Graphics2D g2d = flippedImg.createGraphics();
        g2d.drawImage(op.filter(img, null), 0, 0, null);
        g2d.dispose();

        return flippedImg;
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Translate to the center of the character by adding half the width and height
        g2d.translate(x + imgWidth / 2, y + imgHeight / 2);
        // Rotate around this new center point
        g2d.rotate(angle);

        // Draw the image centered at the origin (0,0) of this rotated space
        if (mouseOntheLeftSide == 1) {
            g2d.drawImage(frame2side[mouseOntheLeftSide][frameIndex], -imgWidth / 2, -imgHeight / 2, imgWidth * 2, imgHeight * 2, null);
        } else {
            g2d.drawImage(frame2side[mouseOntheLeftSide][frameIndex], -imgWidth / 2, -imgHeight / 2, imgWidth * 2, imgHeight * 2, null);
        }

        // Reset rotation and translation
        g2d.rotate(-angle);
        g2d.translate(-(x + imgWidth / 2), -(y + imgHeight / 2));
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
