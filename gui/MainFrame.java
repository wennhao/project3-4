import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainFrame extends JFrame {
    public MainFrame() {

        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(1, 5));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setPreferredSize(new Dimension(1500, 1080));

        JPanel leftPanel = new JPanel(new GridLayout(7, 1));
        leftPanel.setPreferredSize(new Dimension(20, 0));
        leftPanel.setBackground(new Color(252, 212, 68));

        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        // Add buttons to left panel
        JButton button1 = new JButton("Saldo Checken");
        button1.setFont(new Font("Arial", Font.BOLD, 30));
        button1.setPreferredSize(new Dimension(20, 50));
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
        button2.setFont(new Font("Arial", Font.BOLD, 30));
        button2.setPreferredSize(new Dimension(20, 50));
        button2.setBackground(Color.WHITE);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            // Code to open a new frame for Button 2
            dispose();
            new StartFrame();
            }
        });
        leftPanel.add(button2);
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        
        mainPanel.add(leftPanel); // add your left panel

        JPanel leftPanel2 = new JPanel(new GridLayout(1,1));
        leftPanel2.setPreferredSize(new Dimension(400, 0));
        leftPanel2.setBackground(new Color(252, 212, 68));
        mainPanel.add(leftPanel2); // add a second left panel to make the buttons smaller or make the space bigger in the center

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        // centerPanel.setBackground(Color.BLUE);
        centerPanel.setBackground(new Color(252, 212, 68));
        centerPanel.setPreferredSize(new Dimension(900, 1080));
        centerPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        
        // Load the image from file
        ImageIcon imageIcon = new ImageIcon("../gui/binglogo.PNG");
        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);
        // Add the JLabel to the center panel
        centerPanel.add(imageLabel);
        JLabel keuzeLabel = new JLabel("Welkom\n maak uw keuze");
        keuzeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        centerPanel.add(keuzeLabel);
        mainPanel.add(centerPanel);

        JPanel rightPanel2 = new JPanel(new GridLayout(1,1));
        rightPanel2.setPreferredSize(new Dimension(400, 0));
        rightPanel2.setBackground(new Color(252, 212, 68));
        mainPanel.add(rightPanel2);

        JPanel rightPanel = new JPanel(new GridLayout(7, 1));
        rightPanel.setPreferredSize(new Dimension(20, 0));
        rightPanel.setBackground(new Color(252, 212, 68));
    
        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        // Add buttons to right panel
        JButton button3 = new JButton("Snel Opnemen");
        button3.setFont(new Font("Arial", Font.BOLD, 30));
        button3.setPreferredSize(new Dimension(20, 50));
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
        button4.setFont(new Font("Arial", Font.BOLD, 30));
        button4.setPreferredSize(new Dimension(20, 50));
        button4.setBackground(Color.WHITE);
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button 4
            }
        });
        rightPanel.add(button4);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton button5 = new JButton("Afbreken");
        button5.setFont(new Font("Arial", Font.BOLD, 30));
        button5.setPreferredSize(new Dimension(20, 50));
        button5.setBackground(Color.WHITE);
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button 5
                dispose();
                new StartFrame();
            }
        });
        rightPanel.add(button5);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        mainPanel.add(rightPanel);

        setContentPane(mainPanel);
        setVisible(true);
    }
}

