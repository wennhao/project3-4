import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

public class PinScherm extends JFrame {
    private JTextField pinField;
    private JLabel resultLabel;
    private int triesLeft = 3;
    private StringBuilder password;
    private SerialPort serialPort;
    private InputStream inputStream;
    private final AtomicReference<String> errorMessage = new AtomicReference<>(null);

    public PinScherm() {
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        ImageIcon imageIcon = new ImageIcon("../ATMgui/src/binglogo.PNG");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(imageLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));

        JLabel titleLabel = new JLabel("Voer uw pincode in:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.setBackground(new Color(252, 212, 68));

        JPanel pinPanel = new JPanel();
        pinPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pinPanel.setBackground(new Color(252, 212, 68));
        pinField = new JPasswordField(4);
        pinField.setEnabled(true);
        pinField.setFont(new Font("Arial", Font.BOLD, 30));
        pinField.setPreferredSize(new Dimension(240, 50));

        ((AbstractDocument) pinField.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
                    throws BadLocationException {
                int newLength = fb.getDocument().getLength() - length + str.length();
                if (newLength <= 4) {
                    super.replace(fb, offs, length, str, a);
                }
            }
        });
        pinPanel.add(pinField);

        mainPanel.add(pinPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        resultLabel = new JLabel();
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 40));
        resultLabel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(resultLabel);

        setContentPane(mainPanel);
        setVisible(true);

        password = new StringBuilder();
        SerialManager.openSerialPort(SharedData.port);
        setupSerial();

    }

    private void handleIncorrectPIN() {
        triesLeft--;
        if (triesLeft <= 0) {
            resultLabel.setText("Geen pogingen meer over");
            pinField.setEnabled(false);
        } else {
            String incorrectMessage = "Verkeerde pincode! U heeft nog " + triesLeft + " pogingen over.";
            resultLabel.setText(incorrectMessage);
            pinField.setText("");
        }
    }

    private void setupSerial() {
        serialPort = SerialManager.getSerialPort();
        if (serialPort == null || !serialPort.isOpen()) {
            System.err.println("Serial port is not open or does not exist.");
            return;
        }
        inputStream = serialPort.getInputStream();
        new Thread(() -> {
            while (true) {
                try {
                    // Read a single byte from the input stream
                    int data = inputStream.read();
                    // Convert the byte to a character
                    char c = (char) data;
                    // If the character is a digit and access is not blocked, add it to the password
                    if (Character.isDigit(c) && pinField.isEnabled()) {
                        password.append(c);
                        SwingUtilities.invokeLater(() -> {
                            pinField.setText(password.toString());
                        });
                    } else if (c == 'C') {
                        SwingUtilities.invokeLater(() -> {
                            password.setLength(0);
                            pinField.setText("");
                        });
                    } else if (c == 'B') {
                        SwingUtilities.invokeLater(() -> {
                            if (password.length() > 0) {
                                password.setLength(password.length() - 1);
                                pinField.setText(password.toString());
                            }
                        });
                    } else if (c == 'D') { // If the character is 'D', submit the PIN
                        if (password.length() == 4) {
                            if (password.toString().equals(SharedData.pincode)) {
                                SwingUtilities.invokeLater(this::handleCorrectPIN);
                            } else {
                                SwingUtilities.invokeLater(this::handleIncorrectPIN);
                            }
                            password.setLength(0); // Reset the password after evaluating
                        }
                    }
                } catch (IOException ex) {
                    if (errorMessage.get() == null || !errorMessage.get().equals(ex.getMessage())) {
                        errorMessage.set(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void handleCorrectPIN() {
        resultLabel.setText("Correct PIN!");
        SerialManager.closeSerialPort();
        new KeuzeScherm();
        dispose();
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
        SerialManager.closeSerialPort();
        super.dispose();
    }
}
