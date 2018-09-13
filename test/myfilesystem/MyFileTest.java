package myfilesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MyFileTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void givenMyFileAfterAddLineWhenGetSizeIsIvoketThenReturnNumberOfCharsInTheLine() {
        MyFile test = new MyFile("test");
        try {
            test.setTextAtLine(1, "Test line", true);
        } catch (InvalidArgumentException e) {
            fail();
        }
        assertEquals(10, test.getSize());
    }

    @Test
    public void givenMyFileAfterMultipleAddLineWhenGetSizeIsIvoketThenReturnNumberOfCharsInTheLinePlusNumOfLines() {
        MyFile test = new MyFile("test");
        try {
            test.setTextAtLine(1, "Test line", true);
            test.setTextAtLine(2, "Test line 1", true);
        } catch (InvalidArgumentException e) {
            fail();
        }
        assertEquals(22, test.getSize());
    }

    @Test
    public void givenMyFileAfterSetTextAtLineWhenGetSizeIsIvoketThenReturnNumberOfCharsInTheLinePlusNumOfLines() {
        MyFile test = new MyFile("test");
        try {
            test.setTextAtLine(1, "Test line", true);
            test.setTextAtLine(2, "Test line 1", true);
            test.setTextAtLine(2, "Test line 1 after set", true);
        } catch (InvalidArgumentException e) {
            fail();
        }
        assertEquals(32, test.getSize());
    }

    @Test
    public void givenMyFileAfterSetTextAtLineWhenToStringIsInvokedThenReturnTheFileContent() {
        MyFile test = new MyFile("test");
        try {
            test.setTextAtLine(1, "Test line", true);
            test.setTextAtLine(2, "Test line 1", true);
        } catch (InvalidArgumentException e) {
            fail();
        }
        String content = "Test line Test line 1";
        assertEquals(content, test.toString());
    }

    @Test(expected = InvalidArgumentException.class)
    public void givenMyFileAndInvalidRowIndexWhenSetTextAtLineIsInvokedThenThrowAnException() {
        MyFile test = new MyFile("test");
        try {
            test.setTextAtLine(1, "Test line", true);
            test.setTextAtLine(0, "jdkd", false);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }

    @Test
    public void givenMyFileAndNegativeNumWhenSetTextAtLineIsInvokedThenThrowAnException() {
        exceptionRule.expect(InvalidArgumentException.class);
        exceptionRule.expectMessage("Invalid line index");
        MyFile test = new MyFile("test");
        try {
            test.setTextAtLine(0, "jdkd", false);
            test.setTextAtLine(1, "Test line", true);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }
}
