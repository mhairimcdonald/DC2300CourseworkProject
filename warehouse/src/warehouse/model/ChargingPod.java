package warehouse.model;

import java.util.ArrayList;

public class ChargingPod implements Actor {

	private Location location;
	private String UID;
	private int chargingSpeed;
	private Robot matchingRobot;
	
	public ChargingPod(Location location, String uID, int chargingSpeed, Robot matchingRobot) {
		super();
		this.location = location;
		UID = uID;
		this.chargingSpeed = chargingSpeed;
		this.matchingRobot = matchingRobot;
	}
	
	
	
	public Robot getMatchingRobot() {
		return matchingRobot;
	}



	public void setMatchingRobot(Robot matchingRobot) {
		this.matchingRobot = matchingRobot;
	}



	public ChargingPod() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		
		
	}//tick
	
	public void tick(ArrayList<Robot> robots) {
		for(Robot robot: robots) {
			Location robotLocation = robot.getLocation();
			if((robotLocation == this.getLocation()) && (this.getMatchingRobot().getUID() == robot.getUID())) {
				charge(robot);
			}
		}
		
	}//tick
	
	//This method deals with charging the pods in the simulation. 
	public void charge(Robot currentRobot) {
		currentRobot.setCurrentCharge(currentRobot.getCurrentCharge() + chargingSpeed);
		if (currentRobot.getCurrentCharge()> currentRobot.getMaxCharge()) {
			currentRobot.setCurrentCharge(currentRobot.getMaxCharge());//Probably a better way of dealing with max charge
		}
		
	}//charge

	public int getChargingSpeed() {
		return chargingSpeed;
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

	public void setChargingSpeed(int chargingSpeed) {
		this.chargingSpeed = chargingSpeed;
	}



	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
