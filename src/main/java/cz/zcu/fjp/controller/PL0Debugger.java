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
		ObservableList<StackItem> stackItems = stackActual.getStackItems();
		switch (instruction.getInstruction()) {
			case "LIT":{
				stackItems.add(new StackItem(stackItems.size() + 1, instruction.getOperand()));
				stackActual.setStackItems(stackItems);
				stackActual.setTop(instruction.getOperand());			
				break;
			}
			case "OPR":{
				break;
			}
			case "LOD":{
				break;
			}
			case "STO":{
				StackItem item = stackItems.remove(stackItems.size() - 1);
				item.setIndex(instruction.getOperand());
				stackItems.set(instruction.getOperand() -1, item);
			}
			case "CAL":{
				break;
			}
			case "INT":{
				for (int i = 0; i < instruction.getOperand(); i++) {
					stackItems.add(new StackItem(i + 1, 0));
				}
				stackActual.setStackItems(stackItems);
				break;
			}
			case "JMP":{		
				break;	
			}
			case "JMC":{
				break;
			}
			case "RET":{
				break;
			}		
		}

		stackActual.setProgramCounter(instruction.getIndex());
				
		TreeItem<StackItem> root = new TreeItem<StackItem>(new StackItem(-1, -1));
		root.setExpanded(true);
		stackActual.getStackItems().stream().forEach((stackItem) -> {
            root.getChildren().add(new TreeItem<>(stackItem));
        });   	
		stackActual.setRoot(root);
		
		return stackActual;
	}
	
	public Stack getFutureStack(Instruction instruction) {
		return stackActual;
	}
	
	public Instruction getFutureInstruction(Instruction actual, ObservableList<Instruction> instructions) {
			
		Instruction future = null;
		if (actual == null) {
			future = instructions.get(0);
		} else {
			System.out.println(actual.toString());
			switch (actual.getInstruction()) {
				case "LIT":{
					future = instructions.get(actual.getIndex() + 1);
					break;
				}
				case "OPR":{
					future = instructions.get(actual.getIndex() + 1);
					break;
				}
				case "LOD":{
					future = instructions.get(actual.getIndex() + 1);
					break;
				}
				case "STO":{
					future = instructions.get(actual.getIndex() + 1);
					break;
				}
				case "CAL":{
					int futureIdx = actual.getOperand();
					future = instructions.get(futureIdx);
					break;
				}
				case "INT":{
					future = instructions.get(actual.getIndex() + 1);
					break;
				}
				case "JMP":{
					int futureIdx = actual.getOperand();
					future = instructions.get(futureIdx);			
					break;	
				}
				case "JMC":{
					int futureIdx = actual.getOperand();
					if (stackActual.getTop() == 0) {
						future = instructions.get(futureIdx);
					} else {
						future = instructions.get(actual.getIndex() + 1);						
					}
					break;
				}
				case "RET":{
					//TODO implement after stack implementation
					future = instructions.get(0);
					break;
				}		
			}
		}
		System.out.println(future.toString());
		return future;
		
	}
	
}
