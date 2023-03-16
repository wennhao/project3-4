import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame implements ActionListener {

private JTextField usernameField;
private JPasswordField passwordField;
private JButton loginButton;


public LoginForm() {
setTitle("Login Form");
setSize(300, 150);
setDefaultCloseOperation(EXIT_ON_CLOSE);


JPanel panel = new JPanel();
JLabel usernameLabel = new JLabel("Username:");
JLabel passwordLabel = new JLabel("Password:");
usernameField = new JTextField(10);
passwordField = new JPasswordField(10);
loginButton = new JButton("Login");
loginButton.addActionListener(this);


panel.add(usernameLabel);
panel.add(usernameField);
panel.add(passwordLabel);
panel.add(passwordField);
panel.add(loginButton);


setContentPane(panel);
setVisible(true);
}

public void actionPerformed(ActionEvent e) {
String username = usernameField.getText();
String password = new String(passwordField.getPassword());

if (username.equals("bingatm") && password.equals("debeste")) {
JOptionPane.showMessageDialog(this, "Login successful!");
} else {
JOptionPane.showMessageDialog(this, "Login failed. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
}
}

public static void main(String[] args) {
new LoginForm();
}
}