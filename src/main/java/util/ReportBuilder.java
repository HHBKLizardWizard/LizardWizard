package util;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.IOException;

/**
 * Created by iho on 22.06.2017.
 */
public class ReportBuilder {
    /**
     * @param fileName a string representing the name of the generated pdf file
     * @return a named PdfDocument object
     * @throws IOException
     */
    public PdfDocument createPdf(String fileName) throws IOException {
        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        return pdf;
    }

    /**
     * @param pdf         a PdfDocument object
     * @param isLandscape defines if the document should be picture or landscape
     * @return            returns a Document object to be filled by ReportFillers
     */
    public Document createDocument(PdfDocument pdf, boolean isLandscape) {
        Document document = isLandscape ? new Document(pdf, PageSize.A4.rotate()) : new Document(pdf);
        document.setMargins(20, 20, 20, 20);
        return document;
    }
}