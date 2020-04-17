package fernuni.propra.algorithm;

import java.util.Comparator;
import java.util.Iterator;

import fernuni.propra.internal_data_model.Wall;

public class WallContainerNorth extends WallContainerAbstract{

	@Override
	protected boolean isValidWall(Wall wall, double limit, double low, double high) {
		// TODO Auto-generated method stub
		return wall.overlapsXrange(low, high) &&  wall.getP1().getY()>=limit;
	}

	@Override
	protected Comparator<Wall> getComparator() {
		return new Comparator<Wall>() { // TODO: dont sort complete list -> find correct position and insert there
			@Override
			public int compare(Wall o1, Wall o2) {
				if (o1.getP1().getY() < o2.getP1().getY()) {
					return -1;
				} else if (o1.getP1().getY()>o2.getP1().getY()) {
					return 1;
				} 
				return 0;
			}
		};
	}

	@Override
	protected boolean isCorrectWallType(Wall wall) {
		return wall.isNorthWall();
	}
}
