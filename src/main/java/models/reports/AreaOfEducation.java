package models.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class AreaOfEducation {

    private String name;
    private List<Subject> subjectList;

    public AreaOfEducation()
    {
    }

    public AreaOfEducation(String name)
    {
        this.name = name;
        subjectList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
