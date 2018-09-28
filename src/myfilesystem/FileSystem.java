package myfilesystem;

import java.io.IOException;

public interface FileSystem {

    String switchDirectory(String name, String currentPath) throws InvalidArgumentException;

    void mkdir(String name, String currentPath)
            throws InvalidArgumentException, NotEnoughSpaceException;

    void createFile(String name, String currentPath)
            throws InvalidArgumentException, NotEnoughSpaceException, IOException;

    void displayFileContent(String name, String currentPath, String encoding)
            throws InvalidArgumentException, IOException;

    void writeInFile(String name, int line, String text, boolean overwrite, String currentPath,
            String encoding) throws IOException, InvalidArgumentException, NotEnoughSpaceException;

    void printCommands();

    void ls(String currentPath) throws IOException;

    void lsSortedDes(String currentPath);

    void getWc(String name, boolean lineCount, String currentPath)
            throws InvalidArgumentException, IOException;

    void printWcforText(String text, boolean lineCount);

    void removeFile(String name, String currentPath) throws InvalidArgumentException, IOException;

    void removeLinesFromFile(String name, int start, int end, String currentPath)
            throws InvalidArgumentException, IOException;

}