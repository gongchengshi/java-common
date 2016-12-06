package sel.common;

import junit.framework.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SimHashTest {
    private String ResourceAsString(String name) throws IOException {
        InputStream inputStream = SimHashTest.class.getResourceAsStream(name);

        java.util.Scanner s = new java.util.Scanner(inputStream, StandardCharsets.UTF_8.toString()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : null;
    }

    private void PrintBitStrings(String name, long first, long second) {
        System.out.println(name);
        PrintBitStrings(first, second);
    }

    private void PrintBitStrings(long first, long second) {
        System.out.println(Long.toBinaryString(first));
        System.out.println(Long.toBinaryString(second));
    }

    private void CompareByWord(long file1Hash, String file2Path, String lang, int expectedDist) throws IOException {
        long file2Hash = SimHash.ByWord(ResourceAsString(file2Path), lang);
        PrintBitStrings(file2Path, file1Hash, file2Hash);
        long dist = SimHash.HammingDistance(file1Hash, file2Hash);
        Assert.assertEquals(expectedDist, dist);
    }

    private void CompareBySentence(long file1Hash, String file2Path, String lang, int expectedDist) throws IOException {
        long file2Hash = SimHash.BySentence(ResourceAsString(file2Path), lang);
        PrintBitStrings(file2Path, file1Hash, file2Hash);
        long dist = SimHash.HammingDistance(file1Hash, file2Hash);
        Assert.assertEquals(expectedDist, dist);
    }

    @Test
    public void testByWordZh() throws Exception {
        String basePath = "SimHashTest/zh/";
        long hash = SimHash.ByWord(ResourceAsString(basePath+"short.txt"), "zh");
        CompareByWord(hash, basePath + "small_diff_short.txt", "zh", 0);
        CompareByWord(hash, basePath + "small_diff_short2.txt", "zh", 1);
        CompareByWord(hash, basePath + "large_diff_short.txt", "zh", 20);

        hash = SimHash.ByWord(ResourceAsString(basePath+"long.txt"), "zh");
        CompareByWord(hash, basePath + "small_diff_long.txt", "zh", 0);
        CompareByWord(hash, basePath + "large_diff_long.txt", "zh", 15);
    }

    @Test
    public void testBySentenceZh() throws Exception {
        String basePath = "SimHashTest/zh/";
        long hash = SimHash.BySentence(ResourceAsString(basePath+"short.txt"), "zh");
        CompareBySentence(hash, basePath + "small_diff_short.txt", "zh", 16);
        CompareBySentence(hash, basePath + "small_diff_short2.txt", "zh", 11);
        CompareBySentence(hash, basePath + "large_diff_short.txt", "zh", 32);

        hash = SimHash.BySentence(ResourceAsString(basePath+"long.txt"), "zh");
        CompareBySentence(hash, basePath + "small_diff_long.txt", "zh", 1);
        CompareBySentence(hash, basePath + "large_diff_long.txt", "zh", 24);
    }

    @Test
    public void testByWordEn() throws Exception {
        String basePath = "SimHashTest/en/";
        long hash;
        hash = SimHash.ByWord(ResourceAsString(basePath+"short.txt"), "en");
        CompareByWord(hash, basePath + "small_diff_short.txt", "en", 2);
        CompareByWord(hash, basePath + "large_diff_short.txt", "en", 31);

        hash = SimHash.ByWord(ResourceAsString(basePath+"long.txt"), "en");
        CompareByWord(hash, basePath + "small_diff_long.txt", "en", 0);
        CompareByWord(hash, basePath + "small_diff_long2.txt", "en", 1);
        CompareByWord(hash, basePath + "large_diff_long.txt", "en", 17);

        hash = SimHash.ByWord(ResourceAsString(basePath+"whitespace.txt"), "en");
        CompareByWord(hash, basePath + "diff_whitespace.txt", "en", 0);
    }

    @Test
    public void testBySentenceEn() throws Exception {
        String basePath = "SimHashTest/en/";
        long hash;
        hash = SimHash.BySentence(ResourceAsString(basePath+"short.txt"), "en");
        CompareBySentence(hash, basePath + "small_diff_short.txt", "en", 11);
        CompareBySentence(hash, basePath + "large_diff_short.txt", "en", 28);

        hash = SimHash.BySentence(ResourceAsString(basePath+"long.txt"), "en");
        CompareBySentence(hash, basePath + "small_diff_long.txt", "en", 5);
        CompareBySentence(hash, basePath + "large_diff_long.txt", "en", 33);

        hash = SimHash.BySentence(ResourceAsString(basePath+"whitespace.txt"), "en");
        CompareBySentence(hash, basePath + "diff_whitespace.txt", "en", 0);
    }

    @Test
    public void testHash() throws Exception {

    }

    @Test
    public void testHammingDistance() throws Exception {
        Assert.assertEquals(0, SimHash.HammingDistance(0, 0));
        Assert.assertEquals(0, SimHash.HammingDistance(1, 1));
        Assert.assertEquals(0, SimHash.HammingDistance(Long.MAX_VALUE, Long.MAX_VALUE));
        Assert.assertEquals(0, SimHash.HammingDistance(Long.MIN_VALUE, Long.MIN_VALUE));
        Assert.assertEquals(1, SimHash.HammingDistance(0, 1));
        Assert.assertEquals(64, SimHash.HammingDistance(0, -1));
        Assert.assertEquals(63, SimHash.HammingDistance(0, -2));

        Assert.assertEquals(43, SimHash.HammingDistance(0, -123456789101112L));
        Assert.assertEquals(19, SimHash.HammingDistance(0, 123456789101112L));
    }

    @Test
    public void testPercentageDifference() throws Exception {
        Assert.assertEquals(1.0/64.0, SimHash.PercentageDifference(0,1));
        Assert.assertEquals(2.0/64.0, SimHash.PercentageDifference(0,3));
        Assert.assertEquals(1.0, SimHash.PercentageDifference(0,-1));
    }
}
