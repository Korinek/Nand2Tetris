package assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) throw new Exception("Please specify an input file.");

        String fileName = args[0];
        File inputFile = new File(fileName);

        SymbolTable st = buildFirstPassSymbolTable(createNewParser(inputFile));
        performSecondPassWrite(createNewParser(inputFile), st);
    }

    private static SymbolTable buildFirstPassSymbolTable(Parser parser) throws Exception {
        SymbolTable st = new SymbolTable();
        int nextInstructionLine = 0;
        while(parser.hasMoreCommands()) {
            parser.advance();
            CommandType commandType = parser.getCommandType();

            if (commandType == CommandType.Label) {
                st.addEntry(parser.getSymbol(), nextInstructionLine);
            } else {
                nextInstructionLine++;
            }
        }

        return st;
    }

    private static void performSecondPassWrite(Parser parser, SymbolTable st) {

    }

    private static Parser createNewParser(File inputFile) throws FileNotFoundException {
        return new Parser(new Scanner(inputFile));
    }
}