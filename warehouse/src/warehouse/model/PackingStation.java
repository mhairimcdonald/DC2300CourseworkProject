package warehouse.model;

public class PackingStation implements Actor {

	private int noOfItems;
	private int ticksToPackItem;
	private int x,y;
	private String UID;
	private int ticksToFinishPacking;
	
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

	public int getNoOfItems() {
		return noOfItems;
	}

	// set with number of items delivered by robot, set to 0 when finished packing and items are dispatched
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

	//ask robot to bring certain items to them MAY NOT BE NEEDED
	public void ask() {
		
	}//ask
	
	public void pack() {
		--ticksToFinishPacking; 
	}//pack
	
	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}

}
