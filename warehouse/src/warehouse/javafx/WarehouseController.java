package warehouse.javafx;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import warehouse.io.ConfigFile;
import warehouse.io.FileLoader;
import warehouse.io.WriteToFile;
import warehouse.model.Warehouse;

public class WarehouseController {

	@FXML
	private Button startButton;
	@FXML
	private Button pauseButton;
	@FXML
	private Button resetButton;
	@FXML
	private Label statusUpdate;
	@FXML
	private AnchorPane gridBoardAnchor;
	@FXML
	private MenuItem menuQuit;
	@FXML
	private GridPane simulationBoard;
	private ConfigFile cf;

	public WarehouseController(Warehouse w) {
		cf = new ConfigFile();
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
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SIM files (*.sim)", "*.sim");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			FileLoader fl = new FileLoader();
			cf = fl.parseFile(file);
			int cols = cf.getWidth();
			int rows = cf.getHeight();
			createGridBoard(cols, rows);
		}
	}

	@FXML
	public void menuSaveAsAction() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		// Set dialog menu title
		fileChooser.setTitle("Save Current Resource File");
		// Set file extension filter to txt
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SIM files (*.sim)", "*.sim");
		fileChooser.getExtensionFilters().add(extFilter);
		// Open Save Window
		File f = fileChooser.showSaveDialog(stage);
		if (f != null) {
			WriteToFile wf = new WriteToFile();
			try {
				wf.writeFile(f, cf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void menuQuitAction() {
		// Get the current window.
		Stage stage = (Stage) gridBoardAnchor.getScene().getWindow();
		// close window
		stage.close();
	}

	public void saveFile(File f) {

	}

	public void createGridBoard(int cols, int rows) {
		int numCols = 3;
		int numRows = 3;
		// If values returned are greater than zero, set col and rows
		if (cols != 0) {
			numCols = cols;
		}
		if (rows != 0) {
			numRows = rows;
		}
		GridPane gp = new GridPane();
		// Make the cells obvious
		gp.setGridLinesVisible(true);

		ColumnConstraints colConst = new ColumnConstraints();
		// Set cell size to take up an equal amount of space
		colConst.setPercentWidth(100.0 / numCols);
		RowConstraints rowConst = new RowConstraints();
		rowConst.setPercentHeight(100.0 / numRows);
		
		//Create the ActorShape which is used to add Actor's visual representations onto the gameBoard.
		ActorShapes creator = new ActorShapes();
		
		// Add a pane to each cell.
		for (int i = 0; i < numCols; i++) {
			//Set the number of columns to include in the grid
			gp.getColumnConstraints().add(colConst);
			gp.getRowConstraints().add(rowConst);
			
			for (int j = 0; j < numRows; j++) {
				//Create the pane to be added to this cell within the gridpane
				AnchorPane p = new AnchorPane();
				//Set the pane's ID to equal it's position
				p.setId("anchorPane_" + i + "_" + j);
				//Create the StackPane used for an Actor object
				StackPane sp = new StackPane();
				StackPane sp1 = new StackPane();
				sp.setId("c001");
				sp1.setId("r001");
				//Create the visual representation of the object.
				sp = creator.createChargePod("c001");
				sp1 = creator.createRobot("r001");
				anchorToEdges(sp);
				anchorToEdges(sp1);
				p.getChildren().add(sp);
				p.getChildren().add(sp1);
				gp.add(p, i, j);
			}
		}
		creator = null;

		// Set the grid to take up the entire anchor size
		AnchorPane.setBottomAnchor(gp, 0.0);
		AnchorPane.setTopAnchor(gp, 0.0);
		AnchorPane.setLeftAnchor(gp, 0.0);
		AnchorPane.setRightAnchor(gp, 0.0);

		if (simulationBoard == null) {
			// If there is currently no board, add this one
			simulationBoard = gp;
			// Add grid to the Anchor
			gridBoardAnchor.getChildren().addAll(gp);
		} else {
			// If there is currently a board, remove it then add this one.
			gridBoardAnchor.getChildren().remove(simulationBoard);
			simulationBoard = gp;
			// Add grid to the Anchor
			gridBoardAnchor.getChildren().addAll(gp);
		}
	}
	
	private void anchorToEdges(Node n) {
		AnchorPane.setBottomAnchor(n, 0.0);
		AnchorPane.setTopAnchor(n, 0.0);
		AnchorPane.setLeftAnchor(n, 0.0);
		AnchorPane.setRightAnchor(n, 0.0);
	}
}
