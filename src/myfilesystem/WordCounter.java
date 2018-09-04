package myfilesystem;

import java.util.StringTokenizer;

public class WordCounter {

    public int countText(String text) {
        StringTokenizer st = new StringTokenizer(text);
        return st.countTokens();
    }

    public int countFile(MyFile test) {
        return countText(test.toString());
    }

    public int countLinesInText(String text) {
        StringTokenizer st = new StringTokenizer(text, "\r\n");
        return st.countTokens();
    }
}
