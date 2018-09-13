package myfilesystem;

import java.util.StringTokenizer;

public class WordCounter {

    public static int countText(String text) {
        StringTokenizer st = new StringTokenizer(text);
        return st.countTokens();
    }

    public static int countLinesInText(String text) {
        StringTokenizer st = new StringTokenizer(text, "\r\n");
        return st.countTokens();
    }
}
