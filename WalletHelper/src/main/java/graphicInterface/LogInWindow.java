package graphicInterface;

import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import userUtilities.LocalUser;

public class LogInWindow {

    public LocalUser setWindow() {
        logInWindow.setTitle("Wallet Helper - Log In");
        logInWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        logInWindow.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));

        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton logIn = new JButton("Log in");
        JButton register = new JButton("Register new account");



        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String (passwordChars);
                localUser = LocalUser.logIn(username, password);
                if (localUser.isEmpty()) {
                    // TODO Show "username or password is wrong" message and make the user try again
                }
                showMessageDialog(null, "Wrong credentials, try again");
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInWindow.dispose();
                // TODO RegisterWindow.setFrame();
            }
        });



        return localUser.get();
    }

    JFrame logInWindow = new JFrame();
    public Optional<LocalUser> localUser;
}
