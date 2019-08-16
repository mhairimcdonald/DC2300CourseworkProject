package warehouse.model;

import java.util.HashMap;
import java.util.LinkedList;

import warehouse.io.ConfigFile;

public class WarehouseToConfigFile {
	
	WarehouseToConfigFile(){
		
	}
	
	public ConfigFile parseWarehouse(HashMap<Location,LinkedList<Actor>> warehouse) {
		ConfigFile cf = new ConfigFile();
		
		return cf;
	}

}
