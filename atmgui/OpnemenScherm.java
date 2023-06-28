import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public class OpnemenScherm extends JFrame {
    private JTextField bedragTextField;
    private SerialPort serialPort;
    private boolean isListening;

    public OpnemenScherm() {
        setTitle("Opnemen");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JPanel leftPanel = new JPanel(new GridLayout(7, 1));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setBackground(new Color(252, 212, 68));

        // Add empty space to left panel
        for (int i = 0; i < 3; i++) {
            leftPanel.add(new JLabel(""));
        }

        JButton button1 = new JButton("Terug");
        button1.setFont(new Font("Arial", Font.BOLD, 25));
        button1.setPreferredSize(new Dimension(230, 70));
        button1.setMinimumSize(new Dimension(230, 70));
        button1.setBackground(Color.WHITE);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KeuzeScherm();
            }
        });
        leftPanel.add(button1);
        leftPanel.add(new JLabel(""));

        JButton button2 = new JButton("Afbreken");
        button2.setFont(new Font("Arial", Font.BOLD, 25));
        button2.setPreferredSize(new Dimension(230, 70));
        button2.setMinimumSize(new Dimension(230, 70));
        button2.setBackground(Color.WHITE);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EindScherm();
            }
        });
        leftPanel.add(button2);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(252, 212, 68));

        // Load the image from file
        ImageIcon imageIcon = new ImageIcon("../ATMgui/src/binglogo.PNG");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(imageLabel);

        JLabel keuzeLabel = new JLabel("Welkom maak uw keuze");
        keuzeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        keuzeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        keuzeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(keuzeLabel);

        // Create JTextField
        bedragTextField = new JTextField();
        bedragTextField.setFont(new Font("Arial", Font.BOLD, 24));
        bedragTextField.setMaximumSize(new Dimension(500, 50));
        bedragTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(bedragTextField);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(7, 1));
        rightPanel.setPreferredSize(new Dimension(250, 0));
        rightPanel.setBackground(new Color(252, 212, 68));

        // Add empty space to right panel
        for (int i = 0; i < 5; i++) {
            rightPanel.add(new JLabel(""));
        }

        JButton button3 = new JButton("Bedrag Opnemen");
        button3.setFont(new Font("Arial", Font.BOLD, 25));
        button3.setPreferredSize(new Dimension(230, 70));
        button3.setMinimumSize(new Dimension(230, 70));
        button3.setBackground(Color.WHITE);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountStr = bedragTextField.getText().trim();
                if (isValidAmount(amountStr)) {
                    int amount = Integer.parseInt(amountStr);
                    sendToSerial(amount);
                    // Add this line to update the balance when the button is clicked
                    updateBalance(amount);
                    dispose();
                    new EindScherm();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a number between 1 and 250 that is divisible by 5.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rightPanel.add(button3);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);

        SerialManager.openSerialPort(SharedData.port);
        setupSerial();
        startListening();
    }

    private boolean isValidAmount(String amountStr) {
        try {
            int amount = Integer.parseInt(amountStr);
            return amount >= 1 && amount <= 250 && amount % 5 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void openSerialPort(String portName) {
        SerialPort serialPort = SerialPort.getCommPort(portName);
        if (serialPort.isOpen() || serialPort.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
        }
    }


    private void sendToSerial(int amount) {
        if (serialPort != null && serialPort.isOpen()) {
            String amountStr = String.valueOf(amount);
            byte[] data = amountStr.getBytes();
            serialPort.writeBytes(data, data.length);
        }
    }

    private void setupSerial() {
        serialPort = SerialManager.getSerialPort();
        if (serialPort == null || !serialPort.isOpen()) {
            System.err.println("Could not open COM port.");
            return;
        }
    }

    private void startListening() {
        isListening = true;
        new Thread(() -> {
            while (isListening && serialPort != null && serialPort.isOpen()) {
                byte[] data = new byte[1];
                int bytesRead = serialPort.readBytes(data, data.length);
                if (bytesRead > 0) {
                    char c = (char) data[0];
                    SwingUtilities.invokeLater(() -> {
                        handleKeypadInput(c);
                    });
                }
            }
        }).start();
    }

    private void handleKeypadInput(char c) {
        if (Character.isDigit(c) && bedragTextField.isEnabled()) {
            String currentText = bedragTextField.getText();
            bedragTextField.setText(currentText + c);
        } else if (c == 'B') {
            String currentText = bedragTextField.getText();
            if (!currentText.isEmpty()) {
                bedragTextField.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if (c == 'C') {
            bedragTextField.setText("");
        } else if (c == 'D') {
            String amountStr = bedragTextField.getText().trim();
            if (isValidAmount(amountStr)) {
                int amount = Integer.parseInt(amountStr);
                sendToSerial(amount);
                updateBalance(amount);
                dispose();
                new EindScherm();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a number between 1 and 250 that is divisible by 5.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void closeSerialPort() {
        SerialManager.closeSerialPort(); // Closing the serial port
    }

    @Override
    public void dispose() {
        closeSerialPort();
        isListening = false;
        super.dispose();
    }
    // Update balance method
    private void updateBalance(double withdrawalAmount) {
        String cardNumber = SharedData.cardnumber;
        double updatedBalance = SharedData.balance - withdrawalAmount;

        // Update the balance in SharedData
        SharedData.balance = updatedBalance;

        // Create the URL for the API endpoint
        String apiUrl = "http://145.24.222.171:8888/users/" + cardNumber + "/balance";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request type to PUT
            conn.setRequestMethod("PUT");

            // Enable input and output streams
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Set the content type and accept headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            // Create JSON payload
            String payload = "{\"balance\": \"" + updatedBalance + "\"}";

            // Write payload to the output stream
            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.flush();

            // Read the response
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            // Disconnect
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScanScherm::new);
    }
}
