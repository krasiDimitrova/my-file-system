package myfilesystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MyRealFileSystemTest {

    @Test
    public void givenStringREpresentingPathWhenMoveToCurrentIsInvokedThenReturnTheStringAsPath() {
        MyRealFileSystem fs = new MyRealFileSystem();
        String originalPath = "Home test";
        String expected = ".\\Home\\test";
        assertEquals(expected, fs.moveToCurrent(originalPath).toString());
    }

}
