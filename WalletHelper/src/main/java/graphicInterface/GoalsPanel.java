package graphicInterface;

import userUtilities.LocalUser;
import utilities.RoundedBorder;
import utilities.stringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.*;
import java.awt.*;

public class GoalsPanel {
    /*
    TODO:
        - [ ] label "Your goals:"
        - [ ] table with goals:
            - GoalID
            - Title
            - Val
            - Goal
            - Currency
            - CreateTimeStamp
            - Deadline
        yield new String[] { "ID", "Title", "Funds", "Goal", "Currency", "Created", "Deadline"};

     */
    private final static Font boldLargeTextFont = new Font("Manrope", Font.BOLD, 20);
    final static Color backgroundColor = new Color(45, 49, 56);
    final static Color textColor = new Color(255, 255, 222);
    final static Color accentColor = new Color(113, 55, 210);
    final static Color logOutColor = new Color(219, 65, 70);
    final static Color inputColor = new Color(83, 70, 117);
    final static RoundedBorder roundedBorder = new RoundedBorder(8);

    //static JFrame window = new JFrame("Accounts panel windows - FOR DEV PURPOSES");
    static JPanel goalPanel = new JPanel();

    private static void addTopText() {

        JPanel thisPanel = new JPanel(new BorderLayout());
        JLabel yourGoalText = new JLabel("YOUR GOALS");
        yourGoalText.setHorizontalAlignment(SwingConstants.CENTER);
        yourGoalText.setFont(boldLargeTextFont);
        yourGoalText.setForeground(textColor);

        //thisPanel.setBorder(new LineBorder(textColor)); // TODO remove after debug
        //yourGoalText.setBorder(new LineBorder(textColor));

        thisPanel.setBackground(backgroundColor);
        thisPanel.add(yourGoalText);

        goalPanel.add(thisPanel);
    }

    private static void addTable(LocalUser user) {

        final String[] columnHeads = {"Goal ID", "Title", "Funds", "Goal", "Deadline", "Date added"};
        String[][] contents = user.goalsInfo.stream().map(goalRecord ->
                new String[]{
                        String.valueOf(goalRecord.GoalID),
                        goalRecord.Title,
                        String.format("%.2f %s", goalRecord.Val, goalRecord.Currency),
                        String.format("%.2f %s", goalRecord.Goal, goalRecord.Currency),
                        goalRecord.Deadline,
                        stringUtils.dateFormat(goalRecord.CreateTimeStamp)
                }).toArray(String[][]::new);

        JTable table = new JTable(contents, columnHeads);

        final Font thisFont = new Font("Manrope", Font.PLAIN, 12);

        table.setFont(thisFont);
        table.setBackground(backgroundColor);
        table.setForeground(textColor);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        table.setRowHeight(20);

        JScrollPane thisScrollPane = new JScrollPane(table);
        thisScrollPane.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
        thisScrollPane.setBackground(backgroundColor);

        goalPanel.add(thisScrollPane);
    }

    public static JPanel setGoalsPanel(LocalUser user) {

        goalPanel.removeAll();
        goalPanel = new JPanel();
        goalPanel.setBorder(new EmptyBorder(new Insets(20, 30, 20, 30)));
        goalPanel.setLayout(new /*GridLayout(3, 1)*/ FlowLayout());
        goalPanel.setBackground(backgroundColor);
        addTopText();
        addTable(user);

        return goalPanel;
    }
}

/*

    packRows(table, 2);
    LineWrapCellRenderer renderer = new LineWrapCellRenderer();
        table.setDefaultRenderer(String.class, renderer);



public static int getPreferredRowHeight(JTable table, int rowIndex, int margin) {
        int height = table.getRowHeight();

        for (int c=0; c<table.getColumnCount(); c++) {
        TableCellRenderer renderer = table.getCellRenderer(rowIndex, c);
        Component comp = table.prepareRenderer(renderer, rowIndex, c);
        int h = comp.getPreferredSize().height + 2*margin;
        height = Math.max(height, h);
        }
        return height;
        }

public static void packRows(JTable table, int margin) {
        packRows(table, 0, table.getRowCount(), margin);
        }

public static void packRows(JTable table, int start, int end, int margin) {
        for (int r=0; r<table.getRowCount(); r++) {
        // Get the preferred height
        int h = getPreferredRowHeight(table, r, margin);

        // Now set the row height using the preferred height
        if (table.getRowHeight(r) != h) {
        table.setRowHeight(r, h);
        }
        }
        }

public class LineWrapCellRenderer  extends JTextArea implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        this.setText((String)value);
        this.setWrapStyleWord(true);
        this.setLineWrap(true);
        return this;
    }
}
 */