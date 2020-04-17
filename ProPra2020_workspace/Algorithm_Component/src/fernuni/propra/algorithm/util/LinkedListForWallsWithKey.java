package fernuni.propra.algorithm.util;

import java.util.Iterator;
import java.util.LinkedList;

import fernuni.propra.internal_data_model.Wall;

public class LinkedListForWallsWithKey {
	private LinkedList<Wall> walls = new LinkedList<Wall>();
	private double key;
	
	public LinkedListForWallsWithKey(double key) {
		this.key = key;
	}
	
	double getKey() {
		return key;
	}
	
	void addWall(Wall wall) {
		walls.add(wall);
	}
	
	Iterator<Wall> iterator() {
		return walls.iterator();
	}
	


}
