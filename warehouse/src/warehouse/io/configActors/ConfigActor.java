package warehouse.io.configActors;

public abstract class ConfigActor {
	
	private String uID;
	private int row;
	private int col;

	public ConfigActor() {

	}

	public ConfigActor(String uID, int row, int col) {
		this.uID = uID;
		this.row = row;
		this.col = col;
	}

	public String getuID() {
		return uID;
	}

	public void setuID(String uID) {
		this.uID = uID;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
