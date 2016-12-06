package sel.common;


import com.google.common.collect.ImmutableMap;

import java.net.URI;
import java.util.Map;

public class URL {
    public static final Map<String, Integer> DEFAULT_PORT = ImmutableMap.of(
            "http", 80,
            "https", 443,
            "wss", 443,
            "ftp", 21,
            "telnet", 23);

    static {
    }

    public void URL(String orig, String base) {

    }

    public static String DecodeUrl(URI uri) {
        StringBuilder sb = new StringBuilder(uri.toString().length());
        String decodedPart;

        decodedPart = uri.getScheme();
        if (decodedPart != null) {
            sb.append(decodedPart);
            sb.append("://");
        }
        decodedPart = uri.getHost();
        if (decodedPart != null) {
            sb.append(decodedPart);
        }

        decodedPart = uri.getPath();
        if (decodedPart != null) {
            sb.append(decodedPart);
        }

        if(uri.getPort() != -1) {
            sb.append(String.valueOf(uri.getPort()));
        }

        decodedPart = uri.getQuery();
        if(decodedPart != null) {
            sb.append("?");
            sb.append(decodedPart);
        }

        decodedPart = uri.getFragment();
        if(decodedPart != null) {
            sb.append("#");
            sb.append(decodedPart);
        }

        return sb.toString();
    }
}
