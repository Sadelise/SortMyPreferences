package sortmypreferences.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import sortmypreferences.Database;

public class LoginListener implements ActionListener {

    private Database db;
    private JButton signIn;
    private JButton newUser;
    private JTextField name;
    private JTextField password;
    private JLabel error;
    private UserInterface ui;

    public LoginListener(UserInterface ui, Database db, JTextField name, JTextField password, JLabel error, JButton signIn, JButton newUser) {
        this.signIn = signIn;
        this.newUser = newUser;
        this.name = name;
        this.password = password;
        this.ui = ui;
        this.db = db;
        this.error = error;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == signIn) {
            if (db.signIn(name.getText(), password.getText())) {
                signIn.setEnabled(false);
                ui.switchTo(ui.mainMenu());
            } else {
                error.setText("Wrong password or user name");
            }
        }
        if (ae.getSource() == newUser) {
            newUser.setText("hgdhh");
            ui.switchTo(ui.newUser());
        }
    }
}
