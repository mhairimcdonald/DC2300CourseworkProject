package warehouse.model.estimation;

import java.util.HashMap;

import warehouse.model.Actor;
import warehouse.model.Location;
import warehouse.model.PackingStation;
import warehouse.model.Robot;
import warehouse.model.path.PathMapper;

public class Tester {

	public static void main(String[] args) {

		ManhattanCostEstimator m = new ManhattanCostEstimator();
		PathMapper pm = new PathMapper();
		
		Location l1 = createLocation(0, 0);
		Location l2 = createLocation(0, 1);
		Location l3 = createLocation(0, 2);
		Location l4 = createLocation(1, 0);
		Location l5 = createLocation(1, 1);
		Location l6 = createLocation(1, 2);
		Location l7 = createLocation(2, 0);
		Location l8 = createLocation(2, 1);
		Location l9 = createLocation(2, 2);
		Actor a = new Robot();
		Actor b = null;
		Actor c = new PackingStation();
		HashMap<Location, Actor> map = new HashMap<Location, Actor>();
		
		Location curr = l2;
		Location dest = l7;
		
		System.out.println("Destination["+dest.getCol()+","+dest.getRow()+"]");
		System.out.println("Current["+curr.getCol()+","+curr.getRow()+"]");
		
		map.put(l1, a);
		map.put(l2, b);
		map.put(l3, a);
		map.put(l4, a);
		map.put(l5, a);
		map.put(l6, c);
		map.put(l7, c);
		map.put(l8, c);
		map.put(l9, b);
		
		HashMap<Location, Integer> valueMap = m.getCostMap(dest, map);

		pm.setValueMap(valueMap);
		
		//Initialise your PM
		pm.setCurrMap(map);
		
		Location[] surrLoc = pm.getSurroundingLocations(curr);
		System.out.println("Current Location["+curr.getCol()+","+curr.getRow()+"]");
		for (Location l : surrLoc) {
			if (l!=null) {
				System.out.println("X:"+l.getCol()+"|Y:"+l.getRow());
			}
		}
		
		Location nextNearest = pm.getNextNearest(curr);
		System.out.println("Next Nearest:["+nextNearest.getCol()+","+nextNearest.getRow()+"]");
		
		pm.setObstructions();
		
		nextNearest = pm.getNextNearest(curr);
		if (nextNearest != null) {
			System.out.println("Next Nearest:["+nextNearest.getCol()+","+nextNearest.getRow()+"]");
		} else {
			System.out.println("No movable locations!");
		}
		


	}
	
	private static Location createLocation(int col, int row) {
		Location l = new Location();
		l.setCol(col);
		l.setRow(row);
		return l;
	}

}
