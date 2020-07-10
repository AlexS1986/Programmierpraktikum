package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Room;
import fernuni.propra.internal_data_model.Wall;

public class UserSolveAASTest {

	private IRoom roomStar, roomHufeisen;
	private Point pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8, pc9, pc10, pc11, pc12;
	private Point p31, p32, p33, p34, p35, p36, p37, p38;

	@Before
	public void setup() {

		// star
		pc1 = new Point(1, -1);
		pc2 = new Point(2, -1);
		pc3 = new Point(2, 1);
		pc4 = new Point(1, 1);
		pc5 = new Point(1, 2);
		pc6 = new Point(-1, 2);
		pc7 = new Point(-1, 1);
		pc8 = new Point(-2, 1);
		pc9 = new Point(-2, -1);
		pc10 = new Point(-1, -1);
		pc11 = new Point(-1, -2);
		pc12 = new Point(1, -2);
		LinkedList<Point> cornersStar = new LinkedList<Point>();
		cornersStar.add(pc1);
		cornersStar.add(pc2);
		cornersStar.add(pc3);
		cornersStar.add(pc4);
		cornersStar.add(pc5);
		cornersStar.add(pc6);
		cornersStar.add(pc7);
		cornersStar.add(pc8);
		cornersStar.add(pc9);
		cornersStar.add(pc10);
		cornersStar.add(pc11);
		cornersStar.add(pc12);

		roomStar = new Room("star", null, cornersStar);

		// hufeisen
		p31 = new Point(-2, 0);
		p32 = new Point(2, 0);
		p33 = new Point(2, 2);
		p34 = new Point(1, 2);
		p35 = new Point(1, 1);
		p36 = new Point(-1, 1);
		p37 = new Point(-1, 2);
		p38 = new Point(-2, 2);
		LinkedList<Point> cornersHufeisen = new LinkedList<Point>();
		cornersHufeisen.add(p31);
		cornersHufeisen.add(p32);
		cornersHufeisen.add(p33);
		cornersHufeisen.add(p34);
		cornersHufeisen.add(p35);
		cornersHufeisen.add(p36);
		cornersHufeisen.add(p37);
		cornersHufeisen.add(p38);
		roomHufeisen = new Room("hufeisen", null, cornersHufeisen);

	}

	/**
	 * Tests if only 1 lamp is found for roomStar and 2 lamps are found for
	 * roomHufeisen
	 */
	@Test
	public void testSolve() {
		// Arrange
		UserSolveAAS userSolveStar = new UserSolveAAS();
		UserSolveAAS userSolveHufeisen = new UserSolveAAS();

		// Act
		try {
			userSolveStar.solve(roomStar, 100);
			userSolveHufeisen.solve(roomHufeisen, 100);
		} catch (UserSolveAASException e) {
			fail();
		}
		Iterator<Lamp> lampIteratorStar = roomStar.getLamps();
		Iterator<Lamp> lampIteratorHufeisen = roomHufeisen.getLamps();

		// Assert

		// star
		assertTrue("At least one lamp should have been found", lampIteratorStar.hasNext());
		lampIteratorStar.next();
		assertFalse("Only one lamp should have been found", lampIteratorStar.hasNext());

		// Hufeisen
		assertTrue("At least one lamp should have been found", lampIteratorHufeisen.hasNext());
		lampIteratorHufeisen.next();
		assertTrue("Second lamp should have been found", lampIteratorHufeisen.hasNext());
		lampIteratorHufeisen.next();
		assertFalse("Only two lamps should have been found", lampIteratorHufeisen.hasNext());

	}

}
