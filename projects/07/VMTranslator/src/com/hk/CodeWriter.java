package com.hk;


import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeWriter {

    private static final Map<String, String> _segmentToAddressBase = new HashMap<>();
    static {
        _segmentToAddressBase.put("this", "THIS"); // THIS -> R3
        _segmentToAddressBase.put("that", "THAT"); // THAT -> R4
        _segmentToAddressBase.put("local", "LCL"); // LCL -> R1
        _segmentToAddressBase.put("pointer", "R3");
        _segmentToAddressBase.put("argument", "ARG"); // ARG -> R2
        _segmentToAddressBase.put("temp", "R5");
        _segmentToAddressBase.put("static", "16"); // range 16-255
    }

    private int _comparisonId;
    private BufferedWriter _output;
    private String _staticName;

    public void setOutput(final BufferedWriter output, final String staticName) throws IOException {
        _output = output;
        _staticName = staticName;

        _comparisonId = 0;

        //Init stack
        writeLine("@256");
        writeLine("D=A");
        writeLine("@SP");
        writeLine("M=D");
    }

    public void writeArithmetic(final String command) throws IOException {
        writeLine("// " + command);
        switch(command) {
            case "add":
                writeAddArithmetic();
                break;
            case "sub":
                writeSubArithmetic();
                break;
            case "neg":
                writeNegArithmetic();
                break;
            case "eq":
                writeEqArithmetic();
                break;
            case "gt":
                writeGtArithmetic();
                break;
            case "lt":
                writeLtArithmetic();
                break;
            case "and":
                writeAndArithmetic();
                break;
            case "or":
                writeOrArithmetic();
                break;
            case "not":
                writeNotArithmetic();
                break;
            default:
                break;
        }
    }

    public void writePushPop(final CommandType commandType, final String segment, final int index) throws Exception {
        switch (commandType) {
            case POP:
                writePop(segment, index);
                break;
            case PUSH:
                writePush(segment, index);
                break;
            default:
                break;
        }
    }

    public void closeOutput() throws IOException {
        _output.close();
    }

    private void writePush(final String segment, final int index) throws Exception {
        writeLine("// push " + segment + " " + index);
        switch (segment) {
            case "constant":
                writePushConstant(index);
                break;
            case "this":
            case "that":
            case "local":
            case "argument":
            case "static":
                writePushMemoryCommon(_segmentToAddressBase.get(segment), index);
                break;
            case "temp":
            case "pointer":
                writePushRegisterCommon(_segmentToAddressBase.get(segment), index);
                break;
            default:
                throw new Exception("Invalid push segment");
        }
    }

    private void writePop(final String segment, final int index) throws Exception {
        writeLine("// pop " + segment + " " + index);

        switch (segment) {
            case "constant":
                writePopConstant(index);
                break;
            case "this":
            case "that":
            case "local":
            case "argument":
            case "static":
                writePopIntoMemoryCommon(_segmentToAddressBase.get(segment), index);
                break;
            case "temp":
            case "pointer":
                writePopIntoRegisterCommon(_segmentToAddressBase.get(segment), index);
                break;
            default:
                throw new Exception("Invalid pop segment");
        }
    }

    private void writeCommonTwoOperationArithmeticSetup() throws IOException {
        loadTopOfStackValueIntoD();

        //Decrement top of stack address
        writeLine("@SP");
        writeLine("M=M-1");

        //Load pointer to top stack value into A
        writeLine("@SP");
        writeLine("A=M-1");
    }

    private void writeAddArithmetic() throws IOException {
        writeCommonTwoOperationArithmeticSetup();
        writeLine("M=D+M");
    }

    private void writeNotArithmetic() throws IOException {
        loadTopOfStackValueIntoD();
        writeLine("M=!M");
    }

    private void writeOrArithmetic() throws IOException {
        writeCommonTwoOperationArithmeticSetup();
        writeLine("M=D|M");
    }

    private void writeAndArithmetic() throws IOException {
        writeCommonTwoOperationArithmeticSetup();
        writeLine("M=D&M");
    }

    private void writeNegArithmetic() throws IOException {
        loadTopOfStackValueIntoD();
        writeLine("M=-M");
    }

    private void writeSubArithmetic() throws IOException {
        writeCommonTwoOperationArithmeticSetup();
        writeLine("M=M-D");
    }

    private void writeLtArithmetic() throws IOException {
        writeComparison("LT");
    }

    private void writeGtArithmetic() throws IOException {
        writeComparison("GT");
    }

    private void writeEqArithmetic() throws IOException {
        writeComparison("EQ");
    }

    private void writeComparison(final String operator) throws IOException {
        //Load top of Stack Value into D
        writeLine("@SP");
        writeLine("A=M-1");
        writeLine("D=M");

        //Decrement stack pointer
        writeLine("@SP");
        writeLine("M=M-1");

        //Load top of stack Value into A
        writeLine("A=M-1");

        //Put comparison value into D
        writeLine("D=M-D");

        //Put the boolean value into D (-1 = true and 0 = false
        writeLine("@" + operator + "_" + _comparisonId);
        writeLine("D;J"+operator);
        writeLine("D=0");
        writeLine("@" + operator + "_" + _comparisonId + "_END");
        writeLine("0;JMP");
        writeLabel(operator + "_" + _comparisonId);
        writeLine("D=-1");
        writeLabel(operator + "_" + _comparisonId + "_END");

        //Put the boolean value on top of the stack
        writeLine("@SP");
        writeLine("A=M-1");
        writeLine("M=D");

        _comparisonId++;
    }

    private void loadTopOfStackValueIntoD() throws IOException {
        //Load top of stack value into D
        writeLine("@SP");
        writeLine("A=M-1");
        writeLine("D=M");
    }

    private void writeLabel(final String inner) throws IOException {
        writeLine("(" + inner + ")");
    }


    private void writeLine(final String stringToWrite) throws IOException {
        _output.write(stringToWrite);
        _output.newLine();
    }

    private void writePushConstant(final int value) throws IOException {
        writeLine("@"+value);
        writeLine("D=A");

        writePushValueInD();
    }

    private void writePopConstant(final int value) throws IOException {
        writePutTopStackValueIntoD();

        //Load the value that was on top of the stack, but is now in register D, into the loc
        writeLine("@"+value);
        writeLine("M=D");

        writeDecrementStackPointer();
    }


    private void writePushStatic(final int index) throws IOException {

    }

    private void writePopStatic(final int index) throws IOException {

    }

    private void writePushValueInD() throws IOException {
        //Load top of stack pointer in A
        writeLine("@SP");
        writeLine("A=M");

        //Put value to push on to top of stack
        writeLine("M=D");

        //Increment top of stack pointer
        writeLine("@SP");
        writeLine("M=M+1");
    }

    private void writePutTopStackValueIntoD() throws IOException {
        //Load the value that was on top of the stack into register D
        writeLine("@SP");
        writeLine("A=M-1");
        writeLine("D=M");
    }

    private void writeDecrementStackPointer() throws IOException {
        //Decrement SP
        writeLine("@SP");
        writeLine("M=M-1");
    }

    private void writePushMemoryCommon(final String segmentBase, final int offset) throws IOException {
        writeLine("@"+segmentBase);
        writeLine("D=M");
        writeLine("@"+offset);
        writeLine("A=A+D");
        writeLine("D=M");
        writePushValueInD();
    }

    private void writePushRegisterCommon(final String segmentBase, final int offset) throws IOException {
        writeLine("@"+segmentBase);
        writeLine("D=A"); // D = 3
        writeLine("@"+offset); //A = 0
        writeLine("A=A+D"); // A = 3
        writeLine("D=M");// D = RAM[3]

        writePushValueInD();
    }

    private void writePopIntoMemoryCommon(final String segmentBase, final int offset) throws IOException {
        writePutTopStackValueIntoD();

        //pop local 0

        //Put top stack value into R13
        writeLine("@R13"); //A = 13
        writeLine("M=D"); //Ram[13] = 10

        writeLine("@"+offset); //A = 0
        writeLine("D=A"); // D = 0
        writeLine("@"+segmentBase); // A = LCL
        writeLine("A=M"); // A = RAM[LCL] = 300
        writeLine("D=A+D"); // D now has target memory address // D = 300

        writeLine("@R14"); // A = 14
        writeLine("M=D"); // R14 now has target memory address // A[14] = 300

        writeLine("@R13"); //A = 13
        writeLine("D=M"); // D now has the top stack value to store // D=10

        writeLine("@R14"); // A = 14
        writeLine("A=M"); // A = 300
        writeLine("M=D"); // store value //

        writeDecrementStackPointer();
    }

    private void writePopIntoRegisterCommon(final String segmentBase, final int offset) throws IOException {
        writePutTopStackValueIntoD();

        //Put top stack value into R13
        writeLine("@R13"); //A = 13
        writeLine("M=D"); //Ram[13] = 999

        writeLine("@"+offset); //A = 3
        writeLine("D=A"); // D = 3
        writeLine("@"+segmentBase); // A = 3
        writeLine("D=A+D"); // D now has target memory address // D = 6

        writeLine("@R14"); // A = 14
        writeLine("M=D"); // R14 now has target memory address // A[14] = 6

        writeLine("@R13"); //A = 13
        writeLine("D=M"); // D now has the top stack value to store //D=999

        writeLine("@R14"); // A = 14
        writeLine("A=M"); // A = 6
        writeLine("M=D"); // store value //

        writeDecrementStackPointer();
    }
}
