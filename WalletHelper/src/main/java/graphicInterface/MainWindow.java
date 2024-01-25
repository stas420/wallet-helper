package graphicInterface;

import userUtilities.LocalUser;
import utilities.RoundedBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// TODO ICON!!!!
public class MainWindow {

    // panel0 - top ribbon -> "Hello anon" + time + buttons (change..., refresh, log out)
    // panel1 - left -> accounts table + set main button with 'int field'
    // panel2 - middle -> goals table
    // panel3 - right -> transactions table
    final Color backgroundColor = new Color(45, 49, 56);
    final Color textColor = new Color(255, 255, 222);
    final Color accentColor = new Color(113, 55, 210);

    final Color logOutColor = new Color(219, 65, 70);
    final RoundedBorder roundedBorder = new RoundedBorder(8);

    Font manrope = new Font("Manrope", Font.PLAIN, 15);

    private void setFrame() {


        SwingUtilities.invokeLater(() ->
        {
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setSize(1366, 768);
            mainFrame.setResizable(false);
            ImageIcon icon = new ImageIcon(LogInWindow.class.getResource("/wallet-icon.png"));
            mainFrame.setIconImage(icon.getImage());


            GridBagLayout mainGridLayout = new GridBagLayout();
            mainFrame.setLayout(mainGridLayout);

            GridLayout tablesGridLayout = new GridLayout(1, 3);
            tablesPanel.setLayout(tablesGridLayout);
            tablesPanel.setBackground(backgroundColor);

            final GridBagConstraints topRow = new GridBagConstraints();
            topRow.gridx = 0;
            topRow.gridy = 0;
            topRow.weightx = 1.0;
            topRow.weighty = 0.2; // 20% of the height
            topRow.fill = GridBagConstraints.BOTH;

            setTopRibbonPanel("Anon");
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

            tablesPanel.add(leftPanel);
            tablesPanel.add(centerPanel);
            tablesPanel.add(rightPanel);

            setPanels();

            mainFrame.add(tablesPanel, bottomRow);
            mainFrame.setVisible(true);
        });
    }

    private void setTopRibbonPanel(String username) {
        topRibbonPanel.setLayout(new BorderLayout());
        topRibbonPanel.setBorder(new EmptyBorder(new Insets (20,20,20,20)));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20)));
        textPanel.setBackground(backgroundColor);

        JLabel helloLabel = new JLabel("Hello, " + username + "!");
        helloLabel.setFont(new Font("Manrope", Font.BOLD, 30));
        helloLabel.setForeground(textColor);
        textPanel.add(helloLabel, BorderLayout.NORTH);

        Date current = new Date();
        JLabel dateLabel = new JLabel(current.toString());
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


        JButton changeInfoButton = new JButton("Change ur info");
        changeInfoButton.setBackground(backgroundColor);
        changeInfoButton.setForeground(textColor);
        changeInfoButton.setBorder(roundedBorder);
        changeInfoButton.setFont(manrope);

        JButton refreshAllButton = new JButton("Refresh All");
        refreshAllButton.setBackground(backgroundColor);
        refreshAllButton.setForeground(textColor);
        refreshAllButton.setBorder(roundedBorder);
        refreshAllButton.setFont(manrope);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBackground(backgroundColor);
        logOutButton.setForeground(logOutColor);
        logOutButton.setBorder(roundedBorder);
        logOutButton.setFont(manrope);

        buttonPanel.add(changeInfoButton);
        buttonPanel.add(refreshAllButton);
        buttonPanel.add(logOutButton);

        topRibbonPanel.add(buttonPanel, BorderLayout.EAST);
    }

    private static void updateLabel(JLabel label) {
        label.setText((new Date()).toString());
    }

    private void setPanels() {
        // TODO reach panels classes
        // ...


    }

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

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setFrame();
    }
}


