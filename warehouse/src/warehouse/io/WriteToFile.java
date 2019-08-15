package warehouse.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import warehouse.io.configActors.*;

public class WriteToFile {

	public WriteToFile() {

	}

	public void writeFile(File f, ConfigFile cf) throws IOException {
		FileWriter write = null;
		try {

			write = new FileWriter(f);
			String s = createFileString(cf);
			write.write(s);
		} finally {
			if (write != null) {
				write.close();
			}
		}
	}

	private String createFileString(ConfigFile cf) {
		String returnString = "format 1\n";
		returnString += unpackWidth(cf) + "\n";
		returnString += unpackHeight(cf) + "\n";
		returnString += unpackCapacity(cf) + "\n";
		returnString += unpackChargeSpeed(cf) + "\n";
		for (ConfigRobot c : cf.getPodRobot()) {
			returnString += unpackRobotPod(c);
		}
		for (ConfigStorageShelf c : cf.getShelf()) {
			returnString += unpackStorageShelf(c);
		}
		for (ConfigPackingStation c : cf.getStation()) {
			returnString += unpackPackingStation(c);
		}
		for (ConfigOrder c : cf.getOrder()) {
			returnString += unpackOrders(c);
		}
		return returnString;
	}

	private String unpackWidth(ConfigFile cf) {
		String s = "width " + cf.getWidth();
		return s;
	}

	private String unpackHeight(ConfigFile cf) {
		String s = "height " + cf.getHeight();
		return s;
	}

	private String unpackCapacity(ConfigFile cf) {
		String s = "capacity " + cf.getCapacity();
		return s;
	}

	private String unpackChargeSpeed(ConfigFile cf) {
		String s = "chargeSpeed " + cf.getChargeSpeed();
		return s;
	}

	private String unpackRobotPod(ConfigRobot c) {
		String s = "podRobot "+c.getChargingPoduID()+ " "+c.getuID()+" "+c.getRow()+" "+c.getCol()+"\n";
		return s;
	}

	private String unpackStorageShelf(ConfigStorageShelf c) {
		String s = "shelf "+c.getuID()+" "+c.getRow()+" "+c.getCol()+"\n";
		return s;
	}

	private String unpackPackingStation(ConfigPackingStation c) {
		String s = "station "+c.getuID()+" "+c.getRow()+" "+c.getCol()+"\n";
		return s;
	}

	private String unpackOrders(ConfigOrder c) {
		String s = "order "+c.getTicksToPack();
		for (String os : c.getStorageLocations()) {
			s += " "+os;
		}
		s += "\n";
		return s;
	}

}
