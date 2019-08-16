package warehouse.model.estimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import warehouse.model.Actor;
import warehouse.model.Location;

public class ManhattanCostEstimator implements ICostEstimation {
	
	@Override
	public int estimateCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int estimateCost(Location curr, Location dest, HashMap<Location, Actor> map) {
		HashMap<Location, Integer> mapBreakdown = new HashMap<Location, Integer>();
		//Coordinates for destination
		int destYPos = dest.getRow();
		int destXPos = dest.getCol();
		//Coordinates for start Location
		int currYPos = curr.getRow();
		int currXPos = curr.getCol();
		for (Location l : map.keySet()) {
			//Get this Location's coordinates
			int ypos = l.getRow();
			int xpos = l.getCol();
			
			int ydiff = Math.abs(destYPos) - Math.abs(ypos);
			
			int xdiff = Math.abs(destXPos) - Math.abs(xpos);
			
			
			int cost = ydiff + xdiff;
			
			
			
			mapBreakdown.put(l, cost);
			
		}
		//mapBreakdown.add(e)
		return 0;
	}
	
	public HashMap<Location, Integer> getCostMap(Location dest, HashMap<Location, ArrayList<Actor>> map){
		HashMap<Location, Integer> mapBreakdown = new HashMap<Location, Integer>();
		//Coordinates for destination
		int destXPos = dest.getCol();
		int destYPos = dest.getRow();
		

		for (Location l : map.keySet()) {
			//Get this Location's coordinates
			int xpos = l.getCol();
			int ypos = l.getRow();
			
			int xdiff = Math.abs(destXPos) - Math.abs(xpos);
			int ydiff = Math.abs(destYPos) - Math.abs(ypos);
			
			
			int cost = Math.abs(ydiff) + Math.abs(xdiff);
			
			System.out.println("Cell["+xpos+","+ypos+"]:- Value:"+cost);
			
			
			mapBreakdown.put(l, cost);
			
		}
		//mapBreakdown.add(e)
		return mapBreakdown;
		
	}

	

}
