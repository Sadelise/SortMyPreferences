package sortmypreferences;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import sortmypreferences.domain.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import sortmypreferences.domain.SortMe;

public class Database {

    private HashMap<String, String> usersAndPasswords;
    private User currentUser;
    private Scanner reader;

    public Database(Scanner reader, Sorter sorter) {
        this.usersAndPasswords = new HashMap<>();
        this.currentUser = null;
        this.reader = reader;
        loadFiles();
    }

    public Boolean addNewUser(String name, String password) {
        if (this.usersAndPasswords.containsKey(name)) {
            return false;
        }
        this.usersAndPasswords.put(name, password);
        savePasswords();
        return true;
    }

    public Boolean signIn(String name, String password) {
        if (this.usersAndPasswords.containsKey(name)) {
            if (this.usersAndPasswords.get(name).equals(password)) {
                this.currentUser = new User(name);
                File file = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser + "/" + currentUser + ".txt");
                if (file.exists()) {
                    loadUser(file);
                }
                return true;
            }
        }
        return false;
    }

    public void savePasswords() {
        try {
            FileWriter writer = new FileWriter("passwords.txt");

            for (String name : usersAndPasswords.keySet()) {
                writer.write(name);
                writer.write(System.getProperty("line.separator"));
                writer.write(usersAndPasswords.get(name));
                writer.write(System.getProperty("line.separator"));
                writer.write(".");
                writer.write(System.getProperty("line.separator"));
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("A problem encountered while attempting to save passwords.");
        }
    }

    private void loadFiles() {
        try {
            reader = new Scanner(new File("passwords.txt"));
            while (reader.hasNextLine()) {
                String name = reader.nextLine();
                if (name.equals(".")) {
                    continue;
                }
                if (reader.hasNextLine()) {
                    String password = reader.nextLine();
                    usersAndPasswords.put(name, password);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Reader has encountered a problem reading the file.");
        }
    }

    public void saveUser() {
        FileWriter writer = null;
        try {
            File file = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser + "/" + currentUser + ".txt");
            if(!file.exists()) {
                new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser).mkdirs();
            }
            writer = new FileWriter(file);
            ArrayList<String> lists = currentUser.getAllLists();
            for (String listName : lists) {
                writer.write(System.getProperty("line.separator"));
                writer.write(listName);
                writer.write(System.getProperty("line.separator"));

                File f = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser.toString() + "/SaveFiles/" + listName + "/");
                deleteOldImages(f, listName);

                for (SortMe s : currentUser.getList(listName)) {
                    if (s.getPicture() != null) {
                        saveImage(listName, s);
                    }
                    writer.write(s.getRank() + "");
                    writer.write(System.getProperty("line.separator"));
                    writer.write(s.getNimi());
                    writer.write(System.getProperty("line.separator"));
                }
                writer.write("-");
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteOldImages(File f, String listName) {
        if (f.exists()) {
            String[] entries = f.list();
            for (String s : entries) {
                File currentFile = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser.toString() + "/" + listName + "/" + s);
                currentFile.delete();
            }
        }
    }

    private void saveImage(String listName, SortMe s) {
        File imageFile = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser.toString() + "/" + listName + "/" + s.getRank() + ". " + s.getNimi());
        if (!imageFile.exists()) {
            new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser.toString() + "/" + listName + "/" + s.getRank() + ". " + s.getNimi()).mkdirs();
        }

        Image img = s.getPicture().getImage();

        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.SCALE_SMOOTH);

        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        try {
            ImageIO.write(bi, "jpg", imageFile);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadUser(File savefile) {
        ArrayList<SortMe> newList = new ArrayList<>();
        String listname = "";
        try {
            savefile = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser + "/" + currentUser + ".txt");
            reader = new Scanner(savefile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.equals("-")) {
                    currentUser.addList(listname, newList);
                    newList = new ArrayList<>();
                    listname = "";
                } else {
                    if (listname.equals("")) {
                        listname = line;
                    } else {
                        int rank = Integer.parseInt(line);
                        SortMe newItem = new SortMe(reader.nextLine());
                        newItem.setRank(rank);
                        newList.add(newItem);
                        File imageFile = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser.toString() + "/" + listname + "/" + newItem.getRank() + ". " + newItem.getNimi());
                        if (imageFile.exists()) {
                            ImageIcon icon = new ImageIcon(imageFile.getPath());
                            newItem.addPicture(icon);
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteList(String listName) {
        currentUser.deleteList(listName);
        File f = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/SortMyPreferences/SaveFiles/" + currentUser.toString() + "/" + listName + "/");
        deleteOldImages(f, listName);
        f.delete();
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
