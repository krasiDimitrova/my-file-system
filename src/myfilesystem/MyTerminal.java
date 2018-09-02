package myfilesystem;

import java.util.Scanner;

public class MyTerminal {
    private MyFolder start;
    private MyFolder current;

    public MyTerminal() {
        start = new MyFolder("/");
        start.makeNewFolderInside("Home");
        current = start.getFolderByName("Home");
        printCurrentPath();
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
            current = current.getFolderByName(name);
            break;
        }
        }
        if (current == start) {
            System.out.print('/' + ">");
        } else {
            printCurrentPath();
        }
    }

    public void mkdir(String name) {
        current.makeNewFolderInside(name);
        printCurrentPath();
    }

    public void createFile(String name) {
        if (name.equals("")) {
            System.out.println("File nedds name!");
            return;
        }
        current.makeNewFileInside(name);
        printCurrentPath();
    }

    public void cat(String name) {
        MyFile openedF = current.getFileByName(name);
        openedF.printContent();
        printCurrentPath();
    }

    public void write(String name, int line, String text, boolean overwrite) {
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
        printCurrentPath();
    }

    public void printCommands() {
        System.out.println("Supported commands:");
        System.out.println("cd mkdir create_file cat write ls help");
        printCurrentPath();
    }

    public void ls() {
        System.out.println("Files:");
        current.listFiles();
        System.out.println("Folders:");
        current.listFolders();
    }

    public void lsSortedDes() {
        current.printSortedByNameAndSize();
        printCurrentPath();
    }

    public void readComands() {
        Scanner input = new Scanner(System.in);
        String command = input.next();
        String remaining = "";
        if ((remaining = input.nextLine()).equals("")) {
            if (command.equals("ls")) {
                try {
                    ls();
                } catch (Exception e) {
                    System.out.println("Invalid command");
                }
            }
            printCurrentPath();
            return;
        }
        Scanner remain = new Scanner(remaining);
        String name = remain.next();
        switch (command) {
        case "cd": {
            try {
                cd(name);
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
            break;
        }
        case "mkdir": {
            try {
                mkdir(name);
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
            break;
        }
        case "create_file": {
            try {
                createFile(name);
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
            break;
        }
        case "cat": {
            try {
                cat(name);
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
            break;
        }
        case "write": {
            String check = remain.next();
            if (check.equals("-overwrite")) {
                int line = remain.nextInt();
                remain.skip(" ");
                String text = remain.nextLine();
                try {
                    write(name, line, text, true);
                } catch (Exception e) {
                    System.out.println("Invalid command");
                }
            } else {
                int line = Integer.parseInt(check);
                remain.skip(" ");
                String text = remain.nextLine();
                try {
                    write(name, line, text, false);
                } catch (Exception e) {
                    System.out.println("Invalid command");
                }
            }
            break;
        }
        case "ls": {
            if (name.equals("--sorted")) {
                try {
                    lsSortedDes();
                } catch (Exception e) {
                    System.out.println("Invalid command");
                }
            } else if (name.equals("|")) {
                current.printAllFilesSizes();
            } else {
                System.out.println("Invalid command");
            }
            printCurrentPath();
            break;
        }
        case "help": {
            printCommands();
            break;
        }
        case "wc": {
            if (name.equals("-l")) {
                remain.skip(" ");
                name = remain.next();
                if (current.getFileByName(name) != null) {
                    System.out.println(current.getFileByName(name).getLineCount());
                } else {
                    StringBuilder text = new StringBuilder(name);
                    while (remain.hasNextLine()) {
                        text.append(remain.nextLine());
                    }
                    System.out.println(WordCounter.countText(text.toString()));
                }
            } else {
                if (current.getFileByName(name) != null) {
                    System.out.println(WordCounter.countFile(current.getFileByName(name)));
                } else {
                    StringBuilder text = new StringBuilder(name);
                    while (remain.hasNextLine()) {
                        text.append(remain.nextLine());
                    }
                    System.out.println(WordCounter.countText(text.toString()));
                }
            }
            printCurrentPath();
            break;
        }
        default: {
            printCommands();
            printCurrentPath();
            break;
        }
        }
    }
}
