package warehouse.javafx;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import warehouse.io.ConfigFile;
import warehouse.io.FileLoader;
import warehouse.io.WriteToFile;
import warehouse.io.configActors.*;
import warehouse.model.*;


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
	public static ConfigFile primaryConfigFile;
	private Pane[][] gridPaneIndex;

	public WarehouseController() {
		primaryConfigFile = new ConfigFile();
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
		Simulation simulation = new Simulation();

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
			newWarehouse.initModality(Modality.APPLICATION_MODAL);
			newWarehouse.showAndWait();
			reloadPrimaryConfigFile();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void reloadPrimaryConfigFile() {
		int cols = primaryConfigFile.getWidth();
		int rows = primaryConfigFile.getHeight();
		createGridBoard(cols, rows);
		
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
			reset();
			FileLoader fl = new FileLoader();
			primaryConfigFile = fl.parseFile(file);
			reloadPrimaryConfigFile();
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
				wf.writeFile(f, primaryConfigFile);
			} catch (IOException e) {
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
		gridPaneIndex = new Pane[numRows][numCols];
		GridPane gp = new GridPane();
		// Make the cells obvious
		gp.setGridLinesVisible(true);

		ColumnConstraints colConst = new ColumnConstraints();
		// Set cell size to take up an equal amount of space
		colConst.setPercentWidth(100.0 / numCols);
		RowConstraints rowConst = new RowConstraints();
		rowConst.setPercentHeight(100.0 / numRows);

		// Set Column Constraints on all Columns
		for (int i = 0; i < numCols; i++) {
			gp.getColumnConstraints().add(colConst);
		}
		// Set Row Constraints on all Rows
		for (int i = 0; i < numRows; i++) {
			gp.getRowConstraints().add(rowConst);
		}
		// Place an AnchorPane and give it a unique id in each cell.
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				AnchorPane p = new AnchorPane();
				p.setId("pane " + i + " " + j);
				gp.add(p, i, j);
				gridPaneIndex[j][i] = p;
			}
		}
		//Initialize the creator used to make the different actor visuals
		ActorShapes creator = new ActorShapes();
		
		for (ConfigPackingStation c : primaryConfigFile.getStation()) {
			StackPane sp = new StackPane();
			sp.setId(c.getuID());
			sp = creator.createPackingStation(c.getuID());
			anchorToEdges(sp);
			gridPaneIndex[c.getRow()][c.getCol()].getChildren().add(sp);
		}
		for (ConfigRobot c : primaryConfigFile.getPodRobot()) {
			StackPane spr = new StackPane();
			spr.setId(c.getuID());
			spr = creator.createRobot(c.getuID());
			anchorToEdges(spr);
			
			StackPane spc = new StackPane();
			spc.setId(c.getuID());
			spc = creator.createChargePod(c.getuID());
			anchorToEdges(spc);
			
			gridPaneIndex[c.getRow()][c.getCol()].getChildren().add(spc);
			gridPaneIndex[c.getRow()][c.getCol()].getChildren().add(spr);
		}
		for (ConfigStorageShelf c : primaryConfigFile.getShelf()) {
			StackPane sp = new StackPane();
			sp.setId(c.getuID());
			sp = creator.createStorageShelf(c.getuID());
			anchorToEdges(sp);
			gridPaneIndex[c.getRow()][c.getCol()].getChildren().add(sp);
		}

		
		anchorToEdges(gp);

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
	
	private void reset() {
		gridPaneIndex = null;
		primaryConfigFile = null;
	}
}
