package util;

import models.reports.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slade on 24.06.2017.
 */
public class TestData {

    public ReportHeader getReportHeaderExample() {
        // preparing testdata TODO: remove
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setClassForm("Blockunterricht");
        reportHeader.setDepartment("Informationstechnik Industrie");
        reportHeader.setEducationalSupervisor("Herr Gottwald");
        reportHeader.setJob("Fachinformatiker/in Anwendungsentwicklung");
        reportHeader.setYearOfTraining(1);
        return reportHeader;
    }

    public LearningSituation getLearningSituationExample(String name, int startWeek, int endWeek) {
        LearningSituation learningSituation = new LearningSituation();
        learningSituation.setId(1);
        learningSituation.setName(name);
        learningSituation.setStartWeek(startWeek);
        learningSituation.setEndWeek(endWeek);
        return learningSituation;
    }

    public FieldOfLearning getFieldOfLearningExample(String name) {
        List<LearningSituation> learningSituationList = new ArrayList<>();

        learningSituationList.add(this.getLearningSituationExample("LS 1.2: Erarbeitung von Zeug", 3, 6)); //TODO addLearningSituation
        learningSituationList.add(this.getLearningSituationExample("LS 1.1: Erarbeitung von was anderem", 1, 2));
        learningSituationList.add(this.getLearningSituationExample("LS 1.3: Präsentation über Zeug", 7, 11));

        FieldOfLearning fieldOfLearning = new FieldOfLearning();
        fieldOfLearning.setName(name);
        fieldOfLearning.setLessonHours(60);
        fieldOfLearning.setLearningSituationList(learningSituationList);
        return fieldOfLearning;
    }

    public FieldOfLearning getFieldOfLearningExample2(String name) {
        List<LearningSituation> learningSituationList = new ArrayList<>();

        learningSituationList.add(this.getLearningSituationExample("LS 2.2: Erarbeitung von Zeug", 3, 4)); //TODO addLearningSituation
        learningSituationList.add(this.getLearningSituationExample("LS 2.1: Erarbeitung von was anderem", 1, 2));
        learningSituationList.add(this.getLearningSituationExample("LS 2.3: Präsentation über Zeug", 6, 9));
        learningSituationList.add(this.getLearningSituationExample("LS 2.4: Präsentation über Zeug", 10, 12));

        FieldOfLearning fieldOfLearning = new FieldOfLearning();
        fieldOfLearning.setName(name);
        fieldOfLearning.setLessonHours(60);
        fieldOfLearning.setLearningSituationList(learningSituationList);
        return fieldOfLearning;
    }

    public Subject getSubjectExample(String name) {
        List<FieldOfLearning> fieldOfLearningList = new ArrayList<>();
        fieldOfLearningList.add(this.getFieldOfLearningExample("LF 1: Betrieb und sein Umfeld")); //TODO add fieldoflearning
        fieldOfLearningList.add(this.getFieldOfLearningExample2("LF 2: Geschäftsprozesse und so"));

        Subject subject = new Subject();
        subject.setName(name);
        subject.setFieldOfLearningList(fieldOfLearningList);
        return subject;
    }

    public AreaOfEducation getAreaOfEducationExample(String name) {
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(this.getSubjectExample("Wirtschafts- und Geschäftsprozesse"));//TODO subject

        AreaOfEducation areaOfEducation = new AreaOfEducation();
        areaOfEducation.setName(name);
        areaOfEducation.setSubjectList(subjectList);
        return areaOfEducation;
    }

    public ReportData getReportDataExample() {
        List<AreaOfEducation> areaOfEducationList = new ArrayList<>();
        areaOfEducationList.add(this.getAreaOfEducationExample("Berufsbezogener Lernbereich"));

        ReportData reportData = new ReportData();

        reportData.setReportHeader(this.getReportHeaderExample());

        reportData.setAreaOfEducationList(areaOfEducationList);

        return reportData;
    }
}
