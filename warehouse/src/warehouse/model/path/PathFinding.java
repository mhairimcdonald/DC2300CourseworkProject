package warehouse.model.path;

import java.util.HashMap;
import java.util.LinkedList;

import warehouse.model.Location;

public class PathFinding {

	private LinkedList<Location> positions;
	private HashMap<Location, Integer> destTable;
	
	public PathFinding() {
		positions = new LinkedList<Location>();
		destTable = new HashMap<Location, Integer>();
		
	}
	
	private Location getNextPosition(Location destination) {
		//set locations?
		positions.add(destination);
		destTable.put(destination, 0);
		while (!positions.isEmpty()) {
			Location currentPosition = positions.removeFirst();
			//retrieve table cell for curr position
			//for every adjacent position
			for(Location l : positions) {
				//check if obstacle present
				//retrieve cell for position, 
				//if table cell not set, set value to large number
				//if currentposition value + 1 < adjacent position value
					//set counter(?) for adjacent postition to current value + 1
					//add adjacent position to linked list
			}
		}
		//smallest table cell position
		return null;
	}
	
	private void setLocations(LinkedList locationMap) {
		positions.addAll(locationMap);
	}
	
	private int getCost(Location destination) {
		return 0;
	}
}
