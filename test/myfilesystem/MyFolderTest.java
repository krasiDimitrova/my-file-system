package myfilesystem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import myfilesystem.MyFolder;

public class MyFolderTest {

    @Test
    public void givenFolderStructureWhenGetFolerdPathIsInvokedThenReturnThePathOfASubFolder() {
        MyFolder test = new MyFolder("test");
        test.makeNewFolderInside("SubFolder");
        String path = test.getFolderByName("SubFolder").getFolderPath();
        assertEquals("/test/SubFolder", path);
    }

}
