package warehouse.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

	private String unpackRobotPods(ConfigFile cf) {
		String s = "";
		//Robot String = "podRobot " + id + " " + xpos + " " + ypos + "\n"
		//Insert the Charging Pod ID at 9 in the Robot String 
		return s;
	}

	private String unpackRobots(ConfigFile cf) {
		String s = "";
		int i = 0;
		while (i < cf.getPodRobot().size()) {
			// Placeholder
			// s = cf.getPodRobot().get(i).toString();
			i++;
		}
		return s;
	}

	private String unpackChargingPods(ConfigFile cf) {
		String s = "";
		int i = 0;
		while (i < cf.getPod().size()) {
			// Placeholder
			// s = cf.getPod().get(i).toString();
			i++;
		}
		return s;
	}

	private String unpackStorageShelves(ConfigFile cf) {
		String s = "";
		int i = 0;
		while (i < cf.getShelf().size()) {
			// Placeholder
			// s = cf.getShelf().get(i).toString();
			i++;
		}
		return s;
	}

	private String unpackPackingStations(ConfigFile cf) {
		String s = "";
		int i = 0;
		while (i < cf.getStation().size()) {
			// Placeholder
			// s = cf.getStation().get(i).toString();
			i++;
		}
		return s;
	}

	private String unpackOrders(ConfigFile cf) {
		String s = "";
		int i = 0;
		while (i < cf.getOrder().size()) {
			// Placeholder
			// s = cf.getOrder().get(i).toString();
			i++;
		}
		return s;
	}

}
