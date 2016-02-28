package com.hk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Please specify file or directory to compile.");
        }

        final List<File> inputFiles = getFiles(args[0]);

        final CodeWriter codeWriter = new CodeWriter();

        for (final File inputFile : inputFiles) {
            final String outputPath = inputFile.getPath().replaceAll(Pattern.quote(".vm"), ".asm");
            codeWriter.setOutput(new BufferedWriter(new FileWriter(outputPath)), inputFile.getName().replaceAll(".vm", ""));

            final Parser parser = new Parser(new Scanner(inputFile));

            while (parser.hasMoreCommands()) {
                parser.advance();

                final CommandType commandType = parser.getCommandType();

                switch(commandType) {
                    case ARITHMETIC:
                        codeWriter.writeArithmetic(parser.getArg1());
                        break;
                    case PUSH:
                    case POP:
                        codeWriter.writePushPop(commandType, parser.getArg1(), Integer.parseInt(parser.getArg2()));
                        break;
                    default:
                        break;
                }
            }

            codeWriter.closeOutput();
        }

    }

    private static List<File> getFiles(final String path) throws Exception {
        if (path.endsWith(".vm")) {
            final ArrayList<File> list = new ArrayList<>();
            list.add(new File(path));
            return list;
        }

        //assume directory
        return Files.walk(Paths.get(path))
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .filter((file) -> {
                try {
                    return file.getCanonicalPath().endsWith(".vm");
                } catch (IOException e) {
                    return false;
                }
            })
            .collect(Collectors.toList());
    }
}
