package warehouse.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import warehouse.io.configActors.*;

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
					// This is standing in place for the Warehouse, as that's what this will
					// actually make
					cf = new ConfigFile();
					while (read.hasNext()) {
						// Sorts through the input and appends it to the Warehouse (Currently
						// ConfigFile)
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
			/*
			 * Remove "podRobot" from string, then create array of
			 * properties, then create Robot object and add to ConfigFile
			 */
			s = s.substring(9);
			ArrayList<String> strings = parseString(s);
			ConfigRobot cr = setupRobot(strings);
			cf.addRobot(cr);
			
			System.out.println("Robot:" + cr.getuID() + ", at (Row:" + cr.getRow() + "|Col:" + cr.getCol() + ") | ChargingPod: "+cr.getChargingPoduID());
			
		} else if (s.contains("shelf")) {
			/*
			 * Remove "shelf" from string, then create array of properties,
			 * then create ConfigPackingStation object and add to ConfigFile
			 */
			s = s.substring(6);
			ArrayList<String> strings = parseString(s);
			ConfigStorageShelf cs = setupShelf(strings);
			cf.addShelf(cs);
			
			System.out.println("Shelf:" + cs.getuID() + ", at (Row:" + cs.getRow() + "|Col:" + cs.getCol() + ")");
			
		} else if (s.contains("station")) {
			
			/*
			 * Remove "station" from string, then create array of properties, 
			 * then create ConfigPackingStation object and add to ConfigFile
			 */
			s = s.substring(8);
			ArrayList<String> strings = parseString(s);
			ConfigPackingStation cp = setupStation(strings);
			cf.addStation(cp);
			
			System.out.println("Station:" + cp.getuID() + ", at (Row:" + cp.getRow() + "|Col:" + cp.getCol() + ")");
			
		} else if (s.contains("order")) {
			/*
			 * Remove "order" from string, then create array of properties.
			 * Create ConfigOrder object and add to ConfigFile
			 */
			s = s.substring(6);
			ArrayList<String> strings = parseString(s);
			ConfigOrder co = setupOrder(strings);
			cf.addOrder(co);
			
			String out = "";
			int i = 0;
			while (i<co.getStorageLocations().size()) {
				out += co.getStorageLocation(i);
				out += "|";
				i++;
			} 
			System.out.println("Order:"+co.getTicksToPack()+" has pieces at Storage Shelves: |"+out);
		}
		return false;
	}

	public ArrayList<String> parseString(String s) {
		Scanner read = null;
		ArrayList<String> properties = new ArrayList<String>();
		read = new Scanner(s);
		while (read.hasNext()) {
			// If there are strings to parse, get the next part.
			String input = read.next();
			// Add it to the array to be returned
			properties.add(input);
		}
		// Return the array list of properties, broken up by part

		if (read != null) {
			// Close scanner after done
			read.close();
		}
		return properties;
	}

	public ConfigRobot setupRobot(ArrayList<String> s) {
		/*
		 * Create new ConfigPackingStation, assign UID, 
		 * PodUID, Row and Col pos, then return.
		 */

		ConfigRobot r = new ConfigRobot();
		r.setuID(s.get(1));
		r.setChargingPoduID(s.get(0));
		r.setRow(Integer.parseInt(s.get(2)));
		r.setCol(Integer.parseInt(s.get(3)));
		r.setLocation(r.getRow(), r.getCol());
		
		return r;
	}

	public ConfigStorageShelf setupShelf(ArrayList<String> s) {
		/*
		 * Create new ConfigStorageShelf, assign UID, 
		 * Row and Col pos, then return.
		 */
		
		ConfigStorageShelf cs = new ConfigStorageShelf();
		cs.setuID(s.get(0));
		cs.setRow(Integer.parseInt(s.get(1)));
		cs.setCol(Integer.parseInt(s.get(2)));
		cs.setLocation(cs.getRow(), cs.getCol());
		
		return cs;
	}
	
	public ConfigPackingStation setupStation(ArrayList<String> s) {
		/*
		 * Create new ConfigPackingStation, assign UID, 
		 * Row and Col pos, then return.
		 */
		
		ConfigPackingStation cp = new ConfigPackingStation();
		cp.setuID(s.get(0));
		cp.setRow(Integer.parseInt(s.get(1)));
		cp.setCol(Integer.parseInt(s.get(2)));
		cp.setLocation(cp.getRow(), cp.getCol());
		
		return cp;
	}
	
	public ConfigOrder setupOrder(ArrayList<String> s) {
		/*
		 * Create new ConfigOrder, assign UID, 
		 * Create ArrayList<String> and put all StorageUIDs into it.
		 * Assign them to storageLocations then return
		 */
		
		ConfigOrder cp = new ConfigOrder();
		cp.setTicksToPack(s.get(0));
		int i = 1;
		ArrayList<String> storageUIDs = new ArrayList<String>();
		while (i<s.size()){
			storageUIDs.add(s.get(i));
			i++;
		}
		cp.setStorageLocations(storageUIDs);
		return cp;
	}

}
