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
    private long size;

    public MyFolder(String name) {
        files = new HashMap<String, MyFile>();
        folders = new HashMap<String, MyFolder>();
        this.name = name;
        this.parent = null;
        setSize();
    }

    public void setSize() {
        size = folders.size();
        for (MyFile f : files.values()) {
            size += f.getSize();
        }
    }

    private String getName() {
        return name;
    }

    public String getParentName() {
        return parent.getName();
    }

    public long getSize() {
        return size;
    }

    public void makeNewFolderInside(String name) throws InvalidArgumentException {
        if (folders.containsKey(name)) {
            throw new InvalidArgumentException("Folder with name \"" + name + "\" already Exists");
        } else {
            MyFolder newFolder = new MyFolder(name);
            newFolder.parent = this;
            folders.put(name, newFolder);
            setSize();
        }
    }

    public boolean hasFolderWithName(String name) {
        return folders.containsKey(name);
    }

    public MyFolder getFolderByName(String name) throws InvalidArgumentException {
        if (folders.containsKey(name)) {
            return folders.get(name);
        } else {
            throw new InvalidArgumentException("Folder with name \"" + name + "\" dosen't exist");
        }
    }

    public void addOverwrittenFile(String name, MyFile toAdd) {
        toAdd.setNotDeleted();
        toAdd.deleteContent();
        files.put(name, toAdd);
        setSize();
    }

    public void makeNewFileInside(String name, long sizeLimit)
            throws InvalidArgumentException, NotEnoughSpaceException {
        MyFile newFile = new MyFile(name);
        if (newFile.getSize() < sizeLimit) {
            if (files.containsKey(name) && !files.get(name).isDeleted()) {
                throw new InvalidArgumentException(
                        "File with name \"" + name + "\" already Exists");
            } else {
                files.put(name, newFile);
                setSize();
            }
        } else {
            throw new NotEnoughSpaceException();
        }
    }

    public void writeInFile(String name, int line, String text, boolean overwrite, long sizeLimit)
            throws InvalidArgumentException, NotEnoughSpaceException {
        if (files.containsKey(name)) {
            MyFile cfile = files.get(name);
            if (!cfile.isDeleted()) {
                if (WordCounter.countChars(text) < sizeLimit) {
                    cfile.setTextAtLine(line, text, overwrite);
                    setSize();
                } else {
                    throw new NotEnoughSpaceException();
                }
            }
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void printFileLineCount(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            MyFile cfile = files.get(name);
            if (!cfile.isDeleted()) {
                cfile.printLineCount();
            }
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void printFileWordsCount(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            MyFile cfile = files.get(name);
            if (!cfile.isDeleted()) {
                cfile.printWordsCount();
            }
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void displayFile(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            MyFile cfile = files.get(name);
            if (!cfile.isDeleted()) {
                cfile.printContent();
            }
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void listFiles() {
        if (files.size() > 0) {
            for (Map.Entry<String, MyFile> pair : files.entrySet()) {
                if (!pair.getValue().isDeleted()) {
                    System.out.println(pair.getValue().getName());
                }
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
            if (!file.isDeleted()) {
                System.out.println(file.getName());
            }
        }
    }

    public void deleteFile(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            files.get(name).delete();
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public void deleteFileLines(String name, int start, int end) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            files.get(name).deleteLines(start, end);
            setSize();
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
    }

    public MyFile getDeletedFile(String name) throws InvalidArgumentException {
        if (files.containsKey(name)) {
            MyFile toDelete = files.get(name);
            if (toDelete.isDeleted()) {
                files.remove(name);
                setSize();
                return toDelete;
            }
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" doesn't exist");
        }
        return null;
    }

}
