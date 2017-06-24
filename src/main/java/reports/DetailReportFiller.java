package reports;

import com.itextpdf.layout.Document;

/**
 * Created by iho on 22.06.2017.
 */
public class DetailReportFiller implements IReportFiller {

    public void fillReport(Document document, Object data) {
        try {

            this.createHeader(document);
        } catch (Exception e) {
            System.out.println(e);
        }


        document.close();
    }

    public Document createHeader(Document document) {
        String header = "pdfHtml Header and footer example using page-events";
        //Header headerHandler = new Header(header);
        //document.add();
        return null;
    }

}
