package gameproject;

public class Bird extends Entity {

    protected boolean isAlive;
    

    public Bird(int flyHeight) {
        super(0, flyHeight);

        setSpeed(4);
        setIsAlive(true);
        setFrameIndex(0);
        setIsMoving(true);
        loadImages(6, "/Bird1/tile00");
        setX(getX() - imgWidth); //starting from left and spawn is hidden
    }

    @Override
    public void run() {
        while (isMoving) {
            try {
                Thread.sleep(60); // Adjust as needed for frame rate
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

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
