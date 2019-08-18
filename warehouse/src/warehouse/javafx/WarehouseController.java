package warehouse.javafx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
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
	private Button resetButton;
	@FXML
	private Label statusUpdate;
	@FXML
	private AnchorPane gridBoardAnchor;
	@FXML
	private MenuItem menuQuit;
	@FXML
	private MenuItem gitHubMenu;
	@FXML
	private GridPane simulationBoard;
	private Simulation sim;
	
	private ConfigFile originalConfigFile;
	public static ConfigFile runningConfigFile;
	public static boolean configValid;
	private Pane[][] gridPaneIndex;
	
	public static String outputMessage;

	public WarehouseController() {
		runningConfigFile = new ConfigFile();
		originalConfigFile = new ConfigFile();
		configValid = false;
		sim = new Simulation();
	}

	@FXML
	public void initialize() {

	}

	@FXML
	public void startSimulation() {
		if (!configValid) {
			//If the config hasn't been set properly, error.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Config Loaded");
			alert.setHeaderText("You need to load or create a .sim file first.");
			alert.showAndWait();
			return;
		}
		startButton.setDisable(true);
		startButton.setDefaultButton(false);
		resetButton.setDisable(false);
		resetButton.setDefaultButton(true);
		statusUpdate.setText("Status: Running");
		sim.start(runningConfigFile);
		boolean running = true;
		while (running) {
			ConfigFile newConfig = sim.continueSimulation();
			if (newConfig == null) {
				running = false;
				break;
			}
			runningConfigFile = newConfig;
			configValid = true;
			reloadRunningConfigFile();
		}
		
		System.out.println(outputMessage);
		

	}

	@FXML
	public void resetSimulation() {
		startButton.setDisable(false);
		startButton.setDefaultButton(true);
		resetButton.setDefaultButton(false);
		resetButton.setDisable(true);
		statusUpdate.setText("Status: Waiting");
		loadPrimaryConfigFile();
		configValid = false;

	}

	@FXML
	public void newWarehouse() {
		//Set false at the start to ensure to partial configFile builds.
		configValid = false;
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
			reloadRunningConfigFile();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void reloadRunningConfigFile() {
		//If the configFile isn't valid, set the GUI to default.
		if (!configValid) {
			GridPane gp = new GridPane();
			setSimulationBoard(gp);
			runningConfigFile = null;
			return;
		}
		int cols = runningConfigFile.getWidth();
		int rows = runningConfigFile.getHeight();
		createGridBoard(cols, rows);
		
	}
	
	private void loadPrimaryConfigFile() {
		runningConfigFile = originalConfigFile;
		
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
			runningConfigFile = fl.parseFile(file);
			//If the load has gotten this far you can trust the ConfigFile to be valid.
			configValid = true;
			reloadRunningConfigFile();
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
				wf.writeFile(f, runningConfigFile);
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
	
	@FXML
	public void gitHubMenuAction() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Open Webpage?");
		alert.setHeaderText("This will open a webpage\n"
							+ "to this projects GitHub\n"
							+ "repository. Continue? ");
		alert.showAndWait();
		if (alert.getResult() != ButtonType.OK) {
			return;
		}

		String websiteURL = "https://github.com/mhairimcdonald/DC2300CourseworkProject";
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI(websiteURL));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open" + websiteURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		
		for (ConfigPackingStation c : runningConfigFile.getStation()) {
			StackPane sp = new StackPane();
			sp.setId(c.getuID());
			sp = creator.createPackingStation(c.getuID());
			anchorToEdges(sp);
			gridPaneIndex[c.getRow()][c.getCol()].getChildren().add(sp);
		}
		for (ConfigRobot c : runningConfigFile.getPodRobot()) {
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
		for (ConfigStorageShelf c : runningConfigFile.getShelf()) {
			StackPane sp = new StackPane();
			sp.setId(c.getuID());
			sp = creator.createStorageShelf(c.getuID());
			anchorToEdges(sp);
			gridPaneIndex[c.getRow()][c.getCol()].getChildren().add(sp);
		}

		
		anchorToEdges(gp);

		setSimulationBoard(gp);
	}
	
	public void setSimulationBoard(GridPane gridPane) {
		if (simulationBoard == null) {
			// If there is currently no board, add this one
			simulationBoard = gridPane;
			// Add grid to the Anchor
			gridBoardAnchor.getChildren().addAll(gridPane);
		} else {
			// If there is currently a board, remove it then add this one.
			gridBoardAnchor.getChildren().remove(simulationBoard);
			simulationBoard = gridPane;
			// Add grid to the Anchor
			gridBoardAnchor.getChildren().addAll(gridPane);
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
		runningConfigFile = null;
	}
}
