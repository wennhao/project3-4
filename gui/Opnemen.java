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
        mainPanel.setBackground(new Color(252, 212, 68));

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1920, 200));
        topPanel.setBackground(new Color(252, 212, 68));

        ImageIcon imageIcon = new ImageIcon("../gui/binglogo.PNG");

        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);

        // Create a panel with FlowLayout to center the imageLabel
        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(new Color(252, 212, 68));
        imagePanel.add(imageLabel);

        // Add the imagePanel to the GUI
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

        JPanel leftPanel = new JPanel(new GridLayout(7, 1));
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setBackground(new Color(252, 212, 68));

        leftPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        JButton buttonBack = new JButton("Terug");
        buttonBack.setFont(new Font("Arial", Font.BOLD, 30));
        buttonBack.setPreferredSize(new Dimension(200, 100));
        buttonBack.setBackground(Color.WHITE);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button on the left side corner.
                dispose();
                new MainFrame();
            }
        });
        leftPanel.add(buttonBack);

        JButton buttonAfbreken = new JButton("Afbreken");
        buttonAfbreken.setFont(new Font("Arial", Font.BOLD, 30));
        buttonAfbreken.setPreferredSize(new Dimension(200, 100));
        buttonAfbreken.setBackground(Color.WHITE);
        buttonAfbreken.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open a new frame for Button on the left side corner.
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
        JButton opnemenButton = new JButton("Opnemen");
        opnemenButton.setFont(new Font("Arial", Font.BOLD, 30));
        opnemenButton.setPreferredSize(new Dimension(200, 100));
        opnemenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bedragText = bedragTextField.getText();
                if (bedragText.isEmpty()) {
                    JOptionPane.showMessageDialog(Opnemen.this, "Voer een bedrag in.", "Fout", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        double bedrag = Double.parseDouble(bedragText);
                        if (bedrag > 250) {
                            JOptionPane.showMessageDialog(Opnemen.this, "Het limiet om te pinnen is 250 euro.", "Limiet overschreden", JOptionPane.WARNING_MESSAGE);
                        } else if (bedrag % 5 == 0) {
                            int check = (int) (bedrag / 5);
                            JOptionPane.showMessageDialog(Opnemen.this, "Je hebt " + bedrag + " euro opgenomen.", "Opname succesvol", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new EindScherm();
                        } else {
                            JOptionPane.showMessageDialog(Opnemen.this, "Het bedrag moet deelbaar zijn door 5.", "Fout", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Opnemen.this, "Ongeldig bedrag.", "Fout", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        centerInnerPanel.add(opnemenButton, gbc);

        centerPanel.add(centerInnerPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.NORTH);

        add(mainPanel);

        setVisible(true);
    }
}
