package models.reports;

import java.util.List;

/**
 * Created by iho on 23.06.2017.
 */
public class ReportData {
    private List<AreaOfEducation> areaOfEducationList;
    private ReportHeader reportHeader;

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
