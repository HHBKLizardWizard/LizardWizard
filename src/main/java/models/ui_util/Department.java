package models.ui_util;

import models.ui_util.Teacher;

import java.util.List;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Department
{
    private Integer id;
    private String name;
    private String abrv;
    private String description;

    private Teacher chief;
    private Teacher repChief;
    /*Integer ID_Schulform;*/

    public Department(Integer did, String name, String abrv, String
            description, Teacher chief, Teacher repChief){
        this.id = did;
        this.name = name;
        this.abrv = abrv;
        this.description = description;
        this.chief = chief;
        this.repChief = repChief;
        this.chief.setDepartment(this);
        this.repChief.setDepartment(this);
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

    public String getAbrv()
    {
        return abrv;
    }

    public void setAbrv(String abrv)
    {
        this.abrv = abrv;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Teacher getChief()
    {
        return chief;
    }

    public void setChief(Teacher chief)
    {
        this.chief = chief;
    }

    public Teacher getRepChief()
    {
        return repChief;
    }

    public void setRepChief(Teacher repChief)
    {
        this.repChief = repChief;
    }
}
