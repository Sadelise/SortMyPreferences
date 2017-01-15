package sortmypreferences.domain;

import javax.swing.ImageIcon;

public class SortMe implements Comparable {

    private String name;
    private int rank;
    private ImageIcon picture;

    public SortMe(String name) {
        this.name = name;
    }

    public SortMe(ImageIcon picture, String name) {
        this.name = name;
        this.picture = picture;
    }

    public String getNimi() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.rank + ". " + this.name;
    }

    @Override
    public int compareTo(Object t) {
        SortMe c = (SortMe) t;
        return this.rank - c.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public ImageIcon getPicture() {
        return this.picture;
    }

    public void addPicture(ImageIcon picture) {
        this.picture = picture;
    }
}
