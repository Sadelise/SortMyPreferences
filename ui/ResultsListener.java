package sortmypreferences.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import sortmypreferences.SortMyPreferences;
import sortmypreferences.domain.SortMe;

class ResultsListener implements ActionListener {

    private JButton menu;
    private JButton save;
    private UserInterface ui;
    private JTextField name;
    private SortMyPreferences smp;

    public ResultsListener(SortMyPreferences smp, UserInterface ui, JButton save, JButton menu, JTextField name) {
        this.menu = menu;
        this.ui = ui;
        this.save = save;
        this.name = name;
        this.smp = smp;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == menu) {
            ui.switchTo(ui.mainMenu());
        }

        if (ae.getSource() == save) {
            ArrayList<SortMe> list = smp.getSorter().getSortedList();
            if (list != null) {
                smp.getDatabase().getCurrentUser().addList(name.getText(), list);
            }
            save.setEnabled(false);
            save.setText("Saved!");
        }
    }
}
