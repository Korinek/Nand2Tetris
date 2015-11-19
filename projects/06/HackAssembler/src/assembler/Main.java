package assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) throw new Exception("Please specify an input file and output file.");

        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        SymbolTable st = buildFirstPassSymbolTable(createNewParser(inputFile));
        performSecondPassWrite(createNewParser(inputFile), st, outputFile);
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

    private static void performSecondPassWrite(Parser parser, SymbolTable st, File outputFile) throws Exception {
        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
        Coder coder = new Coder();
        int nextAvailableAddress = 16;

        while(parser.hasMoreCommands()) {
            parser.advance();
            CommandType commandType = parser.getCommandType();

            if (commandType == CommandType.Address) {
                String symbol = parser.getSymbol();
                boolean isSymbolNumeric = symbol.matches("[-+]?\\d*\\.?\\d+");
                if (!isSymbolNumeric && !st.contains(symbol)) {
                    st.addEntry(symbol, nextAvailableAddress++);
                }

                int memoryAddressOfSymbol = isSymbolNumeric ? Integer.parseInt(symbol): st.getAddress(symbol);
                StringBuilder sb = new StringBuilder(Integer.toBinaryString(memoryAddressOfSymbol));
                while(sb.length() < 16) sb.insert(0, '0');

                writer.println(sb.toString());

            } else if (commandType == CommandType.Computation) {
                String destinationCode = coder.getDestinationCode(parser.getDestination());
                String computationCode = coder.getComputationCode(parser.getComputation());
                String jumpCode = coder.getJumpCode(parser.getJump());

                writer.println("111" + computationCode + destinationCode + jumpCode);
            }
        }

        writer.close();
    }

    private static Parser createNewParser(File inputFile) throws FileNotFoundException {
        return new Parser(new Scanner(inputFile));
    }
}