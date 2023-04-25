import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class StartFrame extends JFrame implements ActionListener {
    private JTextField pinField;
    private JLabel resultLabel;
    private int triesLeft = 3;


    public StartFrame() {
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Enter 4-digit PIN:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        mainPanel.setBackground(new Color( 252, 212, 68));

        JPanel pinPanel = new JPanel();
        pinPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pinPanel.setBackground(new Color( 252, 212, 68));
        pinField = new JPasswordField(4);
        pinField.setFont(new Font("Arial", Font.BOLD, 30));
        pinField.setPreferredSize(new Dimension(120, 50));
        
        ((AbstractDocument)pinField.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
                int newLength = fb.getDocument().getLength() - length + str.length();
                if (newLength <= 4) {
                    super.replace(fb, offs, length, str, a);
                }
            }
        });
        pinField.addActionListener(this);
        pinPanel.add(pinField);
        JButton enterButton = new JButton("Enter");
        enterButton.setPreferredSize(new Dimension(150, 100));
        enterButton.addActionListener(this);
        pinPanel.add(enterButton);
        mainPanel.add(pinPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        resultLabel = new JLabel();
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 40));
        resultLabel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(resultLabel);

        setContentPane(mainPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Enter")) {
            String pin = pinField.getText();
            if (pin.length() == 4) {
                if (pin.equals("1234")) {
                    //JOptionPane.showMessageDialog(this, "Correct PIN!");
                    dispose();
                    new MainFrame();
                } else {
                    triesLeft--;
                    if (triesLeft == 0) {
                        resultLabel.setText("No more attempts left!");
                        pinField.setEnabled(false);
                        ((JButton) e.getSource()).setEnabled(false);
                    } else {
                        resultLabel.setText("Incorrect PIN! " + triesLeft + " tries left.");
                        pinField.setText("");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new SnelOpnemen();
    }
}
