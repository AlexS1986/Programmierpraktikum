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

import fernuni.propra.algorithm.runtime_information.RuntimeInformation;
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
	private Point pc1, pc2, pc3,pc4, pc5, pc6, pc7, pc8,pc9, pc10, pc11, pc12;
	private Point p31, p32, p33, p34, p35, p36, p37, p38;
	
	
	
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
		
		// build room star
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
		
		// build room Hufeisen
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
		CandidateSearcher candidateSearcher1 = new CandidateSearcher();
		CandidateSearcher candidateSearcher2 = new CandidateSearcher();
		CandidateSearcher candidateSearcher3 = new CandidateSearcher();
		
		
		//Act
		List<Lamp> candidates = null;
		List<Lamp> candidates2 = null;
		List<Lamp> candidates3 = null;
		try {
			candidates  = candidateSearcher1.searchCandidates(roomStar, new RuntimeInformation());
			candidates2 = candidateSearcher2.searchCandidates(roomHufeisen, new RuntimeInformation());
			candidates3 = candidateSearcher3.searchCandidates(rooms.get(9), new RuntimeInformation());
		} catch (CandidateSearcherException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		assertTrue(candidates != null && candidates.size() == 1 && candidates.get(0).isEqual(new Point(0.5,0.5)));
		
		assertTrue(candidates2 != null && candidates2.size() == 2);
		assertTrue(candidates2.get(0).isEqual(new Point(-1.5,0.5)));
		assertTrue(candidates2.get(1).isEqual(new Point(1.5,0.5)));
	}

	/** Checks if CandidateSearcher correctly determines the reduced tagged rectangles from a set of tagged rectangles that might overlap.
	 * The reduced set contains the rectangles that result from overlapping. Only rectangles whose tags are not a subset of the tags of another rectangle are kept.
	*/
	@Test
	public void testReduceRectangles() { 
		// Arrange
		ArrayList<RectangleWithTag> rectanglesWithTagIn = new ArrayList<RectangleWithTag>();
		CandidateSearcher candidateSearcher = new CandidateSearcher();
		RectangleWithTag refRectangleWithTag = new RectangleWithTag(new Point(-2,0), new Point(-1,1), 0);
		refRectangleWithTag.addTag(1);
		RectangleWithTag refRectangleWithTag2 = new RectangleWithTag(new Point(1,0), new Point(2,1),1);
		refRectangleWithTag2.addTag(2);
		
		// Hufeisenkonfiguration von Rechtecken
		RectangleWithTag rec0 = new RectangleWithTag(new Point(-2, 0), new Point(-1, 2), 0);
		RectangleWithTag rec1 = new RectangleWithTag(new Point(-2, 0), new Point(2, 1), 1);
		RectangleWithTag rec2 = new RectangleWithTag(new Point(1,0), new Point(2,2),2);
		rectanglesWithTagIn.add(rec0); rectanglesWithTagIn.add(rec1); rectanglesWithTagIn.add(rec2);
		
		
		// Act 
		ArrayList<RectangleWithTag> reducedRectangles = new ArrayList<RectangleWithTag>();
		try {
			reducedRectangles = candidateSearcher.reduceRectangles(rectanglesWithTagIn);
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
		// Assert
		assertTrue("Number of reduced rectangles is not correct.", reducedRectangles.size() == 2);
		assertTrue(reducedRectangles.get(0).equals(refRectangleWithTag));
		assertTrue(reducedRectangles.get(1).equals(refRectangleWithTag2));
		
	}
	
}
