package cz.zcu.fjp.controller;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.fjp.model.Instruction;
import cz.zcu.fjp.model.Stack;
import cz.zcu.fjp.model.StackItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
		List<StackItem> items = new ArrayList<StackItem>();
		ObservableList<StackItem> stackItems = FXCollections.observableArrayList(items);
		stackActual.setInstructionCount(instruction.getIndex());
		stackActual.setBasis(instruction.getIndex() * 2);
		stackActual.setTop((instruction.getIndex() * 3) % 2);
		stackActual.setStackItems(stackItems);
		return stackActual;
	}
	
	public Stack getFutureStack(Instruction instruction) {
		
		stackFuture.setInstructionCount(instruction.getIndex());
		stackFuture.setBasis(instruction.getIndex() * 2);
		stackFuture.setTop((instruction.getIndex() * 3) % 2);
		return stackFuture;
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
