package warehouse.model;

public class Location {
	
	private int row;
	private int col;
	
	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Location() {
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return row+","+col;
	}
	
	public int hashCode() {
		return (row<<16) + col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setRow(int i) {
		row = i;
	}
	
	public void setCol(int i) {
		col = i;
	}

}
