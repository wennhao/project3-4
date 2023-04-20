import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Frame2 extends JFrame {
    public Frame2() {
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.setBackground(Color.BLACK);

        JPanel leftPanel = new JPanel(new GridLayout(3, 1));
        leftPanel.setPreferredSize(new Dimension(100, 0));
        leftPanel.setBackground(Color.DARK_GRAY);
        for (int i = 1; i <= 3; i++) {
            JButton button = new JButton("Button " + i);
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.setPreferredSize(new Dimension(100, 50));
            leftPanel.add(button);
        }
        mainPanel.add(leftPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        mainPanel.add(centerPanel);

        JPanel rightPanel = new JPanel(new GridLayout(3, 1));
        rightPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setBackground(Color.DARK_GRAY);
        for (int i = 4; i <= 6; i++) {
            JButton button = new JButton("Button " + i);
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.setPreferredSize(new Dimension(100, 50));
            rightPanel.add(button);
        }
        mainPanel.add(rightPanel);

        setContentPane(mainPanel);
        setVisible(true);
    }
}
