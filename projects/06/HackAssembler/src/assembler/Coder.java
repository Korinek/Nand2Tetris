package assembler;

import java.util.HashMap;

final class Coder {
    private static final HashMap<String, String> _destinationsTable;
    static {
        _destinationsTable = new HashMap<>();
        _destinationsTable.put("null", "000");
        _destinationsTable.put("M", "001");
        _destinationsTable.put("D", "010");
        _destinationsTable.put("MD", "011");
        _destinationsTable.put("A", "100");
        _destinationsTable.put("AM", "101");
        _destinationsTable.put("AD", "110");
        _destinationsTable.put("AMD", "111");
    }

    private static final HashMap<String, String> _jumpsTable;
    static {
        //Jumps
        _jumpsTable = new HashMap<>();
        _jumpsTable.put("JGT", "001");
        _jumpsTable.put("JEQ", "010");
        _jumpsTable.put("JGE", "011");
        _jumpsTable.put("JLT", "100");
        _jumpsTable.put("JNE", "101");
        _jumpsTable.put("JLE", "110");
        _jumpsTable.put("JMP", "111");
    }

    private static final HashMap<String, String> _computationsTable;
    static {
        _computationsTable = new HashMap<>();
        // (when a=0)
        _computationsTable.put("0",   "101010");
        _computationsTable.put("1",   "111111");
        _computationsTable.put("!A",  "110001");
        _computationsTable.put("-D",  "001111");
        _computationsTable.put("-A",  "110011");
        _computationsTable.put("D",   "001100");
        _computationsTable.put("-1",  "111010");
        _computationsTable.put("!D",  "001101");
        _computationsTable.put("A",   "110000");
        _computationsTable.put("D+1", "011111");
        _computationsTable.put("A+1", "110111");
        _computationsTable.put("D-1", "001110");
        _computationsTable.put("A-1", "110010");
        _computationsTable.put("D+A", "000010");
        _computationsTable.put("D-A", "101011");
        _computationsTable.put("A-D", "000111");
        _computationsTable.put("D&A", "000000");
        _computationsTable.put("D|A", "010101");
        // (when a=1)
        _computationsTable.put("M",   "110000");
        _computationsTable.put("!M",  "110001");
        _computationsTable.put("-M",  "110011");
        _computationsTable.put("M+1", "110111");
        _computationsTable.put("M-1", "110010");
        _computationsTable.put("D+M", "000010");
        _computationsTable.put("D-M", "010011");
        _computationsTable.put("M-D", "000111");
        _computationsTable.put("D&M", "000000");
        _computationsTable.put("D|M", "010101");
    }

    public static String destination(String mnemonic) {
        return _destinationsTable.get(mnemonic);
    }

    public static String computation(String mnemonic) {
        return _computationsTable.get(mnemonic);
    }

    public static String jump(String mnemonic) {
        return _jumpsTable.get(mnemonic);
    }
}
