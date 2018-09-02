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

    public String getName() {
        return name;
    }

    public void makeNewFolderInside(String name) {
        MyFolder newFolder = new MyFolder(name);
        newFolder.parent = this;
        folders.put(name, newFolder);
    }

    public MyFolder getFolderByName(String name) {
        return folders.get(name);
    }

    public void makeNewFileInside(String name) {
        MyFile newFile = new MyFile(name);
        files.put(name, newFile);
    }

    public MyFile getFileByName(String name) {
        return files.get(name);
    }

    public void printAllFilesSizes() {
        for (MyFile f : files.values()) {
            System.out.println(f.getName() + " " + f.getLineCount() + " lines");
        }
    }

    public String getFolderPath() {
        MyFolder currentF = this;
        StringBuilder path = new StringBuilder();
        while (currentF.parent != null) {
            path.insert(0, '/' + currentF.getName());
            currentF = currentF.parent;
        }
        if (currentF.getName() != "/") {
            path.insert(0, '/' + currentF.getName());
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

    public MyFolder getParent() {
        return this.parent;
    }

    public void printSortedByNameAndSize() {
        Set<String> sortedFoldersNames = new TreeSet<String>(folders.keySet());
        Set<MyFile> sortedFilesNames = new TreeSet<MyFile>(
                (MyFile file1, MyFile file2) -> file2.getSize() - file1.getSize());
        for (MyFile file : files.values()) {
            sortedFilesNames.add(file);
        }
        System.out.println("Folders:");
        for (String name : sortedFoldersNames) {
            System.out.println(name);
        }
        System.out.println("Files:");
        for (MyFile file : sortedFilesNames) {
            System.out.println(file.getName());
        }
    }

}
