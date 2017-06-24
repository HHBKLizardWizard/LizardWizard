package reports;

import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import models.reports.ReportData;
import models.reports.ReportHeader;

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
    private Document createDocument(PdfDocument pdf, boolean isLandscape) {
        Document document = isLandscape ? new Document(pdf, PageSize.A4.rotate()) : new Document(pdf);
        document.setMargins(20, 20, 20, 20);
        return document;
    }

    /**
     * creates the header section for every generated pdf
     * @param document  PdfDocument object
     * @param headerData object combining all information necessary to build the pdf header
     */
    private void createPdfHeader(Document document, ReportHeader headerData) {

        // set page title
        Paragraph title = new Paragraph("Didaktischer Jahresplan");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFontColor(new DeviceRgb(100,149,237));

        // create uniform style for header section
        Style style = new Style();
        style.setTextAlignment(TextAlignment.LEFT);
        style.setFontSize(8);
        style.setFontColor(new DeviceRgb(100,149,237));
        style.setMargin(0);

        // set department
        Paragraph department = new Paragraph("Abteilung: " + headerData.getDepartment());
        department.addStyle(style);

        // set job
        Paragraph job = new Paragraph("Ausbildungsberuf: " + headerData.getJob());
        job.addStyle(style);

        // set yearOfTraining
        Paragraph yearOfTraining = new Paragraph("Ausbildungsjahr: " + headerData.getYearOfTraining());
        yearOfTraining.addStyle(style);

        // set classForm
        Paragraph classForm = new Paragraph("Unterrichtsform: " + headerData.getClassForm());
        classForm.addStyle(style);

        // set educationalSupervisor
        Paragraph educationalSupervisor = new Paragraph("Bildungsgangleitung: " + headerData.getEducationalSupervisor());
        educationalSupervisor.addStyle(style);

        document.add(title);
        document.add(department);
        document.add(job);
        document.add(yearOfTraining);
        document.add(classForm);
        document.add(educationalSupervisor);
    }

    public Document createAnnualReport(PdfDocument pdfDocument, ReportData reportData) {
        Document document = this.createDocument(pdfDocument, true);
        Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        AnnualReport annualReport = new AnnualReport(table);

        table.setWidthPercent(100);

        this.createPdfHeader(document, reportData.getReportHeader());
        annualReport.createAnnualReportHeader();
        annualReport.createAnnualReportBody(reportData);

        document.add(new Paragraph(""));
        document.add(table);

        return document;
    }

    public Table createDetailReport() {
        Table table = new Table(new float[]{1 ,1, 1, 1});

        return null;
    }
}