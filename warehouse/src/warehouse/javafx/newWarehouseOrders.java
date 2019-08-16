package warehouse.javafx;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import warehouse.io.ConfigFile;
import warehouse.io.configActors.ConfigOrder;

public class newWarehouseOrders {
	
	@FXML private Button newOrderButton;
	@FXML private Button closeButton;
	@FXML private Button finishButton;
	@FXML private VBox orderEntryBox;
	private ArrayList<NewOrder> newOrders;
	private NewOrder currentOrder;
	private int noOfOrders;
	private ConfigFile cf;
	
	public newWarehouseOrders(ConfigFile cf) {
		this.cf = cf;
		this.noOfOrders = 0;
		newOrders = new ArrayList<NewOrder>();
	}
	
	@FXML
	public void initialize() {

	}
	
	public void hideWindow() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.hide();
	}
	
	@FXML
	public void closeButtonAction() {
		// get the handle to the stage
		Stage stage = (Stage) closeButton.getScene().getWindow();
		// close window
		stage.close();
	}
	
	@FXML
	public void newOrderButtonAction() {
		/*
		 * Each order has an overall design for the GUI.
		 * VBox > HBox & VBox
		 * HBox > 
		 * 		Label "Order: "
		 * 		Label = currentOrderNumber;
		 * 		Region. This must be set to always grow
		 * 		Label "TicksToPack: "
		 * 		TextField = setPromptText = "value between 0-99"
		 * VBox >
		 * 		Label = "Please Check Applicable Storage Shelves"
		 * 		For every 5 Shelves
		 * 			> HBox
		 * 				Labelx(1-5) = ssUID
		 * 				CheckBox
		 * Add first VBox to orderEntryBox
		 * Then add Separator after
		 */
		
		currentOrder = new NewOrder();
		VBox main = new VBox();
		VBox.setVgrow(main, Priority.ALWAYS);
		VBox.setMargin(main, new Insets(5));
		main.setStyle("-fx-border-insets: 0; "
				+ "-fx-border-width: 2; "
				+ "-fx-border-color:  black lightgray lightgray black");
		/*
		 * Create top and bottom part of the OrderBox and
		 * update currentOrder with this Order's variables to 
		 * be referenced later.
		 */
		HBox top = createTopOrderBox();
		VBox bot = createBottomOrderBox();
		
		//Combine the top and bottom part od the OrderBox
		main.getChildren().addAll(top, bot);
		
		//Iterate the total number of orders for display on labels.
		noOfOrders++;
		
		Separator sep = new Separator();
		
		//Add the display pieces together and add to the Window
		orderEntryBox.getChildren().addAll(main, sep);
		
		/*
		 * Add the currentOrder now that it is complete.
		 * These will be used to create ConfigOrders to
		 * complete the ConfigFile.
		 */
		newOrders.add(currentOrder);
	}
	
	@FXML
	public void finishButtonAction() {
		
		if (noOfOrders == 0) {
			//If there are no orders, Alert and return to stop progress
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No Orders");
			alert.setHeaderText("You must include at least one Order.");
			alert.showAndWait();
			return;
		}
		
		//For each order the user has made
		for (NewOrder o : newOrders) {
			/*
			 * If one of the orders are invalid, error and return to
			 * prevent creation of ConfigFile until all orders
			 * have input
			 */
			if (!checkWhetherOrderValid(o)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Order");
				alert.setHeaderText("All orders must include:\n"
						+ "TicksToPack: A number between 1-99\n"
						+ "At least one Storage Shelf checked.");
				alert.showAndWait();
				return;
			}
			ConfigOrder co = new ConfigOrder();
			/*
			 * Transform the NewOrder into a ConfigOrder then
			 * add it to ConfigFile's order array.
			 */
			co = writeOrder(o);
			cf.addOrder(co);
		}
		/*
		 *  The ConfigFile is now complete. Returning it back
		 *  to the original Window.
		 */
		WarehouseController.primaryConfigFile = cf;
		closeButtonAction();
	}
	
	public boolean checkWhetherOrderValid(NewOrder o) {
		/*
		 * Need to check whether there is at least one checkBox ticked,
		 * and whether the order has any text entered in it's textbox.
		 * If both are true, return true, else return false;
		 */
		boolean atLeastOneCheckBox = false;
		boolean someTextEntered = false;
		for (CheckBox cb : o.getBoxes()) {
			if (cb.isSelected()) {
				atLeastOneCheckBox = true;
			}
		}
		if (!o.getTickNo().getText().isEmpty()) {
			//Check that the number being entered is actually a number
			if (removeSpecialCharactersSetToInt(o.getTickNo().getText()) != -1) {
				someTextEntered = true;
			}
		}
		
		if (atLeastOneCheckBox && someTextEntered) {
			
			return true;
		}
		return false;
	}
	
	public ConfigOrder writeOrder(NewOrder o) {
		/*
		 * Create a new ConfigOrder.
		 * Get the Number of Ticks from the input NewOrder
		 * and parse it into the new ConfigOrder.
		 * Get the StorageShelf UID name from each CheckBox
		 * if they are ticked and add them to the 
		 * new ConfigOrder
		 * Then return the ConfigOrder
		 */
		ConfigOrder c = new ConfigOrder();
		//This has already been validated before being set here.
		String s = o.getTickNo().getText().replaceAll("[^0-9]", "");
		c.setTicksToPack(s);
		for (CheckBox cb : o.getBoxes()) {
			if (cb.isSelected()) {
				c.addStorageLocation(cb.getText());
			}
		}
		return c;
	}
	
	public int removeSpecialCharactersSetToInt(String s) {
		int i = -1;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			System.out.println("No numberic value entered.");
			return i;
		}
		return i;
	}
	
	private HBox createTopOrderBox() {
		//Create the HBox for the top-half of each order.
		HBox h = new HBox();
		HBox.setMargin(h, new Insets(4));
		
		//Create the labels for the current order
		Label l1 = new Label("Order: ");
		int currOrder = noOfOrders+1;
		Label l2 = new Label(""+currOrder);
		
		//A divider for neatness
		Region r = new Region();
		r.setPrefWidth(150);
		
		//Create the label and TextField for the ticks we want from the user
		Label l3 = new Label("TicksToPack: ");
		TextField tf = new TextField();
		tf.setPromptText("value between 0-99");
		//Assign the TextField to the currentOrder variable to reference later
		currentOrder.setTickNo(tf);
		
		//Add all this to the HBox and return it.
		h.getChildren().addAll(l1,l2,r,l3,tf);
		return h;
	} 
	
	private VBox createBottomOrderBox() {
		//Create the VBox for the bottomn-half of each order
		VBox v = new VBox();
		VBox.setMargin(v, new Insets(5));
		v.setStyle("-fx-border-color: black; "
				+ "-fx-background-color: gainsboro");
		Label l1 = new Label("Please Check Applicable Storage Shelves");
		v.getChildren().add(l1);
		//Iterate into lines of 5 lables and checkboxes
		for (int i = 0; i < cf.getShelf().size(); i += 5) {
			
			/*
			 * Create the HBox to store up to 5
			 * CheckBoxes. Align its contents to the center.
			 */
			HBox h = new HBox();
			h.setSpacing(10);
			HBox.setMargin(h, new Insets(5));
			h.setPadding(new Insets(5));
			h.setAlignment(Pos.CENTER);
			/*
			 * Decide what to iterate on:
			 * If there are >= 5 shelves remaining, 
			 * iterate against 5. If there are less than 5 
			 * shelves, iterate against no.Shelves remaining.
			 * This allows blocks of 5 checkboxes and a final
			 * dynamicly sized block for the remaining amount.
			 */
			if ((cf.getShelf().size()-i) >= 5) {
				//For the next 5 StorageShelves
				for (int j = i; j < i+5; j++) {
					CheckBox cb = new CheckBox();
					//Set the CheckBox's text to the Shelf it represents.
					cb.setText(cf.getShelf().get(j).getuID());

					// Add the checkbox to currentOrder's array to be referenced later.
					currentOrder.addBox(cb);
					
					//Add checkbox to Hbox for displaying
					h.getChildren().add(cb);
				}
			} else if ((cf.getShelf().size()-i) < 5) {
				//For the remaining number of shelves
				for (int j = i; j < cf.getShelf().size(); j++) {
					CheckBox cb = new CheckBox();
					//Set the CheckBox's text to the Shelf it represents.
					cb.setText(cf.getShelf().get(j).getuID());

					// Add the checkbox to currentOrder's array to be referenced later.
					currentOrder.addBox(cb);
					//Add checkbox to Hbox for displaying
					h.getChildren().add(cb);
				}
			} else {
				System.out.println("OrderBox Error Found!");
				return null;
			}
			//Add HBox and its contents into the VBox
			v.getChildren().add(h);
		}
		return v;
	}
	

	
	

}
