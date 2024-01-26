package graphicInterface;

import userUtilities.LocalUser;
import utilities.RoundedBorder;
import utilities.stringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HistoryPanel {
    /* TODO
        - [ ] label with text
        - [ ] Table
            - Transaction ID
            - Account ID
            - Value before
            - Change
            - Title
            - Currency
            - Time stamp
        - [ ] Highlighting records (positive change -> green, negative -> red)
     */

    private final static Font boldLargeTextFont = new Font("Manrope", Font.BOLD, 20);
    final static Color backgroundColor = new Color(45, 49, 56);
    final static Color textColor = new Color(255, 255, 222);
    final static Color accentColor = new Color(113, 55, 210);
    final static Color logOutColor = new Color(219, 65, 70);
    final static Color inputColor = new Color(83, 70, 117);
    final static RoundedBorder roundedBorder = new RoundedBorder(8);

    //static JFrame window = new JFrame("Accounts panel windows - FOR DEV PURPOSES");
    static JPanel historyPanel = new JPanel();

    private static void addTopText() {

        JPanel thisPanel = new JPanel(new BorderLayout());
        JLabel yourHistoryText = new JLabel("YOUR TRANSACTION HISTORY");
        yourHistoryText.setHorizontalAlignment(SwingConstants.CENTER);
        yourHistoryText.setFont(boldLargeTextFont);
        yourHistoryText.setForeground(textColor);

        //thisPanel.setBorder(new LineBorder(textColor)); // TODO remove after debug
        //yourHistoryText.setBorder(new LineBorder(textColor));

        thisPanel.setBackground(backgroundColor);
        thisPanel.add(yourHistoryText);

        historyPanel.add(thisPanel);
    }

    private static void addTable(LocalUser user) {

        final String[] columnHeads = {"Transaction ID", "Account ID", "Value before", "Change", "Title", "Date"};
        String[][] contents = user.historyInfo.stream().map(historyRecord ->
                new String[]{
                        String.valueOf(historyRecord.transId),
                        String.valueOf(historyRecord.accId),
                        String.format("%.2f", historyRecord.valBefore),
                        String.format("%.2f %s", historyRecord.change, historyRecord.currency),
                        historyRecord.title,
                        stringUtils.dateFormat(historyRecord.timeStamp)
                }).toArray(String[][]::new);

        JTable table = new JTable(contents, columnHeads);

        final Font thisFont = new Font("Manrope", Font.PLAIN, 12);

        table.setFont(thisFont);
        table.setBackground(backgroundColor);
        table.setForeground(textColor);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(20);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, centerRenderer);

        JScrollPane thisScrollPane = new JScrollPane(table);
        thisScrollPane.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
        thisScrollPane.setBackground(backgroundColor);

        historyPanel.add(thisScrollPane);
    }

    public static JPanel setHistoryPanel(LocalUser user) {

        historyPanel.removeAll();
        historyPanel = new JPanel();
        historyPanel.setBorder(new EmptyBorder(new Insets(20, 30, 20, 30)));
        historyPanel.setLayout(new /*GridLayout(3, 1)*/ FlowLayout());
        historyPanel.setBackground(backgroundColor);
        addTopText();
        addTable(user);

        return historyPanel;
    }
}
