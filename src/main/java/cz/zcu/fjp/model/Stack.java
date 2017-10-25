package cz.zcu.fjp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Class representing Stack information
 * Contains info about basis, top and instruction count
 * Also contains info about list of items in stack
 */
public class Stack {
	private SimpleIntegerProperty base = new SimpleIntegerProperty();
	private SimpleIntegerProperty top = new SimpleIntegerProperty();
	private SimpleIntegerProperty programCounter = new SimpleIntegerProperty();
	private ObservableList<StackItem> stackItems;
	private TreeItem<StackItem> root;
	
	public Stack(int base, int top, int instructionCount, ObservableList<StackItem> stackItems, TreeItem<StackItem> root) {
		super();
		setBase(base);
		setTop(top);
		setProgramCounter(instructionCount);
		setStackItems(stackItems);
		setRoot(root);
	}
	
	public Stack() {
		this(0, 0, 0, FXCollections.observableArrayList(), new TreeItem<StackItem>(new StackItem(-1, -1)));
	}

	public int getBase() {
		return base.get();
	}
	public void setBase(int base) {
		this.base.set(base);
	}
	public int getTop() {
		return top.get();
	}
	public void setTop(int top) {
		this.top.set(top);
	}
	public int getProgramCounter() {
		return programCounter.get();
	}
	public void setProgramCounter(int instructionCount) {
		this.programCounter.set(instructionCount);
	}
	public ObservableList<StackItem> getStackItems() {
		return stackItems;
	}
	public void setStackItems(ObservableList<StackItem> stackItems) {
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
				.append("Program counter - ").append(getProgramCounter())
				.append("Base - ").append(getBase())
				.append("Top stack register - ").append(getTop())
				.append("StackItems - ").append(getStackItems().toString())
				.append("Root - ").append(getRoot().toString())
				.toString();	
	}
}
