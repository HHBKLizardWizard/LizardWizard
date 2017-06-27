package models;

/**
 * Created by Administrator on 27.06.2017.
 */
public class LearningTechnique
{
    private Integer id;
    private String technique;
    private String expertise;
    private LearningSituation learningSituation;
    public LearningTechnique(Integer id,
                             String technique,
                             String expertise,
                             LearningSituation ls)
    {
        this.id = id;
        this.technique = technique;
        this.expertise = expertise;
        this.learningSituation = ls;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTechnique()
    {
        return technique;
    }

    public void setTechnique(String technique)
    {
        this.technique = technique;
    }

    public String  getExpertise()
    {
        return expertise;
    }

    public void setExpertise(String expertise)
    {
        this.expertise = expertise;
    }

    public LearningSituation getLearningSituation() {
        return learningSituation;
    }

    public void setLearningSituation(LearningSituation learningSituation) {
        this.learningSituation = learningSituation;
    }
}
