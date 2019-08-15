package warehouse.model;

public class PackingStation implements Actor {

	private int noOfItems;
	private int ticksToPackItem, ticksToDispatch, ticksPacking;
	private Location location;
	private String UID;
	
	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public int getTicksToPackItem() {
		return ticksToPackItem;
	}

	public void setTicksToPackItem(int ticksToPackItem) {
		this.ticksToPackItem = ticksToPackItem;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}//tick

	public void ask() {
		
	}//ask
	
	public void dispatch() {
		//add completed order to report
		noOfItems = 0;
		ticksToDispatch = 0;
		ticksPacking = 0;
		
	}//dispatch
	
	public void idle() {
		
	}//idle
	
	public void pack(Robot robot) {
		noOfItems = robot.get
	}//pack
	
	public int getTicksToDispatch() {
		return ticksToDispatch;
	}

	public void setTicksToDispatch(int ticksToDispatch) {
		this.ticksToDispatch = ticksToDispatch;
	}

	public int getTicksPacking() {
		return ticksPacking;
	}

	public void setTicksPacking(int ticksPacking) {
		this.ticksPacking = ticksPacking;
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

	public void receive() {
		
	}//receive

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		if(noOfItems > 0) {
			ticksPacking++;
			if(ticksPacking == ticksToDispatch) {
				dispatch();
			}
		}
	}

}
