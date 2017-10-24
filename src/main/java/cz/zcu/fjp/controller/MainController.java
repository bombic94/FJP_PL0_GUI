package cz.zcu.fjp.controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class MainController implements Initializable {

    @FXML
    private SplitPane splitPane;
    
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
    
    private PL0Debugger pl0 = PL0Debugger.getInstance();

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	final ObservableList<Instruction> instructions = FXCollections.observableArrayList(
    		new Instruction(0, "JMP", 0, 18, "info"),
    		new Instruction(1, "JMP", 0, 11, "info"),
    		new Instruction(2, "JMP", 0, 3, "info"),
    		new Instruction(3, "INT", 0, 4, "info"),
    		new Instruction(4, "LIT", 0, 11, "info"),
    		new Instruction(5, "STO", 0, 3, "info"),
    		new Instruction(6, "LIT", 0, 22, "info"),
    		new Instruction(7, "STO", 1, 3, "info"),
    		new Instruction(8, "LIT", 0, 33, "info"),
    		new Instruction(9, "STO", 2, 3, "info"),
    		new Instruction(10, "RET", 0, 0, "info"),
    		new Instruction(11, "INT", 0, 4, "info"),
    		new Instruction(12, "LIT", 0, 10, "info"),
    		new Instruction(13, "STO", 1, 3, "info"),
    		new Instruction(14, "LIT", 0, 20, "info"),
    		new Instruction(15, "STO", 0, 3, "info"),
    		new Instruction(16, "CAL", 0, 3, "info"),
    		new Instruction(17, "RET", 0, 0, "info"),
    		new Instruction(18, "INT", 0, 4, "info"),
    		new Instruction(19, "LIT", 0, 100, "info"),
    		new Instruction(20, "STO", 0, 3, "info"),
    		new Instruction(21, "CAL", 0, 11, "info"),
    		new Instruction(22, "RET", 0, 0, "info")
    	);
    	tableInstructions.setItems(instructions);
    	tableInstructions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruction>() {
    		
    	    @Override
    	    public void changed(ObservableValue<? extends Instruction> observable, Instruction oldValue, Instruction actual) {
    	    	if (instructions.size() > actual.getIndex() + 1) {
    	    		Instruction future = instructions.get(actual.getIndex() + 1);
    	    		Stack futureStack = pl0.getFutureStack(future);
    	    		future1.setText(futureStack.getInstructionCount() + "");
    	    		future2.setText(futureStack.getBasis() + "");
    	    		future3.setText(futureStack.getTop() + "");
    	    	} else {
    	    		future1.setText("-");
    	    		future2.setText("-");
    	    		future3.setText("-");
    	    	}
    	    	Stack actualStack = pl0.getActualStack(actual);
    	    	actual1.setText(actualStack.getInstructionCount() + "");    
    	    	actual2.setText(actualStack.getBasis() + "");
    	    	actual3.setText(actualStack.getTop() + "");
    	    }
    	});
    	
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

    }

    @FXML
    void stepBack(ActionEvent event) {
    	int oldPosition = tableInstructions.getSelectionModel().getSelectedIndex();
    	int newPosition = oldPosition - 1;
    	tableInstructions.requestFocus();
    	tableInstructions.getSelectionModel().select(newPosition);
    	tableInstructions.getFocusModel().focus(newPosition);
    }

    @FXML
    void stepForward(ActionEvent event) {
    	int oldPosition = tableInstructions.getSelectionModel().getSelectedIndex();
    	int newPosition = oldPosition + 1;
    	tableInstructions.requestFocus();
    	tableInstructions.getSelectionModel().select(newPosition);
    	tableInstructions.getFocusModel().focus(newPosition);
    }

}
