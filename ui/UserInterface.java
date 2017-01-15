package sortmypreferences.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;
import sortmypreferences.SortMyPreferences;
import sortmypreferences.Sorter;
import sortmypreferences.domain.SortMe;

public class UserInterface implements Runnable {

    private JFrame frame = new JFrame();
    private SortMyPreferences smp;

    public UserInterface(SortMyPreferences smp) {
        this.smp = smp;
    }

    @Override
    public void run() {
        frame = new JFrame("Sort My Preference");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        switchTo(login());
    }

    public void switchTo(JPanel panel) {
        JScrollPane scrollContainer = new JScrollPane(panel);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(scrollContainer);
        panel.setBackground(Color.white);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel login() {
        JPanel panel = new JPanel(new MigLayout(
                "align center", // Layout Constraints
                "[]", // Column constraints
                "[]30[][][][]")); // Row constraints

        JLabel message = new JLabel("Sign in or create a new account");
        JLabel error = new JLabel(" ");
        error.setForeground(Color.red);
        JLabel name = new JLabel("User name:");
        JLabel password = new JLabel("Password:");
        JTextField nameField = new JTextField();
        JTextField passwordField = new JTextField();
        JButton signIn = new JButton("Sign in");
        JButton newUser = new JButton("New User");

        panel.add(message, "span, wrap");
        panel.add(name);
        panel.add(nameField, "width 150, span 2, wrap");
        panel.add(password);
        panel.add(passwordField, "width 150, wrap");
        panel.add(error, "span, wrap");
        panel.add(signIn);
        panel.add(newUser, "wrap");

        LoginListener listener = new LoginListener(this, smp.getDatabase(), nameField, passwordField, error, signIn, newUser);
        nameField.addActionListener(listener);
        passwordField.addActionListener(listener);
        signIn.addActionListener(listener);
        newUser.addActionListener(listener);
        return panel;
    }

    public JPanel newUser() {
        JPanel panel = new JPanel(new MigLayout(
                "align center", // Layout Constraints
                "[]", // Column constraints
                "[][][][]")); // Row constraints

        JLabel headline = new JLabel(" ");
        JLabel message = new JLabel(" ");
        message.setForeground(Color.red);
        JLabel name = new JLabel("User name:");
        JLabel password = new JLabel("Password:");
        JLabel password2 = new JLabel("Repeat password:");
        JTextField nameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField passwordField2 = new JTextField();
        JButton signIn = new JButton("Sign up");
        JButton back = new JButton("Back");

        panel.add(headline, "wrap");
        panel.add(name);
        panel.add(nameField, "width 150, wrap");
        panel.add(password);
        panel.add(passwordField, "width 150, wrap");
        panel.add(password2);
        panel.add(passwordField2, "width 150, wrap");
        panel.add(message, "span, wrap");
        panel.add(signIn);
        panel.add(back);

        NewUserListener listener = new NewUserListener(this, smp.getDatabase(), message, nameField, passwordField, passwordField2, signIn, back);
        nameField.addActionListener(listener);
        passwordField.addActionListener(listener);
        signIn.addActionListener(listener);
        back.addActionListener(listener);
        return panel;
    }

    public JPanel mainMenu() {
        JPanel panel = new JPanel(new MigLayout(
                "align center", // Layout Constraints
                "[]", // Column constraints
                "[]30[]")); // Row constraints

        JLabel tervetuloa = new JLabel("Welcome to the sorter!");
        JButton uusi = new JButton("Make a new list");
        JButton vanha = new JButton("Load previous list");
        JButton exit = new JButton("Exit");

        panel.add(tervetuloa, "wrap");
        panel.add(uusi, "");
        panel.add(vanha, "wrap");
        panel.add(exit);

        MainMenuListener listener = new MainMenuListener(this, smp, uusi, vanha, exit);
        uusi.addActionListener(listener);
        vanha.addActionListener(listener);
        exit.addActionListener(listener);
        return panel;
    }

    public JPanel addItems() {
        JPanel panel = new JPanel(new MigLayout(
                "align center", // Layout Constraints
                "[]", // Column constraints
                "[]30[]")); // Row constraints

        JLabel tervetuloa = new JLabel("Add new by pressing \"add\".");
        JTextField newSortable = new JTextField();
        newSortable.setPreferredSize(new Dimension(200, 25));
        JButton add = new JButton("add");
        JButton sort = new JButton("Sort!");
        JButton searchPicture = new JButton("Load picture");
        sort.setEnabled(false);

        panel.add(tervetuloa, "wrap");
        panel.add(newSortable);
        panel.add(add);
        panel.add(searchPicture, "wrap");
        panel.add(sort);

        AddItemsListener listener = new AddItemsListener(smp, this, newSortable, add, sort, searchPicture);
        newSortable.addActionListener(listener);
        add.addActionListener(listener);
        sort.addActionListener(listener);
        searchPicture.addActionListener(listener);
        return panel;
    }

    public JPanel sorter() {
        JPanel panel = new JPanel(new MigLayout(
                "align center", // Layout Constraints
                "[]", // Column constraints
                "[]")); // Row constraints

        Sorter sorter = smp.getSorter();
        JButton left = new JButton();
        JButton right = new JButton();
        left.setSize(new Dimension(50, 50));
        right.setSize(new Dimension(50, 50));
        ImageIcon leftPicture = sorter.getLeft().getPicture();
        ImageIcon rightPicture = sorter.getRight().getPicture();

        if (leftPicture != null) {
            left.setIcon(leftPicture);
            left.setBorder(null);
            left.setBackground(Color.white);
        } else {
            left.setText(sorter.getLeft().getNimi());
        }
        if (rightPicture != null) {
            right.setIcon(rightPicture);
            right.setBorder(null);
            right.setBackground(Color.white);
        } else {
            right.setText(sorter.getRight().getNimi());
        }

        JLabel click = new JLabel("Click the one you prefer.");
        JButton both = new JButton("Both");

        panel.add(click, "span, center, wrap");
        panel.add(left, "center");
        panel.add(both, "center, width 60:60:70");
        panel.add(right, "center");

        left.setPreferredSize(new Dimension(100, 25));
        both.setPreferredSize(new Dimension(100, 25));
        right.setPreferredSize(new Dimension(100, 25));

        SorterListener listener = new SorterListener(sorter, this, left, right, both);
        left.addActionListener(listener);
        right.addActionListener(listener);
        both.addActionListener(listener);
        return panel;
    }

    public JPanel results(ArrayList<SortMe> resultList) {
        JPanel panel = new JPanel(new MigLayout(
                "align center", // Layout Constraints
                "", // Column constraints
                "")); // Row constraints

        JLabel results = new JLabel("Results:");

        JPanel resultPanel = new JPanel(new MigLayout());
        resultPanel.setBackground(Color.white);
        for (SortMe s : resultList) {
            if (s.getPicture() != null) {
                resultPanel.add(new JLabel(s.getRank() + ". "), "center, wrap");
                resultPanel.add(new JLabel(s.getNimi()), "center, wrap");
                resultPanel.add(new JLabel(s.getPicture()), "center, wrap");
            } else {
                resultPanel.add(new JLabel(s.getRank() + ". "), "center");
                resultPanel.add(new JLabel(s.getNimi()), "wrap");
            }
        }
        JTextField name = new JTextField("Name your list");
        JButton save = new JButton("Save this list");
        JButton menu = new JButton("Main menu");

        panel.add(results, "center, wrap, span");
        panel.add(resultPanel, "center, wrap, span");
        panel.add(name, "width 150");
        panel.add(save);
        panel.add(menu);

        ResultsListener listener = new ResultsListener(smp, this, save, menu, name);
        save.addActionListener(listener);
        menu.addActionListener(listener);
        return panel;
    }

    JPanel chooseList() {
        JPanel panel = new JPanel(new MigLayout(
                "align center", // Layout Constraints
                "", // Column constraints
                "[][]30[]30[][][]")); // Row constraints

        JLabel instruction = new JLabel("Choose list, then choose action.");

        JLabel empty = new JLabel("");
        JButton menu = new JButton("Main menu");
        JButton showResults = new JButton("Show results");
        JButton sort = new JButton("Sort again");
        JButton delete = new JButton("Delete");
        JButton confirm = new JButton("Confirm delete");
        confirm.setForeground(Color.red);
        confirm.setVisible(false);

        ArrayList<JButton> listButtons = new ArrayList<>();
        ArrayList<String> lists = smp.getDatabase().getCurrentUser().getAllLists();

        if (lists.isEmpty()) {
            empty.setText("You haven't saved any lists.");
        } else {
            for (String listName : lists) {
                listButtons.add(new JButton(listName));
            }
        }
        ChooseListListener listener = new ChooseListListener(this, smp, listButtons, menu, showResults, sort, delete, confirm);

        JPanel buttons = new JPanel(new MigLayout());
        buttons.setBackground(Color.white);
        for (JButton list : listButtons) {
            buttons.add(list, "center, span, wrap");
            list.addActionListener(listener);
        }

        panel.add(instruction, "center, wrap, span");
        panel.add(empty, "center, wrap, span");
        panel.add(buttons, "center, wrap, span");
        panel.add(showResults, "width 100");
        panel.add(sort, "width 100");
        panel.add(delete, "width 100, wrap");
        panel.add(confirm, "span, wrap, center");
        panel.add(menu, "south");

        menu.addActionListener(listener);
        sort.addActionListener(listener);
        showResults.addActionListener(listener);
        delete.addActionListener(listener);
        confirm.addActionListener(listener);
        return panel;
    }

    void exit() {
        smp.getDatabase().saveUser();
        System.exit(0);
    }

}
