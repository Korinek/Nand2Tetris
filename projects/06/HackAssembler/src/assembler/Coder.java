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
        _jumpsTable.put("null", "000");
        _jumpsTable.put("JGT",  "001");
        _jumpsTable.put("JEQ",  "010");
        _jumpsTable.put("JGE",  "011");
        _jumpsTable.put("JLT",  "100");
        _jumpsTable.put("JNE",  "101");
        _jumpsTable.put("JLE",  "110");
        _jumpsTable.put("JMP",  "111");
    }

    private static final HashMap<String, String> _computationsTable;
    static {
        _computationsTable = new HashMap<>();
        // (when a=0)
        _computationsTable.put("0",   "0101010");
        _computationsTable.put("1",   "0111111");
        _computationsTable.put("!A",  "0110001");
        _computationsTable.put("-D",  "0001111");
        _computationsTable.put("-A",  "0110011");
        _computationsTable.put("D",   "0001100");
        _computationsTable.put("-1",  "0111010");
        _computationsTable.put("!D",  "0001101");
        _computationsTable.put("A",   "0110000");
        _computationsTable.put("D+1", "0011111");
        _computationsTable.put("A+1", "0110111");
        _computationsTable.put("D-1", "0001110");
        _computationsTable.put("A-1", "0110010");
        _computationsTable.put("D+A", "0000010");
        _computationsTable.put("D-A", "0101011");
        _computationsTable.put("A-D", "0000111");
        _computationsTable.put("D&A", "0000000");
        _computationsTable.put("D|A", "0010101");
        // (when a=1)
        _computationsTable.put("M",   "1110000");
        _computationsTable.put("!M",  "1110001");
        _computationsTable.put("-M",  "1110011");
        _computationsTable.put("M+1", "1110111");
        _computationsTable.put("M-1", "1110010");
        _computationsTable.put("D+M", "1000010");
        _computationsTable.put("D-M", "1010011");
        _computationsTable.put("M-D", "1000111");
        _computationsTable.put("D&M", "1000000");
        _computationsTable.put("D|M", "1010101");
    }

    public String getDestinationCode(String mnemonic) {
        return _destinationsTable.get(mnemonic);
    }

    public String getComputationCode(String mnemonic) {
        return _computationsTable.get(mnemonic);
    }

    public String getJumpCode(String mnemonic) {
        return _jumpsTable.get(mnemonic);
    }
}
