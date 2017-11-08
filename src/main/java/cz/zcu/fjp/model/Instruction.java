package cz.zcu.fjp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class representing instruction. List of instructions is shown in program
 * instruction table. Contains information about Index, instruction name, level
 * of basis and operand information. Additionally can contain debug info.
 */
public class Instruction {

	private SimpleIntegerProperty index = new SimpleIntegerProperty();
	private InstructionEnum instruction;
	private SimpleIntegerProperty level = new SimpleIntegerProperty();
	private SimpleIntegerProperty operand = new SimpleIntegerProperty();
	private SimpleStringProperty debug = new SimpleStringProperty();

	public Instruction(int index, InstructionEnum instruction, int level, int operand, String debug) {
		super();
		setIndex(index);
		setInstruction(instruction);
		setLevel(level);
		setOperand(operand);
		setDebug(debug);
	}
	public Instruction(int index, String instruction, int level, int operand, String debug) {
		this(index, InstructionEnum.valueOf(instruction), level, operand, debug);
	}

	public Instruction(int index, String instruction, int level, int operand) {
		this(index, instruction, level, operand, "");
	}

	public Instruction() {
		this(-1, InstructionEnum.EMPTY, -1, -1, "");
	}

	public int getIndex() {
		return index.get();
	}

	public void setIndex(int index) {
		this.index.set(index);
	}

	public InstructionEnum getInstruction() {
		return instruction;
	}

	public void setInstruction(InstructionEnum instruction) {
		this.instruction = instruction;
	}
	
	public void setInstruction(String instruction) {
		this.instruction = InstructionEnum.valueOf(instruction);
	}

	public int getLevel() {
		return level.get();
	}

	public void setLevel(int level) {
		this.level.set(level);
	}

	public int getOperand() {
		return operand.get();
	}

	public void setOperand(int operand) {
		this.operand.set(operand);
	}

	public String getDebug() {
		return debug.get();
	}

	public void setDebug(String debug) {
		this.debug.set(debug);
	}

	@Override
	public String toString() {
		return new StringBuilder("Instruction: ")
				.append("Index - ").append(getIndex())
				.append(", Instruction - ").append(getInstruction())
				.append(", Level - ").append(getLevel())
				.append(", Operand - ").append(getOperand())
				.append(", Debug - ").append(getDebug())
				.toString();
	}
}
