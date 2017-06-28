package models;

import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class Subject {
    private Integer id;
    private Integer year;
    private Integer position;
    private String name;
    private List<FieldOfLearning> fieldOfLearningList;
    private AreaOfEducation areaOfEducation;

    public Subject()
    {
    }

    public Subject(Integer id, Integer year, Integer position, Integer aoeId, String name) {
        this.id = id;
        this.year = year;
        this.position = position;
        this.areaOfEducation = (AreaOfEducation.values()[aoeId]);
        this.name = name;
    }

    public Subject(Integer id,
                   String name,
                   List<FieldOfLearning> fieldOfLearningList)
    {
        this.id = id;
        this.name = name;
        this.fieldOfLearningList = fieldOfLearningList;
    }


    public AreaOfEducation getAreaOfEducation() {
        return areaOfEducation;
    }

    public void setAreaOfEducation(AreaOfEducation areaOfEducation) {
        this.areaOfEducation = areaOfEducation;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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
}
