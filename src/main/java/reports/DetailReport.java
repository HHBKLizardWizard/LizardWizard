package reports;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import models.reports.*;

/**
 * Created by Slade on 24.06.2017.
 */
public class DetailReport {

    private Table table;

    public DetailReport(Table table) { this.table = table; }

    public void createDetailReport(ReportData reportData) {

        for (AreaOfEducation areaOfEducation : reportData.getAreaOfEducationList()) {
            for (Subject subject : areaOfEducation.getSubjectList()) {
                this.insertLearningSituationData(areaOfEducation, subject);
            }
        }
    }

    private void insertLearningSituationData(AreaOfEducation areaOfEducation, Subject subject) {
        for (FieldOfLearning fieldOfLearning : subject.getFieldOfLearningList()) {
            for (LearningSituation learningSituation : fieldOfLearning.getLearningSituationList()) {
                this.createDetailReport(areaOfEducation, subject, fieldOfLearning, learningSituation);
            }
        }
    }

    public void createDetailReport(AreaOfEducation areaOfEducation, Subject subject,
                                   FieldOfLearning fieldOfLearning, LearningSituation learningSituation) {
        Paragraph subjectParagraph = new Paragraph("Fach: " + learningSituation.getSubject())
                .setFontSize(8);

        Paragraph fieldOfLearningParagraph = new Paragraph("Lernfeld :");//TODO Infos aus FieldOfLearning einfügen

        this.table.addHeaderCell(new Cell(1, 4)
                .add(subjectParagraph));

        this.table.addHeaderCell(new Cell(1, 4)
                .add(fieldOfLearningParagraph));

    }
}
