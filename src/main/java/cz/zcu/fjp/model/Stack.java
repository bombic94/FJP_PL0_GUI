package cz.zcu.fjp.model;

import java.util.List;

public class Stack {
	private int basis;
	private int top;
	private int instructionCount;
	private List<StackItem> stackItems;
	
	public Stack(int basis, int top, int instructionCount, List<StackItem> stackItems) {
		super();
		this.basis = basis;
		this.top = top;
		this.instructionCount = instructionCount;
		this.stackItems = stackItems;
	}
	public int getBasis() {
		return basis;
	}
	public void setBasis(int basis) {
		this.basis = basis;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getInstructionCount() {
		return instructionCount;
	}
	public void setInstructionCount(int instructionCount) {
		this.instructionCount = instructionCount;
	}
	public List<StackItem> getStackItems() {
		return stackItems;
	}
	public void setStackItems(List<StackItem> stackItems) {
		this.stackItems = stackItems;
	}
}
