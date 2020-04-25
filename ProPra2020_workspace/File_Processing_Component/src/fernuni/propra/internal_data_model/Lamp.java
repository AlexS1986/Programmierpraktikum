package fernuni.propra.internal_data_model;

import java.util.HashSet;
import java.util.Iterator;

public class Lamp extends Point{
	private volatile boolean on;
	HashSet<Integer> tagsOfCoveredRectangles = new HashSet<Integer>();
	
	public Lamp(double x, double y) {
		super(x, y);
	}
	
	public Lamp(double x, double y, int tag) {
		super(x, y);
		tagsOfCoveredRectangles.add(tag);
	}
	
	public Lamp(double x, double y, HashSet<Integer> tags) {
		super(x,y);
		if (tags != null) {
			this.tagsOfCoveredRectangles = tags;
		}
	}
	
	public void addTag(Integer tag) {
		tagsOfCoveredRectangles.add(tag);
	}
	
	public HashSet<Integer> getCopyOfTags() { 
		HashSet<Integer> outTags = new HashSet<Integer>();
		for (Integer tag : tagsOfCoveredRectangles) {
			outTags.add(tag);
		}
		return outTags;
	}
	
	public Iterator<Integer> iteratorTag() {
		return tagsOfCoveredRectangles.iterator();
	}

	public void turnOn() {
		on = true;
	}
	
	public void turnOff() {
		on = false;
	}
	
	public boolean getOn() {
		return on;
	}
	
	public Lamp deepCopy() {
		Lamp outLamp = new Lamp(this.getX(),this.getY());
		for(Integer tag : this.tagsOfCoveredRectangles) {
			outLamp.addTag(tag);
		}
		if (this.on) {
			outLamp.turnOn();
		}
		return outLamp;
	}
	
	
}
