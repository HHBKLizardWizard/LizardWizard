package models.ui_util;

import models.reports.Subject;

import java.util.List;

/**
 * Created by Administrator on 23.06.2017.
 */
public class ProfessionSubject
{
    private Integer id;
    private Subject subject;
    private List<WayOfTeachingProfession> wayOfTeachingProfessionList;
    private Integer Jahr;
    private Integer Position;

    public ProfessionSubject(Integer id,
                             Subject subject,
                             List<WayOfTeachingProfession> wayOfTeachingProfessionList,
                             Integer jahr, Integer position)
    {
        this.id = id;
        this.subject = subject;
        this.wayOfTeachingProfessionList = wayOfTeachingProfessionList;
        Jahr = jahr;
        Position = position;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public Integer getJahr()
    {
        return Jahr;
    }

    public void setJahr(Integer jahr)
    {
        Jahr = jahr;
    }

    public Integer getPosition()
    {
        return Position;
    }

    public void setPosition(Integer position)
    {
        Position = position;
    }
}
