package cz.zcu.fjp.controller;

import org.apache.log4j.Logger;

import cz.zcu.fjp.model.Instruction;
import cz.zcu.fjp.model.Stack;
import cz.zcu.fjp.model.StackItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Singleton PL0Debugger takes care about main control of PL0 instructions Sets
 * instruction and stack information
 */
public class PL0Debugger {

	final static Logger log = Logger.getLogger(PL0Debugger.class);

	private static PL0Debugger instance = null;

	private Stack stack = new Stack();
	private ObservableList<StackItem> stackItemsToAdd;// = FXCollections.observableArrayList();

	protected PL0Debugger() {

	}

	/**
	 * Returns instance of PL0Debugger
	 * 
	 * @return PL0Debugger instance
	 */
	public static PL0Debugger getInstance() {
		if (instance == null) {
			instance = new PL0Debugger();
		}
		return instance;
	}

	/**
	 * Returns stack which is set by finding future instruction, so only return instance.
	 * 
	 * @return Stack for future instruction
	 */
	public Stack getFutureStack() {
		return stack;
	}

	/**
	 * Find out which instruction will be executed next.
	 * Based on future instruction method sets stack information (PC, Top, Base, StackItems)
	 * 
	 * @param actual Instruction which is executed at the moment.
	 * 
	 * @param instructions ObservableList of instructions in table.
	 * 
	 * @return Future instruction
	 */
	public Instruction getFutureInstruction(Instruction actual, ObservableList<Instruction> instructions) {

		ObservableList<TreeItem<StackItem>> stackItems = getActualList();

		Instruction future = null;
		if (actual == null) { // first instruction
			future = instructions.get(0);
		} else {

			log.info("Actual instruction: " + actual.toString());
			switch (actual.getInstruction()) {
			case LIT: {
				future = tryGetInstruction(actual.getIndex() + 1, instructions);

				stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), actual.getOperand())));
				trySetTop(stackItems);
				trySetPC(future);
				
