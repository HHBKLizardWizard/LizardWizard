package models;

/**
 * Created by Administrator on 22.06.2017.
 */
public class Profession
{
    private Integer id;
    private String name;
    private Department department;
    private Teacher departmentChief;

    public Profession(Integer bid, String professionName, Department depID,
                      Teacher depChief){
        this.id = bid;
        this.name = professionName;
        this.department = depID;
        this.departmentChief = depChief;
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

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public Teacher getDepartmentChief()
    {
        return departmentChief;
    }

    public void setDepartmentChief(Teacher departmentChief)
    {
        this.departmentChief = departmentChief;
    }
}
