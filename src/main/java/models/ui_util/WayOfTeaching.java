package models.ui_util;

/**
 * Created by Administrator on 26.06.2017.
 */
public class WayOfTeaching
{
        private Integer id;
        private String name;
        private String abrv;

    public WayOfTeaching(Integer id, String name, String abrv)
    {
        this.id = id;
        this.name = name;
        this.abrv = abrv;
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
}
