package cz.zcu.fjp.controller;

import cz.zcu.fjp.model.Instruction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class MainController {

    @FXML
    private TitledPane titledPane;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TreeTableView<?> tableStateActual;

    @FXML
    private TreeTableColumn<?, ?> col1Index;

    @FXML
    private TreeTableColumn<?, ?> col1Value;

    @FXML
    private TreeTableView<?> tableStateFuture;

    @FXML
    private TreeTableColumn<?, ?> col2Index;

    @FXML
    private TreeTableColumn<?, ?> col2Value;

    @FXML
    private TableView<Instruction> tableInstructions;

    @FXML
    private TableColumn<?, ?> columnIndex;

    @FXML
    private TableColumn<?, ?> columnInstruction;

    @FXML
    private TableColumn<?, ?> columnLevel;

    @FXML
    private TableColumn<?, ?> columnOperand;

    @FXML
    private TableColumn<?, ?> columnInfo;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLoad;

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
