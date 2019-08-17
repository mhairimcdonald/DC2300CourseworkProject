package warehouse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import warehouse.io.ActorConfigConversion;
import warehouse.io.ConfigFile;
import warehouse.io.configActors.*;
import warehouse.javafx.WarehouseController;

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
	private ConfigFile oldConfig;
	//public static PathFinding Variable robots will use to work out next location/ distance to certain locations

	public void start(ConfigFile cf) {
		this.oldConfig = cf;
		running = true;
		int width = cf.getWidth();
		int height = cf.getHeight();
		warehouse = new Warehouse(height, width);
		actors = new LinkedList<Actor>();
		orders = new LinkedList<Order>();
		robots = new LinkedList<Robot>();
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
			if (actor instanceof ConfigRobot) {
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
			} else if (actor instanceof ConfigStorageShelf) {
				int row = actor.getRow();
				int col = actor.getCol();
				String UID = actor.getuID();
				Location location = new Location(row, col);
				StorageShelf storageShelf = new StorageShelf(location, UID);
				actors.add(storageShelf);
			} else if (actor instanceof ConfigPackingStation) {
				int row = actor.getRow();
				int col = actor.getCol();
				String UID = actor.getuID();
				Location location = new Location(row, col);
				PackingStation packingStation = new PackingStation(location, UID);
				actors.add(packingStation);
			} else {
				System.out.println("Invalid");
			}
		}//for Loop
		warehouse = new Warehouse(width, height);
		warehouse.setWarehouse(actors, width, height);
		updateWarehouse = warehouse;
	}// start


	public String continueSimulation() {
		while(running) {
			updateWarehouse = warehouse;
			Boolean finished = false;
			Boolean allDispatched = true;
			for(Iterator<Actor> iter = actors.iterator(); iter.hasNext(); ) {
				Actor actor = iter.next();
				if (actor instanceof Robot) {
					
					Location newPosition = ((Robot)actor).tick(this.updateWarehouse.getWarehouse()); //I want to move here
					Location oldPosition = actor.getLocation(); //This is where I am.
					
					System.out.println("===================================\n"
							+ "I want to move to:"+newPosition+"\n"
									+ "I'm currently at:"+oldPosition+"\n"
											+ "My Destination is: ["+((Robot)actor).getCurrentDestination().getCol()+","+((Robot)actor).getCurrentDestination().getRow()+"]\n"
											+ "===================================");
					
					if (newPosition==null) {
						System.out.println("I didn't move. I'm already there.");
						//don't move. I'm fine
					} else if (newPosition != oldPosition) {
						//Remove Robot from its LinkedLisk<Actor> at it's current Pos
						for (Actor a : this.updateWarehouse.getWarehouse().get(oldPosition)) {
							if (a == actor) {
								this.updateWarehouse.getWarehouse().get(oldPosition).remove(a);
								System.out.println("I'm no longer at:"+oldPosition);
							}
						}
						//Add Robot to the LinkedList at it's new Pos
						this.updateWarehouse.getWarehouse().get(newPosition).add(actor);
						((Robot) actor).setLocation(newPosition);
						System.out.println("I'm now at:"+newPosition);
					}
					
					if(((Robot)actor).crash(this.updateWarehouse.getWarehouse())) {
						Location crashLocation = ((Robot)actor).getLocation();
						LinkedList<Actor> crashedRobots = mapState.get(crashLocation);
						stopString = "Robot " + ((Robot)crashedRobots.getFirst()).getUID() + " has crashed into Robot " + ((Robot)crashedRobots.getLast()).getUID();
						running = false;
					}else if(((Robot)actor).batteryEmpty()){
						stopString = "Robot " + ((Robot)actor).getUID() + " has run out of battery";
						running = false;
					}
				} else if ( actor instanceof ChargingPod) {
					((ChargingPod)actor).tick(robots);

				} else if ( actor instanceof PackingStation) {
					if (!orders.isEmpty()) {
						Order nextOrder = orders.getFirst();
						if (((PackingStation) actor).needOrder(nextOrder)) {
							orders.removeFirst();
						}
						((PackingStation)actor).tick(robots);
					}
					
				} else {
					System.out.println("Other!");

				}
				if(orders.isEmpty() && allDispatched) {
					stopString = "The simulation is complete with: " + tickCounter;
					running = false;

				}
			}//for
		warehouse = updateWarehouse;
		WarehouseToConfigFile wc = new WarehouseToConfigFile();
		ConfigFile cf = new ConfigFile();
		ActorConfigConversion acc = new ActorConfigConversion();
		cf.setChargeSpeed(oldConfig.getChargeSpeed());
		cf.setCapacity(oldConfig.getCapacity());
		cf.setHeight(oldConfig.getHeight());
		cf.setWidth(oldConfig.getWidth());
		ArrayList<ConfigOrder> aco = new ArrayList<ConfigOrder>();
		ArrayList<ConfigPackingStation> acps = new ArrayList<ConfigPackingStation>();
		ArrayList<ConfigRobot> acr = new ArrayList<ConfigRobot>();
		ArrayList<ConfigStorageShelf> acss = new ArrayList<ConfigStorageShelf>();
		
		for (Location l : warehouse.getWarehouse().keySet()) {
			for (Actor a : warehouse.getWarehouse().get(l)) {
				if (a instanceof Robot) {
					ConfigRobot cr = acc.robotToConfigRobot((Robot)a);
					acr.add(cr);
				} else if (a instanceof PackingStation) {
					ConfigPackingStation cps = acc.packingStationToConfigPackingStation((PackingStation) a);
					acps.add(cps);
				} else if (a instanceof StorageShelf) {
					ConfigStorageShelf css = acc.storageShelfToConfigStorageShelf((StorageShelf) a);
					acss.add(css);
				}
				for (Order o : orders) {
					ConfigOrder co = acc.orderToConfigOrder(o);
					aco.add(co);
				}
			}
		}
		
		//Write configfile to WarehouseController and update grid
		
		tickCounter++;
		System.out.println("No. Ticks:"+tickCounter);
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		System.out.println(stopString);
		return stopString;
	}// continueSimulation

	public void dispatch() {

	}// dispatch

	public void generateReport() {
    
  }// generate report

  public void reset() {
		running = true;
		actors.clear();
		warehouse.clear();
		updateWarehouse.clear();
	}
	
	
}
