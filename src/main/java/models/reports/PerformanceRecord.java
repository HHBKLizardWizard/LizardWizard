package models.reports;

import org.omg.CORBA.INTERNAL;

/**
 * Created by Administrator on 27.06.2017.
 */
public class PerformanceRecord
{
    private Integer id;
    private String type;

    public PerformanceRecord(Integer id, String type)
    {
        this.id = id;
        this.type = type;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
