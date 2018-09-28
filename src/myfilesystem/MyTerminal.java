package myfilesystem;

import java.io.IOException;
import java.util.Scanner;

public class MyTerminal {

    private boolean status;
    private FileSystem fs;
    private StringBuilder currentPath;

    public MyTerminal(FileSystem fs) {
        status = true;
        this.fs = fs;
        currentPath = new StringBuilder("/ Home");
        printPath();
    }

    public boolean getSatus() {
        return status;
    }

    private void printPath() {
        String path = new String(currentPath).replace(" ", "/");
        System.out.println(path + ">");
    }

    private void addToPath(String name) {
        currentPath.append(" " + name);
    }

    private void removeFromPath() {
        int start = currentPath.lastIndexOf(" ");
        int end = currentPath.length();
        currentPath.delete(start, end);
    }

    private void cd(String name) {
        switch (name) {
            case ".": {
                break;
            }
            case "..": {
                if (currentPath.toString().equals("/")) {
                    System.out.println("Cannot move backwards");
                } else {
                    removeFromPath();
                }
                break;
            }
            default: {
                try {
                    String newDir = fs.switchDirectory(name, currentPath.toString());
                    addToPath(newDir);
                    break;
                } catch (InvalidArgumentException e) {
                    System.out.println("cd exception: " + e.getMessage());
                }
            }
        }
    }

    private void mkdir(String name) {
        try {
            fs.mkdir(name, currentPath.toString());
        } catch (InvalidArgumentException | NotEnoughSpaceException e) {
            System.out.println("mkdir exception: " + e.getMessage());
        }
    }

    private void createFile(String name) {
        try {
            fs.createFile(name, currentPath.toString());
        } catch (InvalidArgumentException | NotEnoughSpaceException | IOException e) {
            System.out.println("create_file exception: " + e.getMessage());
        }
    }

    private void cat(String name) {
        try {
            fs.displayFileContent(name, currentPath.toString());
        } catch (InvalidArgumentException | IOException e) {
            System.out.println("cat exception: " + e.getMessage());
        }
    }

    private void rm(String name) {
        try {
            fs.removeFile(name, currentPath.toString());
        } catch (InvalidArgumentException | IOException e) {
            System.out.println("rm exception: " + e.getMessage());
        }
    }

    private void remove(String input) {
        Scanner in = new Scanner(input);
        String name = in.next();
        int start = in.nextInt();
        int end = in.nextInt();
        try {
            fs.removeLinesFromFile(name, start, end, currentPath.toString());
        } catch (InvalidArgumentException | IOException e) {
            System.out.println("write exception: " + e.getMessage());
        } finally {
            in.close();
        }
    }

    private void getWrite(String input) {
        Scanner in = new Scanner(input);
        try {
            String name = in.next();
            if (name.equals("-overwrite")) {
                in.skip(" ");
                String n = in.next();
                int num = in.nextInt();
                in.skip(" ");
                String text = in.nextLine();
                fs.writeInFile(n, num, text, true, currentPath.toString());
            } else {
                int num = in.nextInt();
                in.skip(" ");
                String text = in.nextLine();
                fs.writeInFile(name, num, text, false, currentPath.toString());
            }
        } catch (InvalidArgumentException | NotEnoughSpaceException e) {
            System.out.println("write exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input");
        } finally {
            in.close();
        }
    }

    private void printCommands() {
        fs.printCommands();
    }

    private void ls(Boolean sorted) {
        if (sorted) {
            fs.lsSortedDes(currentPath.toString());
        } else {
            try {
                fs.ls(currentPath.toString());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getWc(String input, Scanner scanner) {
        Scanner in = new Scanner(input);
        String name = in.next();
        try {
            if (name.equals("-l")) {
                in.skip(" ");
                String n = in.next();
                if (!in.hasNext()) {
                    fs.getWc(n, true, currentPath.toString());
                } else {
                    String text = n + in.nextLine() + "\n" + getMultipleLineInput(scanner);
                    fs.printWcforText(text, true);
                }
            } else {
                if (!in.hasNext()) {
                    fs.getWc(name, false, currentPath.toString());
                } else {
                    String text = name + in.nextLine() + "\n" + getMultipleLineInput(scanner);
                    fs.printWcforText(text, false);
                }
            }
        } catch (InvalidArgumentException | IOException e) {
            System.out.println("wc exception:" + e.getMessage());
        } finally {
            in.close();
        }
    }

    private String getMultipleLineInput(Scanner scanner) {
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
            case "rm": {
                rm(input.substring(1));
                break;
            }
            case "remove": {
                remove(input.substring(1));
                break;
            }
            case "wc": {
                getWc(input.substring(1), scanner);
                break;
            }
            case "ls": {
                if (input.equals(" --sorted")) {
                    ls(true);
                } else {
                    ls(false);
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
        printPath();
    }
}
