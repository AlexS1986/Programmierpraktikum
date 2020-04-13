package fernuni.propra.internal_data_model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Room implements IRoom {
	private List<Lamp> lamps;
	private final LinkedList<Point> corners;
	private final boolean counterClockWise;
	
	public Room(List<Lamp> lamps, LinkedList<Point> corners, boolean counterClockWise) {
		this.lamps = lamps;
		this.corners = corners;
		this.counterClockWise = counterClockWise;
	}

	@Override
	public Iterator<Lamp> getLamps() {
		return lamps.iterator();
	}

	@Override
	public Iterator<Point> getCorners() {
		if (counterClockWise) {
			return corners.iterator();
		} else {
			return corners.descendingIterator();
		}	
	}

	@Override
	public void addLamp(Lamp lamp) {
		lamps.add(lamp);

	}

}
