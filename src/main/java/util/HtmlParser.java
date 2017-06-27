package util;

/**
 * Created by Slade on 25.06.2017.
 */
public class HtmlParser {

    public String removeHtmlTags(String string) {
        return string.replaceAll("<[^>]*>", "");
    }

    public String removePTags(String string) {
        return string.replaceAll("<[^>]p>|<p>", "");
    }

    public String removeSpans(String string) {
        return string.replaceAll("<[^>]span[^>]*>", "");
    }
}
