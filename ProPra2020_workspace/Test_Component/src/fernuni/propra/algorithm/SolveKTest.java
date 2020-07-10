package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import fernuni.propra.algorithm.runtime_information.RuntimeInformation;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Room;

public class SolveKTest {

	private IRoom roomSquare, roomStar, roomHufeisen;
	private Point p1, p2, p3, p4;
	private Point pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8, pc9, pc10, pc11, pc12;
	private Point p31, p32, p33, p34, p35, p36, p37, p38;
	private LinkedList<Point> corners;

	@Before
	public void setup() {
		p1 = new Point(0, 0);
		p2 = new Point(1, 0);
		p3 = new Point(1, 1);
		p4 = new Point(0, 1);

		corners = new LinkedList<Point>();
		corners.add(p1);
		corners.add(p2);
		corners.add(p3);
		corners.add(p4);

		roomSquare = new Room("test", null, corners);

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
	 * Tests whether this class general functionality is implemented correctly Also
	 * checks getNumberOfLampsBestSolution
	 */
	@Test
	public void testRun() {
		// Arrange
		SolveK sK = new SolveK(roomHufeisen, new RuntimeInformation());

		// Act
		sK.run();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException ie) {

		}
		int lampsNo = sK.getNumberOfOnLampsBestSolution();
		boolean correctLampNumber = lampsNo == 2;

		// Assert
		assertTrue("LampNumberIsNotCorrect", correctLampNumber);
	}

	/**
	 * Tests if call really blocks until computation is done
	 */
	@Test
	public void testTestIfComputationFinished() {
		// Arrange
		RuntimeInformation rti = new RuntimeInformation();
		SolveK sK = new SolveK(roomHufeisen, rti);
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.yield(); // allow main thread to proceed
					Thread.currentThread().sleep(1000);
					Thread t2 = new Thread(sK);
					t2.start();
				} catch (InterruptedException e) {
				}
			}
		};
		Thread t = new Thread(r);
		t.start();

		// Act

		long startTime = System.currentTimeMillis();
		try {
			sK.testIfComputationFinished();
		} catch (InterruptedException e) {

		}
		long endTime = System.currentTimeMillis();
		long timeBlocked = endTime - startTime;

		// Assert
		assertTrue("testIfComputationFinished did not " + "block until computation was done", timeBlocked > 1000);
	}

}
