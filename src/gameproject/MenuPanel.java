package gameproject;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    ButtonListen listen;
    JButton jbtEasy = new JButton("Easy");
    JButton jbtNormal = new JButton("Normal");
    JButton jbtHard = new JButton("Hard");
    JButton jbtImpossible = new JButton("Impossible");

    public MenuPanel(GameProject game) {

        JPanel jpnmenu = new JPanel(new GridLayout(5, 4, 0, 10));
        listen = new ButtonListen(game);

        jbtEasy.addActionListener(listen);
        jbtNormal.addActionListener(listen);
        jbtHard.addActionListener(listen);
        jbtImpossible.addActionListener(listen);

        JLabel title = new JLabel("Protect Planet");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);

        jpnmenu.add(title);
        jpnmenu.add(jbtEasy);
        jpnmenu.add(jbtNormal);
        jpnmenu.add(jbtHard);
        jpnmenu.add(jbtImpossible);

        add(jpnmenu);
    }

    class ButtonListen implements ActionListener {

        GameProject game;

        public ButtonListen(GameProject game) {
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbtEasy) {
                game.startGame(1);
            } else if (e.getSource() == jbtNormal) {
                game.startGame(2);
            } else if (e.getSource() == jbtHard) {
                game.startGame(3);
            } else if (e.getSource() == jbtImpossible) {
                game.startGame(4);
            }
        }

    }

}
