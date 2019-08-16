package warehouse.model;

public class PackingStation implements Actor {

	private int noOfItems;
	private int ticksToPackItems, ticksToDispatch, ticksPacking;
	private Location location;
	private String UID;
	private Order currentOrder;
	public PackingStation(Location location, String uID) {
		super();
		this.noOfItems = 0;
		this.ticksToPackItems = ticksToPackItems;
		this.ticksToDispatch = 0;
		this.ticksPacking = 0;
		this.location = location;
		this.currentOrder = null;
		UID = uID;
	}


	
	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public int getTicksToPackItems() {
		return ticksToPackItem;
	}

	public void setTicksToPackItems(int ticksToPackItems) {
		this.ticksToPackItems = ticksToPackItems;
	}

	public PackingStation(int noOfItems, int ticksToPackItem, int ticksToDispatch, int ticksPacking, Location location,
			String uID) {
		super();
		this.noOfItems = noOfItems;
		this.ticksToPackItems = ticksToPackItem;
		this.ticksToDispatch = ticksToDispatch;
		this.ticksPacking = ticksPacking;
		this.location = location;
		UID = uID;
	}

	//Basic Constructor
	public PackingStation() {
		// TODO Auto-generated constructor stub
	}



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
	public void tick() {
		// TODO Auto-generated method stub
		if(noOfItems > 0) {
			ticksPacking++;
			if(ticksPacking == ticksToDispatch) {
				dispatch();
			}
		}
		else
	}

}
