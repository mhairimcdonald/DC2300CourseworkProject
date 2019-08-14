package warehouse.io.configActors;

import java.util.ArrayList;

public class ConfigOrder {
	
	private String uID;
	private ArrayList<String> storageLocations;
	
	public ConfigOrder() {}
	
	public ConfigOrder(String uID, ArrayList<String> storageLocations) {
		this.uID = uID;
		this.storageLocations = storageLocations;
	}

	public String getuID() {
		return uID;
	}

	public void setuID(String uID) {
		this.uID = uID;
	}

	public ArrayList<String> getStorageLocations() {
		return storageLocations;
	}
	
	public String getStorageLocation(int i) {
		return storageLocations.get(i);
	}

	public void setStorageLocations(ArrayList<String> storageLocations) {
		this.storageLocations = storageLocations;
	}
	
	public void setStorageLocation(int index, String s) {
		this.storageLocations.set(index, s);
	}
	
	public void addStorageLocation(String s) {
		this.storageLocations.add(s);
	}
	
	public void removeStorageLocation(int index) {
		this.storageLocations.remove(index);
	}

}
