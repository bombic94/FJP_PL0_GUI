package cz.zcu.fjp.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import cz.zcu.fjp.model.Instruction;
import cz.zcu.fjp.model.Stack;
import cz.zcu.fjp.model.StackItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Main controll of UI. Comunicates with FXML and sets new values.
 * Defines methods reacting to events and actions.
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label futureInstructionLabel;

    @FXML
    private Label futureBaseLabel;

    @FXML
    private Label futureTopLabel;
    
    @FXML
    private Label actualInstructionLabel;

    @FXML
    private Label actualBaseLabel;

    @FXML
    private Label actualTopLabel;

    @FXML
    private TreeTableView<StackItem> tableStateActual;

    @FXML
    private TreeTableColumn<StackItem, SimpleIntegerProperty> col1Index;

    @FXML
    private TreeTableColumn<StackItem, SimpleIntegerProperty> col1Value;

    @FXML
    private TreeTableView<StackItem> tableStateFuture;

    @FXML
    private TreeTableColumn<StackItem, SimpleIntegerProperty> col2Index;

    @FXML
    private TreeTableColumn<StackItem, SimpleIntegerProperty> col2Value;

    @FXML
    private TableView<Instruction> tableInstructions;

    @FXML
    private TableColumn<Instruction, SimpleIntegerProperty> columnIndex;

    @FXML
    private TableColumn<Instruction, SimpleStringProperty> columnInstruction;

    @FXML
    private TableColumn<Instruction, SimpleIntegerProperty> columnLevel;

    @FXML
    private TableColumn<Instruction, SimpleIntegerProperty> columnOperand;

    @FXML
    private TableColumn<Instruction, SimpleStringProperty> columnInfo;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnLoad;
    
    
    private File file;
	private FileChooser fileChooser;
    private PL0Debugger pl0 = PL0Debugger.getInstance();
    private FileReader fr = FileReader.getInstance();
    private Stack actualStack;
    private Stack futureStack;

    ObservableList<Instruction> instructions;
    
    /**
     * Initialize GUI. Set default values to lables, init filechooser,
     * add listener to changed row in table, and init TreeTableView
     * representing Stack information
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	//setting new filechooser
    	fileChooser = new FileChooser();
		fileChooser.setTitle("Choose file to encrypt");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
		
		resetStackView();	
		
		//setting buttons to disabled - no table loaded
		btnForward.setDisable(true);
		btnReset.setDisable(true);
		
		//listener to change of selected row - needs to set the stack information
    	tableInstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruction>() {  		
    	    @Override
    	    public void changed(ObservableValue<? extends Instruction> observable, Instruction oldValue, Instruction actual) {
    	    	if (actual != null) {    	    		
	    	    	actualStack = pl0.getActualStack(actual);
	    	    	futureStack = pl0.getFutureStack(actual);
	    	    	System.out.println(actualStack.toString());
	    	    	actualInstructionLabel.setText(actualStack.getProgramCounter() + "");    
	    	    	actualBaseLabel.setText(actualStack.getBase() + "");
	    	    	actualTopLabel.setText(actualStack.getTop() + "");
	    	    	
		    		futureInstructionLabel.setText(futureStack.getProgramCounter() + "");
		    		futureBaseLabel.setText(futureStack.getBase() + "");
		    		futureTopLabel.setText(futureStack.getTop() + "");
		    		
		    		tableStateActual.setRoot(actualStack.getRoot());
	       	    	
		    	    tableStateFuture.setRoot(futureStack.getRoot());
    	    	} else {	    	    	
	    	    	resetStackView();
    	    	}	    
    	    }
    	}); 	
    }

	/**
     * Load file by filechooser, then send to process to table.
     * If failed, show alert, else initalize table.
     * @param event
     */
    @FXML
    void loadFile(ActionEvent event) {
    	file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
    	if (file != null) {
	    	instructions = fr.getTable(file);
	    	
			tableInstructions.getItems().clear();
	    	
	    	if (instructions != null) {
	    		this.reset(event);
	    		tableInstructions.setItems(instructions);
	    		btnForward.setDisable(false);
	    		btnReset.setDisable(false);
	    	} else {
	    		resetStackView();
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("Error has happened during loading file");
	    		alert.setContentText("Please check that file is in 'txt' format and contains correct information and only spaces between them.");
	    		alert.showAndWait(); 		
	    	}
    	}
    }

    /**
     * Reset table to first instruction.
     * @param event
     */
    @FXML
    void reset(ActionEvent event) {
    	resetStackView();
    	tableInstructions.setItems(instructions);
    	
    	tableInstructions.requestFocus();
    	tableInstructions.getSelectionModel().clearSelection();
    	
    	btnForward.setDisable(false);
    }

    /**
     * Select next row in program instruction table.
     * @param event
     */
    @FXML
    void stepForward(ActionEvent event) {
    	Instruction actual = tableInstructions.getSelectionModel().getSelectedItem();
    	Instruction future = pl0.getFutureInstruction(actual, instructions);
    	if (future != null) {
	    	int newPosition = future.getIndex();
	    	tableInstructions.requestFocus();
	    	tableInstructions.getSelectionModel().select(newPosition);
	    	tableInstructions.getFocusModel().focus(newPosition);
    	} else  {
    		btnForward.setDisable(true);
    	}
    }

    private void resetStackView() {
		actualInstructionLabel.setText("-");    
    	actualBaseLabel.setText("-");
    	actualTopLabel.setText("-");    	
		futureInstructionLabel.setText("-");
		futureBaseLabel.setText("-");
		futureTopLabel.setText("-");
		tableStateActual.setRoot(null);
		tableStateFuture.setRoot(null);

		pl0.nullStacks();
	}
}
