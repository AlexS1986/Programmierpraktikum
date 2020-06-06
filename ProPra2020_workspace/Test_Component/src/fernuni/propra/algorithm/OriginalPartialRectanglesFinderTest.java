package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fernuni.propra.algorithm.runtime_information.RuntimeInformation;
import fernuni.propra.algorithm.util.Rectangle;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.file_processing.UserReadInputWriteOutputAAS;
import fernuni.propra.file_processing.UserReadInputWriteOutputException;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Room;
import fernuni.propra.internal_data_model.Wall;
import fernuni.propra.user_interface.RoomFrame;
import fernuni.propra.user_interface.RoomPanel;

public class OriginalPartialRectanglesFinderTest {
	private IRoom mockRoom, room, room2, roomStar, roomHufeisen;
	private Point p1, p2, p3,p4, p5, p6, p7, p8;
	private Point pc1, pc2, pc3,pc4, pc5, pc6, pc7, pc8,pc9, pc10, pc11, pc12;
	private Point p31, p32, p33, p34, p35, p36, p37, p38;
	private Wall w1, w2,w3,w4;
	private LinkedList<Point> corners, corners2;
	private static List<IRoom> rooms;
	
	@BeforeClass
	public static void setupBC() {

		String[] xmlPathesOK = {"instances/validationInstances/Selbsttest_clockwise.xml", //0
				"instances/validationInstances/Selbsttest_counterClockwise.xml", //1
				"instances/validationInstances/Selbsttest_100a_incomplete.xml", // 2
				"instances/validationInstances/Selbsttest_100a_incomplete.xml", //3
				"instances/validationInstances/Selbsttest_100a_solved.xml", // 4
				"instances/validationInstances/Selbsttest_100a.xml", // 5
				"instances/validationInstances/Selbsttest_100b.xml", // 6
				"instances/validationInstances/Selbsttest_20a_incomplete.xml", // 7
				"instances/validationInstances/Selbsttest_20a_solved.xml", // 8
				"instances/validationInstances/Selbsttest_20a.xml", // 9
				"instances/validationInstances/Selbsttest_20b.xml", // 10
				"instances/validationInstances/Selbsttest_20c.xml",	// 11
				"instances/validationInstances/Zufallsraum_144_solved.xml" // 12
		};
		
		rooms = new ArrayList<IRoom>();
		
		for(String xmlPath : xmlPathesOK) {
			UserReadInputWriteOutputAAS readAAS = new UserReadInputWriteOutputAAS(xmlPath);
			try {
				rooms.add(readAAS.readInput());
			} catch (UserReadInputWriteOutputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Before
	public void setUp() throws Exception {
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

			@Override
			public void replaceLamps(List<Lamp> lamps) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String printLampPositions() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		pc1 = new Point(1,-1);
		pc2 = new Point(2,-1);
		pc3 = new Point(2,1);
		pc4 = new Point(1,1);
		pc5 = new Point(1,2);
		pc6 = new Point(-1,2);
		pc7 = new Point(-1,1);
		pc8 = new Point(-2,1);
		pc9 = new Point(-2,-1);
		pc10 = new Point(-1,-1);
		pc11 = new Point(-1,-2);
		pc12 = new Point(1,-2);
		LinkedList<Point> cornersStar = new LinkedList<Point>();
		cornersStar.add(pc1);cornersStar.add(pc2);cornersStar.add(pc3);cornersStar.add(pc4);cornersStar.add(pc5);
		cornersStar.add(pc6);cornersStar.add(pc7);cornersStar.add(pc8);cornersStar.add(pc9);cornersStar.add(pc10);
		cornersStar.add(pc11);cornersStar.add(pc12);
		
		roomStar = new Room("star", null, cornersStar);
		
		
		p31 = new Point(-2,0);
		p32 = new Point(2,0);
		p33 = new Point(2,2);
		p34 = new Point(1,2);
		p35 = new Point(1,1);
		p36 = new Point(-1,1);
		p37 = new Point(-1,2);
		p38 = new Point(-2,2);
		LinkedList<Point> cornersHufeisen = new LinkedList<Point>();
		cornersHufeisen.add(p31);cornersHufeisen.add(p32);cornersHufeisen.add(p33);cornersHufeisen.add(p34);cornersHufeisen.add(p35);
		cornersHufeisen.add(p36);cornersHufeisen.add(p37);cornersHufeisen.add(p38);
		roomHufeisen = new Room("hufeisen", null, cornersHufeisen);
		
	}
	
	@Test 
	public void testFindOriginalPartialRectangles() {
		//Arrange
		OriginalPartialRectanglesFinder rectanglesFinder3 = new OriginalPartialRectanglesFinder();
		CandidateSearcher candidateSearcher = (CandidateSearcher) AbstractAlgorithmFactory.getAlgorithmFactory().createCandidateSearcher();
		
		//Act
		IRoom testRoom = rooms.get(9);
		//IRoom testRoom = roomStar;
		ArrayList<RectangleWithTag> rectanglesWithTag = new ArrayList<RectangleWithTag>();
		try {
			rectanglesWithTag = rectanglesFinder3.findOriginalPartialRectangles(testRoom, null);
		} catch (OriginalPartialRectanglesFinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RoomPanel roomPanel = new RoomPanel(testRoom);
		Color[] colors = {Color.blue, Color.red, Color.green, Color.yellow};
		
		
		
		
		//TODO Aufräumen
		
		/*int index = 10;
		RectangleWithTag rec2 = rectanglesWithTag.get(index);
		double width2 = rec2.getP2().getX() - rec2.getP1().getX();
		double height2 = rec2.getP3().getY() - rec2.getP1().getY();
		roomPanel.addRectangle(String.valueOf(index), colors[index % 3], rec2.getP1().getX(), rec2.getP1().getY(), width2, height2);
		*/
		
		/*for (int i = 0; i<rectanglesWithTag.size(); i++) {
			RectangleWithTag rec = rectanglesWithTag.get(i);
			double width = rec.getP2().getX() - rec.getP1().getX();
			double height = rec.getP3().getY() - rec.getP1().getY();
			roomPanel.addRectangle(String.valueOf(i), colors[i % 3], rec.getP1().getX(), rec.getP1().getY(), width, height);
		}*/
		
		/*
		List<RectangleWithTag> reducedRectangles = null;
		try {
			reducedRectangles = candidateSearcher.reduceRectangles(rectanglesWithTag);
		} catch (InterruptedException e2) {
			fail(e2.getMessage());
		} //TODO
		List<Lamp> lamps = new LinkedList<Lamp>();
		try {
			lamps = candidateSearcher.searchCandidates(testRoom, new RuntimeInformation());
		} catch (CandidateSearcherException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator<Lamp> lampsIterator = lamps.iterator();
		while (lampsIterator.hasNext()) {
			testRoom.addLamp(lampsIterator.next());
		}
		
		for (int i = 0; i<reducedRectangles.size(); i++) {
		RectangleWithTag rec = reducedRectangles.get(i);
		double width = rec.getP2().getX() - rec.getP1().getX();
		double height = rec.getP3().getY() - rec.getP1().getY();
		roomPanel.addRectangle(String.valueOf(i), colors[i % 3], rec.getP1().getX(), rec.getP1().getY(), width, height);
		} 
		*/
		//RoomFrame roomFrame = new RoomFrame(roomPanel);
		
		
		
		IRoom testRoom2 = rooms.get(12);
		//Act
		
		//IRoom testRoom = roomStar;
		ArrayList<RectangleWithTag> rectanglesWithTag2 = new ArrayList<RectangleWithTag>();
		try {
			rectanglesWithTag2 = (new OriginalPartialRectanglesFinder()).findOriginalPartialRectangles(testRoom2, null);
		} catch (OriginalPartialRectanglesFinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RoomPanel roomPanel2 = new RoomPanel(testRoom2);
		Color[] colors2 = {Color.blue, Color.red, Color.green, Color.yellow};
		
		List<RectangleWithTag> rectanglesWithTag3 = new ArrayList<RectangleWithTag>();
		try {
			rectanglesWithTag3 = (new CandidateSearcher()).reduceRectangles(rectanglesWithTag2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		RoomFrame roomFrame = new RoomFrame(roomPanel2);
		
		for (int i = 0; i<rectanglesWithTag3.size(); i++) {
		RectangleWithTag rec = rectanglesWithTag3.get(i);
		double width = rec.getP2().getX() - rec.getP1().getX();
		double height = rec.getP3().getY() - rec.getP1().getY();
		roomPanel2.addRectangle(String.valueOf(i), colors[i % 3], rec.getP1().getX(), rec.getP1().getY(), width, height);
		roomPanel2.repaint();
		//roomPanel2.removeLastRectangle();
		} 
		
		
		try {
			Thread.currentThread().sleep(100000);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		
		fail("not yet implemented");
	}

	@Test
	public void testGetAllTags() {
		// Arrange
		OriginalPartialRectanglesFinder rectanglesFinder = new OriginalPartialRectanglesFinder();
		HashSet<Integer> refSet = new HashSet<Integer>();
		refSet.add(0); refSet.add(1); refSet.add(2); refSet.add(3);
		
		//Act
		try {
			rectanglesFinder.sortWallsToContainers(room);
			rectanglesFinder.constructOriginalPartialRectangles();
		} catch (WallContainerException e) {
			fail(e.getMessage());
		} catch (OriginalPartialRectanglesFinderException e) {
			fail(e.getMessage());
		}
		
		//Assert
		assertTrue(rectanglesFinder.getAllTags().containsAll(refSet));
	}

	@Test
	public void testSortWallsToContainers() {
		//Arrange
				OriginalPartialRectanglesFinder originalRectanglesFinder = new OriginalPartialRectanglesFinder();
				//Act
				try {
					originalRectanglesFinder.sortWallsToContainers(room);
				} catch (WallContainerException | OriginalPartialRectanglesFinderException e) {
					fail(e.getMessage());
				}
				
				//Assert
				Iterator<Wall> east = originalRectanglesFinder.eastIterator();
				Iterator<Wall> north = originalRectanglesFinder.northIterator();
				Iterator<Wall> west = originalRectanglesFinder.westIterator();
				Iterator<Wall> south = originalRectanglesFinder.southIterator();
				
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
				OriginalPartialRectanglesFinder rectanglesFinder = new OriginalPartialRectanglesFinder();
				OriginalPartialRectanglesFinder rectanglesFinder2 = new OriginalPartialRectanglesFinder();
				
				
				//Act
				try {
					rectanglesFinder.sortWallsToContainers(room);
					rectanglesFinder.constructOriginalPartialRectangles();
				} catch (WallContainerException e) {
					fail(e.getMessage());
				} catch (OriginalPartialRectanglesFinderException e) {
					fail(e.getMessage());
				}
				
				//2nd room
				try {
					rectanglesFinder2.sortWallsToContainers(room2);
					rectanglesFinder2.constructOriginalPartialRectangles();
				} catch (WallContainerException e) {
					fail(e.getMessage());
				} catch (OriginalPartialRectanglesFinderException e) {
					fail(e.getMessage());
				}
				
				
				//Assert
				Iterator<RectangleWithTag> rectIterator = rectanglesFinder.iteratorOriginalRectangles();
				RectangleWithTag rec1 = rectIterator.next();
				RectangleWithTag rec2 = rectIterator.next();
				RectangleWithTag rec3 = rectIterator.next();
				RectangleWithTag rec4 = rectIterator.next();
				Rectangle ref = new Rectangle(p1, p3);
				
				boolean test1 = !rectIterator.hasNext();
				boolean test2 = rec1.equals(new RectangleWithTag(ref, 0));
				boolean test3 = rec2.equals(new RectangleWithTag(ref, 1));
				boolean test4 = rec3.equals(new RectangleWithTag(ref, 2));
				boolean test5 = rec4.equals(new RectangleWithTag(ref, 3));
				
				assertTrue(test1 && test2 && test3 && test4 && test5);
				
				
				Iterator<RectangleWithTag> rectIterator2 = rectanglesFinder2.iteratorOriginalRectangles();
				RectangleWithTag rec2_1 = rectIterator2.next();
				RectangleWithTag rec2_2 = rectIterator2.next();
				RectangleWithTag rec2_3 = rectIterator2.next();
				RectangleWithTag rec2_4 = rectIterator2.next();
				RectangleWithTag rec2_5 = rectIterator2.next();
				RectangleWithTag rec2_6 = rectIterator2.next();
				Rectangle ref2 = new Rectangle(p1, new Point(1,0.5));
				Rectangle ref3 = new Rectangle(new Point(0.5,0), p3);
				
				boolean test7 = !rectIterator2.hasNext();
				boolean test8 = rec2_1.equals(new RectangleWithTag(ref2, 0));
				boolean test9 = rec2_2.equals(new RectangleWithTag(ref3, 1));
				boolean test10 = rec2_3.equals(new RectangleWithTag(ref3, 2));
				boolean test11 = rec2_4.equals(new RectangleWithTag(ref3, 3));
				boolean test12 = rec2_5.equals(new RectangleWithTag(ref2, 4));
				boolean test13 = rec2_6.equals(new RectangleWithTag(ref2, 5));
				
				
				assertTrue(test7 && test8 && test9 && test10 && test11 && test12 && test13);
	}

}
