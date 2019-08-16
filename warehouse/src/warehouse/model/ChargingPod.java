package warehouse.model;

public class ChargingPod implements Actor {

	private Location location;
	private String UID;
	private int chargingSpeed;
	
	public ChargingPod(Location location, String uID, int chargingSpeed) {
		super();
		this.location = location;
		UID = uID;
		this.chargingSpeed = chargingSpeed;
	}
	
	public ChargingPod() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
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

	
	
	

}
