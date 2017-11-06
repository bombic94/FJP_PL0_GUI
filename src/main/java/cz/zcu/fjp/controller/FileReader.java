package cz.zcu.fjp.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.Collection;
import java.util.stream.Collectors;

import cz.zcu.fjp.model.Instruction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton FileReader takes care of reading data from txt file and parsing
 * them into ObservableList, which is used to fill table
 */
public class FileReader {

	private static FileReader instance = null;

	protected FileReader() {

	}

	public static FileReader getInstance() {
		if (instance == null) {
			instance = new FileReader();
		}
		return instance;
	}

	public ObservableList<Instruction> getTable(File file) {
		Collection<Instruction> list = null;
		try {
			list = Files.readAllLines(new File(file.getAbsolutePath()).toPath()).stream().map(line -> {
				String[] details = line.split("\\s+");
				Instruction i = new Instruction();
				i.setIndex(Integer.parseInt(details[0]));
				i.setInstruction(details[1]);
				i.setLevel(Integer.parseInt(details[2]));
				i.setOperand(Integer.parseInt(details[3]));
				i.setDebug(createDebug(i.getInstruction(), i.getOperand()));
				return i;
			}).collect(Collectors.toList());
			ObservableList<Instruction> tableList = FXCollections.observableArrayList(list);
			return tableList;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	private String createDebug(String instruction, int operand) {
		String debug = "";
		switch (instruction) {
		case "LIT": {
			debug = "Push the literal value onto the stack";
			break;
		}
		case "OPR": {

			switch (operand) {
			case 0: { // return
				debug = "Return from subroutine";
				break;
			}
			case 1: { // negate
				debug = "Operation 'x = -x' (negate)";
				break;
			}
			case 2: { // sum
				debug = "Operation 'x + y' (sum)";
				break;
			}
			case 3: { // substract
				debug = "Operation 'x - y' (substraction)";
				break;
			}
			case 4: { // multiply
				debug = "Operation 'x * y' (multiplication)";
				break;
			}
			case 5: { // divide
				debug = "Operation 'x / y' (division)";
				break;
			}
			case 6: { // modulo
				debug = "Operation 'x % y' (modulo)";
				break;
			}
			case 7: {// odd?
				debug = "Test the value at the top of the stack to see if it's odd";
				break;
			}
			case 8: { // equal
				debug = "Comparision 'x == y' (equal)";
				break;
			}
			case 9: { // not equal
				debug = "Comparision 'x != y' (not equal)";
				break;
			}
			case 10: { // less than
				debug = "Comparision 'x < y' (less than)";
				break;
			}
			case 11: { // greater than or equal
				debug = "Comparision 'x >= y' (greater than or equal)";
				break;
			}
			case 12: { // greater than
				debug = "Comparision 'x > y' (greater than)";
				break;
			}
			case 13: { // less than or equal
				debug = "Comparision 'x <= y' (less than or equal)";
				break;
			}
			}
			break;
		}
		case "LOD": {
			debug = "Load the value to top of stack";
			break;
		}
		case "STO": {
			debug = "Store the value currently at the top of the stack to memory";
			break;
		}
		case "CAL": {
			debug = "Call the subroutine at location address at given level";
			break;
		}
		case "INT": {
			debug = "Increment value on top of stack by given value";
			break;
		}
		case "JMP": {
			debug = "Jump to the instruction at address";
			break;
		}
		case "JMC": {
			debug = "Jump to the instruction at address, if value at top of stack is 0";
			break;
		}
		case "RET": {
			debug = "Return from subroutine";
			break;
		}
		}
		return debug;
	}
}
