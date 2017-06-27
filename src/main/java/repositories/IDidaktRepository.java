package repositories;

import javafx.collections.ObservableList;
import models.*;

import java.util.List;



/**
 * Created by walde on 25.06.2017.
 */
public interface IDidaktRepository {
    ObservableList<Profession> getProfessionList();
    List<Subject> getSubjectList(Profession profession);
    List<FieldOfLearning> getFieldList(Subject subject);
    List<LearningSituation> getLearningSituationList(FieldOfLearning field);
    List<LearningTechnique> getLearningTechniqueList(LearningSituation situation);
    ReportData getReportData(Profession profession, Integer year);
    List<PerformanceRecord>getPerformanceRecordList(LearningSituation situation);
    List<Integer> getDuration(Profession profession);
}

