package warehouse.javafx;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class ActorShapes {
	
	public ActorShapes() {}
	
	public StackPane createRobot(String robotID) {
		Text text = createText(robotID);
		Circle c = encircle(text, Color.ORCHID);
		StackPane layout = new StackPane();
		layout.getChildren().addAll(
				c,
				text
				);
		layout.setPadding(new Insets(20));
		return layout;
		
	}
	
	public StackPane createChargePod(String podID) {
		Text text = createText(podID);
		Rectangle rec = squareSurround(text, Color.BURLYWOOD);
		StackPane layout = new StackPane();
		layout.getChildren().addAll(
				rec,
				text
				);
		layout.setPadding(new Insets(15));
		return layout;
		
	}
	
	public StackPane createPackingStation(String stationID) {
		Text text = createText(stationID);
		Rectangle rec = squareSurround(text, Color.AQUAMARINE);
		StackPane layout = new StackPane();
		layout.getChildren().addAll(
				rec,
				text
				);
		layout.setPadding(new Insets(15));
		return layout;
	}
	
	public StackPane createStorageShelf(String shelfID) {
		Text text = createText(shelfID);
		Rectangle rec = squareSurround(text, Color.INDIANRED);
		StackPane layout = new StackPane();
		layout.getChildren().addAll(
				rec,
				text
				);
		layout.setPadding(new Insets(15));
		return layout;
	}
	
	public Text createText(String s) {
		Text text = new Text(s);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setStyle("-fx-font:38 arial;");
		return text;
	}
	
	private Circle encircle(Text text, Color c) {
		Circle circle = new Circle();
		circle.setFill(c);
		final double PADDING = 15;
		circle.setRadius(getWidth(text) / 2 + PADDING);
		
		return circle;
	}
	
	private Rectangle squareSurround(Text text, Color c) {
		Rectangle rec = new Rectangle();
		rec.setFill(c);
		final double PADDING = 20;
		rec.setWidth(getWidth(text) + PADDING);
		rec.setHeight(rec.getWidth());
		
		return rec;
	}
	
	private double getWidth(Text text) {
		new Scene(new Group(text));
		text.applyCss();
		
		return text.getLayoutBounds().getWidth();
	}
}
