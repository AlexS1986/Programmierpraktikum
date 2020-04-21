package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import java.util.HashSet;
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

public class IlluminationTesterTest {
	private IRoom mockRoom, room, room2, roomStar, roomHufeisen;
	private Point p1, p2, p3,p4, p5, p6, p7, p8;
	private Point pc1, pc2, pc3,pc4, pc5, pc6, pc7, pc8,pc9, pc10, pc11, pc12;
	private Point p31, p32, p33, p34, p35, p36, p37, p38;
	private Wall w1, w2,w3,w4;
	private LinkedList<Point> corners, corners2;

	@Before
	public void setUp() throws Exception {
		p1 = new Point(0,0);
		p2 = new Point(1,0);
		p3 = new Point (1,1);
		p4 = new Point(0,1);
		
		p5 = new Point(0.5, 1.0);
		p6 = new Point(0.5, 0.5);
		p7 = new Point(0,   0.5);
		
		
		
		w1 = new Wall(p1,p2);
		w2 = new Wall(p2,p3);
		w3 = new Wall(p3,p4);
		w4 = new Wall(p4,p1);
		
		List<Wall> walls = new LinkedList<Wall>();
		walls.add(w1); walls.add(w2); walls.add(w3); walls.add(w4);
		
		corners= new LinkedList<Point>();
		corners.add(p1); corners.add(p2); corners.add(p3); corners.add(p4);
		
		corners2= new LinkedList<Point>();
		corners2.add(p1); corners2.add(p2); corners2.add(p3); corners2.add(p5);
		corners2.add(p6); corners2.add(p7);
		
		room = new Room("test", null, corners);	
		room2 = new Room("test", null, corners2);	
		
		
		pc1 = new Point(1,-1);
		pc2 = new Point(2,-1);
		pc3 = new Point(2,1);
		pc4 = new Point(1,1);
		pc5 = new Point(1,2);
		pc6 = new Point(-1,2);
		pc7 = new Point(-1,1);
		pc8 = new Point(-2,1);
		pc9 = new Point(-2,-1);
		pc10 = new Point(-1,-1);
		pc11 = new Point(-1,-2);
		pc12 = new Point(1,-2);
		LinkedList<Point> cornersStar = new LinkedList<Point>();
		cornersStar.add(pc1);cornersStar.add(pc2);cornersStar.add(pc3);cornersStar.add(pc4);cornersStar.add(pc5);
		cornersStar.add(pc6);cornersStar.add(pc7);cornersStar.add(pc8);cornersStar.add(pc9);cornersStar.add(pc10);
		cornersStar.add(pc11);cornersStar.add(pc12);
		
		roomStar = new Room("star", null, cornersStar);
		
		
		p31 = new Point(-2,0);
		p32 = new Point(2,0);
		p33 = new Point(2,2);
		p34 = new Point(1,2);
		p35 = new Point(1,1);
		p36 = new Point(-1,1);
		p37 = new Point(-1,2);
		p38 = new Point(-2,2);
		LinkedList<Point> cornersHufeisen = new LinkedList<Point>();
		cornersHufeisen.add(p31);cornersHufeisen.add(p32);cornersHufeisen.add(p33);cornersHufeisen.add(p34);cornersHufeisen.add(p35);
		cornersHufeisen.add(p36);cornersHufeisen.add(p37);cornersHufeisen.add(p38);
		roomHufeisen = new Room("hufeisen", null, cornersHufeisen);
	}

	@Test
	public void testTestIfRoomIsIlluminatedIRoomIRuntimeIlluminationTester() {
		//Arrange 
		IIlluminationTester illuminationTester = AlgorithmFactory.getAlgorithmFactory().createIlluminiationTester();
		
		//Act
		boolean test1 = false;
		boolean test2 = false;
		boolean test3 = false;
		boolean test4 = false;
		boolean test5 = false;
		
		boolean test6 = false;
		boolean test7 = false;
		boolean test8 = false;
		boolean test9 = false;
		boolean test10 = false;
		
		try {
			//Room 1
			test1 = illuminationTester.testIfRoomIsIlluminated(room, null);
			Lamp lamp = new Lamp(0.5, 0.5);
			lamp.turnOff();
			room.addLamp(lamp);
			test2 = illuminationTester.testIfRoomIsIlluminated(room, null);
			lamp.turnOn();
			test3 = illuminationTester.testIfRoomIsIlluminated(room, null);
			lamp.turnOff();
			Lamp lamp2 = new Lamp(1.0, 3.0);
			lamp2.turnOn();
			room.addLamp(lamp2);
			test4 = illuminationTester.testIfRoomIsIlluminated(room, null);
			Lamp lamp3 = new Lamp(1.0, 1.0);
			lamp3.turnOn();
			room.addLamp(lamp3);
			test5 = illuminationTester.testIfRoomIsIlluminated(room, null);
			
			//Room Hufeisen
			test6 = illuminationTester.testIfRoomIsIlluminated(roomHufeisen, null);
			Lamp lamp4 = new Lamp(-1.5, 0.5);
			lamp4.turnOn();
			roomHufeisen.addLamp(lamp4);
			test7 = illuminationTester.testIfRoomIsIlluminated(roomHufeisen, null);
			Lamp lamp5 = new Lamp(1.5, 0.5);
			lamp5.turnOn();
			roomHufeisen.addLamp(lamp5);
			test8 = illuminationTester.testIfRoomIsIlluminated(roomHufeisen, null);
			
		} catch (IlluminationTesterException e) {
		}

		
		
		//Assert
		assertFalse(test1);
		assertFalse(test2);
		assertTrue(test3);
		assertFalse(test4);
		assertTrue(test5);
		
		assertFalse(test6);
		assertFalse(test7);
		assertTrue(test8);
		
	}

	@Test
	public void testTestIfRoomIsIlluminatedIteratorOfLampHashSetOfIntegerIRuntimeIlluminationTester() {
		//Arrange 
		IIlluminationTester illuminationTester = AlgorithmFactory.getAlgorithmFactory().createIlluminiationTester();
		HashSet<Integer> allTags = new HashSet<Integer>();
		allTags.add(0); allTags.add(1); allTags.add(2); allTags.add(3);
		List<Lamp> lamps= new LinkedList<Lamp>();
		Lamp lamp1 = new Lamp(0,0,0); 
		lamp1.turnOn();
		lamps.add(lamp1);
		
		//Act
		boolean test1 = illuminationTester.testIfRoomIsIlluminated(lamps.iterator(), allTags, null);
		Lamp lamp2 = new Lamp(0,0,1);
		lamp2.addTag(2); 
		lamp2.addTag(3);
		lamp2.turnOn();
		lamps.add(lamp2);
		boolean test2 = illuminationTester.testIfRoomIsIlluminated(lamps.iterator(), allTags, null);
		
		//Assert
		assertFalse(test1);
		assertTrue(test2);
	}

}
