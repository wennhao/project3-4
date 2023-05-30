import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class BeginScherm extends JFrame {
    private JLabel label;
    private Timer timer;
    private String dots;
    private SerialPort serialPort;
    private BufferedReader input;

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

        JButton button = new JButton("Als RFID gescand is");
        button.addActionListener(e -> {
            dispose();
            new StartFrame();
        });
        add(button, BorderLayout.SOUTH);

        // Start the timer
        timer.start();
        setVisible(true);

        // Initialize RFID scanner
        initialize();
    }

    private void initialize() {
        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort selectedPort = null;

        for (SerialPort port : ports) {
            if (port.getSystemPortName().equals("/dev/cu.usbmodem2401")) { // Replace with your Arduino port
                selectedPort = port;
                break;
            }
        }

        if (selectedPort == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        selectedPort.openPort();
        selectedPort.setBaudRate(9600);
        selectedPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    byte[] newData = event.getReceivedData();
                    String inputLine = new String(newData).trim();
                    System.out.println("RFID Card Number: " + inputLine);

                    // Perform GET request to your Flask API and update the label text
                    String apiUrl = "http://145.24.222.171:8888/users" + inputLine;
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(apiUrl))
                            .GET()
                            .build();

                    try {
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        String cardInfo = response.body();

                        SwingUtilities.invokeLater(() -> label.setText(cardInfo)); // Update label text in the Swing event dispatch thread
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        input = new BufferedReader(new InputStreamReader(selectedPort.getInputStream()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BeginScherm::new);
    }
}
