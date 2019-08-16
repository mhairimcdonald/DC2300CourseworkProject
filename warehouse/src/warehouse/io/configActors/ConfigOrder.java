package warehouse.io.configActors;

import java.util.ArrayList;

public class ConfigOrder {
	
	private int ticksToPack;
	private ArrayList<String> storageLocations;
	
	public ConfigOrder() {
		storageLocations = new ArrayList<String>();
	}
	
	public ConfigOrder(String ticksToPack, ArrayList<String> storageLocations) throws NumberFormatException {
		this.ticksToPack = Integer.parseInt(ticksToPack);
		this.storageLocations = storageLocations;
	}

	public int getTicksToPack() {
		return ticksToPack;
	}

	public void setTicksToPack(String ticksToPack) throws NumberFormatException {
		this.ticksToPack = Integer.parseInt(ticksToPack);
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

	public void setTicksToPack(int ticksToPack) {
		this.ticksToPack = ticksToPack;
		
	}

}
