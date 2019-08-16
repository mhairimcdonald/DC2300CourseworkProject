package warehouse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import warehouse.io.configActors.ConfigActor;
import warehouse.model.path.PathMapper;

public class Robot implements Actor {
	
	//Robot's own variables
	private String UID;	
	private Location location;
	private int maxCharge;
	private int currentCharge;
	private boolean item;
	private boolean isCharging;
	private boolean hasOrder;
	private Order order;
	private boolean orderReady;
	private Location packingStationLocation;
	//Robot's connected ChargePad variables
	private String chargePodID;
	private Location chargePodLocation; //Added once during setup
	//Robot's direction based variables
	private LinkedList<Location> destinations;
	private Location currentDestination;
	

	public Robot(String chargePodID, Location chargePodLocation, int maxCharge, Location location, String uID) {
		super();
		this.currentCharge = maxCharge;
		this.chargePodID = chargePodID;
		this.chargePodLocation = chargePodLocation;
		this.maxCharge = maxCharge;
		this.location = location;
		this.UID = uID;
		this.destinations = null;
		this.currentDestination = null;
	}
  
  //basic constructor
	public Robot() {
		this.destinations = new LinkedList<Location>();
		this.isCharging = false;
		this.item = false;
		hasOrder = false;
		order = null;
		orderReady = false;
		packingStationLocation = null;
		
	}
	
	/*
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
	*/

	public LinkedList<Location> getDestinations() {
		return destinations;
	}
	
	public void addDestinationsLast(Location l) {
		destinations.addLast(l);
	}
	
	public Location getLastDestination() {
		return destinations.getLast();
	}

	public void addDestinationsFirst(Location l) {
		destinations.addFirst(l);
	}
	
	public Location getFirstDestination() {
		return destinations.getFirst();
	}

	public Location getCurrentDestination() {
		return currentDestination;
	}

