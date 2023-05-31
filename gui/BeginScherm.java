import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class BeginScherm extends JFrame {
    private JLabel label;
    private JLabel scannedCardLabel;
    private Timer timer;
    private String dots;
    private SerialPort serialPort;
    private StringBuilder cardNumberBuilder;

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

        // Create a JLabel to display the scanned card number
        scannedCardLabel = new JLabel("");
        scannedCardLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scannedCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scannedCardLabel, BorderLayout.NORTH);

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
        add(imagePanel, BorderLayout.CENTER);

        // Retrieve available serial ports
        SerialPort[] ports = SerialPort.getCommPorts();

        // Find the selected port (replace "COM1" with your Arduino's port name)
        SerialPort selectedPort = SerialPort.getCommPort("cu.usbmodem2401");

        if (selectedPort.openPort()) {
            System.out.println("Serial port opened successfully");
            label.setText("Scanning card...");

            cardNumberBuilder = new StringBuilder();

            final SerialPort finalSelectedPort = selectedPort; // Create a final copy of the selectedPort

            // Configure event listener to read data from the serial port
            selectedPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                        byte[] newData = new byte[finalSelectedPort.bytesAvailable()];
                        int numRead = finalSelectedPort.readBytes(newData, newData.length);

                        // Append the received data to the cardNumberBuilder
                        cardNumberBuilder.append(new String(newData, 0, numRead));

                        // Check if the received data contains a complete message
                        if (cardNumberBuilder.toString().contains("\n")) {
                            // Extract the card number
                            String scannedCardNumber = cardNumberBuilder.toString().trim();

                            // Remove any remaining data from the cardNumberBuilder
                            cardNumberBuilder.setLength(0);

                            System.out.println("Scanned card number: " + scannedCardNumber);

                            // Update the scanned card label
                            SwingUtilities.invokeLater(() -> scannedCardLabel.setText("Scanned Card: " + scannedCardNumber));

                            // Send a GET request to retrieve user data based on the scanned card number
                            HttpClient client = HttpClient.newHttpClient();
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create("http://145.24.222.171:8888/users/card/" + scannedCardNumber))
                                    .GET()
                                    .build();

                            try {
                                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                                int statusCode = response.statusCode();

                                // Inside the try block, after receiving the response
                                if (statusCode == 200) {
                                    String responseData = response.body();
                                    System.out.println(responseData); // Example: Print the response data

                                    // Parse the JSON response to extract the pin code
                                    try {
                                        JSONObject jsonObject = new JSONObject(responseData);
                                        String pincode = jsonObject.getString("pincode");
                                        SharedData.pincode = pincode;
                                        System.out.println("Pin code: " + pincode);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (IOException | InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            });
        } else {
            System.out.println("Failed to open the serial port");
        }

        // Start the timer
        timer.start();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BeginScherm::new);
    }
}
