package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class ReportData {
    private List<LearningSituation> learningSituations = new ArrayList<>();
    private Profession profession;
    private ReportHeader reportHeader;

    public ReportData(Profession prof) {
        this.profession = prof;
    }

    public List<LearningSituation> getLearningSituations() {
        return learningSituations;
    }

    public void setLearningSituations(List<LearningSituation> learningSituations) {
        this.learningSituations = learningSituations;
    }

    public ReportHeader getReportHeader() {
        return reportHeader;
    }

    public void setReportHeader(ReportHeader reportHeader) {
        this.reportHeader = reportHeader;
    }

    public List<AreaOfEducation> getAreaOfEducationList() {
        return profession.getAoeList();
    }

    public void setAreaOfEducationList(List<AreaOfEducation> areaOfEducationList) {
        if(profession!=null){
            this.profession.setAoeList(areaOfEducationList);
        }
    }

    public Profession getProfession() {
        return profession;
    }
}
