package assembler;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) throw new Exception("Please specify an input file.");

        String fileName = args[0];
        File inputFile = new File(fileName);
        SymbolTable st = Parser.parse(inputFile);

        String[] fileParts = fileName.split(".");
        File outputFile = new File(fileParts[0] + ".hack");
        Coder.writeCode(outputFile, st);
    }
}