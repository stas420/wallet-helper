package graphicInterface;

import userUtilities.LocalUser;
import static utilities.stringUtils.getColumnArray;

import utilities.Enums;
import utilities.RoundedBorder;
import utilities.stringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class AccountsPanel {
    /* TODO
        - [x] "Your accounts" title
        - [x] set main account
             - [x] label
             - [x] int input field
             - [x] confirmation
             - [x] if brong value then pop-up window appears
        - [x] table with all accounts of current user with fields:
             - account ID
             - title
             - value
             - currency
             - creation time
        - [ ] highlighting accounts with debts (red)
        {"ID", "Title", "Funds", "Currency", "Created"};
    */

    private final static Font boldLargeTextFont = new Font("Manrope", Font.BOLD, 20);
    private final static Font inputTextFont = new Font("Manrope", Font.PLAIN, 12);
    private final static Font buttonFont = new Font("Manrope", Font.PLAIN, 15);
    final static Color backgroundColor = new Color(45, 49, 56);
    final static Color textColor = new Color(255, 255, 222);
    final static Color accentColor = new Color(113, 55, 210);
    final static Color logOutColor = new Color(219, 65, 70);
    final static Color inputColor = new Color(83, 70, 117);
    final static RoundedBorder roundedBorder = new RoundedBorder(8);

    //static JFrame window = new JFrame("Accounts panel windows - FOR DEV PURPOSES");
    static JPanel accountPanel = new JPanel();

    private static void addTopText() {

        JPanel thisPanel = new JPanel(new BorderLayout());
        JLabel yourAccsText = new JLabel("YOUR ACCOUNTS");
        yourAccsText.setHorizontalAlignment(SwingConstants.CENTER);
        yourAccsText.setFont(boldLargeTextFont);
        yourAccsText.setForeground(textColor);

        //thisPanel.setBorder(new LineBorder(textColor)); // TODO remove after debug
        //yourAccsText.setBorder(new LineBorder(textColor));

        thisPanel.setBackground(backgroundColor);
        thisPanel.add(yourAccsText);

        accountPanel.add(thisPanel);
    }

    private static void addMainAccountSetting(LocalUser user) {

        JPanel thisPanel = new JPanel(new FlowLayout());
        JLabel text = new JLabel ("Set main account ID:");
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        JFormattedTextField input = new JFormattedTextField(numberFormat);
        JButton setButton = new JButton("Set");

        //thisPanel.setBorder(new LineBorder(textColor)); // TODO remove after debug

        text.setForeground(textColor);
        text.setBackground(backgroundColor);

        input.setBorder(roundedBorder);
        input.setBackground(inputColor);
        input.setForeground(textColor);
        input.setFont(inputTextFont);
        input.setColumns(7);

        setButton.setFont(buttonFont);
        setButton.setBackground(backgroundColor);
        setButton.setForeground(textColor);
        setButton.setBorder(roundedBorder);

        setButton.addActionListener(e -> {
            int ID = -1;

            try {
                ID = Integer.parseInt(input.getText());
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Wrong value - either not an integer or wrong account ID",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }

            if (ID >= 0) {
                user.userInfo.mainAccount = ID;
                user.pushUserToDB();
                user.pullUserFromDB(user.userInfo.UserName, user.userInfo.Password);
                MainWindow.getMainFrame().dispose();
                MainWindow.setFrame(user);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "Wrong value - either not an integer or wrong account ID",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        thisPanel.setBackground(backgroundColor);

        thisPanel.add(text);
        thisPanel.add(input);
        thisPanel.add(setButton);

        accountPanel.add(thisPanel);

    }

    private static void addTable(LocalUser user) {

        final String[] columnHeads = {"Account ID", "Title", "Funds", "Creation time"};
        String[][] contents = user.accountsInfo.stream().map(accountRecord ->
                    new String[]{
                            String.valueOf(accountRecord.AccID),
                            accountRecord.Title,
                            String.format("%.2f %s", accountRecord.Val, accountRecord.Currency),
                            stringUtils.dateFormat(accountRecord.CreateTimeStamp)
                    }).toArray(String[][]::new);

        JTable table = new JTable(contents, columnHeads);

        final Font thisFont = new Font("Manrope", Font.PLAIN, 12);

        table.setFont(thisFont);
        table.setBackground(backgroundColor);
        table.setForeground(textColor);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        /*
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, centerRenderer);
        */

        JScrollPane thisScrollPane = new JScrollPane(table);
        thisScrollPane.setBackground(backgroundColor);

        thisScrollPane.setBorder(new EmptyBorder(new Insets(0,0,0,0)));


        accountPanel.add(thisScrollPane);
    }

    public static JPanel setAccountsPanel(LocalUser user) {

        accountPanel.removeAll();
        accountPanel = new JPanel();

        accountPanel.setLayout(new /*GridLayout(3, 1)*/ FlowLayout());
        accountPanel.setBackground(backgroundColor);
        addTopText();
        addMainAccountSetting(user);
        addTable(user);

        return accountPanel;
    }
}

/*
JLabel label = new JLabel ("Hello, " + username + "!");
        label.setBounds(15,15,150,20);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setVerticalTextPosition(SwingConstants.TOP);
        label.setFont(new Font("Cambria Math", Font.BOLD, 20));
        label.setForeground(new Color(219,219,219));

        MainWindow.getMainFrame().add(label);
 */

/*
DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        Date d = new Date();

        JLabel label = new JLabel ("Today's date:  " + df.format(d));

        label.setBounds(15, 50, 190, 20);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setVerticalTextPosition(SwingConstants.TOP);
        label.setFont(new Font("Cambria Math", Font.PLAIN, 16));
        label.setForeground(new Color(219,219,219));

        MainWindow.getMainFrame().add(label);
 */