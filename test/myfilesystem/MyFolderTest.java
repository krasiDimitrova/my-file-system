package myfilesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class MyFolderTest {

    @Test
    public void givenFolderStructureWhenGetFolerdPathIsInvokedThenReturnThePathOfASubFolder() {
        MyFolder test = new MyFolder("test");
        try {
            test.makeNewFolderInside("SubFolder");
            String path = test.getFolderByName("SubFolder").getFolderPath();
            assertEquals("/test/SubFolder", path);
        } catch (InvalidArgumentException e) {
            fail();
        }
    }

}
