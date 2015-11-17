package assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

final class Parser {
    public static SymbolTable parse(File file) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            return Parser.parse(sc);
        } catch (Exception e) {
            System.out.println("Could not find the specified file.");
            return null;
        }
    }

    private static SymbolTable parse(Scanner sc) {
        return null;
    }
}
