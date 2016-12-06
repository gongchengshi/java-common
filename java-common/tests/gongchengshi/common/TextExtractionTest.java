package sel.common;

import com.google.common.collect.Sets;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TextExtractionTest {
    String basePath = "TextExtractionTest/";

    private String ResourceAsString(String name) throws IOException {
        InputStream inputStream = TextExtractionTest.class.getResourceAsStream(name);

        java.util.Scanner s = new java.util.Scanner(inputStream, StandardCharsets.UTF_8.toString()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : null;
    }

    @Test
    public void testExtractEmailAddresses() throws Exception {
        String input = ResourceAsString(basePath + "emails1.txt");
        Set<String> actual = new HashSet<String>(TextExtraction.ExtractEmailAddresses(input));

        Set expected = Sets.newHashSet(
                "cliffwtteres@yaho.com",
                "lgprice1279@yaho.com",
                "iloveyou_as56@yaho.com",
                "muktamohon2@yaho.com",
                "fullard-pierce@yaho.com",
                "filixruiz51@yaho.com"
        );

        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void testExtractOutLinks1() throws Exception {
        String input = ResourceAsString(basePath + "links1.htm");
        Collection<String> actual = TextExtraction.ExtractLinksFromHtml(input, "https://en.wikipedia.org/wiki/Main_Page");
    }

    @Test
    public void testExtractOutLinks2() throws Exception {
        String input = ResourceAsString(basePath + "links2.htm");
        Collection<String> outLinks = TextExtraction.ExtractLinksFromHtml(input, "https://en.wikipedia.org/wiki/Main_Page");
    }

    @Test
    public void testExtractOutLinks3() throws Exception {
        String input = ResourceAsString(basePath + "links3.htm");
        Collection<String> outLinks = TextExtraction.ExtractLinksFromHtml(input, "https://en.wikipedia.org/wiki/Main_Page");
    }

    @Test
    public void testExtractOutLinks4() throws Exception {
        String input = ResourceAsString(basePath + "Hasmik Papian - Wikipedia, the free encyclopedia.html");
        Collection<String> actual = TextExtraction.ExtractLinksFromHtml(input, "https://en.wikipedia.org");
//        Collection<String> actual = TextExtraction.ExtractOutLinks(input, null);

        int i = 9;
    }

//    @Test
//    public void testDecodeUrl1() throws Exception {
//        URI uri = new URI("https://www.google.com/search?q=%E6%B3%B0%E5%9B%BD&oq=%E6%B3%B0%E5%9B%BD&aqs=chrome..69i57j0l5.4009j0j7&sourceid=chrome&es_sm=93&ie=UTF-8#SECTION");
//        String actual = TextExtraction.DecodeUrl(uri);
//        System.out.println(actual);
//    }
//
//    @Test
//    public void testDecodeUrl2() throws Exception {
//        URI uri = new URI("https://www.google.com/search.html");
//        String actual = TextExtraction.DecodeUrl(uri);
//        System.out.println(actual);
//    }
//
//    @Test
//    public void testDecodeUrl3() throws Exception {
//        URI uri = new URI("https://www.google.com");
//        String actual = TextExtraction.DecodeUrl(uri);
//        System.out.println(actual);
//    }
//
//    @Test
//    public void testDecodeUrl4() throws Exception {
//        URI uri = new URI("www.google.com");
//        String actual = TextExtraction.DecodeUrl(uri);
//        System.out.println(actual);
//    }
}
