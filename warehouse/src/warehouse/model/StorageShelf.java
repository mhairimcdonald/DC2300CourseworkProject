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
	
	public StorageShelf() {
		
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
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

}
