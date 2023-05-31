import com.fazecast.jSerialComm.SerialPort;

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
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(252, 212, 68));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(252, 212, 68));

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1920, 200));
        topPanel.setBackground(new Color(252, 212, 68));

        ImageIcon imageIcon = new ImageIcon("../gui/binglogo.PNG");
        JLabel imageLabel = new JLabel(imageIcon);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout());
        imagePanel.setBackground(new Color(252, 212, 68));
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("SaldoChecker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        topPanel.add(titleLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(252, 212, 68));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 0, 0);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1));
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setBackground(new Color(252, 212, 68));

        leftPanel.add(new JLabel(""));

        JButton buttonBack = new JButton("Terug");
        buttonBack.setFont(new Font("Arial", Font.BOLD, 30));
        buttonBack.setPreferredSize(new Dimension(200, 100));
        buttonBack.setBackground(Color.WHITE);
        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                dispose();
                new StartFrame();
            }
        });
        leftPanel.add(buttonAfbreken);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerInnerPanel = new JPanel();
        centerInnerPanel.setLayout(new GridBagLayout());
        centerInnerPanel.setBackground(new Color(252, 212, 68));

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel bedragLabel = new JLabel("Bedrag:");
        bedragLabel.setFont(new Font("Arial", Font.BOLD, 30));
        centerInnerPanel.add(bedragLabel, gbc);

        gbc.gridy++;
        bedragTextField = new JTextField(10);
        bedragTextField.setFont(new Font("Arial", Font.PLAIN, 30));
        centerInnerPanel.add(bedragTextField, gbc);

        gbc.gridy++;
        JButton opnemenButton = new JButton("Opnemen");
        opnemenButton.setFont(new Font("Arial", Font.BOLD, 30));
        opnemenButton.setPreferredSize(new Dimension(250, 70));
        opnemenButton.setBackground(Color.WHITE);
        opnemenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bedrag = bedragTextField.getText();
                // Voer de nodige logica uit om bedrag op te nemen
            }
        });
        centerInnerPanel.add(opnemenButton, gbc);

        centerPanel.add(centerInnerPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.NORTH);

        add(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopListening(); // Stop listening for keypad input
                closeSerialPort(); // Close the serial port
            }
        });

        setVisible(true);
    }

    private void setupSerial() {
        SerialPort[] ports = SerialPort.getCommPorts();
        serialPort = null;
        boolean errorDisplayed = false; // Flag to keep track of whether the error has been displayed
        for (SerialPort port : ports) {
            if (port.getSystemPortName().equals("COM8")) {
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
        serialPort.setBaudRate(9600);
        if (!serialPort.isOpen()) {
            serialPort.openPort();
        }
        inputStream = serialPort.getInputStream();
        isListening = true;

        new Thread(() -> {
            StringBuilder number = new StringBuilder();
            while (isListening) {
                try {
                    int data = inputStream.read();
                    char c = (char) data;
                    if (Character.isDigit(c) && bedragTextField.isEnabled()) {
                        number.append(c);
                        SwingUtilities.invokeLater(() -> {
                            bedragTextField.setText(number.toString());
                        });
                    }
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
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Opnemen::new);
    }
}
