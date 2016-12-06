package sel.common;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class SimHash {
    private static final int STEM_SIZE = 4;

    public static long ByWord(String input, String lang) {
        String[] tokens;
        lang = lang == null ? "en" : lang;
        lang = lang.substring(0,2);
        if(lang.equals("zh") || lang.equals("jp") || lang.equals("th")) { // Non-whitespace languages
            Vector<String> tempTokens = new Vector<String>(input.length());
            for(int i=0; i<input.length(); ++i) {
                char c = input.charAt(i);
                if(c > ' ') { // Remove whitespace
                    tempTokens.add(String.valueOf(c));
                }
            }
            tokens = new String[tempTokens.size()];
            tempTokens.toArray(tokens);
        } else {
            tokens = StringUtils.split(input, " \t\r\n");
            for(int i=0; i<tokens.length; ++i) {
                if(tokens[i].length() > STEM_SIZE) {
                    tokens[i] = tokens[i].substring(0, STEM_SIZE);
                }
            }
        }

        return Hash(tokens, false);
    }

    public static long BySentence(String input, String lang) {
        String pattern;
        lang = lang.substring(0,2);
        if(lang.equals("th")) {
            // Thai uses space instead of . between sentences and no space between words.
            pattern = "[ !?]+\\s*";
        } else if(lang.equals("zh") || lang.equals("jp")) {
            // Chinese and Japanese use normal, full, and half width characters with optional whitespace
            pattern = "[.!?\\u3002\\uFE12\\uFF61\\uFF01\\uFF1F]+\\s*"; //"[.!?。︒｡！？]\\s*";
        } else if(lang.equals("ar") || lang.equals("ur") || lang.equals("fa")) {
            // Arabic, Urdu, and Persian are RTL and uses a mirrored version of the question mark.
            pattern = "\\s+[.!?\\u061F]+"; //"\\s+[.!?؟]";
        } else if(lang.equals("he") || lang.equals("yi")) { // "yi" may also be "ji"?
            // Hebrew and Yiddish are RTL
            pattern = "\\s+[.!?]+";
        } else if(lang.equals("el")) {
            // Greek uses ; for questions
            pattern = "[?!.;]+\\s+";
        } else {
            pattern = "[?!.]+\\s+";
        }

        String[] tokens = input.split(pattern);
        return Hash(tokens, true);
    }

    private static HashFunction hashFunction = Hashing.murmur3_128();

    public static long Hash(String[] vector, boolean removeWhitespace) {
        int[] hashBits = new int[64];

        for(String feature : vector) {
            if(removeWhitespace) { // Don't include features that are all whitespace.
                feature = feature.trim();
                if (feature.isEmpty()) {
                    continue;
                }
            }

            long tempHash = hashFunction.hashString(feature, StandardCharsets.UTF_8).asLong();
            for(int i=0; i<64; ++i) {
                if(((tempHash >>> i) & 1L) == 1L) {
                    hashBits[i] += 1;
                } else {
                    hashBits[i] -= 1;
                }
            }
        }

        long hash = 0;
        for(long i=0; i<64; ++i) {
            if(hashBits[(int)i] > 0) {
                hash |= (1L << i);
            }
        }

        return hash;
    }

    public static long HammingDistance(long a, long b) {
        return Long.bitCount(a ^ b);
    }

    public static double PercentageDifference(long a, long b) {
        return HammingDistance(a, b) / 64.0;
    }
}
