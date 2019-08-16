package warehouse.model;

public interface Actor {
	
	public Location getLocation();
	
	public void tick();

	void perform();

}
