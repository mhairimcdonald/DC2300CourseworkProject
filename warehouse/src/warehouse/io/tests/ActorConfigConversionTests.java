package warehouse.io.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import warehouse.io.ActorConfigConversion;
import warehouse.io.configActors.*;
import warehouse.model.*;

class ActorConfigConversionTests {

	@Test
	void orderToConfigOrderTest() {
		ActorConfigConversion acc = new ActorConfigConversion();

		Order o = new Order();
		o.setTicksToPack(10);
		ArrayList<String> itemLocations = new ArrayList<String>();
		itemLocations.add("One");
		itemLocations.add("Two");
		itemLocations.add("Three");
		o.setItemLocations(itemLocations);

		ConfigOrder co = acc.orderToConfigOrder(o);

		assertNotNull(co);
		assertEquals(co.getTicksToPack(), 10);
		assertEquals(co.getStorageLocations().size(), 3);
		assertEquals(co.getStorageLocation(0), "One");
	}

	@Test
	void configOrderToOrderTestd() {
		ActorConfigConversion acc = new ActorConfigConversion();

		ConfigOrder co = new ConfigOrder();
		
		co.setTicksToPack(10);
		ArrayList<String> itemLocations = new ArrayList<String>();
		itemLocations.add("One");
		itemLocations.add("Two");
		itemLocations.add("Three");
		co.setStorageLocations(itemLocations);
		
		Order o = acc.configOrderToOrder(co);
		
		assertNotNull(o);
		assertEquals(o.getTicksToPack(), 10);
		assertEquals(o.getItemLocations().size(), 3);
		assertEquals(o.getItemLocation(0), "One");
	}
	
	@Test
	void packingStationToConfigPackingStationTest() {
		ActorConfigConversion acc = new ActorConfigConversion();
		Location l = new Location(1,2);
		PackingStation ps = new PackingStation();
		ps.setLocation(l);
		ps.setUID("ps01");
		
		ConfigPackingStation cps = acc.packingStationToConfigPackingStation(ps);
		
		assertNotNull(cps);
		assertEquals(cps.getuID(), "ps01");
		assertEquals(cps.getLocation(), l);
		assertEquals(cps.getRow(), 1);
		assertEquals(cps.getCol(), 2);
	}
	
	@Test
	void configPackingStationToPackingStationTest() {
		ActorConfigConversion acc = new ActorConfigConversion();
		Location l = new Location(1,2);
		ConfigPackingStation cps = new ConfigPackingStation();
		cps.setLocation(l);
		cps.setuID("ps01");
		
		PackingStation ps = acc.configPackingStationToPackingStation(cps);
		
		assertNotNull(ps);
		assertEquals(ps.getUID(), "ps01");
		assertEquals(ps.getLocation(), l);
		assertEquals(ps.getLocation().getRow(), 1);
		assertEquals(ps.getLocation().getCol(), 2);
	}
	
	@Test
	void robotToConfigRobotTest() {
		ActorConfigConversion acc = new ActorConfigConversion();
		Location l = new Location(1,2);
		
		Robot r = new Robot();
		r.setUID("r1");
		r.setChargePodID("c1");
		r.setLocation(l);
		
		ConfigRobot cr = acc.robotToConfigRobot(r);
		
		assertNotNull(cr);
		assertEquals(cr.getuID(), "r1");
		assertEquals(cr.getChargingPoduID(), "c1");
		assertEquals(cr.getLocation(), l);
	}
	
	@Test
	void configRobotToRobotTest() {
		ActorConfigConversion acc = new ActorConfigConversion();
		Location l = new Location(1,2);
		
		ConfigRobot cr = new ConfigRobot();
		cr.setuID("r2");
		cr.setChargingPoduID("c2");
		cr.setLocation(l);
		
		Robot r = acc.configRobotToRobot(cr);
		
		assertNotNull(r);
		assertEquals(r.getUID(), "r2");
		assertEquals(r.getChargePodID(), "c2");
		assertEquals(r.getLocation(), l);
	}
	
	@Test
	void configRobotToChargingPodTest() {
		ActorConfigConversion acc = new ActorConfigConversion();
		Location l = new Location(1,2);
		
		ConfigRobot cr = new ConfigRobot();
		cr.setuID("r2");
		cr.setChargingPoduID("c2");
		cr.setLocation(l);
		
		ChargingPod cp = acc.configRobotToChargingPod(cr);
		
		assertNotNull(cp);
		assertEquals(cp.getUID(), "c2");
		assertEquals(cp.getLocation(),l);
	}
	
	@Test
	void storageShelfToConfigStorageShelfTest() {
		ActorConfigConversion acc = new ActorConfigConversion();
		Location l = new Location(1,2);
		
		StorageShelf ss = new StorageShelf();
		ss.setUID("ss1");
		ss.setLocation(l);
		
		ConfigStorageShelf css = acc.storageShelfToConfigStorageShelf(ss);
		
		assertNotNull(css);
		assertEquals(css.getuID(), "ss1");
		assertEquals(css.getLocation(), l);
		assertEquals(css.getRow(), 1);
		assertEquals(css.getCol(), 2);
	}
	
	@Test
	void configStorageShelfToStorageShelfTest() {
		ActorConfigConversion acc = new ActorConfigConversion();
		Location l = new Location(1,2);
		
		ConfigStorageShelf css = new ConfigStorageShelf();
		css.setuID("ss1");
		css.setLocation(l);
		
		StorageShelf ss = acc.configStorageShelfToStorageShelf(css);
		
		assertNotNull(ss);
		assertEquals(ss.getUID(), "ss1");
		assertEquals(ss.getLocation(), l);
	}
	
	
	

}
