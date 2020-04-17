package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.algorithm.util.Rectangle;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Room;
import fernuni.propra.internal_data_model.Wall;

public class CandidateSearcherTest {
	private IRoom mockRoom, room, room2;
	private Point p1, p2, p3,p4, p5, p6, p7, p8;
	private Wall w1, w2,w3,w4;
	private LinkedList<Point> corners, corners2;
	
	@Before
	public void setup() {
		p1 = new Point(0,0);
		p2 = new Point(1,0);
		p3 = new Point (1,1);
		p4 = new Point(0,1);
		
		p5 = new Point(0.5, 1.0);
		p6 = new Point(0.5, 0.5);
		p7 = new Point(0,   0.5);
		
		
		
		w1 = new Wall(p1,p2);
		w2 = new Wall(p2,p3);
		w3 = new Wall(p3,p4);
		w4 = new Wall(p4,p1);
		
		List<Wall> walls = new LinkedList<Wall>();
		walls.add(w1); walls.add(w2); walls.add(w3); walls.add(w4);
		
		corners= new LinkedList<Point>();
		corners.add(p1); corners.add(p2); corners.add(p3); corners.add(p4);
		
		corners2= new LinkedList<Point>();
		corners2.add(p1); corners2.add(p2); corners2.add(p3); corners2.add(p5);
		corners2.add(p6); corners2.add(p7);
		
		room = new Room("test", null, corners);	
		room2 = new Room("test", null, corners2);	
		mockRoom = new IRoom() {
			
			@Override
			public Iterator<Wall> getWalls() {
				// TODO Auto-generated method stub
				return walls.iterator();
			}
			
			@Override
			public int getNumberOfLamps() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMinY() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMinX() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMaxY() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMaxX() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Iterator<Lamp> getLamps() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Iterator<Point> getCorners() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addLamp(Lamp lamp) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
	}
	

	@Test
	public void testSearchCandidates() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortWallsToContainers() {
		//Arrange
		CandidateSearcher candidateSearcher = new CandidateSearcher();
		//Act
		try {
			candidateSearcher.sortWallsToContainers(room);
		} catch (WallContainerException | CandidateSearcherException e) {
			fail(e.getMessage());
		}
		
		//Assert
		Iterator<Wall> east = candidateSearcher.eastIterator();
		Iterator<Wall> north = candidateSearcher.northIterator();
		Iterator<Wall> west = candidateSearcher.westIterator();
		Iterator<Wall> south = candidateSearcher.southIterator();
		
		//east
		boolean test11 = east.hasNext();
		Wall wallEast = east.next();
		boolean test12 = !east.hasNext();
		boolean test13 = wallEast.getP1().isEqual(w2.getP1()) && wallEast.getP2().isEqual(w2.getP2());
		boolean eastBool = test11 && test12 && test13;
		
		//north
		boolean test21 = north.hasNext();
		Wall wallNorth = north.next(); 
		boolean test22 = !north.hasNext();
		boolean test23 = wallNorth.getP1().isEqual(w3.getP1()) && wallNorth.getP2().isEqual(w3.getP2());
		boolean northBool = test21 && test22 && test23;
		
		//west
		boolean test31 = west.hasNext();
		Wall wallWest = west.next(); 
		boolean test32 = !west.hasNext();
		boolean test33 = wallWest.getP1().isEqual(w4.getP1()) && wallWest.getP2().isEqual(w4.getP2());
		boolean westBool = test31 && test32 && test33;
		
		//west
		boolean test41 = south.hasNext();
		Wall wallSouth = south.next(); 
		boolean test42 = !south.hasNext();
		boolean test43 = wallSouth.getP1().isEqual(w1.getP1()) && wallSouth.getP2().isEqual(w1.getP2());
		boolean southBool = test41 && test42 && test43;
		
		assertTrue(eastBool && northBool && westBool && southBool);
		
	}

	@Test
	public void testConstructOriginalPartialRectangles() {
		// Arrange
		CandidateSearcher candidateSearcher = new CandidateSearcher();
		CandidateSearcher candidateSearcher2 = new CandidateSearcher();
		
		//Act
		try {
			candidateSearcher.sortWallsToContainers(room);
			candidateSearcher.constructOriginalPartialRectangles();
		} catch (WallContainerException e) {
			fail(e.getMessage());
		} catch (CandidateSearcherException e) {
			fail(e.getMessage());
		}
		
		//2nd room
		try {
			candidateSearcher2.sortWallsToContainers(room2);
			candidateSearcher2.constructOriginalPartialRectangles();
		} catch (WallContainerException e) {
			fail(e.getMessage());
		} catch (CandidateSearcherException e) {
			fail(e.getMessage());
		}
		
		
		//Assert
		Iterator<RectangleWithTag> rectIterator = candidateSearcher.iteratorOriginalRectangles();
		Rectangle rec1 = rectIterator.next();
		Rectangle rec2 = rectIterator.next();
		Rectangle rec3 = rectIterator.next();
		Rectangle rec4 = rectIterator.next();
		Rectangle ref = new Rectangle(p1, p3);
		
		boolean test1 = !rectIterator.hasNext();
		boolean test2 = rec1.equals(ref);
		boolean test3 = rec2.equals(ref);
		boolean test4 = rec3.equals(ref);
		boolean test5 = rec4.equals(ref);
		boolean test6 = !rectIterator.hasNext();
		
		assertTrue(test1 && test2 && test3 && test4 && test5 && test6);
		
		
		Iterator<RectangleWithTag> rectIterator2 = candidateSearcher2.iteratorOriginalRectangles();
		Rectangle rec2_1 = rectIterator2.next();
		Rectangle rec2_2 = rectIterator2.next();
		Rectangle rec2_3 = rectIterator2.next();
		Rectangle rec2_4 = rectIterator2.next();
		Rectangle rec2_5 = rectIterator2.next();
		Rectangle rec2_6 = rectIterator2.next();
		Rectangle ref2 = new Rectangle(p1, new Point(1,0.5));
		Rectangle ref3 = new Rectangle(new Point(0.5,0), p3);
		
		boolean test7 = !rectIterator2.hasNext();
		boolean test8 = rec2_1.equals(ref2);
		boolean test9 = rec2_2.equals(ref3);
		boolean test10 = rec2_3.equals(ref3);
		boolean test11 = rec2_4.equals(ref3);
		boolean test12 = rec2_5.equals(ref2);
		boolean test13 = rec2_6.equals(ref2);
		
		
		assertTrue(test7 && test8 && test9 && test10 && test11 && test12 && test13);
	}

}
