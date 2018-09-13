package myfilesystem;

import java.util.Scanner;

public class Run {

    public static void main(String[] args) {
        MyFileSystem fs = new MyFileSystem();
        MyTerminal test = new MyTerminal(fs);
        Scanner scan = new Scanner(System.in);
        while (test.getSatus()) {
            test.getCommands(scan);
        }
        scan.close();
    }

}
