import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Opnemen extends JFrame {
    public Opnemen() {
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(252, 212, 68));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1500, 1080));
        mainPanel.setBackground(new Color(252, 212, 68));

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1920, 200));
        topPanel.setBackground(new Color(252, 212, 68));

        ImageIcon imageIcon = new ImageIcon("../gui/binglogo.PNG");

        JLabel imageLabel = new JLabel(imageIcon);

        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(new Color(252, 212, 68));
        imagePanel.add(imageLabel);

        add(imagePanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("SaldoChecker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        topPanel.add(titleLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(252, 212, 68));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 0, 0);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setBackground(new Color(252, 212, 68));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(Box.createVerticalGlue());

        JButton buttonBack = new JButton("Terug");
        buttonBack.setFont(new Font("Arial", Font.BOLD, 30));
        buttonBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBack.setPreferredSize(new Dimension(200, 50));
        buttonBack.setBackground(Color.WHITE);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame();
            }
        });
        leftPanel.add(buttonBack);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton buttonAfbreken = new JButton("Afbreken");
        buttonAfbreken.setFont(new Font("Arial", Font.BOLD, 30));
        buttonAfbreken.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonAfbreken.setPreferredSize(new Dimension(200, 50));
        buttonAfbreken.setBackground(Color.WHITE);
        buttonAfbreken.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartFrame();
            }
        });
        leftPanel.add(buttonAfbreken);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerInnerPanel = new JPanel(new GridBagLayout());
        centerInnerPanel.setBackground(new Color(252, 212, 68));

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel bedragLabel = new JLabel("Bedrag:");
        bedragLabel.setFont(new Font("Arial", Font.BOLD, 30));
        centerInnerPanel.add(bedragLabel, gbc);

        gbc.gridy++;
        JTextField bedragTextField = new JTextField(10);
        bedragTextField.setFont(new Font("Arial", Font.PLAIN, 30));
        centerInnerPanel.add(bedragTextField, gbc);

        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton opnemenButton = new JButton("Opnemen");
        opnemenButton.setFont(new Font("Arial", Font.BOLD, 30));
        opnemenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        opnemenButton.setPreferredSize(new Dimension(200, 50));
        opnemenButton.setBackground(Color.WHITE);
        centerInnerPanel.add(opnemenButton, gbc);

        gbc.gridy++;
        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 30));
        centerInnerPanel.add(resultLabel, gbc);

        centerPanel.add(centerInnerPanel);

        mainPanel.add(centerPanel, BorderLayout.NORTH);

        add(mainPanel);

        // Set the size and position of the buttons
        buttonBack.setPreferredSize(new Dimension(200, 50));
        buttonAfbreken.setPreferredSize(new Dimension(200, 50));
        buttonBack.setMaximumSize(new Dimension(200, 50));
        buttonAfbreken.setMaximumSize(new Dimension(200, 50));

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
