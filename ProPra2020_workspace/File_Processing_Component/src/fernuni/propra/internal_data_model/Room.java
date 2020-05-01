package fernuni.propra.internal_data_model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Element;

public class Room implements IRoom {
	private List<Lamp> lamps = new LinkedList<Lamp>();
	private final LinkedList<Point> corners;
	private boolean counterClockWise;
	private double minX, maxX, minY, maxY;
	private List<Wall> walls = new LinkedList<Wall>();
	private String ID;
	
	public double getMinX() {
		return minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMaxY() {
		return maxY;
	}

	public Room(String ID, List<Lamp> lamps, LinkedList<Point> corners) {
		if (lamps != null) {
			this.lamps = lamps;
		}
		this.corners = corners;
		this.ID = ID;
		computeDimensionAndOrientation();
		
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

	@Override
	public int getNumberOfLamps() {
		return lamps.size();
	}

	private void computeDimensionAndOrientation() {
		if (corners.isEmpty()) {
			throw new IllegalArgumentException("Room does not have any corners!");
		} else {
			minX = corners.get(0).getX(); maxX = minX;
			minY = corners.get(0).getY(); maxY = minY;
		}
		
		Point mostBottomMostRightPoint = null;
		for (Point corner :  corners ) {
			if(mostBottomMostRightPoint != null) {
				if( corner.getY() <= mostBottomMostRightPoint.getY()) {
					if (corner.getX()>mostBottomMostRightPoint.getX()) {
						mostBottomMostRightPoint = corner;
					}
				}
			} else {
				mostBottomMostRightPoint = corner;
			}
			
			if (corner.getX()< minX) {
				minX = corner.getX();
			} else if(corner.getX()>maxX) {
				maxX = corner.getX();
			}
			if (corner.getY()< minY) {
				minY = corner.getY();
			} else if(corner.getY()>maxY) {
				maxY = corner.getY();
			}	
		}
		
		this.counterClockWise = isCounterClockWise(mostBottomMostRightPoint);
	}

	@Override
	public Iterator<Wall> getWalls() {
		if (walls.isEmpty()) {
			computeWalls();
		}
		
		// TODO Auto-generated method stub
		return walls.iterator();
	}

	private void computeWalls() {
		Point firstCorner = null;
		Point previousCorner = null;
		
		Iterator<Point> cornersIterator = getCorners();
		
		while(cornersIterator.hasNext()) {
			Point corner = cornersIterator.next();
			if (firstCorner == null) {
				firstCorner = corner;
			} else {
				Wall newWall = new Wall(previousCorner, corner);
				walls.add(newWall);
			}
			previousCorner = corner;
		}
		Wall newWall = new Wall(previousCorner, firstCorner);	
		walls.add(newWall);
	}

	@Override
	public String getID() {
		return this.ID;
	}
	
	
	private boolean isCounterClockWise(Point mostBottomMostRightPoint) {
		// https://stackoverflow.com/questions/1165647/how-to-determine-if-a-list-of-polygon-points-are-in-clockwise-order/1180256#1180256
		int indexOfBMRMP = corners.indexOf(mostBottomMostRightPoint);
		Point previous;
		Point next;
		if (indexOfBMRMP == 0) {
			previous = corners.get(corners.size()-1); //TODO this is faster with ArrayList?
			next = corners.get(indexOfBMRMP+1);
		} else if(indexOfBMRMP == (corners.size()-1)) {
			previous = corners.get(indexOfBMRMP-1);
			next = corners.get(0);
		} else {
			previous = corners.get(indexOfBMRMP-1);
			next = corners.get(indexOfBMRMP+1);
		}
		
		double dx1 = mostBottomMostRightPoint.getX()-previous.getX();
		double dx2 = next.getX() - mostBottomMostRightPoint.getX();
		
		double dy1 = mostBottomMostRightPoint.getY()-previous.getY();
		double dy2 = next.getY() - mostBottomMostRightPoint.getY();
		
		double crossProduct = dx1*dy2 - dx2*dy1;
		return crossProduct > 0;
	}

	@Override
	public void replaceLamps(List<Lamp> lamps) {
		if (lamps != null) {
			this.lamps = lamps;
		}	
	}

	@Override
	public String printLampPositions() {
		int n = 1;
		String lineSeparator =  System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("The room contains ");
		sb.append(String.valueOf(this.lamps.size()));
		String singPl = lamps.size() == 1 ? " lamp." : " lamps.";
		sb.append(singPl);
		sb.append(lineSeparator);
		sb.append("The lamps are located at:");
		sb.append(lineSeparator);
		for (Lamp lamp : lamps) {
			sb.append("Lamp ");
			sb.append(String.valueOf(n));
			sb.append(" located at x=");
			sb.append(String.valueOf(lamp.getX()));
			sb.append("  y=");
			sb.append(String.valueOf(lamp.getY()));
			sb.append(". The lamp is ");
			String onOff = lamp.getOn() ? "turned on." : "turned off.";
			sb.append(onOff);
			sb.append(lineSeparator);
			n++;
		}
		String outString = sb.toString();
		return outString;
	}
			
}
