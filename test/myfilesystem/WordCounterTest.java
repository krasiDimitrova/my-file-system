package myfilesystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WordCounterTest {

    @Test
    public void givenTwoWordsTextWhenCountTextIsInvokedThenReturn2() {
        String text = "test text";
        assertEquals(2, WordCounter.countText(text));
    }

    @Test
    public void givenTwoWordsTextBeginingWithASpaceWhenCountTextIsInvokedThenReturn2() {
        String text = " test text";
        assertEquals(2, WordCounter.countText(text));
    }

    @Test
    public void givenOneWordBeginignWithASpaceWhenCountTextIsInvokedThenReturn1() {
        String text = "test ";
        assertEquals(1, WordCounter.countText(text));
    }

    @Test
    public void givenEmptyTextWhenCountTextIsInvokedThenReturn0() {
        String text = "";
        assertEquals(0, WordCounter.countText(text));
    }

    @Test
    public void givenTextWithJustSpcaesWhenCountTextIsInvokedThenReturn0() {
        String text = "  ";
        assertEquals(0, WordCounter.countText(text));
    }

    @Test
    public void givenTextWithPunctoationWhenCountTextIsInvokedThenReturnWordCount() {
        String text = "Test, text! .";
        assertEquals(3, WordCounter.countText(text));
    }

    @Test
    public void givenTextWithNewLineWhenCountTextIsInvokedThenReturnWordCount() {
        String text = "Test \n text";
        assertEquals(2, WordCounter.countText(text));
    }

    @Test
    public void givenOneLineFileWhenCountFileIsInvokedThenReturnNumberOfWordsInFile() {
        MyFile test = new MyFile("test");
        test.addLine("test line");
        assertEquals(2, WordCounter.countFile(test));
    }

    @Test
    public void givenFileWhenCountFileIsInvokedThenReturnNumberOfWordsInFile() {
        MyFile test = new MyFile("test");
        test.addLine("test line");
        test.addLine("test line");
        assertEquals(4, WordCounter.countFile(test));
    }

    @Test
    public void givenEmptyFileWhenCountFileIsInvokedThenReturn0() {
        MyFile test = new MyFile("test");
        assertEquals(0, WordCounter.countFile(test));
    }

    @Test
    public void givenFileWithEmptyLineWhenCountFileIsInvokedThenReturnNumberOfWordsInFile() {
        MyFile test = new MyFile("test");
        test.addLine("test line");
        test.addLine("");
        test.addLine("");
        test.addLine("test line");
        assertEquals(4, WordCounter.countFile(test));
    }

}
