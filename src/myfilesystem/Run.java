package myfilesystem;

import java.util.Scanner;

public class Run {

    public static void main(String[] args) {
        MyTerminal test = new MyTerminal();
        Scanner scan = new Scanner(System.in);
        while (test.getSatus()) {
            test.getCommands(scan);
        }
        scan.close();
    }

}
