package fernuni.propra.algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

public class PositionOptimizerTest {
	private IRoom mockRoom, room, room2, roomStar, roomHufeisen;
	private Point p1, p2, p3,p4, p5, p6, p7, p8;
	private Point pc1, pc2, pc3,pc4, pc5, pc6, pc7, pc8,pc9, pc10, pc11, pc12;
	private Point p31, p32, p33, p34, p35, p36, p37, p38;
	private Wall w1, w2,w3,w4;
	private LinkedList<Point> corners, corners2;
	
	@Before
	public void setup() {
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
		mockRoom = new IRoom() {
			
			@Override
			public Iterator<Wall> getWalls() {
				// TODO Auto-generated method stub
				return walls.iterator();
			}
			
			@Override
			public int getNumberOfLamps() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMinY() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMinX() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMaxY() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getMaxX() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Iterator<Lamp> getLamps() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getID() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Iterator<Point> getCorners() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addLamp(Lamp lamp) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
	
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
	public void testOptimizePositions() {
		//Arrange
		IPositionOptimizer positionOptimizer = AlgorithmFactory.getAlgorithmFactory().createPositionOptimizer();
		IPositionOptimizer positionOptimizer2 = AlgorithmFactory.getAlgorithmFactory().createPositionOptimizer();
		ICandidateSearcher candidateSearcher = AlgorithmFactory.getAlgorithmFactory().createCandidateSearcher();
		
		List<Lamp> taggedCandidates = null;
		try {
			taggedCandidates = candidateSearcher.searchCandidates(room, null);
			Lamp lamp = new Lamp(0.0,0.0);
			lamp.addTag(1);
			taggedCandidates.add(lamp);
		} catch (CandidateSearcherException e) {
			fail("Candidates Searcher failed!");
		}
		
		List<Lamp> taggedCandidates2 = new LinkedList<Lamp>();
		Lamp lamp1 = new Lamp(0,0);
		lamp1.addTag(0); 
		Lamp lamp2 = new Lamp(0,0);
		lamp2.addTag(1); 
		Lamp lamp3 = new Lamp(0,0);
		lamp3.addTag(2); 
		Lamp lamp4 = new Lamp(0,0);
		lamp4.addTag(3); 
		
		Lamp lamp5 = new Lamp(0,0);
		lamp5.addTag(1); lamp5.addTag(2);
		Lamp lamp6 = new Lamp(0,0);
		lamp6.addTag(2);  lamp6.addTag(3);
		Lamp lamp7 = new Lamp(0,0);
		lamp7.addTag(3); lamp7.addTag(0);
		
		Lamp lamp8 = new Lamp(0,0);
		lamp8.addTag(1); lamp8.addTag(2); lamp8.addTag(3);
		Lamp lamp9 = new Lamp(0,0);
		lamp9.addTag(2);  lamp9.addTag(3); lamp9.addTag(0);
		Lamp lamp10 = new Lamp(0,0);
		lamp10.addTag(3); lamp10.addTag(0); lamp10.addTag(1);
		
		Lamp lamp11 = new Lamp(0,0);
		lamp11.addTag(0); lamp11.addTag(1); lamp11.addTag(2); lamp11.addTag(3);
		
		taggedCandidates2.add(lamp1); taggedCandidates2.add(lamp2); taggedCandidates2.add(lamp3); taggedCandidates2.add(lamp4);
		taggedCandidates2.add(lamp5); taggedCandidates2.add(lamp6); taggedCandidates2.add(lamp7); taggedCandidates2.add(lamp8);
		taggedCandidates2.add(lamp9); taggedCandidates2.add(lamp10); taggedCandidates2.add(lamp11);
		
		//Act
		List<Lamp> optimizedLamps = positionOptimizer.optimizePositions(null, taggedCandidates, null);
		List<Lamp> optimizedLamps2 = positionOptimizer2.optimizePositions(null, taggedCandidates2, null);
		
		//Assert
		for (int i = 0; i< optimizedLamps2.size()-1; i++) {
			assertFalse(optimizedLamps2.get(i).getOn());
		}
		assertTrue(optimizedLamps2.get(optimizedLamps2.size()-1).getOn());
		
		assertTrue(optimizedLamps.get(0).getOn());
		assertFalse(optimizedLamps.get(1).getOn());
	}

}
