package models;

/**
 * Created by Administrator on 27.06.2017.
 */
public class Department
{
    Integer id;
    String name;
    Teacher teacher;

    public Department(Integer id, String name, Teacher teacher)
    {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
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

    public Teacher getTeacher()
    {
        return teacher;
    }

    public void setTeacher(Teacher teacher)
    {
        this.teacher = teacher;
    }
}
