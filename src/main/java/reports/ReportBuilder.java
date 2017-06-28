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

import models.Template;
import models.LearningSituation;
import models.LearningSituationTableElement;
import models.ReportData;
import models.ReportHeader;

import java.util.List;

/**
 * Created by iho on 22.06.2017.
 */
public class ReportBuilder {

    private List<LearningSituationTableElement> learningSituationList;
    private PdfDocument pdf;
    private ReportData reportData;
    private Template template;

    public ReportBuilder(String fileName, ReportData reportData) {
        try {
            /* //TODO so solls nachher laufen wenn der merge der pdf`s läuft.
            PdfWriter writer = new PdfWriter(fileName);
            this.pdf = new PdfDocument(writer);
            */

            this.reportData = reportData;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param isLandscape defines if the document should be picture or landscape
     * @return            returns a Document object to be filled by with ReportData
     */
    private Document createDocument(boolean isLandscape) {
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

    /**
     *
     */
    public void createReport(Template template) {
        try {
            this.template = template;
            PdfWriter writer = new PdfWriter("annualReport.pdf");

            this.pdf = new PdfDocument(writer);


            //this.createAnnualReport();
            this.createDetailReports();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void createAnnualReport() {
        Document document = this.createDocument(true);
        Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        AnnualReport annualReport = new AnnualReport(table);

        table.setWidthPercent(100);
        table.setFixedLayout();

        this.createPdfHeader(document, reportData.getReportHeader());
        annualReport.createAnnualReport(reportData);

        document.add(new Paragraph(""));
        document.add(table);

        this.learningSituationList = annualReport.getLearningSituationList();
        document.close();
    }

    /**
     *  iterates through a list of detailReports and
     */
    private void createDetailReports() {
        for (LearningSituationTableElement learningSituation : this.learningSituationList) {
           if (learningSituation instanceof LearningSituation) {
               this.createDetailReport((LearningSituation) learningSituation);
               break; //entfernen
           }
       }
    }

    /**
     * creates a single detailReport
     */
    private void createDetailReport(LearningSituation learningSituation) {
        try {
            PdfWriter writer = new PdfWriter("detailReport" + learningSituation.getId() +".pdf");
            this.pdf = new PdfDocument(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Document document = this.createDocument(false);
        Table table = new Table(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        DetailReport detailReport = new DetailReport(table, learningSituation, template);

        table.setWidthPercent(100);
        table.setFixedLayout();

        this.createPdfHeader(document, this.reportData.getReportHeader());

        detailReport.createDetailReportHeader();
        detailReport.createDetailReportBody();

        document.add(new Paragraph(""));
        document.add(table);
        document.close();
    }

    public Document mergeDocuments(Document document1, Document document2) {
        return null;
    }
}