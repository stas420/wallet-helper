package graphicInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import userUtilities.LocalUser;
import utilities.RoundedBorder;

import java.util.Optional;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO ICON!!!!
public class RegisterWindow {

    private static JFrame registerWindow = new JFrame();
    final static Logger logger = LogManager.getLogger(RegisterWindow.class);

    public static void setWindow() {

        RoundedBorder roundedBorder = new RoundedBorder(8);

        registerWindow.setTitle("Wallet Helper - Register");
        registerWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        registerWindow.setSize(485, 500);
        registerWindow.setResizable(false);
        registerWindow.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 15, 32));
        Font manrope = new Font("Manrope", Font.BOLD, 14);

        // username emial phon password currency
        // argumenty: username pass emial phon curr (optional) acc name
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(manrope);
        JLabel emialLabel = new JLabel("Email: ");
        emialLabel.setFont(manrope);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(manrope);
        JLabel phonLabel = new JLabel("Phone: ");
        phonLabel.setFont(manrope);
        JLabel currencyLabel = new JLabel("Preferred currency: ");
        currencyLabel.setFont(manrope);
        JLabel akauntLabel = new JLabel("[Optional] First account name: ");
        akauntLabel.setFont(manrope);

        JTextField usernameField = new JTextField();
        usernameField.setBorder(roundedBorder);
        JTextField emialField = new JTextField();
        emialField.setBorder(roundedBorder);
        JTextField akauntField = new JTextField();
        akauntField.setBorder(roundedBorder);
        JTextField currencyField = new JTextField();
        currencyField.setBorder(roundedBorder);
        JTextField phonField = new JTextField();
        phonField.setBorder(roundedBorder);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(roundedBorder);

        JButton okButton = new JButton("Ok!");
        okButton.setBorder(roundedBorder);
        okButton.setFont(manrope);
        okButton.setBackground(Color.WHITE);

        JButton backButton = new JButton("< Back");
        backButton.setBorder(roundedBorder);
        backButton.setFont(manrope);
        backButton.setBackground(Color.WHITE);

        panel.setBorder(new EmptyBorder(new Insets(10,10,10,10)));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emialLabel);
        panel.add(emialField);
        panel.add(phonLabel);
        panel.add(phonField);
        panel.add(currencyLabel);
        panel.add(currencyField);
        panel.add(akauntLabel);
        panel.add(akauntField);
        panel.add(backButton);
        panel.add(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String emial = emialField.getText();
                String phon = phonField.getText();
                String currency = currencyField.getText();
                String akaunt = akauntField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (username.isEmpty() || emial.isEmpty() || phon.isEmpty() || currency.isEmpty()
                            || password.isEmpty()) {

                    JOptionPane.showMessageDialog(registerWindow,
                            "Couldn't register - at least one entry is empty, please try again.",
                            "Register Error",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                    if (akaunt.isBlank())
                        akaunt = "My new account";

                    Optional<LocalUser> localUser = LocalUser.registerNewUser(username, password, emial, phon, currency, akaunt);

                    if (localUser.isEmpty()) {
                        logger.error("registerWindow - registerNewUser - couldn't register \n"
                                + "Username: " + username + "\nPassword: " + password);

                        JOptionPane.showMessageDialog(registerWindow,
                                "Couldn't register, please try again.",
                                "Register Error",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        registerWindow.dispose();
                        // TODO open main window(local user)
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerWindow.dispose();
                LogInWindow.setWindow();
            }
        });

        registerWindow.add(panel);
        registerWindow.setVisible(true);
    }

}

