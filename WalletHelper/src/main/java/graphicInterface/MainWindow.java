package graphicInterface;

import userUtilities.LocalUser;
import utilities.RoundedBorder;
import utilities.stringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO ICON!!!!
public class MainWindow {

    // panel0 - top ribbon -> "Hello anon" + time + buttons (change..., refresh, log out)
    // panel1 - left -> accounts table + set main button with 'int field'
    // panel2 - middle -> goals table
    // panel3 - right -> transactions table
    final static Color backgroundColor = new Color(45, 49, 56);
    final static Color textColor = new Color(255, 255, 222);
    final static Color accentColor = new Color(113, 55, 210);

    final static Color logOutColor = new Color(219, 65, 70);
    final static RoundedBorder roundedBorder = new RoundedBorder(8);

    final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    static Font manrope = new Font("Manrope", Font.PLAIN, 15);

    public static void setFrame(LocalUser user) {

        loggedInUser = user;
        mainFrame = new JFrame("Wallet Helper");

        SwingUtilities.invokeLater(() ->
        {
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setSize(1500, 768);
            mainFrame.setResizable(false);
            ImageIcon icon = new ImageIcon(LogInWindow.class.getResource("/wallet-icon.png"));
            mainFrame.setIconImage(icon.getImage());

            GridBagLayout mainGridLayout = new GridBagLayout();
            mainFrame.setLayout(mainGridLayout);

            GridLayout tablesGridLayout = new GridLayout(1, 3, 15, 15);
            tablesPanel.removeAll();
            tablesPanel = new JPanel();
            tablesPanel.setLayout(tablesGridLayout);
            tablesPanel.setBackground(backgroundColor);

            final GridBagConstraints topRow = new GridBagConstraints();
            topRow.gridx = 0;
            topRow.gridy = 0;
            topRow.weightx = 1.0;
            topRow.weighty = 0.2; // 20% of the height
            topRow.fill = GridBagConstraints.BOTH;

            setTopRibbonPanel(user.userInfo.UserName);
            topRibbonPanel.setBackground(backgroundColor);
            mainFrame.add(topRibbonPanel, topRow);

            final GridBagConstraints bottomRow = new GridBagConstraints();
            bottomRow.gridx = 0;
            bottomRow.gridy = 1;
            bottomRow.weightx = 1.0;
            bottomRow.weighty = 0.8; // 80% of the height
            bottomRow.fill = GridBagConstraints.BOTH;

            leftPanel.setBackground(backgroundColor);
            centerPanel.setBackground(backgroundColor);
            rightPanel.setBackground(backgroundColor);

            setPanels();
            //leftPanel.setBorder(new LineBorder(textColor)); // TODO remove after debug

            tablesPanel.add(leftPanel);
            tablesPanel.add(centerPanel);
            tablesPanel.add(rightPanel);

            mainFrame.add(tablesPanel, bottomRow);
            mainFrame.setVisible(true);
        });
    }

    private static void setTopRibbonPanel(String username) {
        topRibbonPanel.removeAll();
        topRibbonPanel = new JPanel();
        topRibbonPanel.setLayout(new BorderLayout());
        topRibbonPanel.setBorder(new EmptyBorder(new Insets (20,20,20,20)));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20)));
        textPanel.setBackground(backgroundColor);

        JLabel helloLabel = new JLabel();
        helloLabel.setText("Hello, " + username + "!");
        ImageIcon imageIcon = new ImageIcon(LogInWindow.class.getResource("/wallet-icon.png")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        helloLabel.setIcon(imageIcon);

        helloLabel.setHorizontalTextPosition(JLabel.RIGHT);
        helloLabel.setIconTextGap(20);
        helloLabel.setFont(new Font("Manrope", Font.BOLD, 30));
        helloLabel.setVerticalAlignment(SwingConstants.CENTER);
        helloLabel.setForeground(textColor);
        textPanel.add(helloLabel, BorderLayout.NORTH);


        JLabel dateLabel = new JLabel(sdf.format(new Date()));
        dateLabel.setFont(new Font("Manrope", Font.PLAIN, 20));
        dateLabel.setForeground(textColor);
        textPanel.add(dateLabel);

        Timer timer = new Timer(1000, e -> {
            updateLabel(dateLabel); // Update the label every second
        });
        timer.start(); // Start the timer

        topRibbonPanel.add(textPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new FlowLayout());

        JButton changeInfoButton = new JButton("Change info");
        changeInfoButton.setBackground(backgroundColor);
        changeInfoButton.setForeground(textColor);
        changeInfoButton.setBorder(roundedBorder);
        changeInfoButton.setFont(manrope);

        changeInfoButton.addActionListener(e -> {
            JFrame change = ChangeData.getChangeDataFrame(loggedInUser);
            change.setVisible(true);
        });

        JButton refreshAllButton = new JButton("Refresh all");
        refreshAllButton.setBackground(backgroundColor);
        refreshAllButton.setForeground(textColor);
        refreshAllButton.setBorder(roundedBorder);
        refreshAllButton.setFont(manrope);

        refreshAllButton.addActionListener(e -> {
            mainFrame.removeAll();
            loggedInUser.pullUserFromDB(loggedInUser.userInfo.UserName, loggedInUser.userInfo.Password);
            loggedInUser.pullAllHistoryFromDB();
            loggedInUser.pullAllGoalsFromDB();
            loggedInUser.pullAllAccountsFromDB();
            mainFrame.dispose();
            setFrame(loggedInUser);
        });

        JButton logOutButton = new JButton("Log out");
        logOutButton.setBackground(backgroundColor);
        logOutButton.setForeground(logOutColor);
        logOutButton.setBorder(roundedBorder);
        logOutButton.setFont(manrope);

        logOutButton.addActionListener(e -> {
            loggedInUser.logOutLocally();
            mainFrame.dispose();
            LogInWindow.setWindow();
        });

        buttonPanel.add(changeInfoButton);
        buttonPanel.add(refreshAllButton);
        buttonPanel.add(logOutButton);

        topRibbonPanel.add(buttonPanel, BorderLayout.EAST);
    }

    private static void updateLabel(JLabel label) {
        label.setText(sdf.format(new Date()));
    }

    private static void setPanels() {

        leftPanel = AccountsPanel.setAccountsPanel(loggedInUser);
        centerPanel = HistoryPanel.setHistoryPanel(loggedInUser);
        rightPanel = GoalsPanel.setGoalsPanel(loggedInUser);

    }

    public static LocalUser loggedInUser;
    private static JPanel leftPanel = new JPanel();
    private static JPanel centerPanel = new JPanel();
    private static JPanel rightPanel = new JPanel();
    private static JPanel topRibbonPanel = new JPanel();
    private static JPanel tablesPanel = new JPanel();

    private static JFrame mainFrame = new JFrame("Wallet Helper");

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static JPanel getLeftPanel() {
        return leftPanel;
    }

    public static JPanel getCenterPanel() {
        return centerPanel;
    }

    public static JPanel getRightPanel() {
        return rightPanel;
    }
}


