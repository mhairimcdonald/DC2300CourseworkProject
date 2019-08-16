package warehouse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Robot implements Actor {
	
	private int currentCharge;
	private String chargePodID;
	private int maxCharge;
	private int noOfItems;
	private Location location;
	private String UID;	
	private LinkedList<Location> destinations;
	private Location currentDestination;
	
	
	public Robot(String chargePodID, int maxCharge, Location location, String uID) {
		super();
		this.currentCharge = maxCharge;
		this.chargePodID = chargePodID;
		this.maxCharge = maxCharge;
		this.noOfItems = 0;
		this.location = location;
		this.UID = uID;
		this.destinations = null;
		this.currentDestination = null;
	}
  
  //basic constructor
	public Robot() {
		// TODO Auto-generated constructor stub
	}

	public LinkedList<Location> getDestinations() {
		return destinations;
	}



	public void setDestinations(Order order, Warehouse warehouse) {
		ArrayList<String> destinations = order.getItemLocations();
		int height = warehouse.getHeight();
		int width = warehouse.getWidth();
		HashMap<Location, Actor> map = new HashMap<Location,Actor>();
		Actor[][] currentWarehouse = new Actor[height][width];
		for(int hIndex = 0; hIndex > height; hIndex++) {
			for(int wIndex = 0; wIndex > width; wIndex++) {
				if(currentWarehouse[height][width] != null) {
					Location newLocation = new Location()
					map.put(key, value)
				}
			}
		}
		
		for(String destination: destinations) {
			
		}
	}


	public Location getCurrentDestination() {
		return currentDestination;
	}



	public void setCurrentDestination(Location currentDestination) {
		this.currentDestination = destinations.removeFirst();


	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}
	
	//method to decide if robot is able to accept latest request
	public void requestDecision() {
		
	}//accept
	
	public Boolean getHasItem() {
		return hasItem;
	}

	public void setHasItem(Boolean hasItem) {
		this.hasItem = hasItem;
	}

	public int getCurrentCharge() {
		return currentCharge;
	}

	public void setCurrentCharge(int currentCharge) {
		this.currentCharge = currentCharge;
	}

	public String getChargePodID() {
		return chargePodID;
	}

	public void setChargePodID(String chargePodID) {
		this.chargePodID = chargePodID;
	}

	public int getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(int maxCharge) {
		this.maxCharge = maxCharge;
	}

	//This decides if robot goes to ChargingPod or inital Destination
	public void detectPower() {
		/*
		 * Can Robot go to dest && make it back to CP
		 * YES - go to dest return 
		 * No- go towards CP
		 */
	}//detectPower
	
	public void move() {
		// On each tick do we need this to call algorithm to decide what move to take then make the move?
	}//move
	
	
	public void take() {
		//Robot takes item when it detects shelf it was seeking?
	}//take


	public Location tick(Location loc) {
		
	}
	
	@Override
	public void tick() {
		//positon of robot
		//not sure how to handle where robot aims at
		if(location == currentDestination) {
			Actor actor = objectAtLocation;
			switch(actor.getName()) {
			case("ChargingPod"):{
				if(currentCharge < maxCharge) {
					((ChargingPod) actor).charge(this);
				}
			}
			case("PackingStation"):{ 
				((PackingStation) actor).pack(this);
			}
			case("StorageShelf"):{noOfItems++;}
			case("Robot"):{crash();}
			default:{;}
			
			}
		}
	}

	private void crash() {
		// TODO Auto-generated method stub
		
	}

	
}
