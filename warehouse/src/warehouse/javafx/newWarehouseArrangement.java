package warehouse.javafx;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import warehouse.io.ConfigFile;
import warehouse.io.configActors.*;
import warehouse.model.Location;

public class newWarehouseArrangement {

	@FXML
	private Button closeButton;
	@FXML
	private VBox robotsVBox;
	@FXML
	private VBox stationsVBox;
	@FXML
	private VBox shelvesVBox;
	@FXML
	private Label rowLabel;
	@FXML
	private Label columnLabel;
	private final int numCols;
	private final int numRows;
	private int[] properties;
	/*
	 * First int = Whether it's Robot(0)/Shelf(1)/Station(2)
	 * Second int = WHICH Actor this should denote for
	 * Third int = Whether Row(0) or Column(1)
	 */
	private TextField[][][] textInput;
	/*
	 * Where: 
	 * 0 = NoOfColumns 
	 * 1 = NoOfRows 
	 * 2 = TotalBatteryCapacity 
	 * 3 = BatteryChargeRate 
	 * 4 = NoOfRobots&ChargePads 
	 * 5 = NoOfStorageShelves 
	 * 6 = NoOfPackingStations
	 */

	public newWarehouseArrangement(int[] properties) {
		this.properties = properties;
		/*
		 * Initilise the array. 3 spaces first, because there are 3 types.
		 * Then get the maximum number from properties[4/5/6] second, 
		 * then 2 finally as there will only ever be 2 TextFields (Row/Column)
		 */
		this.textInput = new TextField[3][getMaximumActor(properties)][2];
		numCols = properties[0];
		numRows = properties[1];
	}

	@FXML
	public void initialize() {
		// Set RowLabel and ColumnLabel to display the available input values.
		rowLabel.setText("[0-"+(properties[1]-1)+"]");
		columnLabel.setText("[0-"+(properties[0]-1)+"]");
		/*
		 * Creates a HBox for each entry (Actor, Row, Col) and populates it with a label
		 * and two TextFields
		 */
		for (int i = 0; i < properties[4]; i++) {
			System.out.println("Robot "+i);
			String s = "No. Robots & Pads";
			HBox h = createEntryPoint(s, "robot", i, 0);
			robotsVBox.getChildren().add(h);
		}
		for (int i = 0; i < properties[5]; i++) {
			System.out.println("Shelf "+i);
			String s = "No. Storage Shelves";
			HBox h = createEntryPoint(s, "shelf", i, 1);
			shelvesVBox.getChildren().add(h);
		}
		for (int i = 0; i < properties[6]; i++) {
			System.out.println("station "+i);
			String s = "No. Packing Station";
			HBox h = createEntryPoint(s, "station", i, 2);
			stationsVBox.getChildren().add(h);
		}

	}

	@FXML
	public void closeButtonAction() {
		// get the handle to the stage
		Stage stage = (Stage) closeButton.getScene().getWindow();
		// close window
		stage.close();
	}
	
