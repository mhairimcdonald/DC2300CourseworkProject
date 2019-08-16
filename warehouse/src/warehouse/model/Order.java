package warehouse.model;

import java.util.ArrayList;

public class Order {
	private int ticksToPack;
	private ArrayList<String> itemLocations;
	
	public Order(int ticksToPack, ArrayList<String> itemLocations) {
		super();
		this.ticksToPack = ticksToPack;
		this.itemLocations = itemLocations;
	}

	//Generic Order contructor
	public Order() {
		// TODO Auto-generated constructor stub
	}

	//generates random orders for simulation
	public void randomOrderGeneration() {
		//TODO generate random simulation
	}
	
	public int getTicksToPack() {
		return ticksToPack;
	}

	public void setTicksToPack(int ticksToPack) {
		this.ticksToPack = ticksToPack;
	}

	public ArrayList<String> getItemLocations() {
		return itemLocations;
	}

	public void setItemLocations(ArrayList<String> itemLocations) {
		this.itemLocations = itemLocations;
	}
	
	public String getItemLocation(int i) {
		return itemLocations.get(i);
	}

	//Reads the orders from a file
	public void read() {
		
	}//read

}
