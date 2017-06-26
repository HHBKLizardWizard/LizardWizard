package models.ui_util;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Teacher
{
    private Integer id;
    private String name;
    private String sex;
    private Department department;

    public Teacher(Integer id,
                   String name,
                   String sex)
    {
        this.id = id;
        this.name = name;
        this.sex = sex;
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

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }
}
