package models.reports;

import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class ReportData {
    private List<LearningSituation> learningSituations;
    private List<AreaOfEducation> areaOfEducationList;
    private ReportHeader reportHeader;

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
        return areaOfEducationList;
    }

    public void setAreaOfEducationList(List<AreaOfEducation> areaOfEducationList) {
        this.areaOfEducationList = areaOfEducationList;
    }
}
