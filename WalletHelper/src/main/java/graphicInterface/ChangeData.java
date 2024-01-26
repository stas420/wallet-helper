package graphicInterface;

import com.sun.tools.javac.Main;
import userUtilities.LocalUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.RoundedBorder;
import utilities.stringUtils;

public class ChangeData {

    final static Font textFont = new Font("Manrope", Font.PLAIN, 14);
    final static Font inputFont = new Font("Manrope", Font.PLAIN, 12);
    final static Color backgroundColor = new Color(45, 49, 56);
    final static Color textColor = new Color(255, 255, 222);
    final static Color accentColor = new Color(83, 70, 117);
    final static Color redColor = new Color(219, 65, 70);
    final static RoundedBorder roundedBorder = new RoundedBorder(8);

    public static JFrame getChangeDataFrame(LocalUser user) {

        JFrame frame = new JFrame("WalletHelper - Data Changing");
        frame.setSize(500, 150);
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon(LogInWindow.class.getResource("/wallet-icon.png"));
        frame.setIconImage(icon.getImage());

        JPanel rootPanel = new JPanel(new GridLayout(3,2,5,16));
        rootPanel.setBackground(backgroundColor);
        rootPanel.setBorder(new EmptyBorder(new Insets(5,5,5,5)));

        JLabel sectionLabel = new JLabel("Select section you want to modify: ");
        sectionLabel.setFont(textFont);
        sectionLabel.setForeground(textColor);
        JLabel actionLabel = new JLabel("Select modifying mode: ");
        actionLabel.setFont(textFont);
        actionLabel.setForeground(textColor);

        final String[] sectionChoices = {"Accounts", "History", "Goals"};
        final String[] actionChoices = {"Add new...", "Change...", "Remove..."};

        JComboBox<String[]> sectionDropdown = new JComboBox(sectionChoices);
        sectionDropdown.setFont(textFont);
        sectionLabel.setLabelFor(sectionDropdown);

        JComboBox<String[]> actionDropdown = new JComboBox(actionChoices);
        actionDropdown.setFont(textFont);
        actionLabel.setLabelFor(actionDropdown);

        rootPanel.add(sectionLabel);
        rootPanel.add(sectionDropdown);
        rootPanel.add(actionLabel);
        rootPanel.add(actionDropdown);

        JButton backButton = new JButton("< Back");
        backButton.setFont(textFont);
        backButton.setForeground(redColor);
        backButton.setBackground(backgroundColor);
        backButton.setBorder(roundedBorder);

        JButton okButton = new JButton("Next >");
        okButton.setFont(textFont);
        okButton.setBackground(backgroundColor);
        okButton.setForeground(textColor);
        okButton.setBorder(roundedBorder);

        backButton.addActionListener(e -> {
            frame.removeAll();
            frame.dispose();
        });

        okButton.addActionListener(e -> {
            String chosenSection = (String) sectionDropdown.getSelectedItem();
            String chosenAction = (String) actionDropdown.getSelectedItem();

            Action action;
            Section section;

            if (chosenSection == null || chosenAction == null) {

                logger.error("getChangeDataFrame - chosenSection or chosenAction is null");
                JOptionPane.showMessageDialog(frame,
                        "Wallet Helper - change section/action null error",
                        "ChangeData Error",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (chosenSection.equals(sectionChoices[0])) {
                section = Section.ACCOUNTS;
            }
            else if (chosenSection.equals(sectionChoices[1])) {
                section = Section.HISTORY;
            }
            else {
                section = Section.GOALS;
            }

            if (chosenAction.equals(actionChoices[0])) {
                action = Action.ADD;
            }
            else if (chosenAction.equals(actionChoices[1])) {
                action = Action.CHANGE;
            }
            else {
                action = Action.REMOVE;
                createRemoveWindow(section);
                frame.removeAll();
                frame.dispose();
                return;
            }

            createChangeWindow(section, action);
            frame.removeAll();
            frame.dispose();
        });

        rootPanel.add(backButton);
        rootPanel.add(okButton);
        frame.add(rootPanel);
        return frame;
    }

    private static void createChangeWindow(Section section, Action action) {
        switch (section) {
            case ACCOUNTS:
                createAccountsWindow(action);
                break;
            case HISTORY:
                createHistoryWindow(action);
                break;
            case GOALS:
                createGoalsWindow(action);
                break;
        }
    }

    private static void createAccountsWindow(Action action) {
        JFrame frame = new JFrame("Account change/add window");
        frame.setResizable(false);
        frame.setVisible(true);
        JPanel rootPanel = new JPanel();
        rootPanel.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
        rootPanel.setBackground(backgroundColor);

        JComboBox<Integer[]> accountDropdown;

        if (action == Action.CHANGE) {
            // Add AccID dropdown
            frame.setSize(400, 300);
            rootPanel.setLayout(new GridLayout(5, 2, 5, 16));
            Integer[] selectableAccountIDs = MainWindow.loggedInUser.accountsInfo.stream()
                    .map(accountRecord -> accountRecord.AccID)
                    .toArray(Integer[]::new);
            JLabel accountLabel = new JLabel("Select the account to change by ID");
            accountLabel.setForeground(textColor);
            accountLabel.setFont(textFont);
            accountDropdown = new JComboBox(selectableAccountIDs);
            accountDropdown.setFont(textFont);

            accountLabel.setLabelFor(accountDropdown);
            rootPanel.add(accountLabel);
            rootPanel.add(accountDropdown);
        } else {
            frame.setSize(400, 240);
            rootPanel.setLayout(new GridLayout(4, 2, 5, 16));
            accountDropdown = null;
        }

        // Title
        JLabel titleLabel = new JLabel("Title: ");
        titleLabel.setForeground(textColor);
        titleLabel.setFont(textFont);

        JTextField titleField = new JTextField();
        titleField.setFont(inputFont);
        titleField.setBackground(accentColor);
        titleField.setForeground(textColor);
        titleField.setBorder(roundedBorder);

        titleLabel.setLabelFor(titleField);
        rootPanel.add(titleLabel);
        rootPanel.add(titleField);
        
        // Value
        JLabel valueLabel = new JLabel("Value: ");
        valueLabel.setFont(textFont);
        valueLabel.setForeground(textColor);

        JTextField valueField = new JTextField();
        valueField.setFont(inputFont);
        valueField.setBackground(accentColor);
        valueField.setForeground(textColor);
        valueField.setBorder(roundedBorder);

        valueLabel.setLabelFor(valueField);
        rootPanel.add(valueLabel);
        rootPanel.add(valueField);
        
        // Currency
        JLabel currencyLabel = new JLabel("Currency: ");
        currencyLabel.setFont(textFont);
        currencyLabel.setForeground(textColor);

        JTextField currencyField = new JTextField();
        currencyField.setFont(inputFont);
        currencyField.setBackground(accentColor);
        currencyField.setForeground(textColor);
        currencyField.setBorder(roundedBorder);

        currencyLabel.setLabelFor(currencyField);
        rootPanel.add(currencyLabel);
        rootPanel.add(currencyField);

        // Buttons
        JButton backButton = new JButton("< Back");
        backButton.setFont(textFont);
        backButton.setForeground(redColor);
        backButton.setBackground(backgroundColor);
        backButton.setBorder(roundedBorder);

        JButton okButton = new JButton("Next >");
        okButton.setFont(textFont);
        okButton.setForeground(textColor);
        okButton.setBackground(backgroundColor);
        okButton.setBorder(roundedBorder);

        backButton.addActionListener(e -> {
            frame.removeAll();
            frame.dispose();
            getChangeDataFrame(MainWindow.loggedInUser).setVisible(true);
        });

        okButton.addActionListener(e -> {
            String title = titleField.getText();
            String value = valueField.getText();
            String currency = currencyField.getText();

            if (action == Action.CHANGE) {
                int accountID = (int) accountDropdown.getSelectedItem();
                MainWindow.loggedInUser.updateAccountInDB(String.valueOf(accountID), title, value, currency,
                        String.valueOf(stringUtils.getEpochTimeStamp(new Date())));
                frame.removeAll();
                frame.dispose();
                return;
            }

            if (title.isEmpty() || value.isEmpty() || currency.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Wrong or empty input values",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                float i = Float.parseFloat(value);
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame,
                        "Wrong or empty input values",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            MainWindow.loggedInUser.pushNewAccountToDB(title, value, currency);
            
            frame.removeAll();
            frame.dispose();
        });
        rootPanel.add(backButton);
        rootPanel.add(okButton);
        frame.add(rootPanel);
    }
    
    private static void createHistoryWindow(Action action) {

        JFrame frame = new JFrame("Change/add history info");
        frame.setResizable(false);
        frame.setVisible(true);
        JPanel rootPanel = new JPanel();
        rootPanel.setBackground(backgroundColor);
        rootPanel.setBorder(new EmptyBorder(new Insets(5,5,5,5)));

        JComboBox<Integer[]> historyDropdown;

        if (action == Action.CHANGE) {
            // Add TransID dropdown
            frame.setSize(550, 350);
            rootPanel.setLayout(new GridLayout(7, 2, 5, 16));
            Integer[] selectableHistoryIDs = MainWindow.loggedInUser.historyInfo.stream()
                    .map(historyRecord -> historyRecord.transId)
                    .toArray(Integer[]::new);

            JLabel historyLabel = new JLabel("Select the transaction to change by ID");
            historyLabel.setForeground(textColor);
            historyLabel.setFont(textFont);

            historyDropdown = new JComboBox(selectableHistoryIDs);
            historyDropdown.setFont(textFont);

            historyLabel.setLabelFor(historyDropdown);
            rootPanel.add(historyLabel);
            rootPanel.add(historyDropdown);
        } else {
            frame.setSize(500, 300);
            rootPanel.setLayout(new GridLayout(6, 2, 5, 16));
            historyDropdown = null;
        }

        // Account ID
        Integer[] selectableAccountIDs = MainWindow.loggedInUser.accountsInfo.stream()
                .map(accountRecord -> accountRecord.AccID)
                .toArray(Integer[]::new);
        JLabel accountLabel = new JLabel("Select the account to change by ID");
        accountLabel.setFont(textFont);
        accountLabel.setForeground(textColor);

        JComboBox<Integer[]> accountDropdown = new JComboBox(selectableAccountIDs);
        accountDropdown.setFont(textFont);

        accountLabel.setLabelFor(accountDropdown);
        rootPanel.add(accountLabel);
        rootPanel.add(accountDropdown);

        // Title
        JLabel titleLabel = new JLabel("Title: ");
        titleLabel.setForeground(textColor);
        titleLabel.setFont(textFont);

        JTextField titleField = new JTextField();
        titleLabel.setLabelFor(titleField);
        titleField.setFont(inputFont);
        titleField.setBackground(accentColor);
        titleField.setForeground(textColor);
        titleField.setBorder(roundedBorder);

        rootPanel.add(titleLabel);
        rootPanel.add(titleField);

        // Value before
        JLabel valueLabel = new JLabel("Value before: ");
        valueLabel.setFont(textFont);
        valueLabel.setForeground(textColor);

        JTextField valueField = new JTextField();
        valueField.setFont(inputFont);
        valueField.setBackground(accentColor);
        valueField.setForeground(textColor);
        valueField.setBorder(roundedBorder);


        valueLabel.setLabelFor(valueField);
        rootPanel.add(valueLabel);
        rootPanel.add(valueField);
        
        // Change
        JLabel changeLabel = new JLabel("Change before: ");
        changeLabel.setFont(textFont);
        changeLabel.setForeground(textColor);

        JTextField changeField = new JTextField();
        changeField.setFont(inputFont);
        changeField.setBackground(accentColor);
        changeField.setForeground(textColor);
        changeField.setBorder(roundedBorder);

        changeLabel.setLabelFor(changeField);
        rootPanel.add(changeLabel);
        rootPanel.add(changeField);
        
        // Currency
        JLabel currencyLabel = new JLabel("Currency: ");
        currencyLabel.setFont(textFont);
        currencyLabel.setForeground(textColor);

        JTextField currencyField = new JTextField();
        currencyField.setFont(inputFont);
        currencyField.setBackground(accentColor);
        currencyField.setForeground(textColor);
        currencyField.setBorder(roundedBorder);


        currencyLabel.setLabelFor(currencyField);
        rootPanel.add(currencyLabel);
        rootPanel.add(currencyField);

        // Buttons
        JButton backButton = new JButton("< Back");
        backButton.setFont(textFont);
        backButton.setForeground(redColor);
        backButton.setBackground(backgroundColor);
        backButton.setBorder(roundedBorder);

        JButton okButton = new JButton("Next >");
        okButton.setFont(textFont);
        okButton.setForeground(textColor);
        okButton.setBackground(backgroundColor);
        okButton.setBorder(roundedBorder);

        backButton.addActionListener(e -> {
            frame.removeAll();
            frame.dispose();
            getChangeDataFrame(MainWindow.loggedInUser).setVisible(true);
        });

        okButton.addActionListener(e -> {
            String title = titleField.getText();
            String value = valueField.getText();
            String change = changeField.getText();
            String currency = currencyField.getText();
            String accountID = String.valueOf((Integer) accountDropdown.getSelectedItem());
            if (action == Action.CHANGE) {
                int transID = (int) historyDropdown.getSelectedItem();
                MainWindow.loggedInUser.updateHistoryInDB(String.valueOf(transID), accountID, value, change, currency,
                        title, String.valueOf(stringUtils.getEpochTimeStamp(new Date())));
                frame.removeAll();
                frame.dispose();
                return;
            }

            if (title.isEmpty() || value.isEmpty() || change.isEmpty() ||
                    currency.isEmpty() || accountID.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Wrong or empty input values",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Float.parseFloat(value);
                Float.parseFloat(change);
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame,
                        "Wrong or empty input values",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            MainWindow.loggedInUser.pushNewHistoryToDB(accountID, change, currency, title);
            frame.removeAll();
            frame.dispose();
        });
        rootPanel.add(backButton);
        rootPanel.add(okButton);
        frame.add(rootPanel);
    }

    private static void createGoalsWindow(Action action) {
        JFrame frame = new JFrame("Goal change/add window");
        frame.setResizable(false);
        frame.setVisible(true);
        JPanel rootPanel = new JPanel();
        rootPanel.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
        rootPanel.setBackground(backgroundColor);
        
        JComboBox<Integer[]> goalDropdown;
        if (action == Action.CHANGE) {
            // Add GoalID dropdown
            frame.setSize(500, 350);
            rootPanel.setLayout(new GridLayout(7, 2, 5, 16));
            Integer[] selectableGoalIDs = MainWindow.loggedInUser.goalsInfo.stream()
                    .map(goalRecord -> goalRecord.GoalID)
                    .toArray(Integer[]::new);
            JLabel goalLabel = new JLabel("Select the goal to change by ID");
            goalLabel.setForeground(textColor);
            goalLabel.setFont(textFont);
            goalDropdown = new JComboBox(selectableGoalIDs);
            goalDropdown.setFont(textFont);

            goalLabel.setLabelFor(goalDropdown);
            rootPanel.add(goalLabel);
            rootPanel.add(goalDropdown);
        } else {
            frame.setSize(400, 300);
            rootPanel.setLayout(new GridLayout(6, 2, 5, 16));
            goalDropdown = null;
        }
        
        // Title
        JLabel titleLabel = new JLabel("Title: ");
        titleLabel.setForeground(textColor);
        titleLabel.setFont(textFont);

        JTextField titleField = new JTextField();
        titleField.setFont(inputFont);
        titleField.setBackground(accentColor);
        titleField.setForeground(textColor);
        titleField.setBorder(roundedBorder);

        titleLabel.setLabelFor(titleField);
        rootPanel.add(titleLabel);
        rootPanel.add(titleField);
        
        // Value
        JLabel valueLabel = new JLabel("Funds: ");
        valueLabel.setFont(textFont);
        valueLabel.setForeground(textColor);

        JTextField valueField = new JTextField();
        valueField.setFont(inputFont);
        valueField.setBackground(accentColor);
        valueField.setForeground(textColor);
        valueField.setBorder(roundedBorder);

        valueLabel.setLabelFor(valueField);
        rootPanel.add(valueLabel);
        rootPanel.add(valueField);
        
        // Goal
        JLabel goalLabel = new JLabel("Goal: ");
        goalLabel.setFont(textFont);
        goalLabel.setForeground(textColor);

        JTextField goalField = new JTextField();
        goalField.setFont(inputFont);
        goalField.setBackground(accentColor);
        goalField.setForeground(textColor);
        goalField.setBorder(roundedBorder);

        goalLabel.setLabelFor(goalField);
        rootPanel.add(goalLabel);
        rootPanel.add(goalField);
        
        // Currency
        JLabel currencyLabel = new JLabel("Currency: ");
        currencyLabel.setFont(textFont);
        currencyLabel.setForeground(textColor);

        JTextField currencyField = new JTextField();
        currencyField.setFont(inputFont);
        currencyField.setBackground(accentColor);
        currencyField.setForeground(textColor);
        currencyField.setBorder(roundedBorder);

        currencyLabel.setLabelFor(currencyField);
        rootPanel.add(currencyLabel);
        rootPanel.add(currencyField);
        
        // Deadline
        JLabel deadlineLabel = new JLabel("Deadline: ");
        deadlineLabel.setFont(textFont);
        deadlineLabel.setForeground(textColor);

        JTextField deadlineField = new JTextField();
        deadlineField.setFont(inputFont);
        deadlineField.setBackground(accentColor);
        deadlineField.setForeground(textColor);
        deadlineField.setBorder(roundedBorder);

        deadlineLabel.setLabelFor(deadlineField);
        rootPanel.add(deadlineLabel);
        rootPanel.add(deadlineField);

        // Buttons
        JButton backButton = new JButton("< Back");
        backButton.setFont(textFont);
        backButton.setForeground(redColor);
        backButton.setBackground(backgroundColor);
        backButton.setBorder(roundedBorder);

        JButton okButton = new JButton("Next >");
        okButton.setFont(textFont);
        okButton.setForeground(textColor);
        okButton.setBackground(backgroundColor);
        okButton.setBorder(roundedBorder);

        backButton.addActionListener(e -> {
            frame.removeAll();
            frame.dispose();
            getChangeDataFrame(MainWindow.loggedInUser).setVisible(true);
        });
        okButton.addActionListener(e -> {
            String title = titleField.getText();
            String goal = goalField.getText();
            String value = valueField.getText();
            String currency = currencyField.getText();
            String deadline = deadlineField.getText();
            if (action == Action.CHANGE) {
                int goalID = (int) goalDropdown.getSelectedItem();
                MainWindow.loggedInUser.updateGoalInDB(String.valueOf(goalID),
                        String.valueOf(MainWindow.loggedInUser.userInfo.UserID), title, value, goal, currency,
                        String.valueOf(stringUtils.getEpochTimeStamp(new Date())), deadline);
                frame.removeAll();
                frame.dispose();
                return;
            }

            if (title.isEmpty() || goal.isEmpty() || value.isEmpty() || currency.isEmpty() || deadline.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Wrong or empty input values",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Float.parseFloat(goal);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame,
                        "Wrong or empty input values",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            MainWindow.loggedInUser.pushNewGoalToDB(title, value, goal, currency,
                    String.valueOf(stringUtils.getEpochTimeStamp(new Date())), deadline);
            frame.removeAll();
            frame.dispose();
        });

        rootPanel.add(backButton);
        rootPanel.add(okButton);
        frame.add(rootPanel);
    }

    private static void createRemoveWindow(Section section) {
        JFrame frame = new JFrame("Remove a record");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(400, 150);
        JPanel rootPanel = new JPanel();
        rootPanel.setBackground(backgroundColor);
        rootPanel.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
        rootPanel.setLayout(new GridLayout(2,2,5,16));

        JComboBox<Integer[]> idDropdown = null;
        JLabel idLabel = new JLabel();
        switch (section) {
            case ACCOUNTS:
                Integer[] selectableAccountIDs = MainWindow.loggedInUser.accountsInfo.stream()
                        .map(accountRecord -> accountRecord.AccID)
                        .toArray(Integer[]::new);
                idDropdown = new JComboBox(selectableAccountIDs);
                idLabel.setText("Select the account to remove by ID");
                break;
            case HISTORY:
                Integer[] selectableHistoryIDs = MainWindow.loggedInUser.historyInfo.stream()
                        .map(historyRecord -> historyRecord.transId)
                        .toArray(Integer[]::new);
                idDropdown = new JComboBox(selectableHistoryIDs);
                idLabel.setText("Select the transaction to remove by ID");
                break;
            case GOALS:
                Integer[] selectableGoalIDs = MainWindow.loggedInUser.goalsInfo.stream()
                        .map(goalRecord -> goalRecord.GoalID)
                        .toArray(Integer[]::new);
                idDropdown = new JComboBox(selectableGoalIDs);
                idLabel.setText("Select the goal to remove by ID");
                break;
        }
        idDropdown.setFont(textFont);
        idLabel.setForeground(textColor);
        idLabel.setFont(textFont);

        idLabel.setLabelFor(idDropdown);
        rootPanel.add(idLabel);
        rootPanel.add(idDropdown);

        JButton backButton = new JButton("< Back");
        backButton.setFont(textFont);
        backButton.setForeground(redColor);
        backButton.setBackground(backgroundColor);
        backButton.setBorder(roundedBorder);

        JButton okButton = new JButton("Next >");
        okButton.setFont(textFont);
        okButton.setForeground(textColor);
        okButton.setBackground(backgroundColor);
        okButton.setBorder(roundedBorder);

        backButton.addActionListener(e -> {
            frame.removeAll();
            frame.dispose();
            getChangeDataFrame(MainWindow.loggedInUser).setVisible(true);
        });

        JComboBox<Integer[]> finalIdDropdown = idDropdown;
        okButton.addActionListener(e -> {
            int id = (int) finalIdDropdown.getSelectedItem();
            switch (section) {
                case HISTORY:
                    MainWindow.loggedInUser.deleteHistoryFromDB(id);
                    break;
                case GOALS:
                    MainWindow.loggedInUser.deleteGoalFromDB(id);
                    break;
                case ACCOUNTS:
                    MainWindow.loggedInUser.deleteAccountFromDB(id);
                    break;
            }
            frame.removeAll();
            frame.dispose();
        });

        rootPanel.add(backButton);
        rootPanel.add(okButton);
        frame.add(rootPanel);
    }

    private enum Action {
        ADD,
        CHANGE,
        REMOVE
    }
    private enum Section {
        ACCOUNTS,
        HISTORY,
        GOALS
    }

    private final static Logger logger = LogManager.getLogger(LocalUser.class);
}
