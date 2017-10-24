package cz.zcu.fjp.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class representing one item in list of items in Stack.
 * Contains info about index and value.
 */
public class StackItem {

	private SimpleIntegerProperty index = new SimpleIntegerProperty();
	private SimpleIntegerProperty value = new SimpleIntegerProperty();
	
	public StackItem(int index, int value) {
		super();
		setIndex(index);
		setValue(value);
	}
	
	public int getIndex() {
		return index.get();
	}
	public void setIndex(int index) {
		this.index.set(index);;
	}
	public int getValue() {
		return value.get();
	}
	public void setValue(int value) {
		this.value.set(value);
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Instruction: ")
				.append("Index - ").append(getIndex())
				.append("Value - ").append(getValue())
				.toString();	
	}
}
