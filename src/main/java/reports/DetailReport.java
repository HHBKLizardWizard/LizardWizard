package reports;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import models.reports.*;

/**
 * Created by Slade on 24.06.2017.
 */
public class DetailReport {

    private Table table;

    public DetailReport(Table table) { this.table = table; }

    public void createDetailReport(FieldOfLearning fieldOfLearning, LearningSituation learningSituation) {
        Paragraph subject = new Paragraph("Fach: " + learningSituation.getSubject())
                .setFontSize(8);

        Paragraph fieldOfLearningLabel = new Paragraph("Lernfeld " + fieldOfLearning.getLfNmr());
        Paragraph fieldOfLearningContent = new Paragraph(fieldOfLearning.getName());

        this.table.addHeaderCell(new Cell(1, 4)
                .add(subject));

        this.table.addHeaderCell(new Cell(1, 4)
                .add(fieldOfLearningLabel)
                .add(fieldOfLearningContent));
    }
}
