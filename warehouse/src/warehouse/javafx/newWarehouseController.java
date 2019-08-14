package warehouse.javafx;

import java.io.IOException;
import java.util.Properties;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import warehouse.io.ConfigFile;

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
	private ConfigFile cf;

	public newWarehouseController() {
		cf = new ConfigFile();
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
		capacityInput.setText(capacityInput.getText().replaceAll("[^a-zA-Z0-9]", ""));
		chargeInput.setText(chargeInput.getText().replaceAll("[^a-zA-Z0-9]", ""));

		int columnVar = 3;
		int rowVar = 3;
		int shelfVar = 2;
		int stationVar = 1;
		int robotVar = 1;
		int capacityVar = 20;
		int chargeVar = 1;

		try {
			columnVar = parseToInt(columnVar, columnInput.getText());
			rowVar = parseToInt(rowVar, rowInput.getText());
			shelfVar = parseToInt(shelfVar, storageInput.getText());
			stationVar = parseToInt(stationVar, packingInput.getText());
			robotVar = parseToInt(robotVar, robotInput.getText());
			capacityVar = parseToInt(capacityVar, capacityInput.getText());
			chargeVar = parseToInt(chargeVar, chargeInput.getText());

		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Incorrect Input");
			alert.setHeaderText("You may only enter numbers.");
			alert.showAndWait();
			return;
		}

		int[] properties = new int[7];
		properties[0] = columnVar;
		properties[1] = rowVar;
		properties[2] = capacityVar;
		properties[3] = chargeVar;
		properties[4] = robotVar;
		properties[5] = shelfVar;
		properties[6] = stationVar;

	final FXMLLoader loader = new FXMLLoader();loader.setLocation(getClass().getResource("NewWarehouseArrangement.fxml"));
		loader.setController(new newWarehouseArrangement(properties));

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

	private int parseToInt(int input, String s) {
		if (s.length() != 0) {
			input = Integer.parseInt(s);
			return input;
		} else {
			return input;
		}
	}

}
