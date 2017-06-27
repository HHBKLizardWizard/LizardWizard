package repositories;

import javafx.collections.ObservableList;
import models.reports.*;
import java.util.List;



/**
 * Created by walde on 25.06.2017.
 */
public interface IDidaktRepository {
    ObservableList<Profession> getProfessionList();
    List<Subject> getSubjectList(Profession profession);
    List<FieldOfLearning> getFieldList(Subject subject);
    List<LearningSituation> getLearningSituationList(FieldOfLearning field);
    List<Expertise> getLearningTechniqueList(LearningSituation situation);
    ReportData getReportData(Profession profession);
    List<PerformanceRecord>getPerformanceRecordList(LearningSituation situation);
    List<Integer> getDuration(Profession profession);

    ObservableList<String> getProfessions();





}

