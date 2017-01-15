package sortmypreferences.UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import sortmypreferences.Sorter;

class SorterListener implements ActionListener {

    private JButton left;
    private JButton right;
    private JButton both;
    private UserInterface ui;
    private Sorter sorter;

    public SorterListener(Sorter sorter, UserInterface ui, JButton left, JButton right, JButton both) {
        this.left = left;
        this.right = right;
        this.both = both;
        this.ui = ui;
        this.sorter = sorter;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == left) {
            sorter.choose("left");
        }
        if (ae.getSource() == right) {
            sorter.choose("right");
        }
        if (ae.getSource() == both) {
            sorter.choose("both");
        }
        if (sorter.isItSorted()) {
            ui.switchTo(ui.results(sorter.getSortedList()));
        } else {
            ImageIcon leftPicture = sorter.getLeft().getPicture();
            ImageIcon rightPicture = sorter.getRight().getPicture();

            if (leftPicture != null) {
                left.setIcon(leftPicture);
                left.setBorder(null);
                left.setBackground(Color.white);
            } else {
                this.left.setText(sorter.getLeft().getNimi());
            }
            if (rightPicture != null) {
                right.setIcon(rightPicture);
                right.setBorder(null);
                right.setBackground(Color.white);
            } else {
                right.setText(sorter.getRight().getNimi());
            }
        }
    }
}
