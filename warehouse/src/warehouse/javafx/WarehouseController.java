package warehouse.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
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
		ColumnConstraints col = new ColumnConstraints();
		RowConstraints row = new RowConstraints();
		col.setMaxWidth(50);
		row.setMaxHeight(50);
		simulationBoard.getRowConstraints().add(row);
		simulationBoard.getColumnConstraints().add(col);
		
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

}
