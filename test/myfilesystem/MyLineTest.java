package myfilesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MyLineTest {

    @Test
    public void givenLineTextWhenGetTextIsInvokedThenReturnTheText() {
        MyLine test = new MyLine("Test");
        assertEquals("Test", test.getText());
    }

    @Test
    public void givenEmptyTextWhenGetTextIsInvokedThenReturnTheText() {
        MyLine test = new MyLine("");
        assertEquals("", test.getText());
    }

    @Test
    public void givenLineAfterSetTextWhenGetTextIsInvokedThenReturnTheText() {
        MyLine test = new MyLine();
        test.setText("Test");
        assertEquals("Test", test.getText());
    }

    @Test
    public void givenLineAfterSetTextWhenGetSymbolsCountIsInvokedThenReturnNumberOfCharsInText() {
        MyLine test = new MyLine();
        test.setText("Test");
        assertEquals(4, test.getSymbolsCount());
    }

    @Test
    public void givenTwoLinesWithSameContentWhenEqualsIsInvokedOnTheFirstLineThenReturnTrue() {
        MyLine test = new MyLine();
        test.setText("Test");
        MyLine test2 = new MyLine("Test");
        assertTrue(test.equals(test2));
    }

    @Test
    public void givenTwoLinesWithDifferentContentWhenEqualsIsInvokedOnTheFirstLineThenReturnFalse() {
        MyLine test = new MyLine();
        test.setText("Test");
        MyLine test2 = new MyLine("Test1");
        assertFalse(test.equals(test2));
    }

    @Test
    public void givenTwoLinesWithSameContentWhenHashCodeIsInvokedOnBothThenReturnSameCode() {
        MyLine test = new MyLine();
        test.setText("Test");
        MyLine test2 = new MyLine("Test");
        assertEquals(test.hashCode(), test2.hashCode());
    }

    @Test
    public void givenTwoLinesWithDifferentContentWhenHashCodeIsInvokedOnBothThenReturnDifferentCode() {
        MyLine test = new MyLine();
        test.setText("Test");
        MyLine test2 = new MyLine("Test2");
        assertNotEquals(test.hashCode(), test2.hashCode());
    }

}
