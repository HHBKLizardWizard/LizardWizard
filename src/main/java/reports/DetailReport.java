package reports;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import models.reports.*;

/**
 * Created by Slade on 24.06.2017.
 */
public class DetailReport {

    private Table table;
    private LearningSituation learningSituation;
    private final Style style = new Style()
            .setFontSize(8);

    private final Style boldStyle = new Style()
            .setFontSize(8);


    public DetailReport(Table table, LearningSituation learningSituation) {
        this.table = table;
        this.learningSituation = learningSituation;
    }

    public void createDetailReportBody() {
        this.table.addCell(new Cell(1, 10).add(new Paragraph("")));
        this.table.addCell(new Cell(1, 10).add(new Paragraph("")));
        this.table.addCell(new Cell(1, 10).add(new Paragraph("")));
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
}
