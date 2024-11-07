package levelPanel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HardLevel extends JPanel implements Runnable {

    private volatile boolean running = true;

    public HardLevel() {
        setLayout(null);

        // level title
        JLabel jlbLevel = new JLabel("Hard");
        jlbLevel.setBounds(10, 5, 100, 20);
        jlbLevel.setFont(new Font("Arial", Font.BOLD, 20));

        add(jlbLevel);

    }

    public void stop() {
        running = false;
        System.out.println("levelPanel.HardLevel.stop()");
    }

    @Override
    public void run() {
        System.out.println("start running");
        running = true;
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EasyLevel.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("levelPanel.HardLevel.run()");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to clear the background

        Graphics2D g2d = (Graphics2D) g;
        try {
            g2d.drawImage(ImageIO.read(new File("D:\\Java\\GameProject\\src\\background\\Bg3.png")), 0, 0, getWidth(), getHeight(), this);
        } catch (IOException ex) {
            Logger.getLogger(EasyLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
