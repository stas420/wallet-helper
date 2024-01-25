package graphicInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static java.awt.Component.CENTER_ALIGNMENT;
import static javax.swing.JComponent.setDefaultLocale;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import userUtilities.LocalUser;
import utilities.RoundedBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO ICON!!!!
public class LogInWindow {

    private static final Logger logger = LogManager.getLogger(LogInWindow.class);

    public static void setWindow() {
        logInWindow.setTitle("Wallet Helper - Log In");
        logInWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        logInWindow.setSize(400, 200);
        logInWindow.setResizable(false);

        final RoundedBorder roundedBorder = new RoundedBorder(8);

        Font manrope = new Font("Manrope", Font.BOLD, 15);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 8, 27));

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(manrope);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(manrope);

        JTextField usernameField = new JTextField();
        usernameField.setBorder(roundedBorder);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(roundedBorder);

        manrope = new Font("Manrope", Font.BOLD, 12);
        JButton logInButton = new JButton("Log in");
        logInButton.setBackground(Color.WHITE);
        logInButton.setBorder(roundedBorder);
        logInButton.setFont(manrope);
        logInButton.setMaximumSize(new Dimension(20, 80));

        JButton registerButton = new JButton("Register new account");
        registerButton.setBackground(Color.WHITE);
        registerButton.setBorder(roundedBorder);
        registerButton.setFont(manrope);
        registerButton.setMaximumSize(new Dimension(20,80));

        panel.setBorder(new EmptyBorder(new Insets(7,7,7,7)));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(logInButton);
        panel.add(registerButton);
        logInWindow.add(panel);

        logInButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String (passwordChars);
            Optional<LocalUser> localUser = LocalUser.logIn(username, password);

            if (localUser.isEmpty()) {
                logger.info("Unsuccessful login attempt.\n" +
                        "Username: " + username + "\n" +
                        "Password: " + password);
                JOptionPane.showMessageDialog(logInWindow,
                        "Couldn't log in, password or username is wrong",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                logInWindow.dispose();
                // TODO open main window(local user)
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInWindow.dispose();
                RegisterWindow.setWindow();
            }
        });

        logInWindow.setLocationRelativeTo(null);
        logInWindow.setVisible(true);
    }

    public static void main(String[] args) {
        LogInWindow liw = new LogInWindow();
        liw.setWindow();
    }

    private static JFrame logInWindow = new JFrame();

}
