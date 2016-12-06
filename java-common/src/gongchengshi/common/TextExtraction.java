package sel.common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextExtraction {
    private static Pattern _EmailPattern =
            Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}(\\.[A-Z]{2})?\\b", Pattern.CASE_INSENSITIVE);

    public static Set<String> ExtractEmailAddresses(String text) {
        Set<String> emailAddresses = new HashSet<String>();
        Matcher regexMatcher = _EmailPattern.matcher(text);
        while (regexMatcher.find()) {
            emailAddresses.add(regexMatcher.group().toLowerCase());
        }

        return emailAddresses;
    }

    public static Set<String> ExtractLinksFromHtml(String text, String baseUrl) {
        Document doc = baseUrl == null ? Jsoup.parse(text) : Jsoup.parse(text, baseUrl);

        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        Elements links = doc.select("a[href]");

        Set<String> outLinks = new HashSet<String>();

        for (Element src : media) {
            outLinks.add(src.attr("abs:src"));
        }

        for (Element link : imports) {
            outLinks.add(link.attr("abs:href"));
        }

        for (Element link : links) {
            outLinks.add(link.attr("abs:href"));
        }

        return outLinks;
    }

    private static String _DomainPattern =
            "(((([a-z-]{3,11}://)|(www\\.))[a-z0-9\\-]{1,63}(\\.[a-z0-9\\-]{1,63})*)|([a-z0-9\\-]{1,63}(\\.[a-z0-9\\-]{1,63})*\\.(com|net|org|gov|edu|mil)))";
    private static Pattern _UrlPattern = Pattern.compile(
            _DomainPattern + "(((\\/[\\w\\-.+~%:_]+)+)?\\/?(\\?[\\w\\-.+;%@_]+=[\\w\\-.+;%@_]+(&[\\w\\-.+;%@_]+=[\\w\\-.+;%@_]+)*)?)?",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    public static Set<String> ExtractLinksFromText(String text) {
        Set<String> outLinks = new HashSet<String>();

        if(text == null) {
            return outLinks;
        }

        Matcher m = _UrlPattern.matcher(text);
        while (m.find()) {
            outLinks.add(m.group(1));
        }

        return outLinks;
    }
}
