package assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

final class Coder {
    public static void writeCode(File outputFile, SymbolTable st) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter pw = new PrintWriter(outputFile, "UTF-8");

    }
}
