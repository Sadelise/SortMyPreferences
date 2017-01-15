package sortmypreferences;

import sortmypreferences.domain.SortMe;
import java.util.*;

public class Sorter {

    private ArrayDeque<ArrayDeque<SortMe>> partitionsToBeSorted;
    private ArrayDeque<SortMe> sortedPart;
    private ArrayDeque<SortMe> left;
    private ArrayDeque<SortMe> right;
    private int amountOfSortables;
    private int rank;
    private int rankOfPrevious;
    private int rankOfPreviousLeft;
    private int rankOfPreviousRight;
    private String previouslyChosenSide;

    public Sorter(ArrayList<SortMe> sort) {
        this.rank = 1;
        this.rankOfPrevious = 0;
        this.rankOfPreviousLeft = 0;
        this.rankOfPreviousRight = 0;
        this.previouslyChosenSide = "";
        this.amountOfSortables = sort.size();
        this.partitionsToBeSorted = new ArrayDeque<>();
        this.sortedPart = new ArrayDeque<>();
        prepareQueues(sort);
        enterQueues();
    }

    public Sorter() {
    }

    private void prepareQueues(ArrayList<SortMe> sort) {
        for (SortMe s : sort) {
            s.setRank(0);
            ArrayDeque<SortMe> single = new ArrayDeque<>();
            single.add(s);
            partitionsToBeSorted.add(single);
        }
    }

    private void enterQueues() {
        if (this.partitionsToBeSorted.size() > 1) {
            left = partitionsToBeSorted.removeFirst();
            right = partitionsToBeSorted.removeFirst();
        }
    }

    public Boolean isItSorted() {
        return partitionsToBeSorted.size() == 1 && partitionsToBeSorted.getFirst().size() == amountOfSortables;
    }

    public SortMe getLeft() {
        if (this.left.isEmpty()) {
            return null;
        }
        return this.left.getFirst();
    }

    public SortMe getRight() {
        if (this.right.isEmpty()) {
            return null;
        }
        return this.right.getFirst();
    }

    public void choose(String side) {
        Boolean rankIncrease = false;
        int rankLeft = this.left.getFirst().getRank();
        int rankRight = this.right.getFirst().getRank();

        if (side.equals("left")) {
            rankOfPrevious = rankOfPreviousLeft;
            rankIncrease = addChosenToSortedPart(left, "left");
        } else if (side.equals("right")) {
            rankOfPrevious = rankOfPreviousRight;
            rankIncrease = addChosenToSortedPart(right, "right");
        } else if (side.equals("both")) {
            addBothToSortedPart(rankLeft, rankRight);
            rankIncrease = true;
        }

        saveTheseValues(side, rankLeft, rankRight);
        updateRank(rankIncrease);
        checkForEmptyLists();
    }

    private Boolean addChosenToSortedPart(ArrayDeque<SortMe> chosenSide, String side) {
        Boolean increase = false;
        if (rankOfPrevious != 0 && rankOfPrevious == chosenSide.getFirst().getRank()
                && (previouslyChosenSide.equals(side))) {
            this.rank = rankOfPrevious;
            increase = true;
        }
        chosenSide.getFirst().setRank(this.rank);
        this.sortedPart.add(chosenSide.removeFirst());
        return increase;
    }

    private void addBothToSortedPart(int rankLeft, int rankRight) {
        if ((rankLeft != 0 && rankRight != 0) && rankLeft == rankRight
                && (rankLeft == rankOfPreviousLeft || rankRight == rankOfPreviousRight)) {
            rank = rankLeft;
        }
        left.getFirst().setRank(rank);
        right.getFirst().setRank(rank);
        this.sortedPart.add(left.removeFirst());
        this.sortedPart.add(right.removeFirst());
    }

    public void updateRank(Boolean rankIncrease) {
        if (rankIncrease == true) {
            this.rank++;
        }
        this.rank++;
    }

    private void saveTheseValues(String side, int rankLeft, int rankRight) {
        previouslyChosenSide = side;
        rankOfPrevious = this.rank;
        this.rankOfPreviousLeft = rankLeft;
        this.rankOfPreviousRight = rankRight;
    }

    private void checkForEmptyLists() {
        if (this.left.isEmpty() && this.right.isEmpty()) {
            finishCurrentSortedPart(null, "both");
        } else if (this.left.isEmpty()) {
            finishCurrentSortedPart(right, "right");
        } else if (this.right.isEmpty()) {
            finishCurrentSortedPart(left, "left");
        }
    }

    private void finishCurrentSortedPart(ArrayDeque<SortMe> notEmptyList, String side) {
        if (notEmptyList != null) {
            for (SortMe s : notEmptyList) {
                int rankBeforeChange = s.getRank();
                sortedPart.add(s);

               if (rankOfPrevious != 0 && rankOfPrevious == s.getRank() && (this.previouslyChosenSide.equals(side) || previouslyChosenSide.equals("both"))) {
                    s.setRank(rankOfPrevious);
                } else {
                    s.setRank(rank);
                }
                rankOfPrevious = rankBeforeChange;
                rank++;
            }
        }
        partitionsToBeSorted.add(new ArrayDeque<>(sortedPart));
        sortedPart.clear();
        this.rank = 1;
        this.rankOfPrevious = 0;
        enterQueues();
    }

    public ArrayList<SortMe> getSortedList() {
        ArrayList<SortMe> sorted = new ArrayList<>();
        if (partitionsToBeSorted != null) {
            if (partitionsToBeSorted.size() == 1) {
                for (SortMe s : partitionsToBeSorted.getFirst()) {
                    sorted.add(s);
                }
            }
            return sorted;
        }
        return null;
    }

    public String getSortedListString() {
        ArrayDeque<SortMe> sorted = partitionsToBeSorted.getFirst();
        StringBuilder sb = new StringBuilder();

        for (SortMe s : sorted) {
            sb.append(s);
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
