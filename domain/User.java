package sortmypreferences.domain;

import java.util.*;

public class User {

    private String username;
    private HashMap<String, ArrayList<SortMe>> savedLists;

    public User(String name) {
        this.username = name;
        this.savedLists = new HashMap<>();
    }

    public Boolean doesItExist(String listName) {
        return this.savedLists.containsKey(listName);
    }

    public void addList(String listName, ArrayList<SortMe> sorted) {
        this.savedLists.put(listName, sorted);
    }

    public ArrayList<SortMe> getList(String listName) {
        if (doesItExist(listName)) {
            return this.savedLists.get(listName);
        }
        return null;
    }

    public ArrayList<String> getAllLists() {
        ArrayList<String> lists = new ArrayList<>();
        for (String listName : savedLists.keySet()) {
            lists.add(listName);
        }
        return lists;
    }

    public Boolean deleteList(String list) {
        if (this.doesItExist(list)) {
            this.savedLists.remove(list);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return username;
    }

}
