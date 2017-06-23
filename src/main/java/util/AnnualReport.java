package util;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.IOException;

/**
 * Created by Slade on 22.06.2017.
 */
public class AnnualReport implements IReportFiller {

    public void fillReport(Document document, Object data) {

        document.close();
    }

    public Table createAnnualReport() {
        Cell cell;
        Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        table.setWidthPercent(100);

        for (int i = 0; i < 12; i++) {
            cell = new Cell();
            Paragraph weekOfBlock = new Paragraph((i < 9 ? "0" + (i + 1) : i + 1).toString());
            weekOfBlock.setTextAlignment(TextAlignment.CENTER);
            cell.add(weekOfBlock);

            table.addHeaderCell(cell);
        }
        return table;
    }

    public Table createRow(Table table, Object obj) {
        for (int i = 0; i < 12; i++) {
            Cell cell = new Cell();
            cell.add(new Paragraph("Hallo Welt"));

            table.addCell(cell);
        }
        return table;
    }

    public Table addLernbereich(Table table) {
        Cell lernbereich = new Cell(1, 12);
        lernbereich.add(new Paragraph("test"));
        lernbereich.setTextAlignment(TextAlignment.CENTER);
        table.addCell(lernbereich);
        return table;
    }

}
