package warehouse.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import warehouse.model.*;

import java.io.InputStream;

public class FileLoader {

	public FileLoader() {

	}

	public ConfigFile parseFile(File f) {
		Scanner read = null;
		ConfigFile cf = null;
		try {
			read = new Scanner(f);
			if (read.hasNext()) {
				if (!read.nextLine().equals("format 1")) {
					return null;
				} else {
					String n = "";
					//This is standing in place for the Warehouse, as that's what this will actually make
					cf = new ConfigFile();
					while (read.hasNext()) {
						//Sorts through the input and appends it to the Warehouse (Currently ConfigFile)
						n = read.nextLine();
						checkAndAdd(n, cf);						
					}
				}
			}
			return cf;
		} catch (FileNotFoundException e) {
			return null;
		} finally {
			if (read != null) {
				read.close();
			}
		}

	}
	
	private boolean checkAndAdd(String s, ConfigFile cf) {
		if (s.contains("width")) {
			cf.setWidth(Integer.parseInt(s.substring(6)));
			System.out.println(cf.getWidth());
		} else if (s.contains("height")) {
			cf.setHeight(Integer.parseInt(s.substring(7)));
			System.out.println(cf.getHeight());
		} else if (s.contains("capacity")) {
			cf.setCapacity(Integer.parseInt(s.substring(9)));
			System.out.println(cf.getCapacity());
		} else if (s.contains("chargeSpeed")) {
			cf.setChargeSpeed(Integer.parseInt(s.substring(12)));
			System.out.println(cf.getChargeSpeed());
		} else if (s.contains("podRobot")) {
			//Just create a Robot here
			Robot x = new Robot();
			//Set the robot's conditions here (ID, Position)
			cf.addRobot(x);
			ChargingPod y = new ChargingPod();
			//Set the Pods ID here (ID, Position)
			cf.addPod(y);
			System.out.println("Robot Found");
		} else if (s.contains("shelf")) {
			StorageShelf x = new StorageShelf();
			//Set shelf's conditions here
			//cf.addShelf(x);
			System.out.println("Storage Shelf Found");
		} else if (s.contains("station")) {
			PackingStation x = new PackingStation();
			//Set station's conditions here (ID, Position)
			//cf.addStation(x);
			System.out.println("Packing Station Found");
		} else if (s.contains("order")) {
			Order x = new Order();
			//Set Order's conditions here (ID, StorageShelfIDs<String>)
			//cf.addOrder(x);
			System.out.println("Order Found");
		}
		return false;
	}

}
