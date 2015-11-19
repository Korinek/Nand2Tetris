package assembler;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Parser {
    private Scanner _input;
    private String _currentCommand;
    private Pattern _symbolPattern;

    public Parser(Scanner input) {
        _input = input;
        _symbolPattern = Pattern.compile("^[@|(](.*?)[)]?$");
    }

    public boolean hasMoreCommands() {
        return _input.hasNext();
    }

    public void advance() {
        String currentLine = _input.nextLine();
        currentLine = currentLine.replaceAll("\\s", "");
        currentLine = currentLine.replaceAll("//.*", "");

        if (currentLine.isEmpty()) {
            advance();
        } else {
            _currentCommand = currentLine;
        }
    }

    public CommandType getCommandType() {
        if (_currentCommand.matches("^@.*")) {
            return CommandType.Address;
        }

        if (_currentCommand.matches("^[(].*[)]$")) {
            return CommandType.Label;
        }

        return CommandType.Computation;
    }

    public String getSymbol() throws Exception {
        Matcher symbolMatcher = _symbolPattern.matcher(_currentCommand);
        if (!symbolMatcher.find()) {
            throw new Exception("Current command does not contain a symbol. Command = " + _currentCommand);
        }
        return symbolMatcher.group(1);
    }

    //dest=comp;jmp

    public String getDestination() {
        if(_currentCommand.contains("=")) {
            String[] fields = _currentCommand.split("=");
            return fields[0];
        }

        return "null";
    }

    public String getComputation() {
        if(_currentCommand.contains("=")) {
            String[] fields = _currentCommand.split("=");
            return fields[1];
        } else {
            String[] fields = _currentCommand.split(";");
            return fields[0];
        }
    }

    public String getJump() {
        if(_currentCommand.contains(";")) {
            String[] fields = _currentCommand.split(";");
            return fields[1];
        }
        return "null";
    }
}
