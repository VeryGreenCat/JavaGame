
package gameproject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GrayBird extends Bird{
    
    public GrayBird(int flyHeight) {
        super(flyHeight);
    }
    
    @Override
    protected void loadImages() {
        frames = new BufferedImage[6];
        try {
            for (int i = 0; i < 6; i++) {
                frames[i] = ImageIO.read(getClass().getResourceAsStream("/Bird2/tile00" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgWidth = frames[0].getWidth();
        imgHeight = frames[0].getHeight();
        setX(getX() - imgWidth);
    }
    
}