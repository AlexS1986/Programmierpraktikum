package fernuni.propra.internal_data_model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Represents a lamp or candidate for a lamp position.
 * <p>
 * {@link Lamp}s can be tagged in order to specifiy the portions of an
 * {@link IRoom} that are illuminated if this {@link Lamp} is turned on. Tags
 * are integer values.
 * <p>
 * {@link Lamp}s can either be turned on or be turned off.
 * <p>
 * 
 * @author alex
 *
 */
public class Lamp extends Point {
	private volatile boolean on; // the status of the lamp
	// the tags of this lamp
	HashSet<Integer> tagsOfCoveredRoomPortions = new HashSet<Integer>();

	/**
	 * Constructor
	 * 
	 * @param x : x-component of the new {@link Lamp}
	 * @param y : y-component of the new {@link Lamp}
	 */
	public Lamp(double x, double y) {
		super(x, y);
	}

	/**
	 * Constructor
	 * 
	 * @param x   : x-component of the new {@link Lamp}
	 * @param y   : y-component of the new {@link Lamp}
	 * @param tag : an intial tag
	 */
	public Lamp(double x, double y, int tag) {
		super(x, y);
		tagsOfCoveredRoomPortions.add(tag);
	}

	/**
	 * Constructor
	 * 
	 * @param x    : x-component of the new {@link Lamp}
	 * @param y    : y-component of the new {@link Lamp}
	 * @param tags : The initial tags of this {@link Lamp}
	 */
	public Lamp(double x, double y, HashSet<Integer> tags) {
		super(x, y);
		if (tags != null) {
			this.tagsOfCoveredRoomPortions = tags;
		}
	}

	/**
	 * 
	 * @param tag : the tag to be added.
	 */
	public void addTag(Integer tag) {
		tagsOfCoveredRoomPortions.add(tag);
	}

	/**
	 * Returns a copy the tags of this lamp.
	 * 
	 * @return A {@link HashSet}<{@link Integer}> that represents all tags of this
	 *         {@link Lamp}.
	 */
	public HashSet<Integer> getCopyOfTags() {
		HashSet<Integer> outTags = new HashSet<Integer>();
		for (Integer tag : tagsOfCoveredRoomPortions) {
			outTags.add(tag.intValue());
		}
		return outTags;
	}

	/**
	 * Turns this {@link Lamp} on.
	 */
	public void turnOn() {
		on = true;
	}

	/**
	 * Turns this {@link Lamp} off.
	 */
	public void turnOff() {
		on = false;
	}

	/**
	 * 
	 * @return : a boolean that represents whether this {@link Lamp} is turned on
	 */
	public boolean getOn() {
		return on;
	}

	/**
	 * @return a deep copy of this {@link Lamp}, tags are also deep copied.
	 */
	public Lamp deepCopy() {
		Lamp outLamp = new Lamp(this.getX(), this.getY());
		for (Integer tag : this.tagsOfCoveredRoomPortions) {
			outLamp.addTag(tag.intValue());
		}
		if (this.on) {
			outLamp.turnOn();
		}
		return outLamp;
	}

}
