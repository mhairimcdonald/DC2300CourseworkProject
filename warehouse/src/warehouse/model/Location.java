package warehouse.model;

public class Location {
	
	private int col;
	private int row;
		
	public Location(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	public Location() {
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return col+","+row;
	}
	
	public int hashCode() {
		return (col<<16) + row;
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
