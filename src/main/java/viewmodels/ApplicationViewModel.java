package viewmodels;

import util.AnnualReport;
import util.PdfGenerator;

/**
 * Created by iho on 20.06.2017.
 */
public class ApplicationViewModel {


    public void exportPdf() {
        AnnualReport pdf = new AnnualReport();
        try {
            pdf.createDocument(pdf.createPdf("test.pdf"), true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
