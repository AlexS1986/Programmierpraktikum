package fernuni.propra.algorithm.candidateSearcher;

import java.util.Comparator;
import java.util.Iterator;

import fernuni.propra.internal_data_model.Wall;

public class WallContainerWest extends WallContainerAbstract{
	/**
	 * Adds a {@link Wall} to the container. The wall needs to be a west wall. Sorts the container afterwards.
	 * 
	 * @param wall The {@link Wall} to be added
	 * @throws WallContainerException
	 */
	public void add(Wall wall) throws WallContainerException{
		if (!wall.isWestWall()) throw new WallContainerException("Wall is not a north wall!");
		walls.add(wall);
		walls.sort(new Comparator<Wall>() { // TODO: dont sort complete list -> find correct position and insert there
			@Override
			public int compare(Wall o1, Wall o2) {
				if (o1.getP1().getX() > o2.getP1().getX()) {
					return -1;
				} else if (o1.getP1().getX()<o2.getP1().getX()) {
					return 1;
				} 
				return 0;
			}
		});
	}
	

	public Wall getNearestWestWall(int yLow, int yHigh, int xHigh) throws WallContainerException{
		if (yLow>= yHigh) throw new WallContainerException();
		Iterator<Wall> iterator = walls.iterator();
		Wall nextWall;
		while(iterator.hasNext()) {
			nextWall = iterator.next();
			if (nextWall.overlapsYrange(yLow, yHigh) &&  nextWall.getP1().getX()<xHigh) {
				return nextWall;
			}
		}
		throw new WallContainerException();
	}

}
