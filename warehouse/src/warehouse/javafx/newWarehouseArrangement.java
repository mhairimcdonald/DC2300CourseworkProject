package warehouse.javafx;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import warehouse.io.ConfigFile;

public class newWarehouseArrangement {

	@FXML
	private Button closeButton;
	@FXML
	private VBox robotsVBox;
	@FXML
	private VBox stationsVBox;
	@FXML
	private VBox shelvesVBox;
	private final int numCols;
	private final int numRows;
	private int[] properties;
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
		numCols = properties[0];
		numRows = properties[1];
	}

	@FXML
	public void initialize() {
		/*
		 * Creates a HBox for each entry (Actor, Row, Col) and populates it with a label
		 * and two TextFields
		 */
		for (int i = 0; i < properties[4]; i++) {
			System.out.println("Robot "+i);
			String s = "No. Robots & Pads";
			HBox h = createEntryPoint(s, "robot", i);
			robotsVBox.getChildren().add(h);
		}
		for (int i = 0; i < properties[5]; i++) {
			System.out.println("Shelf "+i);
			String s = "No. Storage Shelves";
			HBox h = createEntryPoint(s, "shelf", i);
			shelvesVBox.getChildren().add(h);
		}
		for (int i = 0; i < properties[6]; i++) {
			System.out.println("station "+i);
			String s = "No. Packing Station";
			HBox h = createEntryPoint(s, "station", i);
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

	@FXML
	public void selectTile(Event e) {
	}

	public String toString() {
		// (GridPane)gridAnchor.getChildren().get
		String s = "The Grid Pane has the following:\r\n" + "Number of Columns: " + numCols + "\r\n"
				+ "Number of Rows: " + numRows + "\r\n" + "And the following panes:\r\n";
		return s;
	}

	private HBox createEntryPoint(String s, String type, int number) {
		HBox h = new HBox();
		Label l = new Label();
		l.setText(s);
		TextField tfrow = new TextField();
		tfrow.setPrefWidth(150);
		tfrow.setPromptText("Row Position");
		TextField tfcol = new TextField();
		tfcol.setPrefWidth(150);
		tfcol.setPromptText("Column Position");
		h.getChildren().addAll(l,tfrow,tfcol);
		h.setPadding(new Insets(5));
		return h;
	}

}
