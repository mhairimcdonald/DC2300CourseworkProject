package warehouse.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import warehouse.io.ConfigFile;
import warehouse.io.configActors.*;

public class Simulation {
	private int tickCounter = 0;
	private List<Order> orders;
	private List<ConfigOrder> configOrders;
	private List<Actor> actors;
	private List<ConfigActor> configActors;
	private List<Actor> newActors;
	private Warehouse warehouse;
	private Warehouse updateWarehouse;
	private int step;
	private boolean running = false;

	public void start(ConfigFile cf) {
		int width = cf.getWidth();
		int height = cf.getHeight();
		warehouse = new Warehouse(height, width);
		actors = new ArrayList<Actor>();
		orders = new ArrayList<Order>();
		configOrders = new ArrayList<ConfigOrder>();
		configOrders.addAll(cf.getOrder());
		configActors = new ArrayList<ConfigActor>();
		configActors.addAll(cf.getPodRobot()); //add config robots
		configActors.addAll(cf.getShelf()); //add config shelves
		configActors.addAll(cf.getStation()); // add config stations
		for(ConfigOrder order: configOrders) {
			ArrayList<String> listOfStorageLocations = order.getStorageLocations();
			int ticksToPack = Integer.parseInt(order.getuID());//NEED TO CHAGE HOW ORDER IS READ
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
				ChargingPod chargePod = new ChargingPod(location, poduID, chargeSpeed);
				actors.add(chargePod);
				
				int capacity = cf.getCapacity();
				Robot robot = new Robot(poduID, capacity, location, UID);
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
			
			}
		}
		//newActors = new ArrayList<Actor>();
		warehouse = new Warehouse(width, height);
		updateWarehouse = new Warehouse(width, height);
		
		reset();
	}// start


	public void continueSimulation() {
		tickCounter++;
		for(Iterator<Actor> iter = actors.iterator(); iter.hasNext(); ) {
			Actor actor = iter.next();
			actor.tick();
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
		populate(warehouse);
	}
	
	public void populate(Warehouse w) {
		warehouse.clear();
		for(int i = 0; i < w.getWidth();i++) {
			//Loop through the columns
			for (int j = 0; i < w.getHeight();j++) {
				//Loop through the rows.
				/*For each cell, check whether there is an 
				 Actor that should be assigned there. */
				//Add actor to the Warehouse in the appropriate location.
				
			}
		}
	}
	
}
