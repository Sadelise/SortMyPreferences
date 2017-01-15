package sortmypreferences.UI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import sortmypreferences.SortMyPreferences;

class AddItemsListener implements ActionListener {

    private JTextField newSortable;
    private JButton add;
    private JButton sort;
    private JButton searchPicture;
    private UserInterface ui;
    private SortMyPreferences smp;
    private JFileChooser fc;
    private ImageIcon icon;
    private File pictureFile;

    public AddItemsListener(SortMyPreferences smp, UserInterface ui, JTextField newSortable, JButton add, JButton sort, JButton searchPicture) {
        this.newSortable = newSortable;
        this.add = add;
        this.sort = sort;
        this.searchPicture = searchPicture;
        this.ui = ui;
        this.smp = smp;
        this.fc = new JFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            if (icon != null) {
                smp.addNewPicture(icon, pictureFile.getName());
                icon = null;
            } else {
                smp.addNewItemToList(newSortable.getText());
            }
            newSortable.setText("");
            if (smp.getList().size() > 1) {
                sort.setEnabled(true);
            }
        }
        if (ae.getSource() == sort) {
            sort.setEnabled(false);
            smp.sortList();
            ui.switchTo(ui.sorter());
        }

        if (ae.getSource() == searchPicture) {
            int returnVal = fc.showOpenDialog(searchPicture);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                pictureFile = fc.getSelectedFile();
                newSortable.setText(pictureFile.getName());
                icon = new ImageIcon(pictureFile.getPath());
                icon = scaleImage(icon, 300, 300);
            }
        }
    }

    public ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if (icon.getIconWidth() > w) {
            nw = w;
            nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if (nh > h) {
            nh = h;
            nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }
}
