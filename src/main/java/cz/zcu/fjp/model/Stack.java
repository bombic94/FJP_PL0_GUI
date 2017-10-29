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
	private StackItem base = new StackItem();
	private StackItem top = new StackItem();
	private SimpleIntegerProperty programCounter = new SimpleIntegerProperty();
	private ObservableList<StackItem> stackItems;
	private TreeItem<StackItem> root;
	
	public Stack(StackItem base, StackItem top, int instructionCount, ObservableList<StackItem> stackItems, TreeItem<StackItem> root) {
		super();
		setBase(base);
		setTop(top);
		setProgramCounter(instructionCount);
		setStackItems(stackItems);
		setRoot(root);
	}
	
	public Stack() {
		this(new StackItem(1,0), new StackItem(0,0), 0, FXCollections.observableArrayList(), new TreeItem<StackItem>(new StackItem(-1, -1)));
	}

	public StackItem getBase() {
		return base;
	}
	public void setBase(StackItem base) {
		this.base = base;
	}
	public StackItem getTop() {
		return top;
	}
	public void setTop(StackItem top) {
		this.top = top;
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
				.append("Base - ").append(getBase().toString())
				.append("Top stack register - ").append(getTop().toString())
				.append("StackItems - ").append(getStackItems().toString())
				.append("Root - ").append(getRoot().toString())
				.toString();	
	}
}
