// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

//KBD = RAM[24576] and SCREEN = RAM[16384]
//Thus we can just loop over the memory locations SCREEN->KBD-1 and color
//each on depending on if RAM[KDB] = 1 or not

    @SCREEN
    D=A
    @COLOR_INDEX
    M=D
(LOOP)
    @COLOR_INDEX
    D=M                 // D = RAM[COLOR_INDEX]
    A=D

    // Color the pixel at COLOR_INDEX
    @KBD
    D=M
    @COLOR_WHITE
    D;JEQ               // If RAM[KBD] = 0, color white, else color black
    @COLOR_INDEX
    A=M
    M=1                 // Color RAM[COLOR_INDEX] Black
    @COLOR_BLACK        // Jump to COLOR_BLACK in order to avoid recoloring it white
    0;JMP

    (COLOR_WHITE)
    @COLOR_INDEX
    A=M
    M=0                 // Color RAM[COLOR_INDEX] White

    (COLOR_BLACK)
    
    @COLOR_INDEX
    M=M+1               // Advance COLOR_INDEX

    //Wrap COLOR_INDEX if need be
    D=M
    @KBD
    D=A-D
    @LOOP
    D; JGT              // Restart the loop since we don't need to wrap yet

    @SCREEN             // Reset COLOR_INDEX to SCREEN
    D=A
    @COLOR_INDEX
    M=D
   
    @LOOP
    0; JMP
(END)
