package myfilesystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WordCounterTest {

    @Test
    public void givenTwoWordsTextWhenCountTextIsInvokedThenReturn2() {
        WordCounter wc = new WordCounter();
        String text = "test text";
        assertEquals(2, wc.countText(text));
    }

    @Test
    public void givenTwoWordsTextBeginingWithASpaceWhenCountTextIsInvokedThenReturn2() {
        WordCounter wc = new WordCounter();
        String text = " test text";
        assertEquals(2, wc.countText(text));
    }

    @Test
    public void givenOneWordBeginignWithASpaceWhenCountTextIsInvokedThenReturn1() {
        WordCounter wc = new WordCounter();
        String text = "test ";
        assertEquals(1, wc.countText(text));
    }

    @Test
    public void givenEmptyTextWhenCountTextIsInvokedThenReturn0() {
        WordCounter wc = new WordCounter();
        String text = "";
        assertEquals(0, wc.countText(text));
    }

    @Test
    public void givenTextWithJustSpcaesWhenCountTextIsInvokedThenReturn0() {
        WordCounter wc = new WordCounter();
        String text = "  ";
        assertEquals(0, wc.countText(text));
    }

    @Test
    public void givenTextWithPunctoationWhenCountTextIsInvokedThenReturnWordCount() {
        WordCounter wc = new WordCounter();
        String text = "Test, text! .";
        assertEquals(3, wc.countText(text));
    }

    @Test
    public void givenTextWithNewLineWhenCountTextIsInvokedThenReturnWordCount() {
        WordCounter wc = new WordCounter();
        String text = "Test \n text";
        assertEquals(2, wc.countText(text));
    }

    @Test
    public void givenOneLineFileWhenCountFileIsInvokedThenReturnNumberOfWordsInFile() {
        WordCounter wc = new WordCounter();
        MyFile test = new MyFile("test");
        test.addLine("test line");
        assertEquals(2, wc.countFile(test));
    }

    @Test
    public void givenFileWhenCountFileIsInvokedThenReturnNumberOfWordsInFile() {
        WordCounter wc = new WordCounter();
        MyFile test = new MyFile("test");
        test.addLine("test line");
        test.addLine("test line");
        assertEquals(4, wc.countFile(test));
    }

    @Test
    public void givenEmptyFileWhenCountFileIsInvokedThenReturn0() {
        WordCounter wc = new WordCounter();
        MyFile test = new MyFile("test");
        assertEquals(0, wc.countFile(test));
    }

    @Test
    public void givenFileWithEmptyLineWhenCountFileIsInvokedThenReturnNumberOfWordsInFile() {
        WordCounter wc = new WordCounter();
        MyFile test = new MyFile("test");
        test.addLine("test line");
        test.addLine("");
        test.addLine("");
        test.addLine("test line");
        assertEquals(4, wc.countFile(test));
    }

}
