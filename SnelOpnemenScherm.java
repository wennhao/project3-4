import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

class SnelOpnemenScherm extends JFrame {
    private SerialPort serialPort;

    public SnelOpnemenScherm() {
        setTitle("SnelOpnemen");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(252, 212, 68));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setPreferredSize(new Dimension(1920, 1080));

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

        JLabel keuzeLabel = new JLabel("Snel Opnemen", SwingConstants.CENTER);
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

        JButton button70 = new JButton("70 euro");
        button70.setFont(new Font("Arial", Font.BOLD, 30));
        button70.setPreferredSize(new Dimension(250, 50));
        button70.setBackground(Color.WHITE);
        button70.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendCommandToArduino("70");
                updateBalance(70.00);
                dispose();
                new EindScherm();
            }
        });
        rightPanel.add(button70);

        rightPanel.add(new JLabel("")); // Add empty row basically blank space between buttons

        mainPanel.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
        setVisible(true);

        // Initialize the serial port
        serialPort = SerialPort.getCommPort(SharedData.port); // Replace with the appropriate port
        serialPort.setBaudRate(9600);

        // Open the serial port
        if (serialPort.openPort()) {
            System.out.println("Serial port opened successfully.");

            // Create a separate thread to read from the serial port
            Thread thread = new Thread(new SerialReader());
            thread.start();
        } else {
            System.err.println("Failed to open serial port.");
        }
    }

    private void sendCommandToArduino(String command) {
        try {
            OutputStream outputStream = serialPort.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
            System.out.println("Command sent to Arduino: " + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SerialReader implements Runnable {
        private InputStream inputStream;

        public SerialReader() {
            inputStream = serialPort.getInputStream();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (inputStream.available() > 0) {
                        int data = inputStream.read();
                        System.out.println("Received data from Arduino: " + data);
                        // Process the received data from Arduino as needed
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SnelOpnemenScherm();
            }
        });
    }
}
