import javax.swing.*;
import java.awt.*;

public class BeginScherm extends JFrame {
    private JLabel label;
    private Timer timer;
    private String dots;

    public BeginScherm() {
        setTitle("BeginScherm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1900, 845);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(252, 212, 68));
        setLayout(new BorderLayout());

        label = new JLabel("Voer uw pas in");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Set initial dots string
        dots = "";

        // Create a timer to update the dots animation
        timer = new Timer(500, e -> {
            // Update the dots string
            dots = dots.length() < 3 ? dots + "." : "";

            // Update the label text
            label.setText("Voer uw pas in" + dots);
        });


        ImageIcon imageIcon = new ImageIcon("../gui/binglogo.PNG");

        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);

        // Create a panel with FlowLayout to center the imageLabel
        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(new Color(252, 212, 68));
        imagePanel.add(imageLabel);

        // Add the imagePanel to the GUI
        add(imagePanel, BorderLayout.NORTH);

        JButton button = new JButton("Als RFID gescanned is");
        button.addActionListener(e -> {
            dispose();
            new StartFrame();
        });
        add(button, BorderLayout.SOUTH);

        // Start the timer
        timer.start();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BeginScherm());
    }
}
