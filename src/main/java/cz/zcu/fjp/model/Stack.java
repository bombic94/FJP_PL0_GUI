package cz.zcu.fjp.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TreeItem;

/**
 * Class representing Stack information
 * Contains info about basis, top and instruction count
 * Also contains info about list of items in stack
 */
public class Stack {
	private SimpleIntegerProperty basis = new SimpleIntegerProperty();
	private SimpleIntegerProperty top = new SimpleIntegerProperty();
	private SimpleIntegerProperty instructionCount = new SimpleIntegerProperty();
	private List<StackItem> stackItems;
	private TreeItem<StackItem> root;
	
	public Stack(int basis, int top, int instructionCount, List<StackItem> stackItems, TreeItem<StackItem> root) {
		super();
		setBasis(basis);
		setTop(top);
		setInstructionCount(instructionCount);
		setStackItems(stackItems);
		setRoot(root);
	}
	
	public Stack() {
		this(0, 0, 0, new ArrayList<StackItem>(), new TreeItem<StackItem>(new StackItem(-1, -1)));
	}

	public int getBasis() {
		return basis.get();
	}
	public void setBasis(int basis) {
		this.basis.set(basis);
	}
	public int getTop() {
		return top.get();
	}
	public void setTop(int top) {
		this.top.set(top);
	}
	public int getInstructionCount() {
		return instructionCount.get();
	}
	public void setInstructionCount(int instructionCount) {
		this.instructionCount.set(instructionCount);
	}
	public List<StackItem> getStackItems() {
		return stackItems;
	}
	public void setStackItems(List<StackItem> stackItems) {
		this.stackItems = stackItems;
	}
	public TreeItem<StackItem> getRoot() {
		return root;
	}
	public void setRoot(TreeItem<StackItem> root) {
		this.root = root;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Stack: ")
				.append("Basis - ").append(getBasis())
				.append("Top - ").append(getTop())
				.append("InstructionCount - ").append(getInstructionCount())
				.append("StackItems - ").append(getStackItems().toString())
				.append("Root - ").append(getRoot().toString())
				.toString();	
	}
}
