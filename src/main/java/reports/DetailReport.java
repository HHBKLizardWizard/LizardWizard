package reports;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import models.LearningSituation;
import models.Template;

/**
 * Created by Slade on 24.06.2017.
 */
public class DetailReport {

    private Table table;
    private LearningSituation learningSituation;
    private Template template;
    private final Style style = new Style()
            .setFontSize(8);

    private final Style boldStyle = new Style()
            .setFontSize(8);


    public DetailReport(Table table, LearningSituation learningSituation, Template template) {
        this.table = table;
        this.learningSituation = learningSituation;
        this.template = template;
    }

    public void createDetailReportBody() {
        if (template.isScenario()) {
            this.createTemplateCell(this.learningSituation.getScenario());
        }

        if (template.isResults()) {
            this.createTemplateCell(this.learningSituation.getLearningResult());
        }

        if (template.isCompetences()) {
            this.createTemplateCell(this.learningSituation.getEssentialSkills());
        }

        if (template.isContents()) {
            this.createTemplateCell(this.learningSituation.getContents());
        }

        if (template.isMaterials()) {
            this.createTemplateCell(this.learningSituation.getClassMaterial());
        }

        if (template.isNotes()) {
            this.createTemplateCell(this.learningSituation.getOrganisationalDetails());
        }

        if (template.isTechnics()) {
            this.createTemplateCell(this.learningSituation.getStudyTechniques());
        }

        if (template.isAchievements()) {
            this.createTemplateCell(this.learningSituation.getCertificateOfPerformance());
        }
    }

    private void createTemplateCell(String text) {
        Cell cell = new Cell(1, 10);
        Div div = new Div();

        String preparedString = this.removeUnsupportedHtmlTags(text);
        this.parseStringToPdf(div, preparedString);

        cell.add(div);
        this.table.addCell(cell);
    }

    public void createDetailReportHeader() {
        Paragraph subject = new Paragraph("Fach: " + this.learningSituation.getSubject())
                .addStyle(boldStyle);

        Paragraph fieldOfLearning = new Paragraph("Lernfeld: ")
                .addStyle(boldStyle);

        Paragraph requiredSituation = new Paragraph("Anforderungssituation: ")
                .addStyle(boldStyle);

        Paragraph learningSituation = new Paragraph("Lernsituation X: ")
                .addStyle(boldStyle);

        Paragraph duration = new Paragraph("Dauer: " + "" + "UStd")
                .addStyle(boldStyle);

        Paragraph id = new Paragraph("ID: ")
                .addStyle(boldStyle);

        this.table.addCell(new Cell(1, 10)
                .add(subject));

        this.table.addCell(new Cell(1, 10)
                .add(fieldOfLearning));

        this.table.addCell(new Cell(1, 10)
                .add(requiredSituation));

        this.table.addCell(new Cell(1, 6)
                .add(learningSituation));

        this.table.addCell(new Cell(1, 2)
                .add(duration));

        this.table.addCell(new Cell(1, 1)
                .add(id));

        this.table.addCell(new Cell(1, 1)
                .add(new Paragraph("")));
    }

    private String removeAllHtmlTags(String string) {
        return string.replaceAll("<[^>]*>", "");
    }

    public void parseStringToPdf(Div div, String text) {
        String tmp;
        String workString = text;
        final String listTag = "<ul>";
        final String closingListTag = "</ul>";

        while (workString.contains(listTag)) {
            tmp = workString.substring(0, workString.indexOf(listTag));
            div.add(new Paragraph(tmp).addStyle(style));

            tmp = workString.substring(workString.indexOf(listTag) + listTag.length(), workString.indexOf(closingListTag));
            this.parseListToPdf(div, tmp);
            workString = workString.substring(workString.indexOf(closingListTag) + closingListTag.length(), workString.length());
        }
        div.add(new Paragraph(workString).addStyle(style));
    }

    private void parseListToPdf(Div div, String text) {
        String tmp;
        String workString = text;
        final String listItemTag = "<li>";
        final String listItemClosingTag = "</li>";

        com.itextpdf.layout.element.List pdfList = new com.itextpdf.layout.element.List();
        while (!workString.isEmpty()) {
            tmp = workString.substring(workString.indexOf(listItemTag) + listItemTag.length(), workString.indexOf(listItemClosingTag));
            workString = workString.substring(workString.indexOf(listItemClosingTag) + listItemClosingTag.length(), workString.length());
            pdfList.add(tmp);
        }
        div.add(pdfList);
    }

    private String removeUnsupportedHtmlTags(String string) {
        String cleanString;
        cleanString = string.replaceAll("</?p[^>]*>", "");
        cleanString = cleanString.replaceAll("</?span[^>]*>", "");
        cleanString = cleanString.replaceAll("</?strong[^>]*>", "");
        cleanString = cleanString.replaceAll("</?br[^>]*>", "\n");

        return cleanString;
    }

}
