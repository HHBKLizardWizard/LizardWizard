package reports;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Slade on 22.06.2017.
 */
public class AnnualReport {

    private Table table;
    private List<LearningSituationTableElement> learningSituationList = null;

    public AnnualReport(Table table) {
        this.table = table;
    }

    public List<LearningSituationTableElement> getLearningSituationList() {
        return this.learningSituationList;
    }

    public void createAnnualReport(ReportData reportData) {
        this.learningSituationList = new ArrayList<>();
        this.createAnnualReportHeader();
        this.createAnnualReportBody(reportData);
    }

    /**
     * creates the first two rows of an annual report containing the 12 weeks of the school block
     *
     * @return returns a table with two rows containing the weeks of the school block
     */
    public void createAnnualReportHeader() {
        Paragraph weeks = new Paragraph("Unterrichtswochen")
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER);

        this.table.addHeaderCell(new Cell(1, 12).add(weeks));

        // create week row
        for (int i = 0; i < 12; i++) {

            Paragraph blockWeek = new Paragraph((i < 9 ? "0" + (i + 1) : i + 1).toString())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(8);
            //.setWidth(50); //TODO hier gehts weiter //alternativ fixed width an table .. margin? strings größer machen?

            Cell cell = new Cell()
                    .add(blockWeek);

            this.table.addHeaderCell(cell);
        }

    }

    /**
     * creates the body of an annualReport
     * @param reportData
     */
    public void createAnnualReportBody(ReportData reportData) {
        this.createSections(reportData);
    }

    /**
     * creates a List with subjectLists grouped by AreaOfEducations
     * @param reportData
     * @return  a List with subjectLists representing a areaOfEducationList
     */
    private List<List<Subject>> getSubjectsOrderedByAreaOfEducation(ReportData reportData) {
        List<List<Subject>> subjectListList = new ArrayList<>();
        List<Subject> subjectList = new ArrayList<>();

        for (Subject subject : reportData.getProfession().getSubjects()) {
            if(subject.getYear() == reportData.getReportHeader().getYearOfTraining())
            subjectList.add(subject);
        }

        List<Subject> subjectListBerufsbezogen = subjectList.stream().filter((subject) ->
                subject.getAreaOfEducation().equals(AreaOfEducation.BERUFSBEZOGEN)).collect(Collectors.toList());

        List<Subject> subjectListBerufsübergreifend = subjectList.stream().filter((subject) ->
                subject.getAreaOfEducation().equals(AreaOfEducation.BERUFSÜBERGREIFEND)).collect(Collectors.toList());

        List<Subject> subjectListDifferenzierung = subjectList.stream().filter((subject) ->
                subject.getAreaOfEducation().equals(AreaOfEducation.DIFFERENZIERUNG)).collect(Collectors.toList());

        subjectListList.add(subjectListBerufsbezogen);
        subjectListList.add(subjectListBerufsübergreifend);
        subjectListList.add(subjectListDifferenzierung);
        return subjectListList;
    }

    /**
     * creates AreaOfEducation sections
     * @param reportData
     */
    private void createSections(ReportData reportData) {
        String areaOfEducationText[] = {"Berufsbezogener Lernbereich",
                "Berufsübergreifender Lernbereich",
                "Differenzierungsbereich"};
        Style paragraphStyle = new Style()
                .setMarginLeft(4)
                .setFontSize(8)
                .setBold();

        List<List<Subject>> areaOfEducationList = this.getSubjectsOrderedByAreaOfEducation(reportData);
        int i = 0;
        for (List<Subject> subjectList : areaOfEducationList) {
                Paragraph aoeParagraph = new Paragraph(areaOfEducationText[i])
                        .addStyle(paragraphStyle);

                this.table.addCell(new Cell(1, 12)
                        .add(aoeParagraph)
                        .setBackgroundColor(new DeviceRgb(100, 149, 237)));

                for (Subject subject : subjectList) {
                    if (!subject.getFieldOfLearningList().isEmpty()) {
                        Paragraph subjectParagraph = new Paragraph(subject.getName())
                                .addStyle(paragraphStyle)
                                .setFontColor(Color.WHITE);

                        //HeaderCell ?
                        this.table.addCell(new Cell(1, 12)
                                .add(subjectParagraph)
                                .setBackgroundColor(new DeviceRgb(169, 169, 169)));

                        this.insertSubjectData(subject);
                    }
                }
                i++;
        }
    }



    /**
     * iterates through a List of fieldOfLearning and inserts data for those to the pdc document
     * @param subject   a subject object holding a list with FieldOfLearning
     */
    private void insertSubjectData(Subject subject) {
        for (FieldOfLearning fieldOfLearning : subject.getFieldOfLearningList()) {
            Paragraph paragraph = new Paragraph(fieldOfLearning.getName() + " ("
                    + fieldOfLearning.getLessonHours() + " UStd)")
                    .setFontSize(8)
                    .setMarginLeft(4);

            this.table.addCell(new Cell(1, 12)
                    .add(paragraph));

            /*List<LearningSituation> sortedList = this.sortLearningSituationsByStartWeek(
                    fieldOfLearning.getLearningSituationList());*/
            List<LearningSituation> sortedList = this.sortLearningSituationsByLsnr(fieldOfLearning.getLearningSituationList());
            List<LearningSituationTableElement> filledList = this.fillWithPlaceholders(sortedList);
            this.learningSituationList.addAll(filledList);

            for (LearningSituationTableElement learningSituation : filledList) {
                this.insertLearningSituation(learningSituation);
            }
        }
    }

    /**
     *
     * @param sortedList
     * @return
     */
    private List<LearningSituationTableElement> fillWithPlaceholders(List<LearningSituation> sortedList) {
        List<LearningSituationTableElement> filledList = new ArrayList<>();

        for (int i = 0; i < sortedList.size(); i++) {
            LearningSituationTableElement current = sortedList.get(i);

            // adds a placeholder to the beginning if the first LearningSituation is not starting in week 1
            if (0 == i && current.getStartWeek() > 1) {
                filledList.add(0, new LearningSituationTableElement(1, current.getStartWeek() - 1));
            }
            filledList.add(current);

            /*  Adds a placeholder to the end if the last LearningSituation is not ending on week 12.
                Fills the report with placeholders ending before the next LearningSituation in the list      */
            if (sortedList.size() - 1 == i) {
                if (current.getEndWeek() < 12) {
                    filledList.add(filledList.size(), new LearningSituationTableElement(current.getEndWeek() + 1, 12));
                } else {
                    continue;
                }
            } else if (sortedList.get(i + 1).getStartWeek() <= current.getEndWeek()) {
                // if next element starts in next block fill with placeholder
                if (current.getEndWeek() < 12) {
                    filledList.add(filledList.size(), new LearningSituationTableElement(
                            current.getEndWeek() + 1, 12));
                }

                if (sortedList.get(i + 1).getStartWeek() > 1) {
                    filledList.add(filledList.size(), new LearningSituationTableElement(
                            1, sortedList.get(i + 1).getStartWeek() - 1));
                }

            } else if (current.getEndWeek() + 1 < sortedList.get(i + 1).getStartWeek()) {
                filledList.add(filledList.size(), new LearningSituationTableElement(
                        current.getEndWeek() + 1, sortedList.get(i + 1).getStartWeek() - 1));
            }
        }
        return filledList;
    }

    private List<LearningSituation> sortLearningSituationsByStartWeek(List<LearningSituation> learningSituationList) {
        Comparator<LearningSituation> byStartWeek = (ls1, ls2) -> Integer.compare(
                ls1.getStartWeek(), ls2.getStartWeek());

        return learningSituationList.stream().sorted(byStartWeek).collect(Collectors.toList());
    }

    private List<LearningSituation> sortLearningSituationsByLsnr(List<LearningSituation> learningSituationList) {
        Comparator<LearningSituation> byLsnr = (ls1, ls2) -> Integer.compare(
                ls1.getLsnr(), ls2.getLsnr());

        return learningSituationList.stream().sorted(byLsnr).collect(Collectors.toList());
    }

    private void insertLearningSituation(LearningSituationTableElement learningSituation) {
        Paragraph paragraph = new Paragraph(learningSituation.getName())
                .setFontSize(8)
                .setMarginLeft(4);

        Cell cell = new Cell(1, learningSituation.getEndWeek() - learningSituation.getStartWeek() + 1)
                .add(paragraph)
                .setBackgroundColor(learningSituation instanceof LearningSituation ?
                        new DeviceRgb(255, 255, 255) : new DeviceRgb(192, 192, 192));

        this.table.addCell(cell);
    }
}
