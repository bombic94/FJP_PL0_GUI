# FJP_PL0_GUI

Simple PL/0 interpreter with GUI in JavaFX.
Made as project at University of West Bohemia in 2017.

Interpreter enables to simulate flow of PL/0 program. 
User can observe list of instructions, list of values in stack and registers: Program Counter, Base and Stack-Top Pointer.
Program can operate with extended instruction set (see complete list of instructions below). 
Program can simulate operation with data in heap, I/O operations and dynamic operations with stack.

## Installation
Download project and in program root directory execute
```
mvn clean install
```

Then run the built .jar file.

## Usage
In GUI press the *Load file* button and choose file with code table (as below). Right now program only supports .txt files.
It is important to keep the format of text as shown in code for sum: *Index - Operation code - Level - Argument*

Code for sum of two numbers: 
```
0 INT 0 5
1 LIT 0 8
2 STO 0 3
3 LIT 0 4
4 STO 0 4
5 LOD 0 3 
6 LOD 0 4
7 OPR 0 2
```

After loading it is possible to go through program step by step by pressing *Step forward* button.
Button *Reset* resets program to first instruction.

## List of possible instructions
Instructions can contain parameters L (level) and A (argument). 0 indicates, that this argument must be always 0.
```
	LIT 0 A
	OPR 0 A
	LOD L A
	STO L A
	CAL L A
	INT 0 A
	JMP 0 A
	JMC 0 A
	RET 0 0
  
	REA 0 0
	WRI 0 0
	OPF 0 A
	RTI 0 0
	ITR 0 0
	NEW 0 0
	DEL 0 0
	LDA 0 0
	STA 0 0
	PLD 0 0
	PST 0 0
```
