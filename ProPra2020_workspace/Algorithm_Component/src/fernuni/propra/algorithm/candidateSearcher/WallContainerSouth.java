package fernuni.propra.algorithm.candidateSearcher;

import java.util.Comparator;
import java.util.Iterator;

import fernuni.propra.internal_data_model.Wall;

public class WallContainerSouth extends WallContainerAbstract {
	/**
	 * Adds a {@link Wall} to the container. The wall needs to be a south wall. Sorts the container afterwards.
	 * 
	 * @param wall The {@link Wall} to be added
	 * @throws WallContainerException
	 */
	public void add(Wall wall) throws WallContainerException{
		if (!wall.isSouthWall()) throw new WallContainerException("Wall is not a north wall!");
		walls.add(wall);
		walls.sort(new Comparator<Wall>() { // TODO: dont sort complete list -> find correct position and insert there
			@Override
			public int compare(Wall o1, Wall o2) {
				if (o1.getP1().getY() > o2.getP1().getY()) {
					return -1;
				} else if (o1.getP1().getY()<o2.getP1().getY()) {
					return 1;
				} 
				return 0;
			}
		});
	}
	
	/**
	 * Returns the next south {@link Wall} that is north of yLow and covers the specified range xLow-xHigh. 
	 * 
	 * @param xLow
	 * @param xHigh
	 * @param yHigh
	 * @return
	 * @throws WallContainerException
	 */
	public Wall getNearestSouthWall(int xLow, int xHigh, int yHigh) throws WallContainerException{
		if (xLow>= xHigh) throw new WallContainerException();
		Iterator<Wall> iterator = walls.iterator();
		Wall nextWall;
		while(iterator.hasNext()) {
			nextWall = iterator.next();
			if (nextWall.overlapsXrange(xLow, xHigh) &&  nextWall.getP1().getY()<yHigh) {
				return nextWall;
			}
		}
		throw new WallContainerException();
	}
}
