package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class FieldOfLearning {

    private Integer id;
    private Integer lessonHours;
    private String name;
    private Integer lfNr;
    private Subject subject;
    private List<LearningSituation> learningSituationList = new ArrayList<>();

    public FieldOfLearning()
    {
    }

    public FieldOfLearning(Integer id, Integer lessonHours, String name,
                           Integer lfNr)
    {
        this.id = id;
        this.lessonHours = lessonHours;
        this.name = name;
        this.lfNr = lfNr;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getLessonHours()
    {
        return lessonHours;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getLfNr() {
        return lfNr;
    }

    public void setLessonHours(Integer lessonHours)
    {
        this.lessonHours = lessonHours;
    }

    public void setLfNr(Integer lfNr)
    {
        this.lfNr = lfNr;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public List<LearningSituation> getLearningSituationList() {
        return learningSituationList;
    }

    public void setLearningSituationList(List<LearningSituation> learningSituationList) {
        this.learningSituationList = learningSituationList;
    }

}
