package warehouse.javafx;

import java.util.ArrayList;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class NewOrder {
	
	private ArrayList<CheckBox> boxes;
	private TextField tickNoField;
	
	public NewOrder() {
		boxes = new ArrayList<CheckBox>();
	}
	
	public NewOrder(ArrayList<CheckBox> boxes, TextField tickNoField) {
		this.boxes = boxes;
		this.tickNoField = tickNoField;
	}

	public ArrayList<CheckBox> getBoxes() {
		return boxes;
	}

	public void setBoxes(ArrayList<CheckBox> boxes) {
		this.boxes = boxes;
	}
	
	public void addBox(CheckBox c) {
		boxes.add(c);
	}

	public TextField getTickNo() {
		return tickNoField;
	}

	public void setTickNo(TextField tickNoField) {
		this.tickNoField = tickNoField;
	}
	
	

}
