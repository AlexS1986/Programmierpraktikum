package fernuni.propra.user_interface;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.LineSegment;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Wall;

public class RoomPanel extends RoomPanelAbstract{ 
	private static final int PIXEL_LAMP_DIAMETER = 10; // in pixels
	private List<PlotRectangle> rectangles = new ArrayList<PlotRectangle>();
	
	public RoomPanel(IRoom room) {
		super(room);
	}
	
	private static class PlotRectangle { //TODO only for testing
		private double x,y,width,height;
		private Color color;
		private String name;
		
		public PlotRectangle(String name, Color color, double x, double y, double width, double height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.name = name;
			this.color = color;
		}        
	}
	
	public void addRectangle(String name, Color color,double x, double y, double width, double height) { //TODO only for testing
		rectangles.add(new PlotRectangle(name, color, x, y, width, height));
	}
	
	public void removeLastRectangle() { // TODO for Debug
		if (rectangles.size() > 0) {
			rectangles.remove(rectangles.size() -1);
		}
	}
	

	@Override
	protected void drawLamps(Graphics2D g2D) {
		double scale = getScale();
		IRoom room = getRoom();
		Iterator<Lamp> lampIterator = room.getLamps();
		while(lampIterator.hasNext()) {
			Lamp lamp = lampIterator.next();
			Color lampColor = lamp.getOn() ? Color.YELLOW :  Color.DARK_GRAY;
			g2D.setColor(lampColor);
			g2D.fillOval( (int) (lamp.getX() * scale) - (int) Math.round(PIXEL_LAMP_DIAMETER/2.0), 
					(int) (lamp.getY() * scale) - (int) Math.round(PIXEL_LAMP_DIAMETER/2.0), PIXEL_LAMP_DIAMETER, PIXEL_LAMP_DIAMETER);
			g2D.setStroke(new BasicStroke(2));
			g2D.setColor(Color.BLACK);
			g2D.drawOval((int) (lamp.getX() * scale) - (int) Math.round(PIXEL_LAMP_DIAMETER/2.0), 
					(int) (lamp.getY() * scale) - (int) Math.round(PIXEL_LAMP_DIAMETER/2.0), PIXEL_LAMP_DIAMETER, PIXEL_LAMP_DIAMETER);
		}
		
	}

	@Override
	protected void drawRoom(Graphics2D g2D) {
		double scale = getScale();
		IRoom room = getRoom();
		g2D.setStroke(new BasicStroke(2/((float) scale)));
		Polygon p = new Polygon();
		
		Iterator<Point> cornerIterator = room.getCorners();
		while(cornerIterator.hasNext()) {
			Point corner = cornerIterator.next();
			p.addPoint((int) (corner.getX()), (int) (corner.getY()));
		}
		
		
		
		g2D.setColor(Color.ORANGE);
		g2D.fillPolygon(p);
		
		g2D.setColor(Color.BLACK);
		Iterator<Wall> wallIterator = room.getWalls();
		while(wallIterator.hasNext()) {
			LineSegment wall = wallIterator.next();
			g2D.drawLine((int) wall.getP1().getX(),(int) wall.getP1().getY(),
					(int) wall.getP2().getX(), (int) wall.getP2().getY());
		}
		
		
	}

	@Override
	protected void drawRectangles(Graphics2D g2D) { //TODO nur Test
		double scale = getScale();
		g2D.setStroke(new BasicStroke(2));
		for (PlotRectangle rectangle: rectangles) {
			g2D.setColor(rectangle.color);
			g2D.drawRect((int) (rectangle.x * scale), (int) (rectangle.y * scale), (int) (rectangle.width * scale), (int) (rectangle.height * scale));
			g2D.drawString(rectangle.name, (int) ((rectangle.x + rectangle.width/2) * scale), 
					(int) ((rectangle.y + rectangle.height/2) * scale) );
		}
	}
	
	
}
