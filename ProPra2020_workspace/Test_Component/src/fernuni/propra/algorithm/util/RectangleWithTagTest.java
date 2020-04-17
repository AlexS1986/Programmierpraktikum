package fernuni.propra.algorithm.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.internal_data_model.Point;

public class RectangleWithTagTest {
	private Point p1,p2,p3,p4;
	private RectangleWithTag rec1;

	@Before
	public void setUp() throws Exception {
		
		//Arrange
		p1 = new Point(0,0);
		p2 = new Point(1,0);
		p3 = new Point(1,1);
		p4 = new Point(0,1);
		List<Integer> initTags = new ArrayList<Integer>();
		initTags.add(1);
		rec1 = new  RectangleWithTag(p1, p3, initTags);
	}

	@Test
	public void testContainsTag() {
		//Act
		boolean test1 = rec1.containsTag(1);
		boolean test2 = !rec1.containsTag(2);
		
		//Assert
		assertTrue(test1);
		assertTrue(test2);
	}

	@Test
	public void testAddTag() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashSet() {
		//Arrange
		List<Integer> initTags2 = new ArrayList<Integer>(); initTags2.add(2);
		RectangleWithTag newRectangleWithTag = new RectangleWithTag(p1, p3, initTags2 );
		HashSet<RectangleWithTag> rectanglesWithTags = new HashSet<RectangleWithTag>();
		rectanglesWithTags.add(rec1);
		
		
		//Act
		boolean test1 = rectanglesWithTags.contains(newRectangleWithTag);
		boolean test2 = rec1.equals(newRectangleWithTag);
		
		//Assert
		assertFalse(test1);
		assertFalse(test2);
	}
	
}
