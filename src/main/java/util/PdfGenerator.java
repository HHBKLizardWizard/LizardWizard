package util;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.IOException;

/**
 * Created by iho on 22.06.2017.
 */
public class PdfGenerator {

    /**
     *
     * @param fileName  a string representing the name of the generated pdf file
     * @return          a named PdfDocument object
     * @throws IOException
     */
    public PdfDocument createPdf(String fileName) throws IOException {
        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        return pdf;
    }

    public Document createDocument(PdfDocument pdf, boolean isLandscape) {
        Document document = isLandscape ? new Document(pdf, PageSize.A4.rotate()) : new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        document.add(new Paragraph("Hello World"));
        document.add(this.createAnnualReport());
        document.close();
        return null;
    }

    public Table createTable() {
        return null;
    }

    public Table createAnnualReport() {
        Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        table.setWidthPercent(100);
        for (int i = 0; i < 12; i++) {
            table.addHeaderCell(new Cell().add(new Paragraph("test")));
        }
        return table;
    }


}
