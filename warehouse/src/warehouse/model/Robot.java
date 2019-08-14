package warehouse.model;

public class Robot implements Actor {
	
	private Boolean hasItem;
	private int currentCharge;
	private String chargePodID;
	private int maxCharge;
	private int x,y;
	private String UID;
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}//tick
	
	//method to decide if robot is able to accept latest request, will need the path finding algortihms
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

	public void setChargePodID(String chargePodID) {
		this.chargePodID = chargePodID;
	}

	public int getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(int maxCharge) {
		this.maxCharge = maxCharge;
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}
	
}
