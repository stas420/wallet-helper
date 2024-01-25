package graphicInterface;

import userUtilities.LocalUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

// TODO ICON!!!!
public class MainWindow {

    // panel0 - top ribbon -> "Hello anon" + time + buttons (change..., refresh, log out)
    // panel1 - left -> accounts table + set main button with 'int field'
    // panel2 - middle -> goals table
    // panel3 - right -> transactions table

    private void setFrame() {
        SwingUtilities.invokeLater(() ->
        {
            //ImageIcon icon = new ImageIcon("./wallet-icon.ico");
            //mainFrame.setIconImage(icon.getImage());

            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.getContentPane().setBackground(new Color(38, 38, 38));
            mainFrame.setSize(1366, 768);
            mainFrame.setResizable(false);


            GridBagLayout mainGridLayout = new GridBagLayout();
            mainFrame.setLayout(mainGridLayout);

            GridLayout tablesGridLayout = new GridLayout(1, 3);
            tablesPanel.setLayout(tablesGridLayout);

            final GridBagConstraints topRow = new GridBagConstraints();
            topRow.gridx = 0;
            topRow.gridy = 0;
            topRow.weightx = 1.0;
            topRow.weighty = 0.2; // 20% of the height
            topRow.fill = GridBagConstraints.BOTH;

            setTopRibbonPanel("Anon");
            topRibbonPanel.setBackground(Color.blue);
            mainFrame.add(topRibbonPanel, topRow);

            final GridBagConstraints bottomRow = new GridBagConstraints();
            bottomRow.gridx = 0;
            bottomRow.gridy = 1;
            bottomRow.weightx = 1.0;
            bottomRow.weighty = 0.8; // 80% of the height
            bottomRow.fill = GridBagConstraints.BOTH;

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

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20)));
        JLabel helloLabel = new JLabel("Hello, " + username + "!");
        helloLabel.setFont(new Font("Comic Sans", Font.BOLD, 30));
        helloLabel.setForeground(new Color(0, 0, 0));
        textPanel.add(helloLabel, BorderLayout.NORTH);

        Date current = new Date();
        JLabel dateLabel = new JLabel(current.toString());
        textPanel.add(dateLabel);

        Timer timer = new Timer(1000, e -> {
            updateLabel(dateLabel); // Update the label every second
        });
        timer.start(); // Start the timer

        topRibbonPanel.add(textPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());


        JButton changeInfoButton = new JButton("Change ur info");
        JButton refreshAllButton = new JButton("Refresh All");
        JButton logOutButton = new JButton("Log Out");

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


