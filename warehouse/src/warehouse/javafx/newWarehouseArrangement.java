package warehouse.javafx;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class newWarehouseArrangement {
	
	@FXML
	private Button closeButton;
	@FXML
	private AnchorPane gridAnchor;
	private final int numCols;
	private final int numRows;

	public newWarehouseArrangement(int cInput, int rInput) {
		numCols = cInput;
		numRows = rInput;
	}

	@FXML
	public void initialize() {
		GridPane gp = new GridPane();
		gp.setGridLinesVisible(true);
		
		ColumnConstraints colConst = new ColumnConstraints();
		colConst.setPercentWidth(100.0/numCols);
		RowConstraints rowConst = new RowConstraints();
		rowConst.setPercentHeight(100.0/numRows);
		
		
		for (int i = 0;i<numCols; i++) {
			gp.getColumnConstraints().add(colConst);
			gp.getRowConstraints().add(rowConst);
			for (int j = 0;j<numRows; j++) {
				Pane p = new Pane();
				p.setId("p_i_j");
				gp.add(p, i, j);
			}
		}
		AnchorPane.setBottomAnchor(gp, 0.0);
		AnchorPane.setTopAnchor(gp, 0.0);
		AnchorPane.setLeftAnchor(gp, 0.0);
		AnchorPane.setRightAnchor(gp, 0.0);
		
		gridAnchor.getChildren().addAll(gp);

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
		//(GridPane)gridAnchor.getChildren().get
		String s = "The Grid Pane has the following:\r\n"
				+ "Number of Columns: "+numCols+"\r\n"
				+ "Number of Rows: "+numRows+"\r\n"
				+ "And the following panes:\r\n";
		return s;
	}
	

}
