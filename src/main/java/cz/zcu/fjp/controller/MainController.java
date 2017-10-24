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
import javafx.collections.FXCollections;
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
    private Label futureBasisLabel;

    @FXML
    private Label futureTopLabel;
    
    @FXML
    private Label actualInstructionLabel;

    @FXML
    private Label actualBasisLabel;

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
		fileChooser.setTitle("Choose file to encrypt");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
		
		//setting empty labels - no info
		actualInstructionLabel.setText("-");    
    	actualBasisLabel.setText("-");
    	actualTopLabel.setText("-");    	
		futureInstructionLabel.setText("-");
		futureBasisLabel.setText("-");
		futureTopLabel.setText("-");
		
		//setting buttons to disabled - no table loaded
		btnForward.setDisable(true);
		btnReset.setDisable(true);
		
		//listener to change of selected row - needs to set the stack information
    	tableInstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruction>() {  		
    	    @Override
    	    public void changed(ObservableValue<? extends Instruction> observable, Instruction oldValue, Instruction actual) {

    	    	if (actual != null) {
	    	    	Stack actualStack = pl0.getActualStack(actual);
	    	    	Stack futureStack = pl0.getFutureStack(actual);
	    	    	
	    	    	actualInstructionLabel.setText(actualStack.getInstructionCount() + "");    
	    	    	actualBasisLabel.setText(actualStack.getBasis() + "");
	    	    	actualTopLabel.setText(actualStack.getTop() + "");
	    	    	
		    		futureInstructionLabel.setText(futureStack.getInstructionCount() + "");
		    		futureBasisLabel.setText(futureStack.getBasis() + "");
		    		futureTopLabel.setText(futureStack.getTop() + "");
    	    	} else {	    	    	
	    	    	actualInstructionLabel.setText("-");    
	    	    	actualBasisLabel.setText("-");
	    	    	actualTopLabel.setText("-");
	    	    	
		    		futureInstructionLabel.setText("-");
		    		futureBasisLabel.setText("-");
		    		futureTopLabel.setText("-");
    	    	}
    	    }
    	});
    	
    	//TODO implement treetableview
    	final ObservableList<StackItem> stackItems = FXCollections.observableArrayList(
    		new StackItem(0, 7),
    		new StackItem(1, 5),
    		new StackItem(2, 4)
    	);
    	TreeItem<StackItem> root = new TreeItem<StackItem>(new StackItem(-1, -1));
    	root.setExpanded(true);
    	stackItems.stream().forEach((stackItem) -> {
            root.getChildren().add(new TreeItem<>(stackItem));
        });
    	tableStateActual.setRoot(root);
    	tableStateFuture.setRoot(root);
    	
    }
    
    /**
     * Load file by filechooser, then send to process to table.
     * If failed, show alert, else initalize table.
     * @param event
     */
    @FXML
    void loadFile(ActionEvent event) {
    	file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
    	instructions = fr.getTable(file);
    	if (instructions != null) {
    		this.reset(event);
    		tableInstructions.setItems(instructions);
    		btnForward.setDisable(false);
    		btnReset.setDisable(false);
    	} else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error has happened during loading file");
    		alert.setContentText("Please check that file is in 'txt' format and contains correct information and only spaces between them.");
    		alert.showAndWait();
    	}
    }

    /**
     * Reset table to first instruction.
     * @param event
     */
    @FXML
    void reset(ActionEvent event) {
    	Instruction actual = instructions.get(0);
    	int newPosition = actual.getIndex();
    	tableInstructions.requestFocus();
    	tableInstructions.getSelectionModel().select(newPosition);
    	tableInstructions.getFocusModel().focus(newPosition);
    }

    /**
     * Select next row in program instruction table.
     * @param event
     */
    @FXML
    void stepForward(ActionEvent event) {
    	Instruction actual = tableInstructions.getSelectionModel().getSelectedItem();
    	Instruction future = pl0.getFutureInstruction(actual, instructions);
    	int newPosition = future.getIndex();
    	tableInstructions.requestFocus();
    	tableInstructions.getSelectionModel().select(newPosition);
    	tableInstructions.getFocusModel().focus(newPosition);
    }

}
