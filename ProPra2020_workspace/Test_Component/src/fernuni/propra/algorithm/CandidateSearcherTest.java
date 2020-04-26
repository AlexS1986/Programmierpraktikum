package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fernuni.propra.algorithm.util.Rectangle;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.file_processing.UserReadInputWriteOutputAAS;
import fernuni.propra.file_processing.UserReadInputWriteOutputException;
import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Room;
import fernuni.propra.internal_data_model.Wall;

public class CandidateSearcherTest {
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
				"instances/validationInstances/Selbsttest_20c.xml"	// 11	
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

			@Override
			public void replaceLamps(List<Lamp> lamps) {
				// TODO Auto-generated method stub
				
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
	public void testSearchCandidates() {
		//Arrange 
		ICandidateSearcher candidateSearcher1 = AlgorithmFactory.getAlgorithmFactory().createCandidateSearcher();
		ICandidateSearcher candidateSearcher2 = AlgorithmFactory.getAlgorithmFactory().createCandidateSearcher();
		ICandidateSearcher candidateSearcher3 = AlgorithmFactory.getAlgorithmFactory().createCandidateSearcher();
		
		//Act
		List<Lamp> candidates = null;
		List<Lamp> candidates2 = null;
		List<Lamp> candidates3 = null;
		try {
			candidates = candidateSearcher1.searchCandidates(room,null);
			candidates2 = candidateSearcher2.searchCandidates(roomHufeisen,null);
			candidates3 = candidateSearcher2.searchCandidates(rooms.get(9),null);
		} catch (CandidateSearcherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		assertTrue(candidates != null && candidates.size() == 1 && candidates.get(0).isEqual(new Point(0.5,0.5)));
		
		assertTrue(candidates2 != null && candidates2.size() == 2);
		assertTrue(candidates2.get(0).isEqual(new Point(-1.5,0.5)));
		assertTrue(candidates2.get(1).isEqual(new Point(1.5,0.5)));
	}


	@Test
	public void testReduceRectangles() {
		// Arrange
		CandidateSearcher candidateSearcher = (CandidateSearcher) AlgorithmFactory.getAlgorithmFactory().createCandidateSearcher();
		RectangleWithTag refRectangle = new RectangleWithTag(new Point(0,0), new Point(1,1),0);
		refRectangle.addTag(1); refRectangle.addTag(2); refRectangle.addTag(3);
		RectangleWithTag refRectangle2 = new RectangleWithTag(new Point(0.5,0), new Point(1,0.5),0);
		refRectangle2.addTag(1); refRectangle2.addTag(2); refRectangle2.addTag(3); refRectangle2.addTag(4); refRectangle2.addTag(5);
		
		RectangleWithTag refRectangle3 = new RectangleWithTag(pc10, pc4, 0);
		refRectangle3.addTag(1); refRectangle3.addTag(2); refRectangle3.addTag(3); refRectangle3.addTag(4); refRectangle3.addTag(5);
		refRectangle3.addTag(6); refRectangle3.addTag(7); refRectangle3.addTag(8); refRectangle3.addTag(9); refRectangle3.addTag(10);
		refRectangle3.addTag(11);
		
		RectangleWithTag refRectangle4 = new RectangleWithTag(p31, p36, 0);
		refRectangle4.addTag(2); refRectangle4.addTag(3); refRectangle4.addTag(6); refRectangle4.addTag(7); 
		
		RectangleWithTag refRectangle5 = new RectangleWithTag(new Point(1,0), new Point(2,1), 0);
		refRectangle5.addTag(1); refRectangle5.addTag(4); refRectangle5.addTag(5); refRectangle5.addTag(7);

		//Act
		//1st room
		List<RectangleWithTag> reducedRectangles = null;
		try {
			reducedRectangles = candidateSearcher.reduceRectangles(
					new OriginalPartialRectanglesFinder().findOriginalPartialRectangles(room, null));

		} catch (OriginalPartialRectanglesFinderException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
				
		//2nd room
		List<RectangleWithTag> reducedRectangles2 = null;
		try {
			reducedRectangles2  = candidateSearcher.reduceRectangles(
					new OriginalPartialRectanglesFinder().findOriginalPartialRectangles(room2, null));
		} catch (OriginalPartialRectanglesFinderException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
		
		//3rd room
		List<RectangleWithTag> reducedRectangles3 = null;
		try {
			reducedRectangles3 = candidateSearcher.reduceRectangles(
					new OriginalPartialRectanglesFinder().findOriginalPartialRectangles(roomStar, null));
		} catch (OriginalPartialRectanglesFinderException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
		
		//4th room
		List<RectangleWithTag> reducedRectangles4 = null;
		try {
			reducedRectangles4  = candidateSearcher.reduceRectangles(
					new OriginalPartialRectanglesFinder().findOriginalPartialRectangles(roomHufeisen, null));
		} catch (OriginalPartialRectanglesFinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//Assert
		boolean test1 = reducedRectangles != null && reducedRectangles.size()==1;
		boolean test2 = reducedRectangles.get(0).equals(refRectangle);
		boolean test3 = reducedRectangles != null && reducedRectangles.size()==1;
		boolean test4 = reducedRectangles2.get(0).equals(refRectangle2);
		boolean test5 = reducedRectangles3 != null && reducedRectangles3.size()==1;
		boolean test6 = reducedRectangles3.get(0).equals(refRectangle3);
		boolean test7 = reducedRectangles4.get(0).equals(refRectangle4);
		boolean test8 = reducedRectangles4.get(1).equals(refRectangle5);
		
		
		assertTrue(test1 && test2 && test3 && test4 && test5 && test6 && test7 && test8);
		
	}
	
}
