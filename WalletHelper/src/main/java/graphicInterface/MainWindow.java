package graphicInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.Font;

public class MainWindow {

    private void addUsernameLabel(String username) {
        JLabel label = new JLabel ("Hello, " + username + "!");
        label.setBounds(15,15,150,20);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setVerticalTextPosition(SwingConstants.TOP);
        label.setFont(new Font("Cambria Math", Font.BOLD, 20));
        label.setForeground(new Color(219,219,219));

        this.mainFrame.add(label);
    }

    private void addDateLabel() {
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        Date d = new Date();

        JLabel label = new JLabel ("Today's date:  " + df.format(d));

        label.setBounds(15, 50, 190, 20);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setVerticalTextPosition(SwingConstants.TOP);
        label.setFont(new Font("Cambria Math", Font.PLAIN, 16));
        label.setForeground(new Color(219,219,219));

        this.mainFrame.add(label);
    }

    private void addFirstTable() {
        // DefaultTableModel ma opcję dodawania wierszy/kolumn/whatever, można zautomatyzować tworzenie tabeli
        // zamiast hardcode;ować je
        JTable tab = new JTable(new DefaultTableModel(new Object[]{"col1", "col2"}, 5));
        //...
    }

    private void setFrame(String username) {

        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.getContentPane().setBackground(new Color(38,38,38));
        this.mainFrame.setSize(1366,768);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLayout(null);

        this.addUsernameLabel(username);
        this.addDateLabel();
        this.addFirstTable();

        this.mainFrame.setVisible(true);
    }

    JFrame mainFrame = new JFrame();

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        mw.setFrame("Nutt");
    }
}
