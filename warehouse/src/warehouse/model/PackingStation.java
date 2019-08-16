package warehouse.model;

import java.util.LinkedList;

public class PackingStation implements Actor {

	private Location location;
	private String UID;
	private Order currentOrder;
	private int ticksLeft;
	private Robot robot;
	private boolean haveItems;
	
	public PackingStation(Location location, String uID) {
		super();
		this.location = location;
		this.currentOrder = null;
		UID = uID;
		this.ticksLeft = 0;
		this.robot = null;
		this.haveItems = false;
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	public int getTicksLeft() {
		return ticksLeft;
	}

	public void setTicksLeft(int ticksLeft) {
		this.ticksLeft = ticksLeft;
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public boolean isHaveItems() {
		return haveItems;
	}

	public void setHaveItems(boolean haveItems) {
		this.haveItems = haveItems;
	}

	public PackingStation() {
		this.haveItems = false;
		this.currentOrder = null;
		this.robot = null;
		this.ticksLeft = -1;
	}
	
	public void tick(LinkedList<Robot> robots) {
		if (currentOrder == null) {return;} //If no order, wait;
		if (ticksLeft <= 0) {//If you've completed your job
			ticksLeft = -1;
			currentOrder = null;
			robot = null;
			haveItems = false;
		}
		if (robot == null) { //If no robot, search robots for a free robot
			for (Robot r : robots) {
				if (r.needsOrder(currentOrder, location)) {
					robot = r;
					break;
				}
			}
		} else {//If you have a robot, check if they're in your place.
			if (robot.getLocation()==location) {
				if (robot.takeOrder()) {
					haveItems = true;
				}
			}
		}
		if (haveItems) {
			ticksLeft -= 1;
		}
		
	}
	
	/*
	 * If this packing station needs an order,
	 * accept the order and return true, otherwise
	 * return false
	 */
	public boolean needOrder(Order o) {
		if (currentOrder==null) {
			currentOrder = o;
			ticksLeft = o.getTicksToPack();
			return true;
		} else {
			return false;
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}
}
