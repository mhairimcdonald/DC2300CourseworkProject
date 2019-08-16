package warehouse.model.path;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import warehouse.model.Actor;
import warehouse.model.Location;
import warehouse.model.Robot;
import warehouse.model.estimation.*;

public class PathMapper {
	
	private HashMap<Location, Integer> valueMap;
	private HashMap<Location, LinkedList<Actor>> mapState;
	
	public PathMapper() {
		this.valueMap = new HashMap<Location, Integer>();
		this.mapState = new HashMap<Location, LinkedList<Actor>>();
	}
	
	public PathMapper(HashMap<Location, LinkedList<Actor>> mapState, Location destination) {
		this.mapState = mapState;
		ManhattanCostEstimator m = new ManhattanCostEstimator();
		this.valueMap = m.getCostMap(destination, mapState);
	}
	
	public void setupMapper(HashMap<Location, LinkedList<Actor>> mapState, Location destination) {
		this.mapState = mapState;
		ManhattanCostEstimator m = new ManhattanCostEstimator();
		this.valueMap = m.getCostMap(destination, mapState);
		setObstructions();
	}
	
	public HashMap<Location, Integer> getValueMap() {
		return valueMap;
	}

	public void setValueMap(HashMap<Location, Integer> map) {
		this.valueMap = map;
	}
	
	
	public void createMap(LinkedList<Location> l) {
		
	}

	public HashMap<Location, LinkedList<Actor>> getMapState() {
		return mapState;
	}

	public void setMapState(HashMap<Location, LinkedList<Actor>> mapState) {
		this.mapState = mapState;
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
					System.out.println("Surround: ["+l.getCol()+","+l.getRow()+"]");
					surr[i] = l;
					i++;
				} else {
					System.out.println("Surrounding Tile Error!");
					return null;
				}
				
			} else if (l.getRow() == currYPos && (l.getCol() == currXPos+1 || l.getCol() == currXPos-1)) {
				if (i<4) {
					System.out.println("Surround: ["+l.getCol()+","+l.getRow()+"]");
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
	
	public void setObstructions() {
		for (Location l : mapState.keySet()) { //Iterate over all Locations in the currentMap
			for (Actor a : mapState.get(l)) {
				if (a instanceof Robot) { //
					setObstructed(l);
				}
			}
			
		}
		
	}
	
	public void setObstructed(Location l) {
		valueMap.replace(l, valueMap.get(l), Integer.MAX_VALUE);
	}

	

}
