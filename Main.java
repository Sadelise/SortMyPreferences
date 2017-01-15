package sortmypreferences;

import java.io.IOException;
import java.util.Scanner;
import sortmypreferences.UI.UserInterface;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in);
        Sorter sorter = new Sorter();
        Database db = new Database(reader, sorter);
        SortMyPreferences smp = new SortMyPreferences(db, sorter);
        UserInterface ui = new UserInterface(smp);
        ui.run();
    }
}
