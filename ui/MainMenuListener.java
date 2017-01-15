package sortmypreferences.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import sortmypreferences.SortMyPreferences;

class MainMenuListener implements ActionListener {

    private JButton newList;
    private JButton loadList;
    private JButton exit;
    private UserInterface ui;
    private SortMyPreferences smp;

    public MainMenuListener(UserInterface ui, SortMyPreferences smp, JButton newList, JButton loadList, JButton exit) {
        this.newList = newList;
        this.loadList = loadList;
        this.exit = exit;
        this.ui = ui;
        this.smp = smp;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == newList) {
            smp.startNewList();
            newList.setEnabled(false);
            ui.switchTo(ui.addItems());
        }
        if (ae.getSource() == loadList) {
            ui.switchTo(ui.chooseList());
        }
        if (ae.getSource() == exit) {
            ui.exit();
        }
    }
}
