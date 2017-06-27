package models.reports;

/**
 * Created by Administrator on 27.06.2017.
 */
public class LearningTechnique
{
    private Integer id;
    private String technique;
    private Expertise expertise;

    public LearningTechnique(Integer id,
                             String technique,
                             Expertise expertise)
    {
        this.id = id;
        this.technique = technique;
        this.expertise = expertise;
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

    public Expertise getExpertise()
    {
        return expertise;
    }

    public void setExpertise(Expertise expertise)
    {
        this.expertise = expertise;
    }
}
