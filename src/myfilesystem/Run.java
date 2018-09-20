package myfilesystem;

import java.io.IOException;
import java.util.Scanner;

public class Run {

    public static void main(String[] args) {
        System.out.println("Press r for working with real files and i for imaginary files: ");
        char c = ' ';
        try {
            do {
                c = (char) System.in.read();
            } while (c != 'r' && c != 'i');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        FileSystem fs = null;
        if (c == 'r') {
            fs = new MyRealFileSystem();
        } else if (c == 'i') {
            fs = new MyFileSystem();
        }
        MyTerminal test = new MyTerminal(fs);
        Scanner scan = new Scanner(System.in);
        while (test.getSatus()) {
            test.getCommands(scan);
        }
        scan.close();
    }
}
