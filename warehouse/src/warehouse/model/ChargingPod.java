package warehouse.model;

public class ChargingPod implements Actor {

	private int chargingSpeed;
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}//tick
	
	//This method deals with charging the pods in the simulation. 
	public void charge(Robot currentRobot) {
		currentRobot.setCharge(currentRobot.getCharge() + chargingSpeed);
		if (currentRobot.getCharge()> currentRobot.getMaxCharge()) {
			currentRobot.setCharge(currentRobot.getMaxCharge);//Probably a better way of dealing with max charge
		}
		
	}//charge

	public int getChargingSpeed() {
		return chargingSpeed;
	}

	public void setChargingSpeed(int chargingSpeed) {
		this.chargingSpeed = chargingSpeed;
	}
	
	

}
