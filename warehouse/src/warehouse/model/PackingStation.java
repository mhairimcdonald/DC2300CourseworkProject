package warehouse.model;

public class PackingStation implements Actor {

	private int noOfItems;
	private int ticksToPackItem;
	
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
		
	}//dispatch
	
	public void idle() {
		
	}//idle
	
	public void pack() {
		
	}//pack
	
	public void receive() {
		
	}//receive

}