				actual.setDebug("Push " + actual.getOperand() + " onto the stack");
				break;
			}
			case OPR: {
				future = tryGetInstruction(actual.getIndex() + 1, instructions);

				switch (actual.getOperand()) {
				case 0: { // return
					break;
				}
				case 1: { // negate
					StackItem item = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = item.getValue() * (-1);
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Operation negate: " + item.getValue() + " * (-1) = " + returnValue);
					break;
				}
				case 2: { // sum
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = item1.getValue() + item2.getValue();
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Operation sum: " + item1.getValue() + " + " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 3: { // substract
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = item1.getValue() - item2.getValue();
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Operation substraction: " + item1.getValue() + " - " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 4: { // multiply
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = item1.getValue() * item2.getValue();
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Operation multiplication: " + item1.getValue() + " * " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 5: { // divide
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = item1.getValue() / item2.getValue();
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Operation division: " + item1.getValue() + " / " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 6: { // modulo
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = item1.getValue() % item2.getValue();
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Operation modulo: " + item1.getValue() + " % " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 7: {// odd?
					StackItem item = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = item.getValue() % 2;
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Test if " + item.getValue() + " is odd: " + item.getValue() + " % 2 = " + returnValue);
					break;
				}
				case 8: { // equal
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = (item1.getValue() == item2.getValue()) ? 1 : 0;
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Comparision equal: " + item1.getValue() + " == " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 9: { // not equal
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = (item1.getValue() != item2.getValue()) ? 1 : 0;
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Comparision not equal: " + item1.getValue() + " != " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 10: { // less than
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = (item1.getValue() < item2.getValue()) ? 1 : 0;
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Comparision less than: " + item1.getValue() + " < " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 11: { // greater than or equal
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = (item1.getValue() >= item2.getValue()) ? 1 : 0;
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Comparision greater than or equal: " + item1.getValue() + " >= " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 12: { // greater than
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = (item1.getValue() > item2.getValue()) ? 1 : 0;
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Comparision greater than: " + item1.getValue() + " > " + item2.getValue() + " = " + returnValue);
					break;
				}
				case 13: { // less than or equal
					StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
					StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
					int returnValue = (item1.getValue() <= item2.getValue()) ? 1 : 0;
					stackItems.add(new TreeItem<StackItem>(new StackItem(getNewIndex(stackItems), returnValue)));
					actual.setDebug("Comparision less than or equal: " + item1.getValue() + " <= " + item2.getValue() + " = " + returnValue);
					break;
				}
				}
				trySetTop(stackItems);
				trySetPC(future);
				break;
			}
			case LOD: {
				future = tryGetInstruction(actual.getIndex() + 1, instructions);

				TreeItem<StackItem> item = getItemOnLevel(actual.getLevel(), actual.getOperand());
				item.getValue().setIndex(getNewIndex(stackItems));
				stackItems.add(item);
				trySetTop(stackItems);
				trySetPC(future);
				
				actual.setDebug("Load " + item.getValue().getValue() + " to top of stack");
				break;
			}
			case STO: {
				future = tryGetInstruction(actual.getIndex() + 1, instructions);

				StackItem item = stackItems.remove(stackItems.size() - 1).getValue();
				setItemOnLevel(actual.getLevel(), actual.getOperand(), new TreeItem<StackItem>(item));
				trySetTop(stackItems);
				trySetPC(future);
				
				actual.setDebug("Store " + item.getValue() + " to memory");
				break;
			}
			case CAL: {
				future = tryGetInstruction(actual.getOperand(), instructions);

				StackItem item1 = new StackItem(stack.getTop().getValue().getIndex() + 1,
						getBase(actual.getLevel()).getValue().getIndex());
				StackItem item2 = new StackItem(stack.getTop().getValue().getIndex() + 2,
						stack.getBase().getValue().getIndex());
				StackItem item3 = new StackItem(stack.getTop().getValue().getIndex() + 3,
						stack.getProgramCounter() + 1);

				// stack.setBase(stack.getTop());
				stack.setProgramCounter(actual.getOperand());

				stackItemsToAdd = FXCollections.observableArrayList(item1, item2, item3);
				
				actual.setDebug("Call subroutine on index " + actual.getOperand() + " and level " + actual.getLevel());
				break;
			}
			case INT: {
				future = tryGetInstruction(actual.getIndex() + 1, instructions);

				if (stackItemsToAdd != null && !stackItemsToAdd.isEmpty()) {
					if (actual.getOperand() > stackItemsToAdd.size()) {
						for (int i = stackItemsToAdd.size(); i < actual.getOperand(); i++) {
							int plus = 1;
							int index = stackItemsToAdd.get(stackItemsToAdd.size() - 1).getIndex() + plus;
							stackItemsToAdd.add(new StackItem(index, 0));
							plus++;
						}
					}

					TreeItem<StackItem> subroot = new TreeItem<StackItem>(stackItemsToAdd.get(0));
					subroot.setExpanded(true);
					for (int i = 1; i < stackItemsToAdd.size(); i++) {
						subroot.getChildren().add(new TreeItem<StackItem>(stackItemsToAdd.get(i)));
					}
					stack.getRoot().getChildren().add(subroot);
					stack.setBase(subroot);

					//if (stackItemsToAdd.get(0).getValue() == stackItemsToAdd.get(1).getValue()) {
						stack.setLevel(stack.getLevel() + 1);
					//}

					stackItemsToAdd.removeAll(stackItemsToAdd);

					trySetTop(subroot.getChildren());
				} else {
					int size = stackItems.size() + 1;

					TreeItem<StackItem> subroot = new TreeItem<StackItem>(new StackItem(size, 0));
					subroot.setExpanded(true);

					for (int i = size + 1; i < size + actual.getOperand(); i++) {
						subroot.getChildren().add(new TreeItem<StackItem>(new StackItem(i, 0)));
					}
					stackItems.add(subroot);

					stack.setBase(subroot);
					stack.setLevel(stack.getLevel() + 1);
					trySetTop(subroot.getChildren());
				}

				trySetPC(future);
				actual.setDebug("Increment stack size by  " + actual.getOperand());
				break;
			}
			case JMP: {
				future = tryGetInstruction(actual.getOperand(), instructions);

				trySetPC(future);
				actual.setDebug("Jump to the instruction at address " + actual.getOperand());
				break;
			}
			case JMC: {

				if (stack.getTop().getValue().getValue() == 0) {
					future = tryGetInstruction(actual.getOperand(), instructions);
				} else {
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
				}

				stackItems.remove(stackItems.size() - 1);
				trySetTop(stackItems);
				trySetPC(future);
				actual.setDebug("Jump to the instruction at address " + actual.getOperand() + ", if value at top is 0");
				break;
			}
			case RET: {

				if (stack.getBase().getValue().getIndex() <= 1) {
					future = null;
					stack.setProgramCounter(-1);
				} else {
					future = tryGetInstruction(stack.getBase().getChildren().get(1).getValue().getValue(),
							instructions);
					trySetPC(future);

					stack.setLevel(stack.getLevel() - 1);
					stack.setBase(findBaseOnIndex(stack.getBase().getChildren().get(0).getValue().getValue()));
					stack.getRoot().getChildren().remove(stack.getRoot().getChildren().size() - 1);

					trySetTop(stack.getBase().getChildren());
				}
				actual.setDebug("Return from subroutine");
				break;
			}
			}
		}

		return future;
	}

	private TreeItem<StackItem> findBaseOnIndex(int index) {
		ObservableList<TreeItem<StackItem>> list = stack.getRoot().getChildren();
		for (TreeItem<StackItem> item : list) {
			if (item.getValue().getIndex() == index) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Get list of items on given level
	 * 
	 * @return list of StackItems on given level
	 */
	private ObservableList<TreeItem<StackItem>> getActualList() {
		log.info("Getting actual list");
		int level = stack.getLevel();
		ObservableList<TreeItem<StackItem>> list = stack.getRoot().getChildren();
		log.info("level: " + level + ", list: " + list);
		if (level > -1) {
			list = list.get(level).getChildren();
			log.info("level: " + level + ", list: " + list);
		}
		return list;
	}

	/**
	 * Set program counter to new instruction if instruction is null, set to -1
	 * 
	 * @param future
	 *            Instruction which will come
	 */
	private void trySetPC(Instruction future) {
		log.info("Setting Program Counter for instruction: " + future);
		if (future == null) {
			stack.setProgramCounter(-1);
		} else {
			stack.setProgramCounter(future.getIndex());
		}
	}

	/**
	 * Set top to new instruction if list is empty, set to -1, -1
	 * 
	 * @param stackItems
	 *            list of stackitems
	 */
	private void trySetTop(ObservableList<TreeItem<StackItem>> stackItems) {
		if (stackItems.isEmpty()) {
			stack.setTop(new TreeItem<StackItem>(new StackItem(-1, -1)));
		} else {
			TreeItem<StackItem> newTop = stackItems.get(stackItems.size() - 1);
			log.info("Setting new Top instruction: " + newTop);
			stack.setTop(newTop);
		}
	}

	/**
	 * Set stack to null when reset is pressed or when new file is loaded
	 */
	public void nullStack() {
		log.info("Reset stack");
		stack = new Stack();
	}

	/**
	 * Get future instruction, if null, program ends
	 * 
	 * @param index
	 *            index in instruction table
	 * @param instructions
	 *            list of instructions
	 * @return instruction on given index
	 */
	public Instruction tryGetInstruction(int index, ObservableList<Instruction> instructions) {
		log.info("Obtaining new instruction on index: " + index);
		Instruction instruction = null;
		try {
			instruction = instructions.get(index);
			log.info("Future instruction: " + instruction.toString());
		} catch (Exception e) {
			log.info("No future instruction");
			// do nothing, return null
		}
		return instruction;
	}

	/**
	 * Find base on given level Method goes through bases up until reaches given
	 * level
	 * 
	 * @param level
	 *            number of levels up
	 * @return Base item
	 */
	private TreeItem<StackItem> getBase(int level) {
		log.info("Getting new base on level: " + level);
		
		TreeItem<StackItem> newBase = stack.getBase();
		while (level > 0) {
			newBase = findBaseOnIndex(newBase.getValue().getValue());
			log.info("base: " + newBase + ", children: " + newBase.getChildren());
			level--;
		}
		return newBase;
	}

	/**
	 * Find item on given level and offset
	 * 
	 * @param level
	 *            number of levels up
	 * @param offset
	 *            offset from base
	 * @return StackItem on level and offset
	 */
	private TreeItem<StackItem> getItemOnLevel(int level, int offset) {
		log.info("Getting item on level: " + level + " and offset: " + offset);
		TreeItem<StackItem> newBase = this.getBase(level);
		TreeItem<StackItem> oldItem = newBase.getChildren().get(offset - 1);
		TreeItem<StackItem> newItem = new TreeItem<StackItem>(
				new StackItem(oldItem.getValue().getIndex(), oldItem.getValue().getValue()));
		return newItem;
	}

	/**
	 * Set item on given level and offset
	 * 
	 * @param level
	 *            number of levels up
	 * @param offset
	 *            offset from base
	 * @param item
	 *            StackItem to modify
	 */
	private void setItemOnLevel(int level, int offset, TreeItem<StackItem> item) {
		log.info("Setting item on level: " + level + " and offset: " + offset + ", item: " + item);
		TreeItem<StackItem> newBase = this.getBase(level);
		item.getValue().setIndex(newBase.getValue().getIndex() + offset);
		newBase.getChildren().set(offset - 1, item);
	}

	/**
	 * Get new index for item on end of list
	 * 
	 * @param stackItems
	 *            list of items
	 * @return stackItem last item
	 */
	private int getNewIndex(ObservableList<TreeItem<StackItem>> stackItems) {
		int newIndex = stackItems.get(stackItems.size() - 1).getValue().getIndex() + 1;
		log.info("Getting new index for new item: " + newIndex);
		return newIndex;
	}
}
