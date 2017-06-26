package util;

import models.reports.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slade on 24.06.2017.
 */
public class TestData {

    private Subject subject;

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

    public LearningSituation getLearningSituationExample(String name, int startWeek, int endWeek, Integer lsnr) {
        LearningSituation learningSituation = new LearningSituation();
        learningSituation.setId(1);
        learningSituation.setName(name);
        learningSituation.setStartWeek(startWeek);
        learningSituation.setEndWeek(endWeek);
        learningSituation.setLsnr(lsnr);
        return learningSituation;
    }

    public FieldOfLearning getFieldOfLearningExample(String name) {
        List<LearningSituation> learningSituationList = new ArrayList<>();

        learningSituationList.add(this.getLearningSituationExample("LS 1.4: Blabla IchBinEsLeid", 4, 11, 4));
        learningSituationList.add(this.getLearningSituationExample("LS 1.5: Hilfe, ich kann nicht aufhören!", 9, 12, 5));
        learningSituationList.add(this.getLearningSituationExample("LS 1.2: Erarbeitung von Zeug", 3, 6, 2)); //TODO addLearningSituation
        learningSituationList.add(this.getLearningSituationExample("LS 1.1: Erarbeitung von was anderem", 1, 2, 1));
        learningSituationList.add(this.getLearningSituationExample("LS 1.3: Präsentation über Zeug", 7, 11, 3));
        learningSituationList.add(this.getLearningSituationExample("LS 1.6: Lorem Ipsum", 5, 8, 6));

        FieldOfLearning fieldOfLearning = new FieldOfLearning();
        fieldOfLearning.setLfNr(1);
        fieldOfLearning.setName(name);
        fieldOfLearning.setLessonHours(60);
        fieldOfLearning.setLearningSituationList(learningSituationList);
        return fieldOfLearning;
    }

    public FieldOfLearning getFieldOfLearningExample2(String name) {
        List<LearningSituation> learningSituationList = new ArrayList<>();

        learningSituationList.add(this.getLearningSituationExample("LS 2.2: Erarbeitung von Zeug", 3, 4, 2)); //TODO addLearningSituation
        learningSituationList.add(this.getLearningSituationExample("LS 2.1: Erarbeitung von was anderem", 1, 2, 1));
        learningSituationList.add(this.getLearningSituationExample("LS 2.3: Präsentation über Zeug", 6, 9, 3));
        learningSituationList.add(this.getLearningSituationExample("LS 2.4: Präsentation über Zeug", 10, 12, 4));
        learningSituationList.add(this.getLearningSituationExample("LS 2.5: Präsentation über Zeug", 3, 10, 5));

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

        subject = new Subject();
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
