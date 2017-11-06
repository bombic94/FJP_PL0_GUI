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
	private TreeItem<StackItem> base;
	private TreeItem<StackItem> top;
	private SimpleIntegerProperty programCounter = new SimpleIntegerProperty();
	private int level;
	private ObservableList<StackItem> stackItems;
	private TreeItem<StackItem> root;
	
	public Stack(TreeItem<StackItem> base, TreeItem<StackItem> top, int instructionCount, int level, ObservableList<StackItem> stackItems, TreeItem<StackItem> root) {
		super();
		setBase(base);
		setTop(top);
		setProgramCounter(instructionCount);
		setLevel(level);
		setStackItems(stackItems);
		setRoot(root);
	}

	public Stack() {
		this(new TreeItem<StackItem>(new StackItem(1, 0)), new TreeItem<StackItem>(new StackItem(0, 0)), 0, -1, FXCollections.observableArrayList(), new TreeItem<StackItem>(new StackItem(-1, -1)));
	}

	public TreeItem<StackItem> getBase() {
		return base;
	}
	public void setBase(TreeItem<StackItem> base) {
		this.base = base;
	}
	public TreeItem<StackItem> getTop() {
		return top;
	}
	public void setTop(TreeItem<StackItem> top) {
		this.top = top;
	}
	public int getProgramCounter() {
		return programCounter.get();
	}
	public void setProgramCounter(int instructionCount) {
		this.programCounter.set(instructionCount);
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
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
