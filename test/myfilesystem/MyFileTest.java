package myfilesystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import myfilesystem.MyFile;

public class MyFileTest {

    @Test
    public void givenMyFileAfterAddLineWhenGetSizeIsIvoketThenReturnNumberOfCharsInTheLine() {
        MyFile test = new MyFile("test");
        test.addLine("Test line");
        assertEquals(10, test.getSize());
    }

    @Test
    public void givenMyFileAfterMultipleAddLineWhenGetSizeIsIvoketThenReturnNumberOfCharsInTheLinePlusNumOfLines() {
        MyFile test = new MyFile("test");
        test.addLine("Test line");
        test.addLine("Test line 1");
        assertEquals(22, test.getSize());
    }

    @Test
    public void givenMyFileAftersetTextAtLineWhenGetSizeIsIvoketThenReturnNumberOfCharsInTheLinePlusNumOfLines() {
        MyFile test = new MyFile("test");
        test.addLine("Test line");
        test.addLine("Test line 1");
        test.setTextAtLine(2, "Test line 1 after set", true);
        assertEquals(32, test.getSize());
    }

}
