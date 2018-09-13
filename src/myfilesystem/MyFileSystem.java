package myfilesystem;

public class MyFileSystem {

    private MyFolder start;
    private MyFolder current;
    private long space;
    private long usedSpace;

    public MyFileSystem() {
        start = new MyFolder("/");
        space = Long.MAX_VALUE;
        try {
            start.makeNewFolderInside("Home");
            current = start.getFolderByName("Home");
        } catch (InvalidArgumentException e) {
        } finally {
            setUsedSpace();
        }
    }

    private void setUsedSpace() {
        usedSpace = start.getSize();
    }

    public String getCurrentPath() {
        return current.getFolderPath() + ">";
    }

    public boolean isFull() {
        return space == usedSpace;
    }

    public boolean hasEnoughSpace(long size) {
        return (space - usedSpace > size);
    }

    public void switchDirectory(String name) throws InvalidArgumentException {
        switch (name) {
            case ".": {
                break;
            }
            case "..": {
                current = current.getParent();
                break;
            }
            default: {
                current = current.getFolderByName(name);
            }
        }
    }

    public void mkdir(String name) throws InvalidArgumentException, NotEnoughSpaceException {
        if (hasEnoughSpace(1)) {
            current.makeNewFolderInside(name);
        } else {
            throw new NotEnoughSpaceException();
        }
    }

    public void createFile(String name) throws InvalidArgumentException, NotEnoughSpaceException {
        if (name.equals("")) {
            throw new InvalidArgumentException("File with empty names not allowed!");
        } else {
            current.makeNewFileInside(name, (space - usedSpace));
        }
    }

    public void displayFileContent(String name) throws InvalidArgumentException {
        current.displayFile(name);
    }

    public void writeInFile(String name, int line, String text, boolean overwrite)
            throws InvalidArgumentException, NotEnoughSpaceException {
        current.writeInFile(name, line, text, overwrite, (space - usedSpace));
    }

    public void printCommands() {
        System.out.println("Supported commands:");
        System.out.println("cd <name>; mkdir<name>; create_file <name>; cat <name>; "
                + "write (<-overwrite>) <name> <line num> <text>; ls (<--sorted>); "
                + "wc <(-l)> <name/text>; help; q");
    }

    public void ls() {
        System.out.println("Files:");
        current.listFiles();
        System.out.println("Folders:");
        current.listFolders();
    }

    public void lsSortedDes() {
        current.printSortedByNameAndSize();
    }

    public void getWc(String name, boolean lineCount) throws InvalidArgumentException {
        if (lineCount) {
            current.printFileLineCount(name);
        } else {
            current.printFileWordsCount(name);
        }
    }

    public void printWcforText(String text, boolean lineCount) {
        if (lineCount) {
            System.out.println(WordCounter.countLinesInText(text) + " lines");
        } else {
            System.out.println(WordCounter.countText(text) + " words");
        }
    }
}
