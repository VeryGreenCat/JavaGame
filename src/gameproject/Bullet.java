package gameproject;

public class Bullet extends Entity {

    private double angle;

    public Bullet(Player player) {//player.getY()-20
        super(player.getX(), player.getMouseX() < player.getX() ? player.getY() - 20 : player.getY() + 17);
        angle = player.getAngle();
        setSpeed(15);
        setFrameIndex(0);
        setIsMoving(true);
        loadImages(1, "/Gun/Bullet");
    }

    @Override
    public void run() {
        while (isMoving) {
            x += Math.cos(angle) * getSpeed();
            y += Math.sin(angle) * getSpeed();

            try {
                Thread.sleep(16); // Adjust for smooth movement
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
