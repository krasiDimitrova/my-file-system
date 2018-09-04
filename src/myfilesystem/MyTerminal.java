package myfilesystem;

import java.util.Scanner;

public class MyTerminal extends WordCounter {
    private MyFolder start;
    private MyFolder current;
    private boolean status;

    public MyTerminal() {
        status = true;
        start = new MyFolder("/");
        start.makeNewFolderInside("Home");
        current = start.getFolderByName("Home");
        printCurrentPath();
    }

    public boolean getSatus() {
        return status;
    }

    public void printCurrentPath() {
        System.out.print(current.getFolderPath() + ">");
    }

    public void cd(String name) {
        switch (name) {
        case ".": {
            break;
        }
        case "..": {
            current = current.getParent();
            break;
        }
        default: {
            if (current.hasFolder(name)) {
                current = current.getFolderByName(name);
            } else {
                System.out.println("No such folder");
            }
            break;
        }
        }
    }

    public void mkdir(String name) {
        current.makeNewFolderInside(name);
    }

    public void createFile(String name) {
        if (name.equals("")) {
            System.out.println("File nedds name!");
            return;
        }
        current.makeNewFileInside(name);
    }

    public void cat(String name) {
        if (current.hasFile(name)) {
            MyFile openedF = current.getFileByName(name);
            openedF.printContent();
        } else {
            System.out.println("No such folder");
        }
    }

    public void getWrite(String input) {
        Scanner in = new Scanner(input);
        String name = in.next();
        if (name.equals("-overwrite")) {
            in.skip(" ");
            String n = in.next();
            int num = in.nextInt();
            in.skip(" ");
            String text = in.nextLine();
            write(n, num, text, true);
        } else {
            int num = in.nextInt();
            in.skip(" ");
            String text = in.nextLine();
            write(name, num, text, false);
        }
        in.close();
    }

    public void write(String name, int line, String text, boolean overwrite) {
        if (current.hasFile(name)) {
            MyFile cFile = current.getFileByName(name);
            if (!cFile.isLine(line)) {
                for (int i = cFile.getLineCount(); i < line - 1; i++) {
                    cFile.addLine("");
                }
                cFile.addLine(text);
            } else {
                if (overwrite) {
                    cFile.setTextAtLine(line, text, true);
                } else {
                    cFile.setTextAtLine(line, text, false);
                }
            }
        } else {
            System.out.println("No such file");
        }
    }

    public void printCommands() {
        System.out.println("Supported commands:");
        System.out.println(
                "cd <name>; mkdir<name>; create_file <name>; cat <name>; write (<-overwrite>) <name> <line num> <text>; ls (<--sorted>); wc <(-l)> <name/text>; help; q");
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

    public void getWc(String input, Scanner scanner) {
        Scanner in = new Scanner(input);
        String name = in.next();
        if (name.equals("-l")) {
            in.skip(" ");
            String n = in.next();
            if (current.hasFile(n)) {
                System.out.println(n + " - " + wcl(n) + " lines");
            } else {
                String text = n + in.nextLine() + "\n" + getMultipleLineInput(scanner);
                System.out.println(countLinesInText(text) + " lines");
            }
        } else {
            if (current.hasFile(name)) {
                System.out.println(name + " - " + countFile(current.getFileByName(name)) + " words");
            } else {
                String text = name + in.nextLine() + "\n" + getMultipleLineInput(scanner);
                System.out.println(countText(text) + " words");
            }
        }
        in.close();
    }

    public int wcl(String name) {
        return current.getFileByName(name).getLineCount();
    }

    public String getMultipleLineInput(Scanner scanner) {
        StringBuilder text = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            text.append("\n" + line);
        }
        return text.toString();
    }

    public void getCommands(Scanner scanner) {
        String command = scanner.next();
        String input = scanner.nextLine();
        switch (command) {
        case "cd": {
            cd(input.substring(1));
            break;
        }
        case "mkdir": {
            mkdir(input.substring(1));
            break;
        }
        case "create_file": {
            createFile(input.substring(1));
            break;
        }
        case "cat": {
            cat(input.substring(1));
            break;
        }
        case "write": {
            getWrite(input.substring(1));
            break;
        }
        case "wc": {
            getWc(input.substring(1), scanner);
            break;
        }
        case "ls": {
            if (input.equals(" --sorted")) {
                lsSortedDes();

            } else if (input.equals(" |")) {
                current.printAllFilesSizes();
            } else {
                ls();
            }
            break;
        }
        case "q": {
            status = false;
            System.out.println("Goodbye");
            return;
        }
        case "help": {
            printCommands();
            break;
        }
        default: {
            System.out.println("Invalid command");
        }
        }
        printCurrentPath();
    }
}
