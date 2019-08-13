package warehouse.javafx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class newWarehouseController {

	@FXML
	private Button closeButton;
	@FXML
	private TextField columnInput;
	@FXML
	private TextField rowInput;
	@FXML
	private TextField storageInput;
	@FXML
	private TextField packingInput;
	@FXML
	private TextField robotInput;
	@FXML
	private TextField capacityInput;
	@FXML
	private TextField chargeInput;

	public newWarehouseController() {
	}

	@FXML
	public void initialize() {

	}

	@FXML
	public void closeButtonAction() {
		// get the handle to the stage
		Stage stage = (Stage) closeButton.getScene().getWindow();
		// close window
		stage.close();
	}

	@FXML
	public void WarehouseArrangement() {
		
		columnInput.setText(columnInput.getText().replaceAll("[^a-zA-Z0-9]", ""));
		rowInput.setText(rowInput.getText().replaceAll("[^a-zA-Z0-9]", ""));
		storageInput.setText(storageInput.getText().replaceAll("[^a-zA-Z0-9]", ""));
		packingInput.setText(packingInput.getText().replaceAll("[^a-zA-Z0-9]", ""));
		robotInput.setText(robotInput.getText().replaceAll("[^a-zA-Z0-9]", ""));
		robotInput.setText(robotInput.getText().replaceAll("[^a-zA-Z0-9]", ""));
		chargeInput.setText(chargeInput.getText().replaceAll("[^a-zA-Z0-9]", ""));

		int cInput = 3;
		int rInput = 3;
		int sInput = 1;
		int pInput = 1;
		int roInput = 1;
		int caInput = 20;
		int chInput = 1;
		
		
		try {
			if (this.columnInput.getText().trim().length()!=0) {
				cInput = Integer.parseInt(this.columnInput.getText());
			}
			if (this.rowInput.getText().trim().length()!=0) {
				rInput = Integer.parseInt(this.rowInput.getText());
			}
			if (this.storageInput.getText().trim().length()!=0) {
				sInput = Integer.parseInt(this.storageInput.getText());
			}
			if (this.packingInput.getText().trim().length()!=0) {
				pInput = Integer.parseInt(this.packingInput.getText());
			}
			if (this.robotInput.getText().trim().length()!=0) {
				roInput = Integer.parseInt(this.robotInput.getText());
			}
			if (this.capacityInput.getText().trim().length()!=0) {
				caInput = Integer.parseInt(this.capacityInput.getText());
			}
			if (this.chargeInput.getText().trim().length()!=0) {
				chInput = Integer.parseInt(this.chargeInput.getText());
			}

		} catch (NumberFormatException nfe) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle ("Incorrect Input");
				alert.setHeaderText("You may only enter numbers.");
				alert.showAndWait();

			return;
		}
		
		int[] propertiesArray = new int[7];
		propertiesArray[0] = cInput;
		propertiesArray[1] = rInput;
		propertiesArray[2] = sInput;
		propertiesArray[3] = pInput;
		propertiesArray[4] = roInput;
		propertiesArray[5] = caInput;
		propertiesArray[6] = chInput;

		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("NewWarehouseArrangement.fxml"));
		loader.setController(new newWarehouseArrangement(cInput, rInput));

		try {
			final Parent parent = (Parent) loader.load();
			final Stage newWarehouse = new Stage();
			Scene scene = new Scene(parent, 600, 400);
			newWarehouse.setScene(scene);
			newWarehouse.setResizable(false);
			newWarehouse.setTitle("New Warehouse");
			newWarehouse.show();
			closeButtonAction();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	/*
	private GridPane createGrid(int col, int row) {
		GridPane boardGrid = new GridPane();
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				Pane tile = new Pane();
				tile.setId("tile_"+i+"_"+j);
				boardGrid.add(tile, j, i);
			}	
		}
		return boardGrid;
	}
	*/

}
