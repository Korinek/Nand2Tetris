package assembler;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Parser {
    private Scanner _input;
    private String _currentCommand;
    private Pattern _addressCommandPattern;
    private Pattern _labelCommandPattern;
    private Pattern _symbolPattern;

    public Parser(Scanner input) {
        _input = input;
        _addressCommandPattern = Pattern.compile("^@.*");
        _labelCommandPattern = Pattern.compile("^[(].*[)]$");
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
        Matcher addressMatcher = _addressCommandPattern.matcher(_currentCommand);
        if (addressMatcher.matches()) {
            return CommandType.Address;
        }

        Matcher labelMatcher = _labelCommandPattern.matcher(_currentCommand);
        if (labelMatcher.matches()) {
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

    public String getDestination() {
        //TODO
        return null;
    }

    public String getComputation() {
        //TODO
        return null;
    }

    public String getJump() {
        //TODO
        return null;
    }
}
