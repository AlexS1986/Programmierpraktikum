package fernuni.propra.user_interface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;


import javax.swing.JPanel;

import fernuni.propra.internal_data_model.IRoom;

public abstract class RoomPanelAbstract extends JPanel{
	private IRoom room;
	private double scale;
	
	public RoomPanelAbstract(IRoom room) {
		this.room = room;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1024, 768));
	}
	
	String getRoomID() {
		return room.getID();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2D= (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		transformToRoomCoordinates(g2D);
		drawRoom(g2D);
		
		scaleBackToScreenCoordinates(g2D);
		drawLamps(g2D);
		drawRectangles(g2D); // TODO nur Test
			
	}
	protected abstract void drawRectangles(Graphics2D g2D);
	
	protected abstract void drawLamps(Graphics2D g2D) ;

	protected abstract void drawRoom(Graphics2D g2D) ;
	
	private void scaleBackToScreenCoordinates(Graphics2D g2D) {
		AffineTransform myTransform;
		myTransform = AffineTransform.getScaleInstance(1/scale, 1/scale);
		g2D.transform(myTransform);
	}

	private void transformToRoomCoordinates(Graphics2D g2D) {
		double sx =  (0.9 * getWidth())/(room.getMaxX()-room.getMinX());
		double sy =  (0.9 * getHeight())/(room.getMaxY()-room.getMinY());
		
		scale = Math.min(sx, sy);
		double centerOffset = 0.5*Math.min(0.1*getWidth(),0.1*getHeight());
		AffineTransform myTransform = AffineTransform.getScaleInstance(scale, scale);
		g2D.transform(myTransform);
		myTransform = AffineTransform.getTranslateInstance(-room.getMinX() + centerOffset/scale,-room.getMinY() + centerOffset/scale);
		g2D.transform(myTransform);
	}
	
	protected IRoom getRoom() {
		return this.room;
	}
	
	protected double getScale() {
		return this.scale;
	}
		
}
