package cz.zcu.fjp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller implements Initializable {
	int numberOfItems;
	String message;
	String finalMsg;
	ArrayList<BigInteger> cipher;
	private File file;
	
	private FileChooser fileChooser;
	@FXML
	private AnchorPane anchor;
	@FXML
	private Button compute;	
	@FXML
	private Button clear;
	@FXML
	private TextArea input;
	@FXML
	private TextArea output;
	@FXML
	private CheckBox fileCheck;
	@FXML
	private TextField outputName;	
	@FXML
	private Button uploadButton;
	@FXML
	private TextField inputName;
	
	
	/**
	 * When Clear button is clicked, all elements are set back to default
	 * Clear all text field and text areas
	 * Set spinner to default value
	 * @param event
	 */
	@FXML
	private void handleClearButtonAction(ActionEvent event){
		input.setText("");
		output.setText("");
		outputName.setText("");
		inputName.setText("");
		fileCheck.setSelected(false);
	}
	
	@FXML
	private void handleUploadButtonAction(ActionEvent event){
		file = fileChooser.showOpenDialog(anchor.getScene().getWindow());
		inputName.setText(file.getAbsolutePath());
	}
	
	/**
	 * When Compute button is clicked, values from spinner and textfield are received
	 * and passed to variables. Next, generating of keys, decryption and encryption is launched
	 * @param event
	 */
	@FXML
	private void handleComputeButtonAction(ActionEvent event){
		input.setText("");
		output.setText("");
		
		PrintWriter out = null;
		String outName = "";
		if (fileCheck.isSelected()){
			if (outputName.getText().equals("")){
				outName = "out.txt";
			}
			else{
				outName = outputName.getText() + ".txt";
			}
				
			byte[] encoded = null;
			try {
				encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
				out = new PrintWriter(file.getParent() + File.separator + outName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			message = new String(encoded, Charset.defaultCharset());
			
		} else { 
			message = input.getText();
		}
		
		if (fileCheck.isSelected()){
			out.print(cipher.toString());
			out.close();
		}
		
	}
	
	@FXML
	private void enableFile(ActionEvent event){
		if (fileCheck.isSelected()){
			outputName.setDisable(false);
			inputName.setDisable(false);
			uploadButton.setDisable(false);
		}
		else{
			outputName.setDisable(true);
			inputName.setDisable(true);
			uploadButton.setDisable(true);
		}
	}
	
	/** 
	 * On start set default value of spinner
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fileCheck.setSelected(false);
		outputName.setDisable(true);
		inputName.setDisable(true);
		uploadButton.setDisable(true);
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choose file to encrypt");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
	}
	
}
