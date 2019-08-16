package warehouse.model.testCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import warehouse.model.*;
import warehouse.model.estimation.ManhattanCostEstimator;
import warehouse.model.path.PathMapper;

class RobotTests {
	
	private static Location createLocation(int col, int row) {
		Location l = new Location();
		l.setCol(col);
		l.setRow(row);
		return l;
	}
	
	private HashMap<Location, ArrayList<Actor>> setup(){
		Location l1 = createLocation(0, 0); //Start
		Location l2 = createLocation(0, 1); //adjec
		Location l3 = createLocation(0, 2);
		Location l4 = createLocation(1, 0); //adjec
		Location l5 = createLocation(1, 1);
		Location l6 = createLocation(1, 2);
		Location l7 = createLocation(2, 0);
		Location l8 = createLocation(2, 1); //Destination
		Location l9 = createLocation(2, 2);
		
		HashMap<Location, ArrayList<Actor>> mapState = new HashMap<Location, ArrayList<Actor>>();
		
		ArrayList<Actor> ar1 = new ArrayList<Actor>();
		Actor a = new PackingStation();
		ar1.add(a);
		
		mapState.put(l1, ar1); //Start
		mapState.put(l2, ar1); //Adjec
		mapState.put(l3, ar1);
		mapState.put(l4, ar1); //Adjec
		mapState.put(l5, ar1); 
		mapState.put(l6, ar1);
		mapState.put(l7, ar1);
		mapState.put(l8, ar1); //Destinatition
		mapState.put(l9, ar1);
		
		return mapState;
	}

	@Test
	void tickTestMoveTowardsDest() {
		Location l1 = createLocation(0, 0); //Start
		Location l2 = createLocation(0, 1); //adjec
		Location l3 = createLocation(0, 2);
		Location l4 = createLocation(1, 0); //adjec
		Location l5 = createLocation(1, 1);
		Location l6 = createLocation(1, 2);
		Location l7 = createLocation(2, 0);
		Location l8 = createLocation(2, 1); //Destination
		Location l9 = createLocation(2, 2);
		
		Location destination = l8;
		Location location = l1;
		
		HashMap<Location, LinkedList<Actor>> mapState = new HashMap<Location, LinkedList<Actor>>();
		
		LinkedList<Actor> ar1 = new LinkedList<Actor>();
		Actor a = new PackingStation();
		ar1.add(a);
		
		mapState.put(l1, ar1); //Start
		mapState.put(l2, ar1); //Adjec
		mapState.put(l3, ar1);
		mapState.put(l4, ar1); //Adjec
		mapState.put(l5, ar1); 
		mapState.put(l6, ar1);
		mapState.put(l7, ar1);
		mapState.put(l8, ar1); //Destinatition
		mapState.put(l9, ar1);
		
		
		Robot r = new Robot();
		ChargingPod cp = new ChargingPod();
		
		
		r.setUID("r1");
		r.setChargePodID("c1");
		r.setCharging(false);
		r.setMaxCharge(20);
		r.setCurrentCharge(r.getMaxCharge());
		r.addDestinationsLast(destination);
		r.setOrder(new Order());
		r.setCurrentDestination(destination);
		r.setItem(false);
		r.setLocation(location);
		r.setChargePodLocation(location);
		cp.setUID("c1");
		cp.setLocation(location);
		cp.setChargingSpeed(2);
		
		Location l = r.tick(mapState);
		
		assertNotNull(l);
		assertEquals(l.getRow(), 1);
		assertEquals(l.getCol(), 0);
	}

	@Test
	void tickTestLowCharge() {	
		Location l1 = createLocation(0, 0); //Start
		Location l2 = createLocation(0, 1); //adjec
		Location l3 = createLocation(0, 2);
		Location l4 = createLocation(1, 0); //adjec
		Location l5 = createLocation(1, 1);
		Location l6 = createLocation(1, 2);
		Location l7 = createLocation(2, 0);
		Location l8 = createLocation(2, 1); //Destination
		Location l9 = createLocation(2, 2);
		
		Location destination = l8;
		Location location = l1;
		
		HashMap<Location, LinkedList<Actor>> mapState = new HashMap<Location, LinkedList<Actor>>();
		
		LinkedList<Actor> ar1 = new LinkedList<Actor>();
		Actor a = new PackingStation();
		ar1.add(a);
		
		mapState.put(l1, ar1); //Start
		mapState.put(l2, ar1); //Adjec
		mapState.put(l3, ar1);
		mapState.put(l4, ar1); //Adjec
		mapState.put(l5, ar1); 
		mapState.put(l6, ar1);
		mapState.put(l7, ar1);
		mapState.put(l8, ar1); //Destinatition
		mapState.put(l9, ar1);
		
		
		Robot r = new Robot();
		ChargingPod cp = new ChargingPod();
		
		
		r.setUID("r1");
		r.setChargePodID("c1");
		r.setCharging(false);
		r.setMaxCharge(3);
		r.setCurrentCharge(r.getMaxCharge());
		r.addDestinationsLast(destination);
		r.setCurrentDestination(destination);
		r.setOrder(new Order());
		r.setItem(false);
		r.setLocation(l3);
		r.setChargePodLocation(location);
		cp.setUID("c1");
		cp.setLocation(location);
		cp.setChargingSpeed(2);
		
		System.out.println("Starting: ["+r.getLocation().getCol()+","+r.getLocation().getRow()+"]");
		Location l = r.tick(mapState);
		
		assertNotNull(l);
		assertEquals(r.getCurrentDestination(), cp.getLocation());
		assertEquals(l, l2);
		System.out.println("Ending: ["+l.getCol()+","+l.getRow()+"]");
		assertEquals(l2.getCol(), l.getCol());
		assertEquals(l2.getRow(), l.getRow());	
	}
	
	@Test
	void crashTestCrashed() {
		Location l1 = createLocation(0, 0); 
		Location l2 = createLocation(0, 1);

		
		HashMap<Location, ArrayList<Actor>> mapState = new HashMap<Location, ArrayList<Actor>>();
		
		ArrayList<Actor> ar1 = new ArrayList<Actor>();
		Robot r1 = new Robot();
		r1.setLocation(l1);
		Robot r2 = new Robot();
		r2.setLocation(l1);
		ar1.add(r1);
		ar1.add(r2);
		
		mapState.put(l1, ar1); 
		mapState.put(l2, ar1); 
		
		assertTrue(r1.crash(mapState));
	}
}
