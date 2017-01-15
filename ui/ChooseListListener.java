package sortmypreferences.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import sortmypreferences.SortMyPreferences;
import sortmypreferences.domain.SortMe;

class ChooseListListener implements ActionListener {

    private UserInterface ui;
    private ArrayList<JButton> listButtons;
    private JButton menu;
    private JButton confirm;
    private JButton results;
    private JButton sort;
    private JButton delete;
    private SortMyPreferences smp;
    private JButton chosenListButton;

    public ChooseListListener(UserInterface ui, SortMyPreferences smp, ArrayList<JButton> listButtons, JButton menu, JButton results, JButton sort, JButton delete, JButton confirm) {
        this.ui = ui;
        this.listButtons = listButtons;
        this.menu = menu;
        this.results = results;
        this.sort = sort;
        this.smp = smp;
        this.delete = delete;
        this.confirm = confirm;
        this.chosenListButton = null;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == menu) {
            ui.switchTo(ui.mainMenu());
        }

        for (JButton button : listButtons) {
            if (ae.getSource() == button) {
                button.setEnabled(false);
                chosenListButton = button;
                ArrayList<SortMe> list = smp.getDatabase().getCurrentUser().getList(button.getText());
                smp.addExistingList(list);
            }
        }

        if (ae.getSource() == results) {
            ui.switchTo(ui.results(smp.getList()));
        }

        if (ae.getSource() == sort) {
            smp.sortList();
            ui.switchTo(ui.sorter());
        }

        if (ae.getSource() == delete) {
            confirm.setVisible(true);
            delete.setEnabled(false);
        }
        if (ae.getSource() == confirm) {
            if (chosenListButton != null) {
                smp.getDatabase().deleteList(chosenListButton.getText());
                chosenListButton.setVisible(false);
            }
            confirm.setVisible(false);
            delete.setEnabled(true);
        }
    }

}
