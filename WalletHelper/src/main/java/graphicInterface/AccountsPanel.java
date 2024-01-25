package graphicInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountsPanel {
    /* TODO
        - [ ] "Your accounts" title
        - [ ] set main account
             - [ ] label
             - [ ] int input field
             - [ ] confirmation
             - [ ] if brong value then pop-up window appears
        - [ ] table with all accounts of current user with fields:
             - account ID
             - title
             - value
             - currency
             - creation time
        - [ ] highlighting accounts with debts (red)
        {"ID", "Title", "Funds", "Currency", "Created"};
    */

    static JFrame window = new JFrame("Accounts panel windows - FOR DEV PURPOSES");
    static JPanel accountPanel = new JPanel();


    public static void addUsernameLabel(String username) {

    }

    private void addDateLabel() {

    }

    private void addFirstTable() {
        // DefaultTableModel ma opcję dodawania wierszy/kolumn/whatever, można zautomatyzować tworzenie tabeli
        // zamiast hardcode;ować je
        JTable tab = new JTable(new DefaultTableModel(new Object[]{"col1", "col2"}, 5));
        //...
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