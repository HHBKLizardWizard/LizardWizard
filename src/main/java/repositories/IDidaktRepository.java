package repositories;

import models.reports.ReportData;

import java.util.List;

/**
 * Created by walde on 25.06.2017.
 */
public interface IDidaktRepository {
    List<String> getProfessions();
    ReportData getReportData(String profName, Integer year);
    Integer getAusbildungsdauer(String profName);
}

