import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

public class StartFrame extends JFrame {
    private JTextField pinField;
    private JLabel resultLabel;
    private int triesLeft = 3;
    private StringBuilder password;
    private SerialPort serialPort;
    private InputStream inputStream;
    private final AtomicReference<String> errorMessage = new AtomicReference<>(null);
    private JButton resetButton;

    public StartFrame() {
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        ImageIcon imageIcon = new ImageIcon("../gui/binglogo.PNG");
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(120, 50));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pinField.setText("");
                password = new StringBuilder(); // Reset the password after resetting the field
            }
        });
        buttonPanel.add(resetButton);

        pinPanel.add(buttonPanel);
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
        setupSerial();
    }

    private void handleIncorrectPIN() {
        triesLeft--;
        if (triesLeft <= 0) {
            resultLabel.setText("No more attempts left!");
            pinField.setEnabled(false);
            resetButton.setEnabled(false);
        } else {
            String incorrectMessage = "Verkeerde pincode! U heeft nog " + triesLeft + " pogingen over.";
            resultLabel.setText(incorrectMessage);
            pinField.setText("");
        }
    }

    private void setupSerial() {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            if (port.getSystemPortName().equals("COM8")) {
                serialPort = port;
                break;
            }
        }
        if (serialPort == null) {
            System.err.println("Could not find COM port.");
            return;
        }
        serialPort.setBaudRate(9600);
        serialPort.openPort();
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
                        if (password.length() == 4) {
                            if (password.toString().equals("1234")) {
                                SwingUtilities.invokeLater(() -> handleCorrectPIN());
                            } else {
                                SwingUtilities.invokeLater(() -> handleIncorrectPIN());
                            }
                            password = new StringBuilder(); // Reset the password after evaluating
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
        dispose();
        new MainFrame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
