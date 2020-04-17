package fernuni.propra.internal_data_model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WallTest {
	Point p1,p2,p3,p4;
	Wall w1,w2,w3,w4,w5,w6,w7,w8,w9;
	
	@Before
	public void setUp() {
		//Arrange
		p1 = new Point(0,0);
		p2 = new Point(1,0);
		p3 = new Point(1,1);
		p4 = new Point(0,1);
		w1 = new Wall(p1,p2);
		w2 = new Wall(p2,p3);
		w3 = new Wall(p3,p4);
		w4 = new Wall(p4,p1);
		
		w5 = new Wall(p2,p1);
		w6 = new Wall(p3, p2);
		w7 = new Wall(p4, p3);
		w8 = new Wall(p1,p4);
		
		w9 = new Wall(p1,p1);
	}

	@Test
	public void testIsNorthWall() {
		//Act
		boolean test1 = w3.isNorthWall();
		boolean test2 = w1.isNorthWall();
		boolean test3 = w2.isNorthWall();
		boolean test4 = w9.isNorthWall();
		
		//Assert
		assertTrue(test1);
		assertFalse(test2);
		assertFalse(test3);
		assertFalse(test4);
	}

	@Test
	public void testIsWestWall() {
		//Act
		boolean test1 = w4.isWestWall();
		boolean test2 = w2.isWestWall();
		boolean test3 = w1.isWestWall();
		boolean test4 = w9.isWestWall();
		
		//Assert
		assertTrue(test1);
		assertFalse(test2);
		assertFalse(test3);
		assertFalse(test4);
	}

	@Test
	public void testIsSouthWall() {
		//Act
		boolean test1 = w1.isSouthWall();
		boolean test2 = w3.isSouthWall();
		boolean test3 = w2.isSouthWall();
		boolean test4 = w9.isSouthWall();
		
		//Assert
		assertTrue(test1);
		assertFalse(test2);
		assertFalse(test3);
		assertFalse(test4);
	}

	@Test
	public void testIsEastWall() {
		//Act
		boolean test1 = w2.isEastWall();
		boolean test2 = w4.isEastWall();
		boolean test3 = w1.isEastWall();
		boolean test4 = w9.isEastWall();
		
		//Assert
		assertTrue(test1);
		assertFalse(test2);
		assertFalse(test3);
		assertFalse(test4);
	}

}
