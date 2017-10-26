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
		fileChooser.setTitle("Choose text file with p-code");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
		
		resetStackView();	
		
		//setting buttons to disabled - no table loaded
		btnForward.setDisable(true);
		btnReset.setDisable(true);
		
		//table is controlled by button, not mouse clicks
		tableInstructions.setMouseTransparent(true);	
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
        Stack futureStack = null;
    	Instruction past = tableInstructions.getSelectionModel().getSelectedItem();
    	Instruction now = pl0.getFutureInstruction(past, instructions);
    	Instruction next = pl0.getFutureInstruction(now, instructions);

		actualInstructionLabel.setText(futureInstructionLabel.getText()); 
    	actualBaseLabel.setText(futureBaseLabel.getText());
    	actualTopLabel.setText(futureTopLabel.getText());		
    	tableStateActual.setRoot(tableStateFuture.getRoot());
    	
    	if (now == null) {
    		btnForward.setDisable(true);
    		
    		tableStateFuture.setRoot(null);
    	} else {
    		futureStack = pl0.getFutureStack(now);
    		
	    	int newPosition = now.getIndex();
	    	tableInstructions.requestFocus();
	    	tableInstructions.getSelectionModel().select(newPosition);
	    	tableInstructions.getFocusModel().focus(newPosition);
    	}
    	
    	if (futureStack == null) {
    		futureInstructionLabel.setText("-");
    		futureBaseLabel.setText("-");
    		futureTopLabel.setText("-");  		   		
    	} else { 		
    		futureInstructionLabel.setText(futureStack.getProgramCounter() + "");
    		futureBaseLabel.setText(futureStack.getBase() + "");
    		futureTopLabel.setText(futureStack.getTop() + "");
    		
    		tableStateFuture.setRoot(futureStack.getRoot());
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
