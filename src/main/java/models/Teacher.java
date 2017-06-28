package models;

/**
 * Created by Administrator on 27.06.2017.
 */
public class Teacher
{
    Integer id;
    String name;
    String sex;

    public Teacher(Integer id, String name, String sex)
    {
        this.id = id;
        this.name = name;
        setSex(sex);
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
        if(sex.equals("W")){
            this.sex = "Frau";
        }
        else{
            this.sex = "Herr";
        }

    }
}
