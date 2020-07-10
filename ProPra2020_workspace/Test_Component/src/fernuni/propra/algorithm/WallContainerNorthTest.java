package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

public class WallContainerNorthTest {
	Point p1, p2, p3, p4;
	Wall w1, w2, w3, w4, w5, w6, w7, w8, w9;

	@Before
	public void setUp() {
		// Arrange
		p1 = new Point(0, 0);
		p2 = new Point(1, 0);
		p3 = new Point(1, 1);
		p4 = new Point(0, 1);
		w1 = new Wall(p1, p2, 0);
		w2 = new Wall(p2, p3, 0);
		w3 = new Wall(p3, p4, 0);
		w4 = new Wall(p4, p1, 0);

		w5 = new Wall(p2, p1, 0);
		w6 = new Wall(p3, p2, 0);
		w7 = new Wall(p4, p3, 0);
		w8 = new Wall(p1, p4, 0);

		w9 = new Wall(p1, p1, 0);
	}

	/**
	 * tests whether an north wall can be added and whether a east wall throws an
	 * exception.
	 */
	@Test
	public void testAdd() {
		// Arrange
		WallContainerNorth wallContainerNorth = new WallContainerNorth();

		// Act
		boolean eastWallAddedWException = false;
		try {
			wallContainerNorth.add(w2);
			fail("WallContainerException expected");
		} catch (WallContainerException ex) {
			eastWallAddedWException = true;
		}

		boolean northWallAddedWOException = false;
		try {
			wallContainerNorth.add(w3);
			northWallAddedWOException = true;
		} catch (WallContainerException ex) {
			fail();
		}

		// Assert
		assertTrue("An east wall should throw an exception", eastWallAddedWException);
		assertTrue("A north wall should not throw an exception", northWallAddedWOException);
	}

	/**
	 * Tests whether the nearest north wall is indeed returned.
	 */
	@Test
	public void testGetNearestNorthWall() {
		// Arrange
		WallContainerNorth wallContainerNorth = new WallContainerNorth();

		try {
			wallContainerNorth.add(w3); // upper wall
		} catch (WallContainerException e) {
		}

		try {
			wallContainerNorth.add(w5); // lower wall
		} catch (WallContainerException e) {
		}

		// Act
		Wall w10 = null;
		try {
			// upper wall expected
			w10 = wallContainerNorth.getNearestWall(-1, 1, 0.5);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		Wall w11 = null;
		try {
			// lower wall expected
			w11 = wallContainerNorth.getNearestWall(-1, 1, 0.0);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		Wall w12 = null;
		try {
			// lower wall expected
			w12 = wallContainerNorth.getNearestWall(-1, 1, -0.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		Wall w13 = null;
		try {
			// no wall expected
			w13 = wallContainerNorth.getNearestWall(-1, -0.5, -0.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		// Assert
		assertTrue("upper wall needs to be returned", w10.getP1().equals(w3.getP1()) && w10.getP2().equals(w3.getP2()));
		assertTrue("upper wall needs to be returned", w11.getP1().equals(w5.getP1()) && w11.getP2().equals(w5.getP2()));
		assertTrue("lower wall needs to be returned", w12.getP1().equals(w5.getP1()) && w12.getP2().equals(w5.getP2()));
		assertTrue("no wall should be returned", w13 == null);

	}

}
