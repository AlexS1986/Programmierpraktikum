package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

public class WallContainerEastTest {
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
	 * tests whether an east wall can be added and whether a south wall throws an
	 * exception.
	 */
	@Test
	public void testAdd() {
		// Arrange
		WallContainerEast wallContainerEast = new WallContainerEast();

		// Act
		boolean southWallAddedWException = false;
		try {
			wallContainerEast.add(w1);
			fail("WallContainerException expected");
		} catch (WallContainerException ex) {
			southWallAddedWException = true;
		}

		boolean eastWallAddedWOException = false;
		try {
			wallContainerEast.add(w2);
			eastWallAddedWOException = true;
		} catch (WallContainerException ex) {
			fail();
		}

		// Assert
		assertTrue("South wall did not throw an exception", southWallAddedWException);
		assertTrue("East wall threw an exception", eastWallAddedWOException);
	}

	/**
	 * Tests whether the nearest east wall is indeed returned.
	 */
	@Test
	public void testGetNearestEastWall() {
		// Arrange
		WallContainerEast wallContainerEast = new WallContainerEast();

		try {
			wallContainerEast.add(w2); // right wall
		} catch (WallContainerException e) {
		}

		try {
			wallContainerEast.add(w8); // left wall
		} catch (WallContainerException e) {
		}

		// Act
		Wall w10 = null;
		try {
			// right wall expected
			w10 = wallContainerEast.getNearestWall(-1, 1, 0.5);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		Wall w11 = null;
		try {
			// left wall expected
			w11 = wallContainerEast.getNearestWall(-1, 1, 0.0);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		Wall w12 = null;
		try {
			// left wall expected
			w12 = wallContainerEast.getNearestWall(-1, 1, -0.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		Wall w13 = null;
		try {
			// no wall expected
			w13 = wallContainerEast.getNearestWall(-1, -0.5, -0.001);
		} catch (WallContainerException e) {
			fail(e.getMessage());
		}

		// Assert
		assertTrue("The right wall w2 should have been returned",
				w10.getP1().equals(w2.getP1()) && w10.getP2().equals(w2.getP2()));
		assertFalse("The right wall w8 should have been returned",
				w11.getP1().equals(w2.getP1()) && w11.getP2().equals(w2.getP2()));
		assertTrue("The left wall w8 should have been returned",
				w12.getP1().equals(w8.getP1()) && w12.getP2().equals(w8.getP2()));
		assertTrue("no wall should have been " + "returned. range does not match", w13 == null);

	}

}
