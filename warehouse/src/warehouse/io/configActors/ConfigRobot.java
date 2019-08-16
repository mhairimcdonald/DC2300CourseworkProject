package warehouse.io.configActors;

public class ConfigRobot extends ConfigActor {

	private String chargingPoduID;

	public ConfigRobot() {}
	
	public ConfigRobot(String uID, String poduID, int row, int col) {
		super(uID, row, col);
		this.chargingPoduID = poduID;
	}

	public String getChargingPoduID() {
		return chargingPoduID;
	}

	public void setChargingPoduID(String chargingPoduID) {
		this.chargingPoduID = chargingPoduID;
	}
	
	
}
		