package warehouse.model.estimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import warehouse.model.Actor;
import warehouse.model.Location;
import warehouse.model.PackingStation;
import warehouse.model.Robot;
import warehouse.model.path.PathMapper;

public class Tester {

	public static void main(String[] args) {

		ManhattanCostEstimator m = new ManhattanCostEstimator();
		PathMapper pm = new PathMapper();
		
		Location l1 = createLocation(0, 0); //adjec
		Location l2 = createLocation(0, 1);
		Location l3 = createLocation(0, 2); //adjec
		Location l4 = createLocation(1, 0);
		Location l5 = createLocation(1, 1); //adjec
		Location l6 = createLocation(1, 2);
		Location l7 = createLocation(2, 0);
		Location l8 = createLocation(2, 1);
		Location l9 = createLocation(2, 2);
		Actor a = new Robot();
		Actor b = null;
		Actor c = new PackingStation();
		LinkedList<Actor> aa = new LinkedList<Actor>(); //Has just robot
		LinkedList<Actor> ab = new LinkedList<Actor>(); //Has no robot
		LinkedList<Actor> ac = new LinkedList<Actor>(); //Has robot and something else
		aa.add(a);
		ab.add(b);
		ab.add(c);
		ac.add(a);
		ac.add(c);
		
		HashMap<Location, LinkedList<Actor>> baseMap = new HashMap<Location, LinkedList<Actor>>();
		
		Location curr = l2;
		Location dest = l7;
		
		System.out.println("Destination["+dest.getCol()+","+dest.getRow()+"]");
		System.out.println("Current["+curr.getCol()+","+curr.getRow()+"]");
		
		baseMap.put(l1, aa); //adjec
		baseMap.put(l2, ab);
		baseMap.put(l3, ab); //adjec
		baseMap.put(l4, ac);
		baseMap.put(l5, ac); //adjec
		baseMap.put(l6, ac);
		baseMap.put(l7, ac);
		baseMap.put(l8, ac);
		baseMap.put(l9, ab);
		
		HashMap<Location, Integer> valueMap = m.getCostMap(dest, baseMap);

		pm.setValueMap(valueMap);
		
		//Initialise your PM
		pm.setMapState(baseMap);
		
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
