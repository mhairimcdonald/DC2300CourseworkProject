package warehouse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import warehouse.io.ConfigFile;
import warehouse.io.configActors.*;

public class Simulation {
	private int tickCounter = 0;
	private LinkedList<Order> orders;
	private List<ConfigOrder> configOrders;
	private LinkedList<Actor> actors;
	private List<ConfigActor> configActors;
	private LinkedList<Robot> robots;
	private Warehouse warehouse;
	private HashMap<Location, LinkedList<Actor>> mapState;
	private Warehouse updateWarehouse;
	private boolean running = false;
	private ArrayList<String> listOfStorageLocations;
	private String stopString = null;
	//public static PathFinding Variable robots will use to work out next location/ distance to certain locations

	public void start(ConfigFile cf) {
		running = true;
		int width = cf.getWidth();
		int height = cf.getHeight();
		warehouse = new Warehouse(height, width);
		actors = new LinkedList<Actor>();
		orders = new LinkedList<Order>();
		configOrders = new ArrayList<ConfigOrder>();
		configOrders.addAll(cf.getOrder());
		configActors = new LinkedList<ConfigActor>();
		
		configActors.addAll(cf.getShelf()); //add config shelves
		configActors.addAll(cf.getStation()); // add config stations
		configActors.addAll(cf.getPodRobot()); //add config robots
		
		for(ConfigOrder order: configOrders) {
			listOfStorageLocations = order.getStorageLocations();
			int ticksToPack = order.getTicksToPack();//NEED TO CHAGE HOW ORDER IS READ
			Order newOrder = new Order(ticksToPack, listOfStorageLocations);
			orders.add(newOrder);
		}
		
		for(ConfigActor actor: configActors) {
			switch(actor.getClass().getCanonicalName()) {
			case "ConfigRobot":{
				int row = actor.getRow();
				int col = actor.getCol();
				String UID = actor.getuID();
				Location location = new Location(row, col);
				String poduID = ((ConfigRobot)actor).getChargingPoduID();
				int chargeSpeed = cf.getChargeSpeed();
				int capacity = cf.getCapacity();
				Robot robot = new Robot(poduID, location, capacity, location, UID);
				ChargingPod chargePod = new ChargingPod(location, poduID, chargeSpeed, robot);
				actors.add(chargePod);
				robots.add(robot);
				actors.add(robot);
			}// case ConfigRobot
			case "ConfigStorageShelf":{
				int row = actor.getRow();
				int col = actor.getCol();
				String UID = actor.getuID();
				Location location = new Location(row, col);
				StorageShelf storageShelf = new StorageShelf(location, UID);
				actors.add(storageShelf);
			}//case ConfigStorageShelf
			case "ConfigPackingStation":{
				int row = actor.getRow();
				int col = actor.getCol();
				String UID = actor.getuID();
				Location location = new Location(row, col);
				PackingStation packingStation = new PackingStation(location, UID);
				actors.add(packingStation);
			}// case ConfigPackingStation
			
			}//switch
		}//for Loop
		warehouse = new Warehouse(width, height);
		warehouse.setWarehouse(actors, width, height);
		updateWarehouse = new Warehouse(width, height);
		updateWarehouse.setWarehouse(actors, height, width);
		
		reset();
	}// start


	public void continueSimulation() {
		tickCounter++;
		while(running) {
			Boolean finished = false;
			Boolean allDispatched = true;
			for(Iterator<Actor> iter = actors.iterator(); iter.hasNext(); ) {
				Actor actor = iter.next();
				switch(actor.getClass().getCanonicalName()) {
				case "Robot":{
					((Robot)actor).tick(this.updateWarehouse.getWarehouse());
					if(((Robot)actor).crash(this.updateWarehouse.getWarehouse())) {
						Location crashLocation = ((Robot)actor).getLocation();
						LinkedList<Actor> crashedRobots = mapState.get(crashLocation);
						stopString = "Robot " + ((Robot)crashedRobots.getFirst()).getUID() + " has crashed into Robot " + ((Robot)crashedRobots.getLast()).getUID();
						running = false;
					}else if(((Robot)actor).batteryEmpty()){
						stopString = "Robot " + ((Robot)actor).getUID() + " has run out of battery";
						running = false;
					}
				}// case Robot
				case "ChargingPod":{
					((ChargingPod)actor).tick(robots);
				}//case ChargingPod
				case "PackingStation":{
					Order nextOrder = orders.getFirst();
					((PackingStation)actor).tick(robots);
					if(((PackingStation)actor).getCurrentOrder() != null) {
						allDispatched = false;
					}else if(nextOrder != null){
						((PackingStation)actor).setCurrentOrder(nextOrder);
						allDispatched = false;
					};
				}// case PackingStation
				default:{}//default
				
				}//switch
				if(orders.isEmpty() && allDispatched) {
					stopString = "The simulation is complete with: " + tickCounter;
				}
			}//for
		warehouse = updateWarehouse;
		updateWarehouse.setWarehouse(updateWarehouse.clearRobots());
		}
		
	}// continueSimulation

	public void dispatch() {

	}// dispatch

	public void generateReport() {
    
  }// generate report

  public void reset() {
		running = true;
		actors.clear();
		warehouse.clear();
		warehouse.reset();
		updateWarehouse.clear();
	}
	
	
}
