package cz.zcu.fjp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Instruction {
	
	private SimpleIntegerProperty index = new SimpleIntegerProperty();
	private SimpleStringProperty instruction = new SimpleStringProperty();
	private SimpleIntegerProperty level = new SimpleIntegerProperty();
	private SimpleIntegerProperty operand = new SimpleIntegerProperty();
	private SimpleStringProperty debug = new SimpleStringProperty();
	
	public Instruction(int index, String instruction, int level, int operand, String debug) {
		super();
		setIndex(index);
		setInstruction(instruction);
		setLevel(level);
		setOperand(operand);
		setDebug(debug);
	}
	
	public Instruction(int index, String instruction, int level, int operand) {
		this(index, instruction, level, operand, "");
	}
	
	public Instruction() {
		this(-1, "", -1, -1, "");
	}
	
	public int getIndex() {
		return index.get();
	}
	public void setIndex(int index) {
		this.index.set(index);
	}
	public String getInstruction() {
		return instruction.get();
	}
	public void setInstruction(String instruction) {
		this.instruction.set(instruction);
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
				.append("Index - ").append(index)
				.append("Instruction - ").append(instruction)
				.append("Level - ").append(level)
				.append("Operand - ").append(operand)
				.append("Debug - ").append(debug)
				.toString();	
	}
}
