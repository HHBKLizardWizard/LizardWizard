package models;

/**
 * Created by Slade on 24.06.2017.
 */
public class LearningSituationTableElement {
    private int startWeek;
    private int endWeek;
    private String name = "";

    public LearningSituationTableElement() {
        // Nothing to do here
    }

    public LearningSituationTableElement(int startWeek, int endWeek) {
        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }
}
