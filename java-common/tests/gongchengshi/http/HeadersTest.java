package sel.http;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HeadersTest {
    @Test()
    public void Constructor() {
        Headers.ContentType ct = new Headers.ContentType("text/html; charset=ISO-8859-4");
        Assert.assertEquals(ct.MediaType, "text/html");
        Assert.assertEquals(ct.Charset, "ISO-8859-4");

        ct = new Headers.ContentType(" text/html;  charset = ISO-8859-4 ");
        Assert.assertEquals(ct.MediaType, "text/html");
        Assert.assertEquals(ct.Charset, "ISO-8859-4");
    }
}
