package fernuni.propra.internal_data_model;

import java.util.Iterator;

public interface IRoom{
	Iterator<Lamp> getLamps();
	int getNumberOfLamps();
	Iterator<Point> getCorners();
	void addLamp(Lamp lamp);
	Iterator<Wall> getWalls();
	double getMinX();
	double getMaxX();
	double getMinY();
	double getMaxY();
	String getID();
	
}

