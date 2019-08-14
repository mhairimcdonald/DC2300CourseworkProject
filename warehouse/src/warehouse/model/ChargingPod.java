package warehouse.model;

public class ChargingPod implements Actor {

	private int x, y;
	private String UID;
	private int chargingSpeed;
	
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public int getChargingSpeed() {
		return chargingSpeed;
	}

	public void setChargingSpeed(int chargingSpeed) {
		this.chargingSpeed = chargingSpeed;
	}
	
	

}
