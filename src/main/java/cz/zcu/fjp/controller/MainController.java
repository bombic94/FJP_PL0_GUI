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

public class MainController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Label future1;

    @FXML
    private Label future2;

    @FXML
    private Label future3;
    
    @FXML
    private Label actual1;

    @FXML
    private Label actual2;

    @FXML
    private Label actual3;

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
    private Button btnBack;

    @FXML
    private Button btnLoad;
    
    
    private File file;
	private FileChooser fileChooser;
    private PL0Debugger pl0 = PL0Debugger.getInstance();
    private FileReader fr = FileReader.getInstance();

    ObservableList<Instruction> instructions;
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	fileChooser = new FileChooser();
		fileChooser.setTitle("Choose file to encrypt");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
		
    	tableInstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruction>() {
    		
    	    @Override
    	    public void changed(ObservableValue<? extends Instruction> observable, Instruction oldValue, Instruction actual) {

    	    	if (actual != null) {
	    	    	Stack actualStack = pl0.getActualStack(actual);
	    	    	Stack futureStack = pl0.getFutureStack(actual);
	    	    	
	    	    	actual1.setText(actualStack.getInstructionCount() + "");    
	    	    	actual2.setText(actualStack.getBasis() + "");
	    	    	actual3.setText(actualStack.getTop() + "");
	    	    	
		    		future1.setText(futureStack.getInstructionCount() + "");
		    		future2.setText(futureStack.getBasis() + "");
		    		future3.setText(futureStack.getTop() + "");
    	    	} else {	    	    	
	    	    	actual1.setText("-");    
	    	    	actual2.setText("-");
	    	    	actual3.setText("-");
	    	    	
		    		future1.setText("-");
		    		future2.setText("-");
		    		future3.setText("-");
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
    @FXML
    void loadFile(ActionEvent event) {
    	file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
    	instructions = fr.getTable(file);
    	if (instructions != null) {
    		this.reset(event);
    		tableInstructions.setItems(instructions);
    	} else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error has happened during loading file");
    		alert.setContentText("Please check that file is in 'txt' format and contains correct information and only spaces between them.");

    		alert.showAndWait();
    	}
    }

    @FXML
    void reset(ActionEvent event) {
    	Instruction actual = instructions.get(0);
    	int newPosition = actual.getIndex();
    	tableInstructions.requestFocus();
    	tableInstructions.getSelectionModel().select(newPosition);
    	tableInstructions.getFocusModel().focus(newPosition);
    }

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
