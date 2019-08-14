package warehouse.javafx;

import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import warehouse.model.Warehouse;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final Warehouse w = createWarehouse(0,0);

			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("WarehouseScene.fxml"));
			loader.setController(new WarehouseController(w));
			final Parent root = loader.load();

			final Scene scene = new Scene(root, 900, 600);
			scene.getStylesheets().add("/warehouse/javafx/javaStyle.css");
			primaryStage.setTitle("Amazon Simulation");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Warehouse createWarehouse(int row, int col) {
		final Warehouse w = new Warehouse(row, col);
		
		/*
		 * TODO: Input the creation of the base warehouse variables here
		 */
		return w;
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
