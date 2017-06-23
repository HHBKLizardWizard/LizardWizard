package util;

import com.itextpdf.layout.Document;

/**
 * Created by iho on 22.06.2017.
 */
public class DetailReportFiller implements IReportFiller {

    public void fillReport(Document document, Object data) {

        document.close();
    }
}
