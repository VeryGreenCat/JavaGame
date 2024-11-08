package gameproject;

public class Plane extends Entity {

    private boolean returning;

    public Plane(int flyHeight) {
        super(0, flyHeight);
        setSpeed(15);
        setFrameIndex(0);
        setIsMoving(true);
        loadImages(2, "/Plane/fly");

        if (flyHeight % 2 == 0) {
            returning = false;
            setX(getX() - imgWidth);
        } else {
            returning = true;
            setX(screenWidth);
            imgWidth *= -1;
            speed *= -1;
        }
        
        imgWidth/=5;
        imgHeight/=5;
    }

    @Override
    public void run() {
        while (isMoving) {
            try {
                Thread.sleep(60); // Adjust as needed for frame rate

                    x += speed; 
                    frameIndex = (frameIndex + 1) % frames.length; // Cycle through frames
               

                if ((!returning && x > screenWidth) || (returning && x < -imgWidth)) {
                    isMoving = false;
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    //getter
    public boolean getIsReturning() {
        return this.returning;
    }
}
