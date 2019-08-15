package warehouse.model;

public class Robot implements Actor {
	
	private Boolean hasItem;
	private int currentCharge;
	private String chargePodID;
	private int maxCharge;
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}//tick
	
	//method to decide if robot is able to accept latest request
	public void requestDecision() {
		
	}//accept
	
	public Boolean getHasItem() {
		return hasItem;
	}

	public void setHasItem(Boolean hasItem) {
		this.hasItem = hasItem;
	}

	public int getCurrentCharge() {
		return currentCharge;
	}

	public void setCurrentCharge(int currentCharge) {
		this.currentCharge = currentCharge;
	}

	public String getChargePodID() {
		return chargePodID;
	}

	public void setChargePodID(String chargePodID) {
		this.chargePodID = chargePodID;
	}

	public int getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(int maxCharge) {
		this.maxCharge = maxCharge;
	}

	public void detectPower() {
		// Not sure if this is needed
	}//detectPower
	
	public void move() {
		// On each tick do we need this to call algorithm to decide what move to take then make the move?
	}//move
	
	
	public void take() {
		//Robot takes item when it detects shelf it was seeking?
	}//take


	
}
