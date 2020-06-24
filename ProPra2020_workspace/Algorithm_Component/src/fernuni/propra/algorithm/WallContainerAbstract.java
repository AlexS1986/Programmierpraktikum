package fernuni.propra.algorithm;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fernuni.propra.internal_data_model.Wall;

public abstract class WallContainerAbstract implements Iterable<Wall> {
	
	List<Wall> walls;
	
	public WallContainerAbstract() {
		walls = new LinkedList<Wall>();
		
	}
	
	public void add(Wall wall) throws WallContainerException{
		if (!isCorrectWallType(wall)) throw new WallContainerException("Wall does not "
				+ "have the correct orientation for this wall container!");
		walls.add(wall);
		walls.sort(getComparator());
	}
	
	public Wall getNearestWall(double low, double high, double limit) throws WallContainerException{
		if (low > high) throw new WallContainerException();
		Iterator<Wall> iterator = walls.iterator();
		Wall nextWall;
		while(iterator.hasNext()) {
			nextWall = iterator.next();
			if (isValidWall(nextWall, limit, low, high)) {
				return nextWall;
			}
		}
		return null;
	}
	
	public Iterator<Wall> iterator() {
		return walls.iterator();
	}
	

	protected abstract boolean isValidWall(Wall wall, double limit, double low, double high);
	
	protected abstract Comparator<Wall> getComparator();
	
	protected abstract boolean isCorrectWallType(Wall wall);
}
