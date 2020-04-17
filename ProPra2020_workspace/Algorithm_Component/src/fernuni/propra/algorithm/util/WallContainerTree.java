package fernuni.propra.algorithm.util;

import java.util.Comparator;
import java.util.TreeMap;

import fernuni.propra.internal_data_model.Wall;

public abstract class WallContainerTree {
	private TreeMap<Double, LinkedListForWallsWithKey> walls;
	private static final double TOL = 0.00001;
	
	public WallContainerTree() {		
		walls = new TreeMap<Double, LinkedListForWallsWithKey>( 
				new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				if (Math.abs(o1 - o2) < TOL) {
					return 0;
				} else {
					if(o1<o2) {
						return -1;
					} else {
						return 1;
					}
				}
			}
		});
	}
	
	
	public void addWall(Wall wall) {
		double key = determineKeyOfWall(wall);
		LinkedListForWallsWithKey listOfWallsAtKey = walls.get(key);
		if (listOfWallsAtKey == null) {
			listOfWallsAtKey = new LinkedListForWallsWithKey(determineKeyOfWall(wall));	
		} 
		listOfWallsAtKey.addWall(wall);
	}
	
	
	
	protected abstract double determineKeyOfWall(Wall wall);
	
}