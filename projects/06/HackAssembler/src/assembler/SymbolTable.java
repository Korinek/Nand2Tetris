package assembler;

// There are 3 types of symbols: Predefined, labels, and variables.

import java.util.HashMap;

public class SymbolTable {
    private static final HashMap<String, Integer> _table;
    static {
        _table = new HashMap<>();
        _table.put("SP", 0);
        _table.put("LCL", 1);
        _table.put("ARG", 2);
        _table.put("THIS", 3);
        _table.put("THAT", 4);
        _table.put("R0", 0);
        _table.put("R1", 1);
        _table.put("R2", 2);
        _table.put("R3", 3);
        _table.put("R4", 4);
        _table.put("R5", 5);
        _table.put("R6", 6);
        _table.put("R7", 7);
        _table.put("R8", 8);
        _table.put("R9", 9);
        _table.put("R10", 10);
        _table.put("R11", 11);
        _table.put("R12", 12);
        _table.put("R13", 13);
        _table.put("R14", 14);
        _table.put("R15", 15);
        _table.put("SCREEN", 16384);
        _table.put("KBD", 24576);
    }

    public void addEntry(String symbol, int address) {
        _table.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return _table.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return _table.get(symbol);
    }
}
