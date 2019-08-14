package warehouse.model;

import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.css.Counter;

public class WarehouseStats {
	
	private HashMap<Class<? extends Actor>, Counter> counters;
	private boolean countsValid;
	
	public WarehouseStats() {
		counters = new HashMap<Class<? extends Actor>, Counter>();
		countsValid = true;
	}
	
	public void reset() {
		countsValid = false;
		Iterator<Class<? extends Actor>> keys = counters.keySet().iterator();
		while(keys.hasNext()) {
			Counter count = counters.get(keys.next());
			//count.reset();
		}
	}
	
	public void incrementCount(Class<? extends Actor> object) {
		Counter count = counters.get(object);
		if (count == null) {
			//There is no counter for that object, create one
			//count = new Counter(object.getName());
			counters.put(object, count);
		}
		//count.increment();
	}
	
	public void countFinished() {
		countsValid = true;
	}
	
	private void generateCounts(Warehouse w) {
		reset();
		for (int row = 0; row < w.getWidth(); row++) {
			for (int col = 0; col < w.getHeight(); col++) {
				Actor a = w.getObjectAt(row, col);
				if(a != null) {
					incrementCount(a.getClass());
				}
			}
		}
		countsValid = true;
	}

}
