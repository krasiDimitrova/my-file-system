package myfilesystem;

public class MyFileSystem {

    private MyFolder start;
    private MyFolder current;

    public MyFileSystem() {
        start = new MyFolder("/");
        try {
            start.makeNewFolderInside("Home");
            current = start.getFolderByName("Home");
        } catch (InvalidArgumentException e) {
        }
    }

    public String getCurrentPath() {
        return current.getFolderPath() + ">";
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

    public void mkdir(String name) throws InvalidArgumentException {
        current.makeNewFolderInside(name);
    }

    public void createFile(String name) throws InvalidArgumentException {
        if (name.equals("")) {
            throw new InvalidArgumentException("File with empty names not allowed!");
        } else {
            current.makeNewFileInside(name);
        }
    }

    public void displayFileContent(String name) throws InvalidArgumentException {
        current.displayFile(name);
    }

    public void writeInFile(String name, int line, String text, boolean overwrite)
            throws InvalidArgumentException {
        current.writeInFile(name, line, text, overwrite);
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
