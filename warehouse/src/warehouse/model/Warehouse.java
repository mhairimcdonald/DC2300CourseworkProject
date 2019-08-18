package warehouse.model;

import java.util.HashMap;
import java.util.LinkedList;

public class Warehouse {

	
	// Height and Width of the Warehouse
	private int height;
	private int width;
	// Storage for the actors
	private HashMap<Location, LinkedList<Actor>> warehouse;

	public Warehouse(int height, int width) {
		this.height = height;
		this.width = width;
		//Create two-dimensional array of Actors, stored by position
		warehouse = new HashMap<Location, LinkedList<Actor>>();
	}
	
	
	
	public void setWarehouse(HashMap<Location, LinkedList<Actor>> warehouse) {
		this.warehouse = warehouse;
	}



	public HashMap<Location, LinkedList<Actor>> getWarehouse() {
		return warehouse;
	}



	public void setWarehouse(LinkedList<Actor> actors, int height, int width) {
		for(int hIndex = 0; hIndex < height; hIndex++) {
			for(int wIndex = 0; wIndex < width; wIndex++) {
				Location mapLoc = new Location(hIndex, wIndex);
				LinkedList<Actor> ll = new LinkedList<Actor>();
				warehouse.put(mapLoc, ll);
			}
		}
		
		for(Actor actor: actors) {
			Location loc = actor.getLocation();
			int lrow = loc.getRow();
			int lcol = loc.getCol();
			for (Location l : warehouse.keySet()) {
				if (l.getCol() == lcol && l.getRow() == lrow ) {
					if (actor instanceof Robot) {((Robot)actor).setLocation(l); ((Robot)actor).setChargePodLocation(l);}
					else if (actor instanceof StorageShelf) {((StorageShelf)actor).setLocation(l); }
					else if (actor instanceof PackingStation) {((PackingStation)actor).setLocation(l); }
					else if (actor instanceof ChargingPod) {((ChargingPod)actor).setLocation(l); }
					
					warehouse.get(l).add(actor);
					break;
				}
			}
		}
	}



	public void setHeight(int height) {
		this.height = height;
	}



	public void setWidth(int width) {
		this.width = width;
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
	
	public LinkedList<Actor> getObjectAt(Location loc) {
		return warehouse.get(loc);
	}
	
	public LinkedList<Actor> getObjectAt(int row, int col) {
		Location location = new Location(row, col);
		return warehouse.get(location);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void operate() {
		//not sure if needed, might need method to create the actual warehouse just
	}//operate
	
	public HashMap<Location,LinkedList<Actor>> clearRobots() {
		HashMap<Location,LinkedList<Actor>> tempMap = new HashMap<Location,LinkedList<Actor>>();
		tempMap = warehouse;
		
		for (Location l : warehouse.keySet()) {
			for (Actor a : warehouse.get(l)) {
				if (a instanceof Robot) {
					tempMap.remove(a);
				}
			}
		}
		
		return tempMap;
	}
}
