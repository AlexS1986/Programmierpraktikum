package File_Processing_Component;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fernuni.propra.file_processing.FilePersistence;
import fernuni.propra.file_processing.PersistenceException;
import fernuni.propra.internal_data_model.Point;

public class FilePersistenceTest {

	@Test
	public void testReadInput() {
		String xmlFile = "instances/validationInstances/Selbsttest_clockwise.xml";
		//String xmlFile = "instances/validationInstances/test";
		FilePersistence persistence = new FilePersistence();
		
		try {
			persistence.readInput(xmlFile);
		} catch (PersistenceException e) {
			fail();
		}
		fail("Not yet implemented");
	}

	@Test
	public void testWriteOutput() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testIsCounterClockWise() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(1,0);
		Point p3 = new Point(1,1);
		Point p4 = new Point(0,1);
		List<Point> corners1  = new ArrayList<Point>();
		List<Point> corners2 = new ArrayList<Point>();
		corners1.add(p1); corners1.add(p2); corners1.add(p3); corners1.add(p4);
		corners2.add(p4); corners2.add(p3); corners2.add(p2); corners2.add(p1);
		assertTrue(!FilePersistence.isCounterClockWise(corners2, p2));
		assertTrue(FilePersistence.isCounterClockWise(corners1, p2));
	}
	


}
