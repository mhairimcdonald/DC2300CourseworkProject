package warehouse.javafx;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import warehouse.io.FileLoader;
import warehouse.model.Warehouse;

public class WarehouseController {
	
	 @FXML private Button startButton;
	 @FXML private Button pauseButton;
	 @FXML private Button resetButton;
	 @FXML private Label statusUpdate;
	 @FXML private GridPane simulationBoard;
	
	public WarehouseController(Warehouse w) {
		
	}
	
	@FXML 
	public void initialize() {

		
	}
	
	@FXML 
	public void startSimulation() {
		startButton.setDisable(true);
		startButton.setDefaultButton(false);
		pauseButton.setDisable(false);
		pauseButton.setDefaultButton(true);
		resetButton.setDisable(false);
		statusUpdate.setText("Status: Running");
		
	}
	
	@FXML 
	public void pauseSimulation() {
		pauseButton.setDisable(true);
		pauseButton.setDefaultButton(false);
		startButton.setDisable(false);
		startButton.setDefaultButton(true);
		statusUpdate.setText("Status: Paused...");
	}
	
	@FXML 
	public void resetSimulation() {
		startButton.setDisable(false);
		startButton.setDefaultButton(true);
		pauseButton.setDisable(true);
		pauseButton.setDefaultButton(false);
		resetButton.setDisable(true);
		statusUpdate.setText("Status: Waiting");
		
	}
	
	@FXML 
	public void newWarehouse() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("NewWarehouseGrid.fxml"));
		loader.setController(new newWarehouseController());
		
		try {
			final Parent parent = (Parent) loader.load();
			final Stage newWarehouse = new Stage();
			newWarehouse.setScene(new Scene(parent, 320, 340));
			newWarehouse.setResizable(false);
			newWarehouse.setTitle("New Warehouse");
			newWarehouse.showAndWait();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	public void menuOpenAction() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			FileLoader fl = new FileLoader();
			fl.parseFile(file);
		}
	}
}
