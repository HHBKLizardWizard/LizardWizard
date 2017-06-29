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
            .setFontSize(8)
            .setBold();

    public DetailReport(Table table, LearningSituation learningSituation, Template template) {
        this.table = table;
        this.learningSituation = learningSituation;
        this.template = template;
    }

    /**
     * creates the detailReport body by calling createTemplateCell()
     */
    public void createDetailReportBody() {
        if (template.isScenario()) {
            this.createTemplateCell("Einstiegsszenario:", this.learningSituation.getScenario());
        }

        if (template.isResults()) {
            this.createTemplateCell("Handlungsprodukt/Lernergebnis:", this.learningSituation.getLearningResult());
        }

        if (template.isCompetences()) {
            this.createTemplateCell("Wesentliche Kompetenzen:", this.learningSituation.getEssentialSkills());
        }

        if (template.isContents()) {
            this.createTemplateCell("Inhalte:", this.learningSituation.getContents());
        }

        if (template.isMaterials()) {
            this.createTemplateCell("Unterrichtsmaterialien:", this.learningSituation.getClassMaterial());
        }

        if (template.isNotes()) {
            this.createTemplateCell("Inhalte:", this.learningSituation.getOrganisationalDetails());
        }

        if (template.isTechnics()) {
            this.createTemplateCell("Lern- und Arbeitstechniken:", this.learningSituation.getStudyTechniques());
        }

        if (template.isAchievements()) {
            this.createTemplateCell("Leistungsnachweis:", this.learningSituation.getCertificateOfPerformance());
        }
    }

    /**
     * creates a template cell with preparedStrings for the report body
     * @param sectionName heading for the section
     * @param text a text as String
     */
    private void createTemplateCell(String sectionName, String text) {
        Cell cell = new Cell(1, 10);
        Div div = new Div();

        if (text != null && !text.isEmpty()) {

            String preparedString = this.removeAllHtmlTags(text);
            preparedString = this.removeEscapedCharacters(preparedString);

            div.add(new Paragraph(sectionName).addStyle(boldStyle));
            div.add(new Paragraph(preparedString.trim()).addStyle(style));

            cell.add(div);
            this.table.addCell(cell);
        }
    }

    /**
     * creates the header section of the report that differs in table layout
     * inserts data directly to the pdf
     */
    public void createDetailReportHeader() {
        Paragraph subject = new Paragraph("Fach: " + this.learningSituation.getSubject())
                .addStyle(boldStyle);

        Paragraph fieldOfLearning = new Paragraph("Lernfeld " +
                this.learningSituation.getFieldOfLearning().getId() +
                ": " + this.learningSituation.getFieldOfLearning().getName())
                .addStyle(boldStyle);

        Paragraph requiredSituation = new Paragraph("Anforderungssituation: " +
                this.learningSituation.getFieldOfLearning().getId() +
                ": " + this.learningSituation.getFieldOfLearning().getName())
                .addStyle(boldStyle);

        Paragraph learningSituation = new Paragraph("Lernsituation: " + this.learningSituation.getName())
                .addStyle(boldStyle);

        Paragraph duration = new Paragraph("Dauer: " + this.learningSituation.getLessonHours() + "UStd")
                .addStyle(boldStyle);

        Paragraph id = new Paragraph("ID: " + this.learningSituation.getId())
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

    /**
     * removes all html tags from the input string
     * @param string a string
     * @return a string without html tags
     */
    private String removeAllHtmlTags(String string) {
        return string.replaceAll("<[^>]*>", "");
    }

    /**
     * removes all with '\' escaped characters from the string
     * @param string a string
     * @return a string with no escaped characters
     */
    private String removeEscapedCharacters(String string) {
        String cleanString;
        cleanString = string.replaceAll("(\\\\[A-Za-z])", "");
        return cleanString;
    }
}
