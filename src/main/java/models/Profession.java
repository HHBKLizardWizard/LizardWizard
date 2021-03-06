package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Profession
{
    private Integer id;
    private String name;
    private String formOfTeaching;
    private Department department;
    private List<Subject> subjects = new ArrayList<>();
    private ObservableList<Integer> durationList = FXCollections.observableArrayList();

    public Profession(Integer bid, String professionName){
        this.id = bid;
        this.name = professionName;
    }

    public Profession(Integer id, String name, Department department)
    {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public ObservableList<Integer> getDurationList()
    {
        return durationList;
    }

    public void setDurationList(ObservableList<Integer> durationList)
    {
        this.durationList = durationList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFormOfTeaching() {
        return formOfTeaching;
    }

    public void setFormOfTeaching(String formOfTeaching) {
        this.formOfTeaching = formOfTeaching;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }


}
