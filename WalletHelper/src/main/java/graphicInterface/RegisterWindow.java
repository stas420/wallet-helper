package graphicInterface;

import userUtilities.LocalUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow {
    JFrame
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

        final LocalUser[] localUser = new LocalUser[1];

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String (passwordChars);

                localUser[0] = LocalUser.logIn(username, password);
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO close this window, open register window
            }
        });

        return localUser[0];
    }

}

