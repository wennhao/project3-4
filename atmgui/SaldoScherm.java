import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SaldoScherm extends JFrame {
    public SaldoScherm() {
        setTitle("SaldoChecker");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(252, 212, 68));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setPreferredSize(new Dimension(1500, 1080));

        JPanel leftPanel = new JPanel(new GridLayout(7, 1));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setBackground(new Color(252, 212, 68));

        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton buttonBack = new JButton("Terug");
        buttonBack.setFont(new Font("Arial", Font.BOLD, 30));
        buttonBack.setPreferredSize(new Dimension(250, 50));
        buttonBack.setBackground(Color.WHITE);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KeuzeScherm();
            }
        });
        leftPanel.add(buttonBack);
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton buttonAfbreken = new JButton("Afbreken");
        buttonAfbreken.setFont(new Font("Arial", Font.BOLD, 30));
        buttonAfbreken.setPreferredSize(new Dimension(250, 50));
        buttonAfbreken.setBackground(Color.WHITE);
        buttonAfbreken.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EindScherm();
            }
        });
        leftPanel.add(buttonAfbreken);
        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(252, 212, 68));

        // Load the image from file
        ImageIcon imageIcon = new ImageIcon("../ATMgui/src/binglogo.PNG");
        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);
        centerPanel.add(imageLabel, BorderLayout.NORTH);

        JLabel keuzeLabel = new JLabel("Je saldo is €" + SharedData.balance, SwingConstants.CENTER);
        keuzeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        centerPanel.add(keuzeLabel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(7, 1));
        rightPanel.setPreferredSize(new Dimension(250, 0));
        rightPanel.setBackground(new Color(252, 212, 68));

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons


        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons
        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons


        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SaldoScherm::new);
    }
}
