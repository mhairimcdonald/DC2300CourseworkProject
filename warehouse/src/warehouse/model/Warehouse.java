package warehouse.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {

	private WarehouseStats stats;
	
	// Height and Width of the Warehouse
	private int height;
	private int width;
	// Storage for the actors
	private HashMap<Location, ArrayList<Actor>> warehouse;

	public Warehouse(int height, int width) {
		this.height = height;
		this.width = width;
		//Create two-dimensional array of Actors, stored by position
		warehouse = new HashMap<Location, ArrayList<Actor>>();
		this.stats = new WarehouseStats();
	}
	
	//Clear the warehouse of all Actors
	public void clear() {
		warehouse.clear();
	}
	
	public void place(Actor actor, int row, int col) {
		place(actor, new Location(row,col));
	}
	
	public void place(Actor actor, Location loc) {
		place(actor, loc);
	}
	
	public ArrayList<Actor> getObjectAt(Location loc) {
		return warehouse.get(loc);
	}
	
	public ArrayList<Actor> getObjectAt(int row, int col) {
		Location location = new Location(col, row);
		return warehouse.get(location);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void reset() {
		stats.reset();
	}
	public void operate() {
		//not sure if needed, might need method to create the actual warehouse just
	}//operate
}
