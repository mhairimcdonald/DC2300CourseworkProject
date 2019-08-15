package warehouse.model;

import java.util.ArrayList;
import java.util.List;

import warehouse.io.ConfigFile;

public class Simulation {
	private int tickCounter = 0;
	private List<Actor> actors;
	private List<Actor> newActors;
	private Warehouse warehouse;
	private Warehouse updateWarehouse;
	private int step;
	private boolean running = false;

	public void start(ConfigFile cf) {
		int width = cf.getWidth();
		int height = cf.getHeight();
		actors = new ArrayList<Actor>();
		newActors = new ArrayList<Actor>();
		warehouse = new Warehouse(width, height);
		updateWarehouse = new Warehouse(width, height);
		
		reset();
	}// start
	
	public void continueSimulation() {
		
	}//continueSimulation
	
	public void dispatch() {
		
	}//dispatch
	
	public void generateReport() {
		
	}// generate report
	
	public void start() {
		
	}//start

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
