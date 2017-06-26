package models.ui_util;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Teacher
{
    private Integer id;
    private String name;
    private String firstName;
    private String abrv;
    private Character sex;
    private Department department;

    public Teacher(Integer id,
                   String name,
                   String firstName,
                   String abrv,
                   Character sex, Department department)
    {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.abrv = abrv;
        this.sex = sex;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getAbrv()
    {
        return abrv;
    }

    public void setAbrv(String abrv)
    {
        this.abrv = abrv;
    }

    public Character getSex()
    {
        return sex;
    }

    public void setSex(Character sex)
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