	public void hideWindow() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.hide();
	}

	@FXML
	public void nextButtonAction() {
		/*
		 * If input characters are only numeric
		 */
		if (!removeSpecialCharactersSetToInt()) {
			//If no numeric figures entered, throw alert and do not progress.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Incorrect Input");
			alert.setHeaderText("You must only enter numbers.\nPlease input all row and column references.");
			alert.showAndWait();
			return;
		}
		
		if (!checkInsideRowColBoundaries()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Incorrect Input");
			alert.setHeaderText("Row/Column positions for entered values cannot exceed the maximum number \n"
					+ "of Rows and Columns entered on the previous page.\n"
					+ "Please ensure Row Values:[0-"+(properties[1]-1)+"], and Column Values:[0-"+(properties[0]-1)+"]");
			alert.showAndWait();
			return;
		}
		if (!checkActorPosOverlap()) {
			//If there are any robots/stations/shelves overlapping, error.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Incorrect Input");
			alert.setHeaderText("Robots, Storage Shelves and Packing Stations cannot initially share a single cell.\n"
					+ "Please ensure there are no cells shared in your input.");
			alert.showAndWait();
			return;
		}
		
		//Create config file and set variables
		ConfigFile cf = new ConfigFile();
		cf.setWidth(properties[0]);
		cf.setHeight(properties[1]);
		cf.setCapacity(properties[2]);
		cf.setChargeSpeed(properties[3]);
		for (int i = 0; i < properties[4]; i++) {
			/*
			 * For each robot, create ConfigRobot and assign
			 * uID ("r"+i), chargePoduID ("c"+i), row 
			 * (textInput[0][i][0]), and column (textInput[0][i][1].
			 */
			ConfigRobot c = new ConfigRobot();
			c.setuID("r"+i);
			c.setChargingPoduID("c"+i);
			c.setRow(Integer.parseInt(textInput[0][i][0].getText()));
			c.setCol(Integer.parseInt(textInput[0][i][1].getText()));
			cf.addRobot(c);
		}
		for (int i = 0; i < properties[5]; i++) {
			/*
			 * For each Shelf, create ConfigStorageShelf and assign
			 * uID ("s"+i), row (textInput[1][i][0]), and 
			 * column (textInput[1][i][2].
			 */
			ConfigStorageShelf c = new ConfigStorageShelf();
			c.setuID("ss"+i);
			c.setRow(Integer.parseInt(textInput[1][i][0].getText()));
			c.setCol(Integer.parseInt(textInput[1][i][1].getText()));
			cf.addShelf(c);
		}
		for (int i = 0; i < properties[6]; i++) {
			/*
			 * For each Shelf, create ConfigStorageShelf and assign
			 * uID ("s"+i), row (textInput[1][i][0]), and 
			 * column (textInput[1][i][2].
			 */
			ConfigPackingStation c = new ConfigPackingStation();
			c.setuID("ps"+i);
			c.setRow(Integer.parseInt(textInput[2][i][0].getText()));
			c.setCol(Integer.parseInt(textInput[2][i][1].getText()));
			cf.addStation(c);
		}
		//ConfigFile is almost complete. Just needs Orders now.
		//Create Controller for NewWarehouseOrder FXML Window
		
		final FXMLLoader loader = new FXMLLoader();loader.setLocation(getClass().getResource("NewWarehouseOrders.fxml"));
		loader.setController(new newWarehouseOrders(cf));

		try {
			final Parent parent = (Parent) loader.load();
			final Stage newWarehouse = new Stage();
			Scene scene = new Scene(parent, 600, 400);
			newWarehouse.setScene(scene);
			newWarehouse.setResizable(false);
			newWarehouse.setTitle("Set Warehouse Orders");
			newWarehouse.initModality(Modality.APPLICATION_MODAL);
			newWarehouse.showAndWait();
			closeButtonAction();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

	

	

	public String toString() {
		// (GridPane)gridAnchor.getChildren().get
		String s = "The Grid Pane has the following:\r\n" + "Number of Columns: " + numCols + "\r\n"
				+ "Number of Rows: " + numRows + "\r\n" + "And the following panes:\r\n";
		return s;
	}

	private HBox createEntryPoint(String s, String type, int actorNumber, int actorType) {
		HBox h = new HBox();
		Label l = new Label(s);
		TextField tfrow = newTextField("Row Position");
		TextField tfcol = newTextField("Column Position");
		/*
		 * Add the textfields to the textInput array.
		 * Rows are loaded first, columns second.
		 * These textfields can then be accessed in nextButtonAction().
		 */
		textInput[actorType][actorNumber][0] = tfrow;
		textInput[actorType][actorNumber][1] = tfcol;
		
		h.getChildren().addAll(l,tfrow,tfcol);
		h.setPadding(new Insets(5));
		return h;
	}
	
	private TextField newTextField(String promptText) {
		TextField tf = new TextField();
		tf.setPrefWidth(150);
		tf.setPromptText(promptText);
		return tf;
	}
	
	/*
	 * Returns the maximum number of Actors from the
	 * properties variable as an integer.
	 */
	private int getMaximumActor(int[] input) {
		int maxValue = input[4];
		for (int i = 5; i < input.length; i++) {
			if (input[i] > maxValue) {
				maxValue = input[i];
			}
		}
		return maxValue;
	}
	
	private boolean checkActorPosOverlap() {
		/*
		 *  Make an array of all the positions each
		 *  Actor is placed at and ensure none are 
		 *  the same.
		 */
		ArrayList<Location> positions = new ArrayList<Location>();
		//For first dimension of textInput (0-2, actorType)
		for (int a = 0; a < 3; a++) {
			//For second dimension of textInput (maxNoActors)
			//4+a = 4 for Robot, 5 forShelves, 6 for Station
			for (int b = 0; b < properties[4+a]; b++) {
				//Create the Location for each input of Row/Col and add it to an array.
				int rowPos = Integer.parseInt(textInput[a][b][0].getText());
				int colPos = Integer.parseInt(textInput[a][b][1].getText());
				Location l = new Location(rowPos, colPos);
				positions.add(l);
			}
		}
		/*
		 * Loop through the array and ensure there are no
		 * results with identical
		 */
		for (int i = 0; i < positions.size();i++) {
			/*
			 * For each Location in positions, get their
			 * row/pos.
			 */
			Integer currRow = positions.get(i).getRow();
			Integer currCol = positions.get(i).getCol();
			/*
			 * Loop through all other Locations further
			 * into the array from the current one.
			 */
			for (int j = i+1; j < positions.size();j++) {
				/*
				 * Compare the other Row&Cols to see if they match.
				 * On a return of 0, they match. Any other value shows
				 * they do not match.
				 */
				if (currRow.compareTo(positions.get(j).getRow()) == 0 && currCol.compareTo(positions.get(j).getCol()) == 0) {
					//On a match, fail the check.
					return false;
				}
			}
		}
		return true;
		
	}
	
	private boolean checkInsideRowColBoundaries() {
		//For first dimension of textInput (0-2, actorType)
		for (int a = 0; a < 3; a++) {
			//For second dimension of textInput (maxNoActors)
			//4+a = 4 for Robot, 5 forShelves, 6 for Station
			for (int b = 0; b < properties[4+a]; b++) {
				//Column = properties[0] - Value = 1+
				//Row = properties[1] - Value = 1+
				int rowPos = Integer.parseInt(textInput[a][b][0].getText());
				if (rowPos >= properties[1]) {
					return false;
				}
				int colPos = Integer.parseInt(textInput[a][b][1].getText());
				if (colPos >= properties[0]) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Remove any special characters from the 
	 * tri-dimensional textInput array.
	 */
	private boolean removeSpecialCharactersSetToInt() {
		//For first dimension of textInput (0-2, actorType)
		boolean success = false;
		for (int a = 0; a < 3; a++) {
			//For second dimension of textInput (maxNoActors)
			//4+a = 4 for Robot, 5 forShelves, 6 for Station
			for (int b = 0; b < properties[4+a]; b++) {
				//For third dimension of textInput (0-1, Row or Col)
				for (int c = 0; c < 2;c++) {
					String s = textInput[a][b][c].getText();
					s = s.replaceAll("[^0-9]", "");
					try {
						Integer.parseInt(s);
					} catch (NumberFormatException e) {
						System.out.println("No numberic value entered.");
						return success;
					}
				}
			}
		}
		success = true;
		return success;
	}

}
