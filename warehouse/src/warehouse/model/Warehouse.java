package warehouse.model;

public class Warehouse {
	private WarehouseStats stats;
	
	// Height and Width of the Warehouse
	private int height;
	private int width;
	// Storage for the actors
	private Actor[][] warehouse;

	public Warehouse(int height, int width) {
		this.height = height;
		this.width = width;
		//Create two-dimensional array of Actors, stored by position
		warehouse = new Actor[height][width];
		this.stats = new WarehouseStats();
	}
	
	//Clear the warehouse of all Actors
	public void clear() {
		for (int row = 0; row < this.height; row++) {
			for (int col = 0; col < this.width; col++) {
				warehouse[row][col] = null;
			}
		}
	}
	
	public void place(Actor actor, int row, int col) {
		place(actor, new Location(row,col));
	}
	
	public void place(Actor actor, Location loc) {
		place(actor, loc);
	}
	
	public Actor getObjectAt(Location loc) {
		return getObjectAt(loc.getRow(), loc.getCol());
	}
	
	public Actor getObjectAt(int row, int col) {
		return warehouse[row][col];
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void reset() {
		stats.reset();
	}
	
	
	public void operate() {
		//not sure if needed, might need method to create the actual warehouse just
	}//operate
}
