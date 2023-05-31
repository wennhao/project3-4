import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortIOException;
import com.sun.java.accessibility.util.EventID;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

public class Opnemen extends JFrame {
    private JTextField bedragTextField;
    private SerialPort serialPort;
    private InputStream inputStream;
    private boolean isListening;

    public Opnemen() {
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
                closeSerialPort();
                dispose();
                new MainFrame();
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
                closeSerialPort();
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
        ImageIcon imageIcon = new ImageIcon("binglogo.PNG");
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
        button3.setFont(new Font("Arial", Font.BOLD, 25 ));
        button3.setPreferredSize(new Dimension(230, 70));
        button3.setMinimumSize(new Dimension(230, 70));
        button3.setBackground(Color.WHITE);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeSerialPort();
                dispose();
                new EindScherm();
            }
        });
        rightPanel.add(button3);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Call your setupSerial method here
        setupSerial();

        setContentPane(mainPanel);
        setVisible(true);
    }
    private void setupSerial() {
        SerialPort[] ports = SerialPort.getCommPorts();
        serialPort = null;
        boolean errorDisplayed = false; // Flag to keep track of whether the error has been displayed
        for (SerialPort port : ports) {
            if (port.getSystemPortName().equals("cu.usbmodem2401")) {
                serialPort = port;
                break;
            }
        }
        if (serialPort == null) {
            if (!errorDisplayed) { // Check if the error has been displayed already
                System.err.println("Could not find COM port.");
                errorDisplayed = true; // Set the flag to display the error only once
            }
            return;
        }

        if (serialPort.isOpen() || serialPort.openPort()) {
            serialPort.setBaudRate(9600);
            inputStream = serialPort.getInputStream();
            isListening = true;
            startListening();
        } else {
            System.err.println("Could not open COM port.");
        }
    }

    private void startListening() {
        new Thread(() -> {
            StringBuilder number = new StringBuilder();
            while (isListening && serialPort != null && serialPort.isOpen()) {
                try {
                    int data = inputStream.read();
                    char c = (char) data;
                    if (Character.isDigit(c) && bedragTextField.isEnabled()) {
                        number.append(c);
                        SwingUtilities.invokeLater(() -> {
                            bedragTextField.setText(number.toString());
                        });
                    } else if (c == 'C') {
                        // Remove the last character from number
                        if (number.length() > 0) {
                            number.deleteCharAt(number.length() - 1);
                        }
                        SwingUtilities.invokeLater(() -> {
                            bedragTextField.setText(number.toString());
                        });
                    } else if (c == 'B') {
                        // Clear the number
                        number.setLength(0);
                        SwingUtilities.invokeLater(() -> {
                            bedragTextField.setText("");
                        });
                    }
                } catch (SerialPortIOException ex) {
                    System.err.println("Serial Port appears to have been shutdown or disconnected. Stopping listener thread.");
                    stopListening();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }


    private void stopListening() {
        isListening = false;
    }

    private void closeSerialPort() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (serialPort != null) {
            serialPort.closePort();
        }
    }

    @Override
    public void dispose() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
        super.dispose();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(Opnemen::new);
    }
}