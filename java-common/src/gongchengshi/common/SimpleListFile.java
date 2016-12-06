package sel.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleListFile {
    private static final Pattern _SectionPattern = Pattern.compile("^\\[(?<section>.*)\\]$", Pattern.MULTILINE);
    private final Map<String, Vector<String>> _lists = new HashMap<String, Vector<String>>();

    public SimpleListFile(InputStream is) throws ReadFailed {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            readFile(reader);
        } catch(IOException ex) {
            throw new ReadFailed(ex);
        }
    }

    public SimpleListFile(Path path) throws ReadFailed {
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            readFile(reader);
        } catch(IOException ex) {
            throw new ReadFailed(path.toString(), ex);
        }
    }

    private void readFile(BufferedReader reader) throws IOException {
        String line;
        Vector<String> currentList = new Vector<String>();
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("<>")) {
                continue;
            }

            Matcher regexMatcher = _SectionPattern.matcher(line);
            if (regexMatcher.find()) {
                currentList = new Vector<String>();
                _lists.put(regexMatcher.group("section"), currentList);
                continue;
            }

            currentList.add(line);
        }
    }

    public Vector<String> get(String sectionName) {
        Vector<String> list = _lists.get(sectionName);
        if(list == null) {
            return new Vector<String>(0);
        }

        return list;
    }

    public Map<String, Vector<String>> Lists() {
        return _lists;
    }

    public class ReadFailed extends Exception {
        public ReadFailed(String path, Exception inner) {
            super(path);
            super.initCause(inner);
        }

        public ReadFailed(Exception inner) {
            super.initCause(inner);
        }
    }
}
