package warehouse.model;

import java.util.Set;

public class StorageShelf implements Actor {
	private Location location;
	private String UID;

	public StorageShelf(Location location, String UID) {
		super();
		this.location = location;
		this.UID = UID;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
