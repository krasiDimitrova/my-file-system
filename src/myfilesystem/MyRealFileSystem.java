package myfilesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyRealFileSystem implements FileSystem {

    private File startDir;

    public MyRealFileSystem() {
        startDir = new File(".\\Home");
        startDir.mkdir();
    }

    public Path moveToCurrent(String currentPath) {
        String[] pathArray = currentPath.substring(2).split(" ");
        Path cPath = Paths.get(".", pathArray);
        return cPath;
    }

    @Override
    public String switchDirectory(String name, String currentPath) throws InvalidArgumentException {
        if (Files.exists(moveToCurrent(currentPath))) {
            return name;
        } else {
            throw new InvalidArgumentException("Folder with name \"" + name + "\" dosen't exist");
        }
    }

    @Override
    public void mkdir(String name, String currentPath) throws InvalidArgumentException {
        String nDPath = moveToCurrent(currentPath + " " + name).toString();
        File nDir = new File(nDPath);
        if (!nDir.exists()) {
            nDir.mkdir();
        } else {
            throw new InvalidArgumentException("Folder with name \"" + name + "\" already Exists");
        }
    }

    @Override
    public void createFile(String name, String currentPath)
            throws InvalidArgumentException, IOException {
        Path nDPath = moveToCurrent(currentPath + " " + name);
        if (!Files.exists(nDPath)) {
            File nFile = new File(nDPath.toString());
            nFile.createNewFile();
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" already Exists");
        }
    }

    @Override
    public void displayFileContent(String name, String currentPath)
            throws InvalidArgumentException, IOException {
        Path nDPath = moveToCurrent(currentPath + " " + name);
        try (BufferedReader in = new BufferedReader(new FileReader(nDPath.toString()))) {
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            throw new InvalidArgumentException("File with name \"" + name + "\" dosen't exist");
        }
    }

    @Override
    public void writeInFile(String name, int line, String text, boolean overwrite,
            String currentPath) throws IOException {
        Path nDPath = moveToCurrent(currentPath + " " + name);
        BufferedWriter out = new BufferedWriter(new FileWriter(nDPath.toString(), true));
        out.write(text + System.getProperty("line.separator"));
        out.close();
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
    public void ls(String currentPath) throws IOException {
        Path dir = moveToCurrent(currentPath);
        File currentDir = new File(dir.toString());
        List<String> listFile = Arrays.asList(currentDir.list());
        for (String s : listFile) {
            System.out.println(s);
        }
    }

    @Override
    public void lsSortedDes(String currentPath) {
        Path dir = moveToCurrent(currentPath);
        File currentDir = new File(dir.toString());
        List<String> listFile = Arrays.asList(currentDir.list());
        Collections.sort(listFile, Collections.reverseOrder());
        for (String s : listFile) {
            System.out.println(s);
        }

    }

    @Override
    public void getWc(String name, boolean lineCount, String currentPath)
            throws InvalidArgumentException, IOException {
        Path nDPath = moveToCurrent(currentPath + " " + name);
        if (lineCount) {
            long lineC = 0;
            try (BufferedReader in = new BufferedReader(new FileReader(nDPath.toString()))) {
                String line = null;
                while ((line = in.readLine()) != null) {
                    lineC++;
                }
            } catch (FileNotFoundException e) {
                throw new InvalidArgumentException("File with name \"" + name + "\" dosen't exist");
            }
            System.out.println(lineC + " lines");
        } else {
            long wordC = 0;
            try (BufferedReader in = new BufferedReader(new FileReader(nDPath.toString()))) {
                String line = null;
                while ((line = in.readLine()) != null) {
                    wordC += WordCounter.countText(line);
                }
            } catch (FileNotFoundException e) {
                throw new InvalidArgumentException("File with name \"" + name + "\" dosen't exist");
            }
            System.out.println(wordC + " words");
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
    public void removeFile(String name, String currentPath)
            throws InvalidArgumentException, IOException {
        Path nDPath = moveToCurrent(currentPath + " " + name);
        if (Files.exists(nDPath)) {
            Files.delete(nDPath);
        } else {
            throw new InvalidArgumentException("File with name \"" + name + "\" dosen't Exists");
        }
    }

    @Override
    public void removeLinesFromFile(String name, int start, int end, String currentPath)
            throws InvalidArgumentException, IOException {
        File readFile = new File(moveToCurrent(currentPath + " " + name).toString());
        File tempFile = new File(moveToCurrent(currentPath + " temp.txt").toString());
        try (BufferedReader in = new BufferedReader(new FileReader(readFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            int lineCount = 1;
            while ((currentLine = in.readLine()) != null) {
                if (lineCount < start || lineCount > end) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            throw new InvalidArgumentException("File with name \"" + name + "\" dosen't Exists");
        }
        readFile.delete();
        tempFile.renameTo(readFile);
    }
}
