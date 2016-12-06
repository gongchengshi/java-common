package sel.http;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ContentDisposition;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class Headers {
    public static class ContentType {
        public final String MediaType;
        public final String Charset;

        public ContentType(String contentType) {
            com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ContentType ct;
            try {
                 ct = new com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ContentType(contentType);
            } catch(ParseException ex) {
                MediaType = null;
                Charset = null;
                return;
            }
            MediaType = ct.getBaseType();
            Charset = ct.getParameter("charset");
        }
    }

    public static String GetFilenameFromContentDisposition(String contentDisposition) {
        try {
            ContentDisposition cd = new ContentDisposition(contentDisposition);
            return cd.getParameter("filename");
        } catch (ParseException ex) {
            return null;
        }
    }
}
