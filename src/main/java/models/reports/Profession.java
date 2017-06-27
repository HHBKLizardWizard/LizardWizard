package models.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Profession
{
    private Integer id;
    private String name;
    private Department department;
    private List<AreaOfEducation> aoeList = new ArrayList<>();
    private List<Integer> durationList = new ArrayList<>();

    public Profession(){

    }

    public Profession(Integer bid, String professionName){
        this.id = bid;
        this.name = professionName;
        this.getAoeList().add(new AreaOfEducation
                                      ("Berufsbezogerner " +
                                               "Lernbereich"));
        this.getAoeList().add(new AreaOfEducation
                                      ("Berufsübergreifender" +
                                               "Lernbereich"));
        this.getAoeList().add(new AreaOfEducation
                                                 ("Differenzierungsbereich"));
    }

    public Profession(Integer id, String name, Department department)
    {
        this.id = id;
        this.name = name;
        this.department = department;
        this.getAoeList().add(new AreaOfEducation
                                                 ("Berufsbezogerner " +
                                                          "Lernbereich"));
        this.getAoeList().add(new AreaOfEducation
                                                 ("Berufsübergreifender" +
                                                          "Lernbereich"));
        this.getAoeList().add(new AreaOfEducation
                                                 ("Differenzierungsbereich"));
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<Integer> getDurationList()
    {
        return durationList;
    }

    public void setDurationList(List<Integer> durationList)
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

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }


    public List<AreaOfEducation> getAoeList()
    {
        return aoeList;
    }

    public void setAoeList(List<AreaOfEducation> aoeList)
    {
        this.aoeList = aoeList;
    }
}
