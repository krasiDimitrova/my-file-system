package myfilesystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MyFolder {

    private Map<String, MyFile> files;
    private Map<String, MyFolder> folders;
    private String name;
    private MyFolder parent;

    public MyFolder(String name) {
        files = new HashMap<String, MyFile>();
        folders = new HashMap<String, MyFolder>();
        this.name = name;
        this.parent = null;
    }

    private String getName() {
        return name;
    }

    public MyFolder getParent() {
        return this.parent;
    }

    public void makeNewFolderInside(String name) throws InvalidArgumentException {
        if (folders.containsKey(name)) {
            throw new InvalidArgumentException("Folder with name \"" + name + "\" already Exists");
        } else {
            MyFolder newFolder = new MyFolder(name);
            newFolder.parent = this;
            folders.put(name, newFolder);
        }
    }

    public MyFolder getFolderByName(String name) throws InvalidArgumentException {
        if (folders.containsKey(name)) {
            return folders.get(name);
        } else {
            throw new InvalidArgumentException("Folder with name \"" + name + "\" dosen't exist");
        }
    }

    public void makeNewFileInside(String name) throws InvalidArgumentException {
        if (folders.containsKey(name)) {
            throw new InvalidArgumentException("File with name \"" + name + "\" already Exists");
        } else {
            MyFile newFile = new MyFile(name);
            files.put(name, newFile);
        }
    }

    public void writeInFile(String name, int line, String text, boolean overwrite)
            throws InvalidArgumentException {
        if (files.containsKey(name)) {
            MyFile cfile = files.get(name);
            cfile.setTextAtLine(line, text, overwrite);
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void printFileLineCount(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            files.get(name).printLineCount();
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void printFileWordsCount(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            files.get(name).printWordsCount();
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void displayFile(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            MyFile cfile = files.get(name);
            cfile.printContent();
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public String getFolderPath() {
        MyFolder currentF = this;
        StringBuilder path = new StringBuilder();
        if (currentF.name == "/") {
            path.insert(0, '/');
        } else {
            while (currentF.parent != null) {
                path.insert(0, '/' + currentF.name);
                currentF = currentF.parent;
            }
        }
        return path.toString();
    }

    public void listFiles() {
        if (files.size() > 0) {
            for (Map.Entry<String, MyFile> pair : files.entrySet()) {
                System.out.println(pair.getValue().getName());
            }
        }
    }

    public void listFolders() {
        if (folders.size() > 0) {
            for (Map.Entry<String, MyFolder> pair : folders.entrySet()) {
                System.out.println(pair.getValue().getName());
            }
        }
    }

    public void printSortedByNameAndSize() {
        Set<String> sortedFoldersNames = new TreeSet<String>(folders.keySet());
        Set<MyFile> sortedFilesBySize = new TreeSet<MyFile>(
                (MyFile file1, MyFile file2) -> file2.getSize() - file1.getSize());
        for (MyFile file : files.values()) {
            sortedFilesBySize.add(file);
        }
        System.out.println("Folders:");
        for (String name : sortedFoldersNames) {
            System.out.println(name);
        }
        System.out.println("Files:");
        for (MyFile file : sortedFilesBySize) {
            System.out.println(file.getName());
        }
    }

}
