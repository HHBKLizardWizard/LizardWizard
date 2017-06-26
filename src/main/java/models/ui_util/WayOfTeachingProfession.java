package models.ui_util;

import models.reports.Profession;

/**
 * Created by Administrator on 26.06.2017.
 */
public class WayOfTeachingProfession
{
    private Integer id;
    private Integer wotprofYear;
    private Integer weeks;

    private Profession profession;
    private WayOfTeaching wayOfTeaching;

    public WayOfTeachingProfession(Integer id,
                                   Profession profession,
                                   WayOfTeaching wayOfTeaching,
                                   Integer wotprofYear, Integer weeks)
    {
        this.id = id;
        this.profession = profession;
        this.wayOfTeaching = wayOfTeaching;
        this.wotprofYear = wotprofYear;
        this.weeks = weeks;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Profession getProfession()
    {
        return profession;
    }

    public void setProfession(Profession profession)
    {
        this.profession = profession;
    }

    public WayOfTeaching getWayOfTeaching()
    {
        return wayOfTeaching;
    }

    public void setWayOfTeaching(WayOfTeaching wayOfTeaching)
    {
        this.wayOfTeaching = wayOfTeaching;
    }

    public Integer getWotprofYear()
    {
        return wotprofYear;
    }

    public void setWotprofYear(Integer wotprofYear)
    {
        this.wotprofYear = wotprofYear;
    }

    public Integer getWeeks()
    {
        return weeks;
    }

    public void setWeeks(Integer weeks)
    {
        this.weeks = weeks;
    }
}
