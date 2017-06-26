package models.reports;

import models.ui_util.Department;
import models.ui_util.Teacher;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Profession
{
    private Integer id;
    private String name;

    public Profession(Integer bid, String professionName){
        this.id = bid;
        this.name = professionName;
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

}
