import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EindScherm extends JFrame {

    private JProgressBar progressBar;

    public EindScherm() {
        setTitle("Eindscherm");
        setSize(1900, 845);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(252, 212, 68)); // Set the background color

        // Load the image from file
        ImageIcon imageIcon = new ImageIcon("binglogo.PNG");

        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(imageLabel, BorderLayout.NORTH);

        // Create a JLabel for the text
        JLabel textLabel = new JLabel("Bedankt voor het gebruik maken van Bin.G!");
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(textLabel, BorderLayout.CENTER);

        // Create the progress bar and add it to the center
        progressBar = new JProgressBar(0, 100);
        panel.add(progressBar, BorderLayout.SOUTH);

        setContentPane(panel);
        setVisible(true);

        // Schedule a task to update the progress bar every 100 milliseconds
        TimerTask task = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                progressBar.setValue(count);

                if (count >= 100) {
                    dispose(); // Close the current frame
                    new BeginScherm(); // Open the BeginScherm frame
                    cancel();
                }
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 100); // Update every 100 milliseconds
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EindScherm());
    }
}
