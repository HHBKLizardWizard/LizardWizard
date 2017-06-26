package repositories;

import javafx.collections.ObservableList;
import models.reports.Profession;
import models.reports.ReportData;

import java.util.List;
import javafx.collections.ObservableList;


/**
 * Created by walde on 25.06.2017.
 */
public interface IDidaktRepository {
    ReportData getReportData(String profName, Integer year);
    Integer getDuration(String profName);
    ObservableList<String> getProfessions();
    List<Profession> getProfessionList();
}

