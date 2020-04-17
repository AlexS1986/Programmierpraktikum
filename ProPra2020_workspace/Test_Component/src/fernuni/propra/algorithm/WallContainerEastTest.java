package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

public class WallContainerEastTest {
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
		WallContainerEast wallContainerEast = new WallContainerEast();
		
		//Act
		boolean test1 = false;
		try {
			wallContainerEast.add(w1);
			fail("WallContainerException expected");
		} catch(WallContainerException ex) {
			test1 = true;
		}
		
		boolean test2 = false;
		try {
			wallContainerEast.add(w2);
			test2 = true;
		} catch (WallContainerException ex) {
			fail();
		}
		
		//Assert
		assertTrue(test1);
		assertTrue(test2);
	}

	@Test
	public void testGetNearestEastWall() {
		//Arrange
		WallContainerEast wallContainerEast = new WallContainerEast();
		
		try {
			wallContainerEast.add(w2);
		} catch (WallContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			wallContainerEast.add(w8);
		} catch (WallContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Act
		Wall w10 = null;
		try {
			w10 = wallContainerEast.getNearestWall(-1, 1, 0.5);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		
		Wall w11 = null;
		try {
			w11 = wallContainerEast.getNearestWall(-1, 1, 0.0);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		
		Wall w12 = null;
		try {
			w12 = wallContainerEast.getNearestWall(-1, 1, -0.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		
		Wall w13 = null;
		try {
			w13 = wallContainerEast.getNearestWall(-1, -0.5, -0.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}
		
		
		//Assert
		assertTrue(w10.getP1().isEqual(w2.getP1()) && w10.getP2().isEqual(w2.getP2()));
		assertFalse(w11.getP1().isEqual(w2.getP1()) && w11.getP2().isEqual(w2.getP2()));
		assertTrue(w12.getP1().isEqual(w8.getP1()) && w12.getP2().isEqual(w8.getP2()));
		assertTrue(w13 == null);
		
	}

}