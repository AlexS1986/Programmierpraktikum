package fernuni.propra.user_interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;

import javax.swing.JPanel;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

/**
 * A template {@link JPanel} that defines the interface for displaying an
 * {@link IRoom} in a 1024x768 window. To this end the {@link IRoom} is scaled.
 * <p>
 * Provides the method paint as a template method, where implementing classes
 * must specify:
 * <p>
 * 1.) How the {@link IRoom} should be displayed
 * <p>
 * 2.) How the {@link Lamp}s of the {@link IRoom} should be displayed.
 * <p>
 * 3.) Optional: How additional rectangles might be displayed.
 * <p>
 * Implementing classes: {@link RoomPanel}
 * <p>
 * 
 * @author alex
 *
 */
public abstract class RoomPanelAbstract extends JPanel {
	private IRoom room;
	private double scale; // the scale needed to display the room
							// on a 1024x768 screen
							// scale ~ pixels/(physical length)

	/**
	 * Constructor
	 * 
	 * @param room : The {@link IRoom} to be displayed.
	 */
	public RoomPanelAbstract(IRoom room) {
		this.room = room;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1024, 768));

	}

	/**
	 * @return the ID of the {@link IRoom}
	 */
	String getRoomID() {
		return room.getID();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;
		// request antialiasing
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw room in pixel coordinates in 1024x768 window
		drawRoom(g2D);

		// draw lamps in pixel coordinates in 1024x768 window
		drawLamps(g2D);

		// draw rectangles in pixel coordinates in 1024x768 window
		drawRectangles(g2D);

		drawLegend(g2D);

		// get x-y origin to bottom left corner of window
		g2D.scale(1, -1);
		g2D.translate(0, -getHeight());

	}

	/**
	 * Draw all rectangles in pixel coordinate system. This function is mainly
	 * introduced for debugging.
	 * 
	 * @param g2D : The {@link Graphics2D} of this panel.
	 */
	protected abstract void drawRectangles(Graphics2D g2D);

	/**
	 * Draw all {@link Lamp}s in pixel coordinate system.
	 * 
	 * @param g2D : The {@link Graphics2D} of this panel.
	 */
	protected abstract void drawLamps(Graphics2D g2D);

	/**
	 * Draw {@link IRoom} in pixel coordinate system
	 * 
	 * @param g2D : The {@link Graphics2D} of this panel.
	 */
	protected abstract void drawRoom(Graphics2D g2D);

	/**
	 * 
	 * @return the {@link IRoom} that is to be drawn.
	 */
	protected IRoom getRoom() {
		return this.room;
	}

	/**
	 * 
	 * @return the current scaling factor, i.e. the ratio of
	 *         deltaPixel/deltaPhysicalLength
	 */
	protected double getScale() {
		double sx = (0.9 * getWidth()) / (room.getMaxX() - room.getMinX());
		double sy = (0.9 * getHeight()) / (room.getMaxY() - room.getMinY());
		scale = Math.min(sx, sy);
		return this.scale;
	}

	/**
	 * The offset in x-direction, with 5% of the total width of this window, as
	 * offset at the boundaries.
	 * 
	 * @return
	 */
	protected double getPixelOffsetX() {
		double scale = getScale();
		double centerOffset = 0.5 * Math.min(0.1 * getWidth(), 0.1 * getHeight());
		IRoom room = getRoom();
		double xOffset = -room.getMinX();
		double pixelOffsetX = xOffset * scale + centerOffset;
		return pixelOffsetX;
	}

	/**
	 * The offset in y-direction, with 5% of the total width of this window, as
	 * offset at the boundaries.
	 * 
	 * @return
	 */
	protected double getPixelOffsetY() {
		double scale = getScale();
		double centerOffset = 0.5 * Math.min(0.1 * getWidth(), 0.1 * getHeight());
		IRoom room = getRoom();
		double yOffset = -room.getMinY();
		double pixelOffsetY = yOffset * scale + centerOffset;
		return pixelOffsetY;
	}

	/**
	 * Add a rectangle to be plotted.
	 * 
	 * @param name:   String to be displayed
	 * @param color:  Color of rectangle
	 * @param x:      x-coordinate of bottom left point
	 * @param y:      y coordinate of bottom left point
	 * @param width   : width of rectangle
	 * @param height: height of rectangles
	 */
	protected abstract void addRectangle(String name, Color color, double x, double y, double width, double height);

	/**
	 * Removes the rectangle that has been added last.
	 */
	protected abstract void removeLastRectangle();

	/**
	 * Draws a simple explanatory string that describes how to interpret the
	 * display.
	 * 
	 * @param g2D
	 */
	private void drawLegend(Graphics2D g2D) {
		int lampOnCount = 0;
		Iterator<Lamp> lampIterator = room.getLamps();
		while(lampIterator.hasNext()) {
			if (lampIterator.next().getOn()) {
				lampOnCount++;
			}
		}
		
		double centerOffset = 0.5 * Math.min(0.1 * getWidth(), 0.1 * getHeight());
		g2D.drawString("yellow circle: lamp; black circle: lamp candidate; lamps on/total: "+
		lampOnCount+"/"+room.getNumberOfLamps(), (int) (centerOffset / 2),
				(int) (centerOffset / 2));
	}

}
