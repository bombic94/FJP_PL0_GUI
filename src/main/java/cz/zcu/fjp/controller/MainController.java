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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Main control of UI. Communicates with FXML and sets new values. Defines
 * methods reacting to events and actions.
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
	
	@FXML
	public TextField textREA;
	
	@FXML
	public TextField textWRI;

	private File file;
	private FileChooser fileChooser;
	private PL0Debugger pl0 = PL0Debugger.getInstance();
	private FileReader fr = FileReader.getInstance();
	private Instruction future;
	private TreeItem<StackItem> rootCopy = null;

	ObservableList<Instruction> instructions;

	/**
	 * Initialize GUI. Set default values to lables, init filechooser, add listener
	 * to changed row in table, and init TreeTableView representing Stack
	 * information
	 * 
	 * @param arg0 URL
	 * 
	 * @param arg1 ResourceBundle
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// setting new filechooser
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choose text file with p-code");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));

		resetStackView();

		// setting buttons to disabled - no table loaded
		btnForward.setDisable(true);
		btnReset.setDisable(true);

		// table is controlled by button, not mouse clicks
		tableInstructions.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });
		
		pl0.setController(this);
	}

	/**
	 * Load file by filechooser, then send to process to table. If failed, show
	 * alert, else initalize table.
	 * 
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

				future = pl0.getFutureInstruction(null, instructions);
			} else {
				resetStackView();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error has happened during loading file");
				alert.setContentText(
						"Please check that file is in 'txt' format and contains correct information and only spaces between them.");
				alert.showAndWait();
			}
		}
	}

	/**
	 * Reset table to first instruction.
	 * 
	 * @param event
	 */
	@FXML
	void reset(ActionEvent event) {
		resetStackView();

		for (Instruction i : instructions) {
			i.setDebug("");
		}
		tableInstructions.setItems(instructions);
		tableInstructions.getColumns().get(0).setVisible(false);
		tableInstructions.getColumns().get(0).setVisible(true);
		
		tableInstructions.requestFocus();
		tableInstructions.getSelectionModel().clearSelection();

		future = pl0.getFutureInstruction(null, instructions);

		btnForward.setDisable(false);
	}

	/**
	 * Select next row in program instruction table.
	 * 
	 * @param event
	 */
	@FXML
	void stepForward(ActionEvent event) {
		Stack futureStack = null;
		Instruction now = future;

		actualInstructionLabel.setText(futureInstructionLabel.getText());
		actualBaseLabel.setText(futureBaseLabel.getText());
		actualTopLabel.setText(futureTopLabel.getText());
		tableStateActual.setRoot(rootCopy);

		try {
			future = pl0.getFutureInstruction(now, instructions);
			if (now == null) {
				btnForward.setDisable(true);

				tableStateFuture.setRoot(null);
			} else {
				futureStack = pl0.getFutureStack();

				int newPosition = now.getIndex();
				tableInstructions.getColumns().get(0).setVisible(false);
				tableInstructions.getColumns().get(0).setVisible(true);
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
				futureBaseLabel.setText("[" + futureStack.getBase().getValue().getIndex() + ", "
						+ futureStack.getBase().getValue().getValue() + "]");
				futureTopLabel.setText("[" + futureStack.getTop().getValue().getIndex() + ", "
						+ futureStack.getTop().getValue().getValue() + "]");

				rootCopy = copy(futureStack.getRoot());
				tableStateFuture.setRoot(rootCopy);
			}
		} catch (Exception e){
			reset(event);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error has happened during execution of instructions");
			alert.setContentText(
					"Please check that instruction does not contain reference to non-existing location");
			alert.showAndWait();
		}
	}

	/**
	 * Create deep copy of stack by copying root TreeItem
	 * 
	 * @param root Parent TreeItem
	 * 
	 * @return Copy of Parent TreeItem
	 */
	private TreeItem<StackItem> copy(TreeItem<StackItem> root) {
		TreeItem<StackItem> copy = new TreeItem<StackItem>(root.getValue());
		for (TreeItem<StackItem> child : root.getChildren()) {
			copy.getChildren().add(copy(child));
		}
		copy.setExpanded(true);
		return copy;
	}

	/**
	 * Reset view to default settings
	 */
	private void resetStackView() {

		actualInstructionLabel.setText("-");
		actualBaseLabel.setText("-");
		actualTopLabel.setText("-");
		futureInstructionLabel.setText("-");
		futureBaseLabel.setText("-");
		futureTopLabel.setText("-");
		tableStateActual.setRoot(null);
		tableStateFuture.setRoot(null);
		textREA.setText("");
		textWRI.setText("");

		rootCopy = null;
		pl0.nullStack();
	}
}
