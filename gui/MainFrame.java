import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainFrame extends JFrame {
    public MainFrame() {

        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JPanel leftPanel = new JPanel(new GridLayout(7, 1));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setBackground(new Color(252, 212, 68));

        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        // Add buttons to left panel
        JButton button1 = new JButton("Saldo Checken");
        button1.setFont(new Font("Arial", Font.BOLD, 25));
        button1.setPreferredSize(new Dimension(230, 70));
        button1.setMinimumSize(new Dimension(230, 70)); // Set minimum size to prevent truncation
        button1.setBackground(Color.WHITE);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button 1
            }
        });
        leftPanel.add(button1);
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton button2 = new JButton("Afbreken");
        button2.setFont(new Font("Arial", Font.BOLD, 25));
        button2.setPreferredSize(new Dimension(230, 70));
        button2.setMinimumSize(new Dimension(230, 70)); // Set minimum size to prevent truncation
        button2.setBackground(Color.WHITE);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button 2
                dispose();
                new BeginScherm();
            }
        });
        leftPanel.add(button2);
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        mainPanel.add(leftPanel, BorderLayout.WEST); // add your left panel

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(252, 212, 68));

        // Load the image from file
        ImageIcon imageIcon = new ImageIcon("binglogo.PNG");
        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);
        centerPanel.add(imageLabel, BorderLayout.NORTH);

        JLabel keuzeLabel = new JLabel("Welkom maak uw keuze");
        keuzeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        keuzeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(keuzeLabel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(7, 1));
        rightPanel.setPreferredSize(new Dimension(250, 0));
        rightPanel.setBackground(new Color(252, 212, 68));

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        // Add buttons to right panel
        JButton button3 = new JButton("Snel Opnemen");
        button3.setFont(new Font("Arial", Font.BOLD, 25 ));
        button3.setPreferredSize(new Dimension(230, 70));
        button3.setMinimumSize(new Dimension(230, 70)); // Set minimum size to prevent truncation
        button3.setBackground(Color.WHITE);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button 3
                dispose();
                new SnelOpnemen();
            }
        });
        rightPanel.add(button3);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton button4 = new JButton("Opnemen");
        button4.setFont(new Font("Arial", Font.BOLD, 25));
        button4.setPreferredSize(new Dimension(230, 70));
        button4.setMinimumSize(new Dimension(230, 70)); // Set minimum size to prevent truncation
        button4.setBackground(Color.WHITE);
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button 4
                dispose();
                new Opnemen();
            }
        });
        rightPanel.add(button4);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton button5 = new JButton("Afbreken");
        button5.setFont(new Font("Arial", Font.BOLD, 25));
        button5.setPreferredSize(new Dimension(230, 70));
        button5.setMinimumSize(new Dimension(230, 70)); // Set minimum size to prevent truncation
        button5.setBackground(Color.WHITE);
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button 5
                dispose();
                new BeginScherm();
            }
        });
        rightPanel.add(button5);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);
    }
}
