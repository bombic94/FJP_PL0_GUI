package cz.zcu.fjp.controller;

import cz.zcu.fjp.model.Instruction;
import cz.zcu.fjp.model.Stack;
import cz.zcu.fjp.model.StackItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Singleton PL0Debugger takes care about main control of PL0 instructions
 * Sets instruction and stack information
 */
public class PL0Debugger {

	private static PL0Debugger instance = null;
	
	private Stack stackActual = new Stack();
	private Stack stackFuture = new Stack();
	
	protected PL0Debugger() {
		
	}
	
	public static PL0Debugger getInstance() {
		if(instance == null) {
	         instance = new PL0Debugger();
	      }
	      return instance;
	}
	
	public Stack getActualStack(Instruction instruction) {
		stackActual.setBase(stackFuture.getBase());
		stackActual.setProgramCounter(stackFuture.getProgramCounter());
		stackActual.setTop(stackFuture.getTop());
		
		ObservableList<StackItem> stackItems = FXCollections.observableArrayList();		
		for (StackItem si : stackFuture.getStackItems()) {
			stackItems.add(si);
		}
		stackActual.setStackItems(stackItems);
		
		TreeItem<StackItem> root = new TreeItem<StackItem>(new StackItem(-1, -1));
		root.setExpanded(true);
		stackActual.getStackItems().stream().forEach((stackItem) -> {
            root.getChildren().add(new TreeItem<>(stackItem));
        });   	
		stackActual.setRoot(root);
		
		return stackActual;
	}
	
	public Stack getFutureStack(Instruction instruction) {
		ObservableList<StackItem> stackItems = stackFuture.getStackItems();
		switch (instruction.getInstruction()) {
			case "LIT":{
				stackItems.add(new StackItem(stackItems.size() + 1, instruction.getOperand()));
				break;
			}
			case "OPR":{
				switch (instruction.getOperand()) {
					case 0:{ //return
						//TODO implement level
						break;
					}
					case 1:{ //negate
						StackItem item = stackItems.remove(stackItems.size() - 1);
						int returnValue = item.getValue() * (-1);
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 2:{ //sum
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = item1.getValue() + item2.getValue();
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 3:{ //substract
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = item1.getValue() - item2.getValue();
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 4:{ //multiply
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = item1.getValue() * item2.getValue();
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 5:{ //divide
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = item1.getValue() / item2.getValue();
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 6:{ //modulo
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = item1.getValue() % item2.getValue();
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 7:{//odd?
						StackItem item = stackItems.remove(stackItems.size() - 1);
						int returnValue = item.getValue() % 2;
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 8:{ //equal
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = (item1.getValue() == item2.getValue()) ? 1 : 0;
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 9:{ //not equal
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = (item1.getValue() != item2.getValue()) ? 1 : 0;
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 10:{ //less than
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = (item1.getValue() < item2.getValue()) ? 1 : 0;
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 11:{ //greater than or equal
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = (item1.getValue() >= item2.getValue()) ? 1 : 0;
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 12:{ //greater than
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = (item1.getValue() > item2.getValue()) ? 1 : 0;
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
					case 13:{ //less than or equal
						StackItem item2 = stackItems.remove(stackItems.size() - 1);
						StackItem item1 = stackItems.remove(stackItems.size() - 1);
						int returnValue = (item1.getValue() <= item2.getValue()) ? 1 : 0;
						stackItems.add(new StackItem(stackItems.size() + 1, returnValue));
						break;
					}
				}
				break;
			}
			case "LOD":{
				//TODO implement level
				StackItem item = stackItems.get(instruction.getOperand());
				item.setIndex(stackItems.size() + 1);
				stackItems.add(item);
				break;
			}
			case "STO":{
				//TODO implement level
				StackItem item = stackItems.remove(stackItems.size() - 1);
				item.setIndex(instruction.getOperand() + 1);
				stackItems.set(instruction.getOperand(), item);
				break;
			}
			case "CAL":{
				//TODO implement level
				TreeItem<StackItem> level = new TreeItem<StackItem>(stackItems.get(stackItems.size() - 1));
				level.setExpanded(true);
				stackItems.set(stackItems.size() - 1, level.getValue());
				break;
			}
			case "INT":{
				int size = stackItems.size();
				for (int i = size; i < size + instruction.getOperand(); i++) {
					stackItems.add(new StackItem(i + 1, 0));
				}			
				break;
			}
			case "JMP":{		
				break;	
			}
			case "JMC":{
				break;
			}
			case "RET":{
				//TODO implement level
				//stackFuture.setBase(0);
				//stackItems = FXCollections.observableArrayList();
				break;
			}		
		}
		stackFuture.setStackItems(stackItems);
		stackFuture.setProgramCounter(instruction.getIndex());
		if (stackItems.size() > 0) {
			stackFuture.setTop(stackItems.get(stackItems.size() - 1).getValue());
		}
		
		TreeItem<StackItem> root = new TreeItem<StackItem>(new StackItem(-1, -1));
		root.setExpanded(true);
		stackFuture.getStackItems().stream().forEach((stackItem) -> {
            root.getChildren().add(new TreeItem<>(stackItem));
        });   	
		stackFuture.setRoot(root);
		
		return stackFuture;
	}
	
	public Instruction getFutureInstruction(Instruction actual, ObservableList<Instruction> instructions) {
			
		Instruction future = null;
		if (actual == null) {
			future = instructions.get(0);
		} else {
			System.out.println("Actual instruction: " + actual.toString());
			switch (actual.getInstruction()) {
				case "LIT":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					break;
				}
				case "OPR":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					break;
				}
				case "LOD":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					break;
				}
				case "STO":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					break;
				}
				case "CAL":{
					future = tryGetInstruction(actual.getOperand(), instructions);
					break;
				}
				case "INT":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					break;
				}
				case "JMP":{
					int futureIdx = actual.getOperand();
					future = tryGetInstruction(futureIdx, instructions);			
					break;	
				}
				case "JMC":{
					int futureIdx = actual.getOperand();
					if (stackActual.getTop() == 0) {
						future = tryGetInstruction(futureIdx, instructions);
					} else {
						future = tryGetInstruction(actual.getIndex() + 1, instructions);						
					}
					break;
				}
				case "RET":{
					//TODO implement after stack implementation
					future = tryGetInstruction(0, instructions);
					break;
				}		
			}
		}
		
		return future;
		
	}

	public void nullStacks() {
		stackActual = new Stack();
		stackFuture = new Stack();
	}
	
	public Instruction tryGetInstruction(int index, ObservableList<Instruction> instructions) {
		Instruction instruction = null;
		try {
			instruction = instructions.get(index);
			System.out.println("Future instruction: " + instruction.toString());
		} catch (Exception e) {
			// do nothing, return null
		}
		return instruction;
	}
	
}
