package myfilesystem;

import java.util.Stack;

public class MyFileSystem implements FileSystem {

    private MyFolder start;
    private long space;
    private long usedSpace;
    private Stack<String> deleted;

    public MyFileSystem() {
        start = new MyFolder("/");
        space = Long.MAX_VALUE;
        try {
            start.makeNewFolderInside("Home");
        } catch (InvalidArgumentException e) {
        } finally {
            setUsedSpace();
        }
        deleted = new Stack<String>();
    }

    private void setUsedSpace() {
        start.setSize();
        usedSpace = start.getSize();
    }

    public boolean isFull() {
        return space == usedSpace;
    }

    public boolean hasEnoughSpace(long size) {
        return (space - usedSpace > size);
    }

    public MyFolder moveToCurrent(String currentPath) {
        MyFolder current = start;
        String[] path = currentPath.split(" ");
        for (int i = 1; i < path.length; i++) {
            try {
                current = current.getFolderByName(path[i]);
            } catch (InvalidArgumentException e) {
                System.out.println("FatalError");
            }
        }
        return current;
    }

    @Override
    public String switchDirectory(String name, String currentPath) throws InvalidArgumentException {
        MyFolder current = moveToCurrent(currentPath);
        if (current.hasFolderWithName(name)) {
            return name;
        } else {
            throw new InvalidArgumentException("Folder with name \"" + name + "\" dosen't exist");
        }
    }

    @Override
    public void mkdir(String name, String currentPath)
            throws InvalidArgumentException, NotEnoughSpaceException {
        MyFolder current = moveToCurrent(currentPath);
        if (hasEnoughSpace(1)) {
            current.makeNewFolderInside(name);
        } else {
            throw new NotEnoughSpaceException();
        }
        setUsedSpace();
    }

    @Override
    public void createFile(String name, String currentPath)
            throws InvalidArgumentException, NotEnoughSpaceException {
        MyFolder current = moveToCurrent(currentPath);
        if (deleted.isEmpty()) {
            createFileFromEmptySpace(name, current);
        } else {
            StringBuilder path = new StringBuilder(deleted.pop());
            int start = path.lastIndexOf(" ");
            int end = path.length();
            String fileName = path.substring(start + 1, end);
            path.delete(start, end);
            MyFile overwrite = getFileToOverwrite(fileName, path.toString());
            createFileWithOverwrite(name, overwrite, current);
        }
        setUsedSpace();
    }

    private MyFile getFileToOverwrite(String name, String path) throws InvalidArgumentException {
        MyFolder current = moveToCurrent(path);
        MyFile toOverwrite = current.getDeletedFile(name);
        return toOverwrite;
    }

    private void createFileWithOverwrite(String name, MyFile toAdd, MyFolder current) {
        current.addOverwrittenFile(name, toAdd);
    }

    private void createFileFromEmptySpace(String name, MyFolder current)
            throws InvalidArgumentException, NotEnoughSpaceException {
        if (name.equals("")) {
            throw new InvalidArgumentException("File with empty names not allowed!");
        } else {
            current.makeNewFileInside(name, (space - usedSpace));
        }
    }

    @Override
    public void displayFileContent(String name, String currentPath, String encoding)
            throws InvalidArgumentException {
        MyFolder current = moveToCurrent(currentPath);
        current.displayFile(name);
    }

    @Override
    public void writeInFile(String name, int line, String text, boolean overwrite,
            String currentPath, String encoding)
            throws InvalidArgumentException, NotEnoughSpaceException {
        MyFolder current = moveToCurrent(currentPath);
        current.writeInFile(name, line, text, overwrite, (space - usedSpace));
        setUsedSpace();
    }

    @Override
    public void printCommands() {
        System.out.println("Supported commands:");
        System.out.println("cd <name>; mkdir <name>; create_file <name>; cat <name>; "
                + "write (<-overwrite>) <name> <line num> <text>; ls (<--sorted>); " + "rm <name>"
                + "remove < file_name > < line_number1 >-< line_number2 >"
                + "wc <(-l)> <name/text>; help; q");
    }

    @Override
    public void ls(String currentPath) {
        MyFolder current = moveToCurrent(currentPath);
        System.out.println("Files:");
        current.listFiles();
        System.out.println("Folders:");
        current.listFolders();
    }

    @Override
    public void lsSortedDes(String currentPath) {
        MyFolder current = moveToCurrent(currentPath);
        current.printSortedByNameAndSize();
    }

    @Override
    public void getWc(String name, boolean lineCount, String currentPath)
            throws InvalidArgumentException {
        MyFolder current = moveToCurrent(currentPath);
        if (lineCount) {
            current.printFileLineCount(name);
        } else {
            current.printFileWordsCount(name);
        }
    }

    @Override
    public void printWcforText(String text, boolean lineCount) {
        if (lineCount) {
            System.out.println(WordCounter.countLinesInText(text) + " lines");
        } else {
            System.out.println(WordCounter.countText(text) + " words");
        }
    }

    @Override
    public void removeFile(String name, String currentPath) throws InvalidArgumentException {
        MyFolder current = moveToCurrent(currentPath);
        current.deleteFile(name);
        deleted.push(currentPath + " " + name);
        setUsedSpace();
    }

    @Override
    public void removeLinesFromFile(String name, int start, int end, String currentPath)
            throws InvalidArgumentException {
        MyFolder current = moveToCurrent(currentPath);
        current.deleteFileLines(name, start, end);
        setUsedSpace();
    }
}
