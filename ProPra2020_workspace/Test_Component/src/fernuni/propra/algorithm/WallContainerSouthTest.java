package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

public class WallContainerSouthTest {
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
	public void testAdd() {
		//Arrange
		WallContainerSouth wallContainerSouth = new WallContainerSouth();
		
		//Act
		boolean test1 = false;
		try {
			wallContainerSouth.add(w2);
			fail("WallContainerException expected");
		} catch(WallContainerException ex) {
			test1 = true;
		}
		
		boolean test2 = false;
		try {
			wallContainerSouth.add(w1);
			test2 = true;
		} catch (WallContainerException ex) {
			fail();
		}
		
		//Assert
		assertTrue(test1);
		assertTrue(test2);
	}

	@Test
	public void testGetNearestWestWall() {
		//Arrange
		WallContainerSouth wallContainerSouth = new WallContainerSouth();
		
		try {
			wallContainerSouth.add(w1);
		} catch (WallContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			wallContainerSouth.add(w7);
		} catch (WallContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Act
		Wall w10 = null;
		try {
			w10 = wallContainerSouth.getNearestWall(-1, 1, 0.5);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		boolean test1 = w10.getP1().isEqual(w1.getP1()) && w10.getP2().isEqual(w1.getP2());
		
		Wall w11 = null;
		try {
			w11 = wallContainerSouth.getNearestWall(-1, 1, 1.0);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		boolean test2 = w11.getP1().isEqual(w7.getP1()) && w11.getP2().isEqual(w7.getP2());
		
		Wall w12 = null;
		try {
			w12 = wallContainerSouth.getNearestWall(-1, 1, 1.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		boolean test3 = w12.getP1().isEqual(w7.getP1()) && w12.getP2().isEqual(w7.getP2());
		
		Wall w13 = null;
		try {
			w13 = wallContainerSouth.getNearestWall(-1, -0.5, -0.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		boolean test4 = w13 == null;
		
		
		//Assert
		assertTrue(test1);
		assertTrue(test2);
		assertTrue(test3);
		assertTrue(test4);
		
	}

}