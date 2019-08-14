package warehouse.io;

import java.util.ArrayList;

import warehouse.io.configActors.*;

public class ConfigFile {
	
	private int width;
	private int height;
	private int capacity;
	private int chargeSpeed;
	private ArrayList<ConfigRobot> robots;
	private ArrayList<ConfigStorageShelf> shelves;
	private ArrayList<ConfigPackingStation> stations;
	private ArrayList<ConfigOrder> orders;
	
	public ConfigFile() {
		
		robots = new ArrayList<ConfigRobot>();
		shelves = new ArrayList<ConfigStorageShelf>();
		stations = new ArrayList<ConfigPackingStation>();
		orders = new ArrayList<ConfigOrder>();
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getChargeSpeed() {
		return chargeSpeed;
	}

	public void setChargeSpeed(int chargeSpeed) {
		this.chargeSpeed = chargeSpeed;
	}

	public ArrayList<ConfigRobot> getPodRobot() {
		return robots;
	}

	public void addRobot(ConfigRobot r) {
		this.robots.add(r);
	}

	public ArrayList<ConfigStorageShelf> getShelf() {
		return shelves;
	}

	public void addShelf(ConfigStorageShelf s) {
		this.shelves.add(s);
	}

	public ArrayList<ConfigPackingStation> getStation() {
		return stations;
	}

	public void addStation(ConfigPackingStation s) {
		this.stations.add(s);
	}

	public ArrayList<ConfigOrder> getOrder() {
		return orders;
	}

	public void addOrder(ConfigOrder s) {
		this.orders.add(s);
	}

}
