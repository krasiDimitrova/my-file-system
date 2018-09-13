package myfilesystem;

import java.util.Scanner;

public class MyTerminal {

    private boolean status;
    MyFileSystem fs;

    public MyTerminal(MyFileSystem fs) {
        status = true;
        this.fs = fs;
        printPath();
    }

    public boolean getSatus() {
        return status;
    }

    private void printPath() {
        System.out.println(fs.getCurrentPath());
    }

    private void cd(String name) {
        try {
            fs.switchDirectory(name);
        } catch (InvalidArgumentException e) {
            System.out.println("cd exception: " + e.getMessage());
        }
    }

    private void mkdir(String name) {
        try {
            fs.mkdir(name);
        } catch (InvalidArgumentException | NotEnoughSpaceException e) {
            System.out.println("mkdir exception: " + e.getMessage());
        }
    }

    private void createFile(String name) {
        try {
            fs.createFile(name);
        } catch (InvalidArgumentException | NotEnoughSpaceException e) {
            System.out.println("create_file exception: " + e.getMessage());
        }
    }

    private void cat(String name) {
        try {
            fs.displayFileContent(name);
        } catch (InvalidArgumentException e) {
            System.out.println("cat exception: " + e.getMessage());
        }
    }

    private void rm(String name) {
        try {
            fs.removeFile(name);
        } catch (InvalidArgumentException e) {
            System.out.println("rm exception: " + e.getMessage());
        }
    }

    private void remove(String name, String input) {
        Scanner in = new Scanner(input);
        int start = in.nextInt();
        int end = in.nextInt();
        try {
            fs.removeLinesFromFile(name, start, end);
        } catch (InvalidArgumentException e) {
            System.out.println("write exception: " + e.getMessage());
        } finally {
            in.close();
        }
    }

    private void getWrite(String input) {
        Scanner in = new Scanner(input);
        String name = in.next();
        try {
            if (name.equals("-overwrite")) {
                in.skip(" ");
                String n = in.next();
                int num = in.nextInt();
                in.skip(" ");
                String text = in.nextLine();
                fs.writeInFile(n, num, text, true);
            } else {
                int num = in.nextInt();
                in.skip(" ");
                String text = in.nextLine();
                fs.writeInFile(name, num, text, false);
            }
        } catch (InvalidArgumentException | NotEnoughSpaceException e) {
            System.out.println("write exception: " + e.getMessage());
        } finally {
            in.close();
        }
    }

    private void printCommands() {
        fs.printCommands();
    }

    private void ls(Boolean sorted) {
        if (sorted) {
            fs.lsSortedDes();
        } else {
            fs.ls();
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
                    fs.getWc(n, true);
                } else {
                    String text = n + in.nextLine() + "\n" + getMultipleLineInput(scanner);
                    fs.printWcforText(text, true);
                }
            } else {
                if (!in.hasNext()) {
                    fs.getWc(name, false);
                } else {
                    String text = name + in.nextLine() + "\n" + getMultipleLineInput(scanner);
                    fs.printWcforText(text, false);
                }
            }
        } catch (InvalidArgumentException e) {
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
