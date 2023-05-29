import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class StartFrame extends JFrame {
    private JLabel titleLabel;
    private JPasswordField passcodeField;
    private JButton submitButton;
    private int wrongAttempts;
    private int maxAttempts = 3;

    public StartFrame() {
        setTitle("StartFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1900, 845);
        getContentPane().setBackground(new Color(252, 212, 68));
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(252, 212, 68));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(252, 212, 68));
        titleLabel = new JLabel("Voer uw pincode in");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titlePanel.add(titleLabel);
        centerPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(new Color(252, 212, 68));

        passcodeField = new JPasswordField(10);
        passcodeField.setFont(new Font("Arial", Font.PLAIN, 20));

        // Set the document filter to allow only four alphanumeric characters
        ((AbstractDocument) passcodeField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= 4 && string.matches("[a-zA-Z0-9]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (fb.getDocument().getLength() - length + text.length() <= 4 && text.matches("[a-zA-Z0-9]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        inputPanel.add(passcodeField);
        centerPanel.add(inputPanel, BorderLayout.CENTER);

        ImageIcon imageIcon = new ImageIcon("../gui/binglogo.PNG");

        // Create a JLabel with the image
        JLabel imageLabel = new JLabel(imageIcon);

        // Create a panel with FlowLayout to center the imageLabel
        JPanel imagePanel = new JPanel(new FlowLayout());
        imagePanel.setBackground(new Color(252, 212, 68));
        imagePanel.add(imageLabel);

        // Add the imagePanel to the GUI
        add(imagePanel, BorderLayout.NORTH);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 30));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] passcodeChars = passcodeField.getPassword();
                String passcode = new String(passcodeChars);
                if (passcode.equals("1234")) {
                    // Correct pin code
                    dispose();
                    // Open the next frame or perform any desired action
                    new MainFrame();
                } else {
                    // Incorrect pin code
                    wrongAttempts++;
                    int remainingAttempts = maxAttempts - wrongAttempts;
                    if (remainingAttempts > 0) {
                        titleLabel.setText("Voer uw pincode in (" + remainingAttempts + " pogingen over)");
                        passcodeField.setText(""); // Clear the passcode field
                    } else {
                        titleLabel.setText("Te vaak verkeerde pincode!");
                        passcodeField.setEditable(false);
                        submitButton.setEnabled(false);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(252, 212, 68));
        buttonPanel.add(submitButton);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartFrame();
            }
        });
    }
}
