package reports;

import com.itextpdf.layout.Document;

/**
 * Created by iho on 23.06.2017.
 */
public interface IReportFiller {

    public void fillReport(Document document, Object data);
}
