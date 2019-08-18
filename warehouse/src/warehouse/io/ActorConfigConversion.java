package warehouse.io;

import java.util.ArrayList;

import warehouse.io.configActors.*;
import warehouse.model.*;

public class ActorConfigConversion {
	
	public ActorConfigConversion () {
		
	}
	
	public ConfigOrder orderToConfigOrder(Order o) {
		ConfigOrder co = new ConfigOrder();
		
		int ticksToPack = o.getTicksToPack();
		ArrayList<String> itemLocations = o.getItemLocations();
		
		co.setStorageLocations(itemLocations);
		co.setTicksToPack(ticksToPack);
		
		return co;
	}
	
	public Order configOrderToOrder(ConfigOrder co) {
		Order o = new Order();
		
		int ticksToPack = co.getTicksToPack();
		ArrayList<String> itemLocations = co.getStorageLocations();
		
		o.setTicksToPack(ticksToPack);
		o.setItemLocations(itemLocations);
		
		return o;
	}
	
	public ConfigPackingStation packingStationToConfigPackingStation(PackingStation ps) {
		ConfigPackingStation cps = new ConfigPackingStation();
		
		String uID = ps.getUID();
		Location l = ps.getLocation();
		int row = l.getRow();
		int col = l.getCol();
		
		cps.setCol(col);
		cps.setRow(row);
		cps.setLocation(l);
		cps.setuID(uID);
		
		return cps;
	}
	
	public PackingStation configPackingStationToPackingStation(ConfigPackingStation cps) {
		PackingStation ps = new PackingStation();
		
		Location l = cps.getLocation();
		String uID = cps.getuID();
		
		ps.setLocation(l);
		ps.setUID(uID);
		
		return ps;
		
	}
	
	public ConfigRobot robotToConfigRobot(Robot r) {
		ConfigRobot cr = new ConfigRobot();

		String chargingPoduID =  r.getChargePodID();
		Location l = r.getLocation();
		int row = l.getRow();
		int col = l.getCol();
		String uID = r.getUID();
		
		cr.setuID(uID);
		cr.setChargingPoduID(chargingPoduID);
		cr.setLocation(l);
		cr.setCol(col);
		cr.setRow(row);
		
		return cr;
	}
	
	/*
	 * MaxCharge is passed into all ChargingPods during setup
	 * (Capacity). CurrentCharge is set to MaxCharge at that time.
	 */
	
	public Robot configRobotToRobot(ConfigRobot cr) {
		Robot r = new Robot();
		
		String chargingPoduID =  cr.getChargingPoduID();
		Location l = cr.getLocation();
		int row = cr.getRow();
		int col = cr.getCol();
		String uID = cr.getuID();
		
		r.setUID(uID);
		r.setChargePodID(chargingPoduID);
		r.setLocation(l);
		r.setChargePodLocation(l);
		
		return r;
	}
	
	/*
	 * This is only called when the ChargingPod is first made, 
	 * while it shares a space with the Robot. Therefore,
	 * both will share a Location.
	 * Charge speed is passed in to all ChargingPods during setup
	 */
	public ChargingPod configRobotToChargingPod(ConfigRobot cr) {
		ChargingPod cp = new ChargingPod();
		
		String chargingPoduID = cr.getChargingPoduID();
		Location l = cr.getLocation();
		
		cp.setUID(chargingPoduID);
		cp.setLocation(l);
		
		return cp;
	}
	
	public ConfigStorageShelf storageShelfToConfigStorageShelf(StorageShelf ss) {
		ConfigStorageShelf css = new ConfigStorageShelf();
		
		String uID = ss.getUID();
		Location l = ss.getLocation();
		int col = l.getCol();
		int row = l.getRow();
		
		css.setuID(uID);
		css.setLocation(l);
		css.setRow(row);
		css.setCol(col);
		
		return css;
	}
	
	public StorageShelf configStorageShelfToStorageShelf(ConfigStorageShelf css) {
		StorageShelf ss = new StorageShelf();
		
		String UID = css.getuID();
		Location l = css.getLocation();
		
		ss.setUID(UID);
		ss.setLocation(l);
		
		return ss;
		
	}

}
