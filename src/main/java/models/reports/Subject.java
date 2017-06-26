package models.reports;

import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class Subject {
    private Integer id;
    private String name;
    private AreaOfEducation areaOfEducation;
    private List<FieldOfLearning> fieldOfLearningList;

    public Subject()
    {
    }

    public Subject(Integer id,
                   String name,
                   AreaOfEducation areaOfEducation,
                   List<FieldOfLearning> fieldOfLearningList)
    {
        this.id = id;
        this.name = name;
        this.areaOfEducation = areaOfEducation;
        this.fieldOfLearningList = fieldOfLearningList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldOfLearning> getFieldOfLearningList() {
        return fieldOfLearningList;
    }

    public void setFieldOfLearningList(List<FieldOfLearning> fieldOfLearningList) {
        this.fieldOfLearningList = fieldOfLearningList;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public AreaOfEducation getAreaOfEducation()
    {
        return areaOfEducation;
    }

    public void setAreaOfEducation(AreaOfEducation areaOfEducation)
    {
        this.areaOfEducation = areaOfEducation;
    }
}
