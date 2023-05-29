import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SnelOpnemen extends JFrame {
    public SnelOpnemen() {

        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(252, 212, 68));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setPreferredSize(new Dimension(1500, 1080));

        JPanel leftPanel = new JPanel(new GridLayout(7, 1));
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setBackground(new Color(252, 212, 68));

        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton buttonBack = new JButton("Terug");
        buttonBack.setFont(new Font("Arial", Font.BOLD, 30));
        buttonBack.setPreferredSize(new Dimension(200, 50));
        buttonBack.setBackground(Color.WHITE);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame();
            }
        });
        leftPanel.add(buttonBack);
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton buttonAfbreken = new JButton("Afbreken");
        buttonAfbreken.setFont(new Font("Arial", Font.BOLD, 30));
        buttonAfbreken.setPreferredSize(new Dimension(200, 50));
        buttonAfbreken.setBackground(Color.WHITE);
        buttonAfbreken.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BeginScherm();
            }
        });
        leftPanel.add(buttonAfbreken);
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(252, 212, 68));

        // Load the image from file
        ImageIcon imageIcon = new ImageIcon("binglogo.PNG");
        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);
        centerPanel.add(imageLabel, BorderLayout.NORTH);

        JLabel keuzeLabel = new JLabel("Snel Opnemen", SwingConstants.CENTER);
        keuzeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        centerPanel.add(keuzeLabel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(7, 1));
        rightPanel.setPreferredSize(new Dimension(200, 0));
        rightPanel.setBackground(new Color(252, 212, 68));

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton button70 = new JButton("70 euro");
        button70.setFont(new Font("Arial", Font.BOLD, 30));
        button70.setPreferredSize(new Dimension(200, 50));
        button70.setBackground(Color.WHITE);
        button70.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for on the top right
            }
        });
        rightPanel.add(button70);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton button50 = new JButton("50 euro");
        button50.setFont(new Font("Arial", Font.BOLD, 30));
        button50.setPreferredSize(new Dimension(200, 50));
        button50.setBackground(Color.WHITE);
        button50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for on the middle right
            }
        });
        rightPanel.add(button50);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton button20 = new JButton("20 euro");
        button20.setFont(new Font("Arial", Font.BOLD, 30));
        button20.setPreferredSize(new Dimension(200, 50));
        button20.setBackground(Color.WHITE);
        button20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for on the bottom right
            }
        });
        rightPanel.add(button20);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);
    }
}
