package repositories;

import javafx.collections.ObservableList;
import models.*;

import java.util.List;

/**
 * Created by walde on 25.06.2017.
 * Interface used for Communication with the Didakt.db
 */
public interface IDidaktRepository {
    ObservableList<Profession> getProfessionList();
    ReportData getReportData(Profession profession, Integer year);
}

