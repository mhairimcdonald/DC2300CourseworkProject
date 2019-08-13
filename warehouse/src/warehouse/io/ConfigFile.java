package warehouse.io;

import java.util.ArrayList;

import warehouse.model.*;

public class ConfigFile {
	
	private int width;
	private int height;
	private int capacity;
	private int chargeSpeed;
	private ArrayList<Robot> robot;
	private ArrayList<ChargingPod> pod;
	private ArrayList<StorageShelf> shelf;
	private ArrayList<PackingStation> station;
	private ArrayList<Order> order;
	
	public ConfigFile() {
		
		robot = new ArrayList<Robot>();
		pod = new ArrayList<ChargingPod>();
		shelf = new ArrayList<StorageShelf>();
		station = new ArrayList<PackingStation>();
		order = new ArrayList<Order>();
		
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

	public ArrayList<Robot> getPodRobot() {
		return robot;
	}

	public void addRobot(Robot r) {
		this.robot.add(r);
	}

	public ArrayList<StorageShelf> getShelf() {
		return shelf;
	}

	public void addShelf(StorageShelf s) {
		this.shelf.add(s);
	}

	public ArrayList<PackingStation> getStation() {
		return station;
	}

	public void addStation(PackingStation s) {
		this.station.add(s);
	}

	public ArrayList<Order> getOrder() {
		return order;
	}

	public void addOrder(Order s) {
		this.order.add(s);
	}

	public ArrayList<ChargingPod> getPod() {
		return pod;
	}

	public void addPod(ChargingPod p) {
		this.pod.add(p);
	}

}
