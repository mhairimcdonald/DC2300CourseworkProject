package warehouse.model.path;

import java.awt.List;
import java.util.HashMap;
import java.util.LinkedList;

import warehouse.model.Actor;
import warehouse.model.Location;
import warehouse.model.Robot;
import warehouse.model.estimation.*;

public class PathMapper {
	
	private LinkedList<Location> positions;
	private HashMap<Location, Integer> valueMap;
	private HashMap<Location, Actor> currMap;
	private HashMap<Location, HashMap<Location, Actor>> mapState;
	
	public PathMapper() {
		positions = new LinkedList<Location>();
		valueMap = new HashMap<Location, Integer>();
		currMap = new HashMap<Location, Actor>();
		mapState = new HashMap<Location, HashMap<Location, Actor>>();
	}

	public LinkedList<Location> getPositions() {
		return positions;
	}

	//Used in Simulation to setup the Pathfinding
	public void setPositions(LinkedList<Location> queueOfPositions) {
		this.positions = queueOfPositions;
	}
	
	public HashMap<Location, Integer> getValueMap() {
		return valueMap;
	}

	public void setValueMap(HashMap<Location, Integer> map) {
		this.valueMap = map;
	}
	
	public void createMap(LinkedList<Location> l) {
		
	}

	public HashMap<Location, HashMap<Location, Actor>> getMapState() {
		return mapState;
	}

	public void setMapState(HashMap<Location, HashMap<Location, Actor>> mapState) {
		this.mapState = mapState;
	}
	
	public void getNextLocation(Location currentLocation, Location destination) {
		ManhattanCostEstimator est = new ManhattanCostEstimator();
		//2. Create shortest distance from dest map by value
		setValueMap(est.getCostMap(destination, currMap));
		//3. Add destination to end of list
		positions.add(destination);
		//4. Set table cell of destination to 0
		valueMap.put(destination, 0);
		//5. While the queue is not empty
		while (!positions.isEmpty()) {
			//a) Retrieve and remove first Location from queue.
			Location pos = positions.getFirst();
			positions.removeFirst();
			/*
			 * b) Retrieve the table cell for that position
			 * Table is stored as HashMap<Location, <Actor>> in Warehouse
			 */
		
		}
	}
	
	public Location getNextNearest(Location current){
	
		int lowestValue = Integer.MAX_VALUE;
		Location bestLocation = new Location();
		//Get the surrounding location objects
		for (Location l : getSurroundingLocations(current)) {
			//For each of them, get their Value from valueMap.
			if (l==null) {
				break;
			}
			int locValue = valueMap.get(l);
			System.out.println("Cell["+l.getCol()+","+l.getRow()+"]:- Value:"+locValue);
			/*
			 * Check for lowest value and store the 
			 * Location with the lowest value
			 */
			if (locValue < lowestValue) {
				lowestValue = locValue;
				bestLocation = l;
			}
		}
		
		/*
		 * If lowestValue = Integer.MAX_VALUE then there are no
		 * movable spots. Robot should idle. Return null.
		 */
		
		if (lowestValue == Integer.MAX_VALUE) {
			bestLocation = null;
		}
		
		/*
		 * bestLocation is the location surrounding the current
		 * Location that is closest to its destination. This is 
		 * where the Robot will want to move.
		 */
		
		return bestLocation;
		
	}
	
	public int getCurrentDistanceCost(Location current) {
		int currXPos = current.getRow();
		int currYPos = current.getCol();
		
		for (Location l : valueMap.keySet()) {
			if (l.getRow() == currYPos && l.getCol() == currXPos) {
				return valueMap.get(l);
			}
		}
		return Integer.MAX_VALUE;
	}
	
	/*
	 * This returns a Location[] List containing object references
	 * to the Locations that surround the current cell's location.
	 */
	public Location[] getSurroundingLocations(Location current){
		int currXPos = current.getCol();
		int currYPos = current.getRow();
		Location[] surr = new Location[4];
		int i = 0;
		for (Location l : valueMap.keySet()) {
			if (l.getCol() == currXPos && (l.getRow() == currYPos+1 || l.getRow() == currYPos-1)) {
				if (i<4) {
					System.out.println("Adding ["+l.getCol()+","+l.getRow()+"]");
					surr[i] = l;
					i++;
				} else {
					System.out.println("Surrounding Tile Error!");
					return null;
				}
				
			} else if (l.getRow() == currYPos && (l.getCol() == currXPos+1 || l.getCol() == currXPos-1)) {
				if (i<4) {
					System.out.println("Adding ["+l.getCol()+","+l.getRow()+"]");
					surr[i] = l;
					i++;
				} else {
					System.out.println("Surrounding Tile Error!");
					return null;
				}
			}
		}
		return surr;
	}

	public HashMap<Location, Actor> getCurrMap() {
		return currMap;
	}

	public void setCurrMap(HashMap<Location, Actor> currMap) {
		this.currMap = currMap;
	}
	
	public void setObstructions() {
		for (Location l : currMap.keySet()) { //Iterate over all Locations in the currentMap
			if (currMap.get(l) instanceof Robot) { //
				setObstructed(l);
			}
		}
		
	}
	
	public void setObstructed(Location l) {
		valueMap.replace(l, valueMap.get(l), Integer.MAX_VALUE);
	}

}