	public void setCurrentDestination(Location currentDestination) {
		this.currentDestination = currentDestination;
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

	public Location getChargePodLocation() {
		return chargePodLocation;
	}

	public void setChargePodLocation(Location chargePodLocation) {
		this.chargePodLocation = chargePodLocation;
	}

	public int getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(int maxCharge) {
		this.maxCharge = maxCharge;
	}

	public boolean getOrder() {
		return hasOrder;
	}

	public void setOrder(boolean order) {
		this.hasOrder = order;
	}

	public boolean hasItem() {
		return item;
	}

	public void setItem(boolean item) {
		this.item = item;
	}

	public boolean isCharging() {
		return isCharging;
	}

	public void setCharging(boolean isCharging) {
		this.isCharging = isCharging;
	}
	
	public boolean isOrderReady() {
		return orderReady;
	}

	public void setOrderReady(boolean orderReady) {
		this.orderReady = orderReady;
	}

	public Location getPackingStationLocation() {
		return packingStationLocation;
	}

	public void setPackingStationLocation(Location packingStationLocation) {
		this.packingStationLocation = packingStationLocation;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean takeOrder() {
		if (orderReady) {
			hasOrder = false;
			order = null;
			orderReady = false;
			packingStationLocation = null;
			destinations.clear();
			destinations.add(chargePodLocation);
			
			return true;
		}
		return false;

	}

	public void tick() {}
	
	public void parseOrder(Order o, HashMap<Location, LinkedList<Actor>> mapState) {
		//Get the UIDs of the Storage Shelves
		//Iterate over the mapState and look for Shelves with matching UIDs
		ArrayList<String> shelfuIDs = o.getItemLocations();
		for (Location l : mapState.keySet()) {
			for (Actor a : mapState.get(l)) {
				if (a instanceof StorageShelf) {
					for (String s : shelfuIDs) {
						if (s.contentEquals(((StorageShelf) a).getUID())) {
							destinations.add(a.getLocation());
						}
					}
				}
			}
		}
	}
	
	public Location tick(HashMap<Location, LinkedList<Actor>> mapState) {
		PathMapper pm = new PathMapper(); //Initialise the Pathfinding object
		Location l = null; //Initialise the return.
		
		
		//Am I charging currently?
		if (isCharging) {
			if (currentCharge == maxCharge) {//If full on charge
				setCharging(false);
			} else {//Still charging. Wait
				return location;
			}
		}
		
		//Do I have order but no destination?	
		if (order == null) { //If I have no order, go to the chargepod.
			setCurrentDestination(chargePodLocation);
		} else {//If I do have an order
			if (destinations.isEmpty()) {//But I don't have a destination
				parseOrder(order, mapState);
			} else {//I have an order and a destination
				//Am I where I want to be?
				if (location == currentDestination) {//If you are, do what you need to do!
					if (location == chargePodLocation) {//You're trying to charge
						setCharging(true);
						destinations.removeFirst();
						return location;
					} else if (location == packingStationLocation) {//You're giving up your order
						orderReady = true;
						destinations.removeFirst();
						return location;
					} else {//You're collecting items from a Shelf
						destinations.removeFirst();
						return location; //Stay here to 'unload'
					}
				} else {//I'm not there yet. Move!
					l = getNextLoc(pm, mapState);
					System.out.println("I returned: ["+l.getCol()+","+l.getRow()+"]");
				}
				
			}
		}
		
		return l;
		
	}
	
	/*
	 * Simulation checks each robot if it needs an order.
	 * If it doesn't, return false, if it does, return true
	 * and linked the order.
	 */
	public boolean needsOrder(Order o, Location l) {
		if (hasOrder) {
			return false;
		} else {
			packingStationLocation = l;
			hasOrder = true;
			order = o;
			orderReady = false;
			return true;
		}
	}
	

	
	//This gets where to move/or whether to stay
	
	public Location getNextLoc(PathMapper pm, HashMap<Location, LinkedList<Actor>> mapState) {  
		//If you have no current destination
		if (currentDestination == null) {
			//And you have no destinations
			if (destinations.isEmpty()) {
				//You WILL be full on charge, have no items and be ready for orders.
				//Wait for next round for a PackingStation to give you order
				setItem(false);
				setCharging(false);
				return location;
			} else {
				//You have destinations. Set currentDestination
				setCurrentDestination(getDestinations().getFirst());
			}
		}
		if (isCharging()) {
			//If it's currently charging, stay put until you have enough charge.
			if (currentCharge == maxCharge) {
				setCharging(false);
				//If full charge, set yourself to stop charging.
				//Waiting a tick to reset tick();
				return location;
			} else {
				//If not done charging, stay put for now.
				return location;
			}
		}
		//If the robot is trying to get to its chargepad
		if (currentDestination == chargePodLocation) {
			//If the robot is currently on it's charge pad
			if (location == chargePodLocation ) {
				setCharging(true);
			} else {
				//If not at your chargepad but wants to get there, move towards it.
				pm.setupMapper(mapState, chargePodLocation);
				Location nextPos = pm.getNextNearest(location);
				return nextPos;
			}
		} //If not, work out if it should be trying to.

		
		/*
		 * Get the number of cell-movements needed to get to 
		 * the current destination. Then work out charge cost.
		 */
		int nextDestCost = getChargeCost(pm, mapState, currentDestination);
		
		/*
		 * Get the number of cell-movements needed to get to 
		 * the Robot's Charging Pod. Then work out charge cost.
		 */
		System.out.println("ChargePod:["+chargePodLocation.getCol()+","+chargePodLocation.getRow()+"]");
		int chargePadCost = getChargeCost(pm, mapState, chargePodLocation);
		
		if (chargePadCost > currentCharge/2 || (currentCharge-nextDestCost) < nextDestCost+chargePadCost) {
			System.out.println("I want to charge!");
			/*
			 * If amount of charge to get to your pad is more 
			 * than half your remaining charge
			 * Or
			 * If remaining charge after destination is greater 
			 * than charge cost to get to destination and then 
			 * to your pad
			 */
			setCurrentDestination(chargePodLocation);
		} else {
			setCurrentDestination(getDestinations().getFirst());
		}
		pm.setupMapper(mapState, currentDestination);
		Location next = pm.getNextNearest(location);
		System.out.println("Next Nearest: ["+next.getCol()+","+next.getRow()+"]");
		
		return next;
	}
	
	private int getChargeCost(PathMapper pm, HashMap<Location, LinkedList<Actor>> mapState, Location destination){
		pm.setupMapper(mapState, destination);
		
		int costToDest = pm.getCurrentDistanceCost(location);
		int actualCost = getActualCost(costToDest);
		
		return actualCost;
	}

	private int getActualCost(int noOfMoves) {
		int tileCost = 1;
		if (hasItem()) {
			tileCost = 2;
		}
		int leeway = (int) Math.ceil(noOfMoves / 5.0); //20% rounded up.
		int totalMovement = leeway + noOfMoves;
		int totalCost = totalMovement * tileCost;
		
		return totalCost;
	}
	
	public boolean crash(HashMap<Location, ArrayList<Actor>> mapState) {
		boolean crashed = false;
		ArrayList<Actor> a = mapState.get(location); //Array at current Loc
		int noOfRobots = 0; //no.Robots in that array
		//If there's more than one actor in that position
		if (a.size()>1) {
			//Get each actor
			
			for(Actor actor: a) {
				//Check if the actor is a Robot
				if (actor instanceof Robot) {
					//For each robot, iterate counter
					noOfRobots++;
				}
			}
		}
		//If more than 1 robot in that position, crash has occured.
		if (noOfRobots>1){
			crashed = true;
		}
		return crashed;
	}
	
	public boolean batteryEmpty() {
		boolean crashed = false;
		return crashed;
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}

	
}
