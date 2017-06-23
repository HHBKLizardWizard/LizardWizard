package models.reports;

import models.LearningSituation;

import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class FieldOfLearning {

    private int lessonHours;
    private List<LearningSituation> learningSituationList;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLessonHours() {
        return lessonHours;
    }

    public void setLessonHours(int lessonHours) {
        this.lessonHours = lessonHours;
    }

    public List<LearningSituation> getLearningSituationList() {
        return learningSituationList;
    }

    public void setLearningSituationList(List<LearningSituation> learningSituationList) {
        this.learningSituationList = learningSituationList;
    }

}
