# FJP_PL0_GUI

Simple PL/0 interpreter with GUI in JavaFX.
Made as project at University of West Bohemia in 2017.

Interpreter enables to simulate flow of PL/0 program. 
User can observe list of instructions, list of values in stack and registers: Program Counter, Base and Stack-Top Pointer

## Installation
Download project and in root directory execute
```
mvn clean install
```

Then run the built .jar file.

## Usage
In GUI press the *Load file* button and choose file with code table (as below). Right now program only supports .txt files.

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
