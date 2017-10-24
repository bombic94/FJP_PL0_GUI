package cz.zcu.fjp.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.Collection;
import java.util.stream.Collectors;

import cz.zcu.fjp.model.Instruction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton FileReader takes care of reading data from txt file
 * and parsing them into ObservableList, which is used to fill table
 */
public class FileReader {

	private static FileReader instance = null;
	
	protected FileReader() {
		
	}
	
	public static FileReader getInstance() {
		if(instance == null) {
	         instance = new FileReader();
	      }
	      return instance;
	}

	public ObservableList<Instruction> getTable(File file) {
		Collection<Instruction> list = null;
		try {
			list = Files.readAllLines(new File(file.getAbsolutePath()).toPath())
			        .stream()
			        .map(line -> {
			            String[] details = line.split("\\s+");
			            Instruction i = new Instruction();
			            i.setIndex(Integer.parseInt(details[0]));
			            i.setInstruction(details[1]);
			            i.setLevel(Integer.parseInt(details[2]));
			            i.setOperand(Integer.parseInt(details[3]));
			            return i;
			        })
			        .collect(Collectors.toList());
			ObservableList<Instruction> tableList = FXCollections.observableArrayList(list);
			return tableList;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
}
