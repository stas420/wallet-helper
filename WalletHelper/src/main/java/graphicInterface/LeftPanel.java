package graphicInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Font;
import java.awt.Color;

public class LeftPanel {

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

    private void addButtons() {
        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


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