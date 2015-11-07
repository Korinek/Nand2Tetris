// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

//initialize product to zero (R2)
//while R0 > 0
    //R2 += R1
    //R0 = R0-1

    @2
    M=0
(LOOP)
    @0
    D=M         //D = R0
    @END
    D;JLE       //Exit the loop if R0 <= 0

    @1
    D=M         //D = R1
    @2
    M=M+D       //R2 += R1

    @0
    M=M-1       //Decrement R0

    @LOOP
    0;JMP // GoTo Loop
(END)
    @END
    0;JMP // Infinite loop
