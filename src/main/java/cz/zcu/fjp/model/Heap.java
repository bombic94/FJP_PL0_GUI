package cz.zcu.fjp.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class representing Heap item (index and value in list)
 */
public class Heap {

	private SimpleIntegerProperty index = new SimpleIntegerProperty();
	private SimpleIntegerProperty value = new SimpleIntegerProperty();

	public Heap(int index, int value) {
		super();
		setIndex(index);
		setValue(value);
	}

	public Heap() {
		this(0, 0);
	}

	public int getIndex() {
		return index.get();
	}

	public void setIndex(int index) {
		this.index.set(index);
	}

	public int getValue() {
		return value.get();
	}

	public void setValue(int value) {
		this.value.set(value);
	}

	@Override
	public String toString() {
		return new StringBuilder("Heap: ")
				.append("Index - ").append(getIndex())
				.append(", Value - ").append(getValue())
				.toString();
	}
}
