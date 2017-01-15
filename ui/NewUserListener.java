package sortmypreferences.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import sortmypreferences.Database;

class NewUserListener implements ActionListener {

    private Database db;
    private JButton signUp;
    private JButton back;
    private JTextField name;
    private JTextField password;
    private JTextField password2;
    private JLabel message;
    private UserInterface ui;

    public NewUserListener(UserInterface ui, Database db, JLabel message, JTextField nameField, JTextField passwordField, JTextField passwordField2, JButton signUp, JButton back) {
        this.signUp = signUp;
        this.back = back;
        this.name = nameField;
        this.password = passwordField;
        this.password2 = passwordField2;
        this.ui = ui;
        this.db = db;
        this.message = message;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == back) {
            ui.switchTo(ui.login());
        }
        if (ae.getSource() == signUp) {
            if (password.getText().equals(password2.getText())) {
                if (db.addNewUser(name.getText(), password.getText())) {
                    signUp.setEnabled(false);
                    message.setText("Signed up successfully. You can now log in.");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ResultsListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    ui.switchTo(ui.login());
                } else {
                    message.setText("Username already exists");
                }
            } else {
                message.setText("Passwords are not matching");
            }
        }
    }
}
