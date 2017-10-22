package cz.zcu.fjp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cz.zcu.fjp.model.Instruction;
import cz.zcu.fjp.model.StackItem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	final ObservableList<Instruction> instructions = FXCollections.observableArrayList(
    		new Instruction(0, "JMP", 0, 6, "info"),
    		new Instruction(1, "JMP", 0, 6, "info"),
    		new Instruction(2, "JMP", 0, 6, "info"),
    		new Instruction(3, "JMP", 0, 6, "info"),
    		new Instruction(4, "JMP", 0, 6, "info")
    	);
    	tableInstructions.setItems(instructions);
    	
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

    }

    @FXML
    void stepForward(ActionEvent event) {

    }

}
