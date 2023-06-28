import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EindScherm extends JFrame {
    private JProgressBar progressBar;
    private SerialPort sp;
    String datetime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    String pincode = SharedData.pincode; // use the appropriate method to retrieve the pin
    String message = datetime + "," + pincode;


    public EindScherm() {
        setTitle("Eindscherm");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Establish connection to the Arduino
        sp = SerialPort.getCommPort(SharedData.port); // replace with your port
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(252, 212, 68)); // Set the background color

        // Load the image from file
        ImageIcon imageIcon = new ImageIcon("../ATMgui/src/binglogo.PNG");

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
        // Prepare the data to send
        String datetime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String pin = SharedData.pincode; // use the appropriate method to retrieve the pin
        String message = datetime + "," + pin + "\n";


        setVisible(true);
        // Send the data to print the receipt
        try {
            sp.getOutputStream().write(message.getBytes());
            sp.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Schedule a task to update the progress bar every 100 milliseconds
        TimerTask task = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                progressBar.setValue(count);

                if (count >= 100) {
                    dispose(); // Close the current frame
                    new ScanScherm(); // Open the BeginScherm frame
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
