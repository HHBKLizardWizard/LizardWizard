package models.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 27.06.2017.
 */
public class Expertise
{
    private Integer id;
    private String name;
    private List<LearningTechnique> learningTechniqueList = new ArrayList<>();

    public Expertise(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<LearningTechnique> getLearningTechniqueList()
    {
        return learningTechniqueList;
    }

    public void setLearningTechniqueList(List<LearningTechnique> learningTechniqueList)
    {
        this.learningTechniqueList = learningTechniqueList;
    }
}
