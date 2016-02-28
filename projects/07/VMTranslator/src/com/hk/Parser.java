package com.hk;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {

    private final static Map<String, CommandType> _commandMap = new HashMap<>();
    static {
        _commandMap.put("add", CommandType.ARITHMETIC);
        _commandMap.put("sub", CommandType.ARITHMETIC);
        _commandMap.put("neg", CommandType.ARITHMETIC);
        _commandMap.put("eq", CommandType.ARITHMETIC);
        _commandMap.put("gt", CommandType.ARITHMETIC);
        _commandMap.put("lt", CommandType.ARITHMETIC);
        _commandMap.put("and", CommandType.ARITHMETIC);
        _commandMap.put("or", CommandType.ARITHMETIC);
        _commandMap.put("not", CommandType.ARITHMETIC);
        _commandMap.put("push", CommandType.PUSH);
        _commandMap.put("pop", CommandType.POP);
        _commandMap.put("return", CommandType.RETURN);
    }

    private Scanner _input;
    private String _command;
    private String[] _commandTokens;

    public Parser(final Scanner input) {
        _input = input;
    }

    /*
    * Returns whether or not there are more commands in the input.
    * */
    public boolean hasMoreCommands() {
        return _input.hasNextLine();
    }

    /*
    * Reads the next command from the input and makes it the current command. Should only be called if
    * hasMoreCommands() returns true. Initially, there is no current command.
    * */
    public void advance() {
        final String line = clean(_input.nextLine());
        if (line.isEmpty()) {
            advance();
            return;
        }

        _command = line;
        _commandTokens = line.split(" ");
    }

    /*
    * Returns the type of the current VM command.
    * */
    public CommandType getCommandType() {
        return _commandTokens == null || _commandTokens.length == 0
            ? CommandType.NONE
            : _commandMap.get(_commandTokens[0]);
    }


    /*
    * Returns the first argument in the current command. In the case of ARITHMETIC, the command itself is returned.
    * Should not be called on command type RETURN.
    * */
    public String getArg1() {
        if (getCommandType() == CommandType.ARITHMETIC) {
            return _command;
        }

        return _commandTokens.length > 0 ? _commandTokens[1] : null;
    }

    /*
    * Returns the second argument of the current command. Should be called only if the current argument is
    * PUSH, POP, FUNCTION, or CALL.
    * */
    public String getArg2() {
        return _commandTokens.length > 1 ? _commandTokens[2] : null;
    }

    public String clean(final String uncleanLine) {
        return uncleanLine.replaceAll("//.*", "").trim();
    }
}
