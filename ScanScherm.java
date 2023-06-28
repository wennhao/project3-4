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

public class ScanScherm extends JFrame {
    private JLabel label;
    private JLabel scannedCardLabel;
    private Timer timer;
    private String dots;
    private SerialPort selectedPort;
    private StringBuilder cardNumberBuilder;

    public ScanScherm() {
        initComponents();
        addComponents();
        configSerialPort();
    }

    private void initComponents() {
        setTitle("BeginScherm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(252, 212, 68));

        label = new JLabel("Voer uw pas in");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        scannedCardLabel = new JLabel("");
        scannedCardLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scannedCardLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dots = "";

        timer = new Timer(500, e -> {
            dots = dots.length() < 3 ? dots + "." : "";
            label.setText("Voer uw pas in" + dots);
        });
    }

    private void addComponents() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(scannedCardLabel, BorderLayout.NORTH);

        ImageIcon imageIcon = new ImageIcon("../ATMgui/src/binglogo.PNG");
        JLabel imageLabel = new JLabel(imageIcon);
        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(new Color(252, 212, 68));
        imagePanel.add(imageLabel);

        add(panel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
    }

    private void configSerialPort() {
        selectedPort = SerialManager.getSerialPort();

        if (selectedPort != null && selectedPort.isOpen()) {
            System.out.println("Serial port opened successfully");
            label.setText("Scanning card...");
            cardNumberBuilder = new StringBuilder();
            final SerialPort finalSelectedPort = selectedPort;

            selectedPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    handleSerialEvent(event, finalSelectedPort);
                }
            });
        } else {
            System.out.println("Failed to open the serial port, it does not exist or is not open.");
        }

        timer.start();
        setVisible(true);
    }

    private void handleSerialEvent(SerialPortEvent event, SerialPort selectedPort) {
        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            byte[] newData = new byte[selectedPort.bytesAvailable()];
            int numRead = selectedPort.readBytes(newData, newData.length);

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

                        // Parse the JSON response to extract the pin code and balance
                        try {
                            JSONObject jsonObject = new JSONObject(responseData);
                            String pincode = jsonObject.getString("pincode");
                            double balance = jsonObject.getDouble("balance"); // Extract the balance
                            String cardnumber = jsonObject.getString("cardnumber");

                            // Store the pincode and balance in the SharedData class
                            SharedData.pincode = pincode;
                            SharedData.balance = balance;
                            SharedData.cardnumber = cardnumber;

                            System.out.println("Pin code: " + pincode);
                            System.out.println("Balance: " + balance); // Print the balance
                            System.out.println("Cardnumber: " + cardnumber);

                            SerialManager.closeSerialPort();
                            new PinScherm();
                            dispose();
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


    public static void main(String[] args) {
        SerialManager.openSerialPort(SharedData.port);
        SwingUtilities.invokeLater(ScanScherm::new);
    }


}
