package warehouse.model;

public class Robot implements Actor {
	
	private Boolean hasItem;
	private int currentCharge;
	private String chargePodID;
	private int maxCharge;
	private int noOfItems;
	private Location location;
	private String UID;
	
	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
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


	@Override
	public void perform() {
		//positon of robot
		//not sure how to handle where robot aims at
		if(objectAtLocation.getUID() = targetUID) {
			Actor actor = objectAtLocation;
			switch(actor.getName()) {
			case("ChargingPod"):{
				if(currentCharge < maxCharge) {
					((ChargingPod) actor).charge(this);
				}
			}
			case("PackingStation"):{ 
				((PackingStation) actor).pack(this);
			}
			case("StorageShelf"):{noOfItems++;}
			case("Robot"):{crash();}
			default:{;}
			
			}
		}
	}

	
}
