package fernuni.propra.file_processing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.file_processing.FilePersistence;
import fernuni.propra.file_processing.PersistenceException;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.LineSegment;
import fernuni.propra.internal_data_model.Point;

public class FilePersistenceTest {
	Point p1,p2,p3,p4,p5;
	LineSegment l1,l2,l3,l4,l5;
	List<LineSegment> lineSegments;


	@Before
	public void setUp() {
		p1 = new Point (0,0);
		p2 = new Point (1,0);
		p3 = new Point(1,1);
		p4 = new Point(0,1);
		l1 = new LineSegment(p1, p2);
		l2 = new LineSegment(p2, p3);
		l3 = new LineSegment(p3,p4);
		l4 = new LineSegment(p4,p1);
		l5 = new LineSegment(p1, p3);
		lineSegments = new ArrayList<LineSegment>();
		lineSegments.add(l1);lineSegments.add(l2); lineSegments.add(l3); lineSegments.add(l4);
	}
	
	
	@Test
	public void testTestWallAndAddToWalls() {
		//Arrange
		LineSegment lccw1 = new LineSegment(p1,p4);
		LineSegment lccw2 = new LineSegment(p4, p3);
		LineSegment lccw3 = new LineSegment(p3,p2);
		LineSegment lccw4 = new LineSegment(p2, p1);
		
		ArrayList<LineSegment> walls1 = new ArrayList<LineSegment>();
		ArrayList<LineSegment> walls2 = new ArrayList<LineSegment>();
		walls2.add(lccw1);
		ArrayList<LineSegment> walls3 = new ArrayList<LineSegment>();
		walls3.add(lccw1); walls3.add(lccw2);
		ArrayList<LineSegment> walls4 = new ArrayList<LineSegment>();
		walls4.add(lccw1); walls4.add(lccw2); walls4.add(lccw3);
		
		
		ArrayList<LineSegment> walls5 = new ArrayList<LineSegment>();
		ArrayList<LineSegment> walls6 = new ArrayList<LineSegment>();
		walls6.add(l1);
		ArrayList<LineSegment> walls7 = new ArrayList<LineSegment>();
		walls7.add(l1); walls7.add(l2);
		ArrayList<LineSegment> walls8 = new ArrayList<LineSegment>();
		walls8.add(l1); walls8.add(l2); walls8.add(l3);
		
		//Act, Assert
		try {
			FilePersistence.testAndAddWallToWalls(lccw1, walls1);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		try {
			FilePersistence.testAndAddWallToWalls(lccw2, walls2);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		try {
			FilePersistence.testAndAddWallToWalls(lccw3, walls3);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		try {
			FilePersistence.testAndAddWallToWalls(lccw4, walls4);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		
		try {
			FilePersistence.testAndAddWallToWalls(l1, walls5);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		try {
			FilePersistence.testAndAddWallToWalls(l2, walls6);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		try {
			FilePersistence.testAndAddWallToWalls(l3, walls7);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		try {
			FilePersistence.testAndAddWallToWalls(l4, walls8);
		} catch(PersistenceException e) {
			fail(e.getMessage());
		}
		
		//Assert
		assertEquals(walls1.get(0), lccw1);
		assertEquals(walls2.get(1), lccw2);
		assertEquals(walls3.get(2), lccw3);
		assertEquals(walls4.get(3), lccw4);
		assertEquals(walls5.get(0), l1);
		assertEquals(walls6.get(1), l2);
		assertEquals(walls7.get(2), l3);
		assertEquals(walls8.get(3), l4);
		
	}
	
	
	@Test
	public void testReadInput() {
		//Arrange
		String[] xmlPathesOK = {"instances/validationInstances/Selbsttest_clockwise.xml",
				"instances/validationInstances/Selbsttest_counterClockwise.xml",
				"instances/validationInstances/Selbsttest_100a_incomplete.xml",
				"instances/validationInstances/Selbsttest_100a_incomplete.xml",
				"instances/validationInstances/Selbsttest_100a_solved.xml",
				"instances/validationInstances/Selbsttest_100a.xml",
				"instances/validationInstances/Selbsttest_100b.xml",
				"instances/validationInstances/Selbsttest_20a_incomplete.xml",
				"instances/validationInstances/Selbsttest_20a_solved.xml",
				"instances/validationInstances/Selbsttest_20a.xml",
				"instances/validationInstances/Selbsttest_20b.xml",
				"instances/validationInstances/Selbsttest_20c.xml"		
		};
		
		String[] xmlPathesNOK = {"instances/validationInstances/Selbsttest_clockwiseNOK.xml",
				"instances/validationInstances/Selbsttest_counterClockwiseNOK.xml"	
		};
		

		FilePersistence persistence = new FilePersistence();
		
		//Act, Assert
		for (String xmlFile: xmlPathesOK) {
			IRoom room = null;
			try {
				room = persistence.readInput(xmlFile);
			} catch(PersistenceException e) {
				fail(e.getMessage());
			}
		}
		
		for (String xmlFile: xmlPathesNOK) {
			IRoom room = null;
			try {
				room = persistence.readInput(xmlFile);
				fail("This xml file is not OK!" + xmlFile);
			} catch(PersistenceException e) {
				
			}
		}
			
	}

	@Test
	public void testWriteOutput() {
		fail("Not yet implemented");
	}
	
	/*@Test
	public void testIsCounterClockWise() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(1,0);
		Point p3 = new Point(1,1);
		Point p4 = new Point(0,1);
		List<Point> counterClockWise  = new ArrayList<Point>();
		List<Point> clockWise = new ArrayList<Point>();
		counterClockWise.add(p1); counterClockWise.add(p2); counterClockWise.add(p3); counterClockWise.add(p4);
		clockWise.add(p4); clockWise.add(p3); clockWise.add(p2); clockWise.add(p1);
		assertTrue(!FilePersistence.isCounterClockWise(clockWise, p2));
		assertTrue(FilePersistence.isCounterClockWise(counterClockWise, p2));
	}*/
	


}
