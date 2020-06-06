package fernuni.propra.file_processing;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.LineSegment;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Room;
import fernuni.propra.internal_data_model.Wall;
import fernuni.propra.internal_data_model.LineSegment;
import fernuni.propra.internal_data_model.LineSegmentException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

class FilePersistence implements IPersistence { 
	private static final String DTDFileName = "DataModel.dtd";
    
    //all lamps are turned on initially
	@Override
	public IRoom readInput(String xmlFilePath) throws PersistenceException {	
		
		
		
		Document document = null;
		InputStreamReader isr = null;
		Room outRoom = null;
		try {
			File xmlFile = new File(xmlFilePath);
			checkFileAvailability(xmlFile);
			
			isr = new FileReader(xmlFile);
			StringBuilder sb = insertDTDForValidation(isr);
			
			//handle xml
			SAXBuilder builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
			document = builder.build(new StringReader(sb.toString()));
			Element raumNode = document.getRootElement();
			
			String ID = raumNode.getChildText("ID");
			
			Element cornersNode = raumNode.getChild("ecken");	
			List<Element> cornerNodes = cornersNode.getChildren("Ecke");
			LinkedList<Point> corners = new LinkedList<Point>();
			List<LineSegment> walls = new ArrayList<LineSegment>(); 
			
			for(Element cornerNode : cornerNodes) {
				Point tmpPoint = new Point(Double.parseDouble(cornerNode.getChildText("x")), Double.parseDouble(cornerNode.getChildText("y")));
				// add wall
				if (!corners.isEmpty()) {
					LineSegment newWall = new Wall(corners.getLast(), tmpPoint);
					testAndAddWallToWalls(newWall, walls);
				}
				
				// add corner
				corners.add(tmpPoint);
			}
			// add last wall
			LineSegment newWall = new Wall(corners.getLast(), corners.getFirst());
			testAndAddWallToWalls(newWall, walls);
			

			//add lamps
			List<Lamp> lamps = getLamps(raumNode, walls);
			
			outRoom = new Room(ID,lamps, corners);
			
		} catch (JDOMException e) {
			throw new PersistenceException(e);
		} catch (NumberFormatException e) {
			throw new PersistenceException(e);
		} catch (IOException e) {
			throw new PersistenceException(e);
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch(IOException e) {
					throw new PersistenceException(e);
				}
			}
		}	
		return outRoom;
	}

	
	@Override
	public void writeOutput(IRoom room, String xmlFile) throws PersistenceException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(xmlFile);
			
			Document outDocument = new Document();
			
			Element raumNode = new Element("Raum");
			outDocument.addContent(raumNode);
			
			Element ID = new Element("ID");
			ID.addContent(room.getID());
			raumNode.addContent(ID);
			
			Element cornersNode = new Element("ecken");
			Iterator<Point> cornersOfRoomIterator = room.getCorners();
			while(cornersOfRoomIterator.hasNext()) {
				Point corner = cornersOfRoomIterator.next();
				Element cornerNode = new Element("Ecke");
				Element xNode = new Element("x");
				Element yNode = new Element("y");
				xNode.addContent(String.valueOf(corner.getX()));
				yNode.addContent(String.valueOf(corner.getY()));
				cornerNode.addContent(xNode);
				cornerNode.addContent(yNode);
				cornersNode.addContent(cornerNode);
			}
			raumNode.addContent(cornersNode);
			
			Iterator<Lamp> lampIterator = room.getLamps();
			if (lampIterator.hasNext()) {
				Element lampsNode = new Element("lampen");
				while(lampIterator.hasNext()) {
					Lamp lamp = lampIterator.next();
					Element lampNode = new Element("Lampe");
					Element xNode = new Element("x");
					Element yNode = new Element("y");
					xNode.addContent(String.valueOf(lamp.getX()));
					yNode.addContent(String.valueOf(lamp.getY()));
					lampNode.addContent(xNode);
					lampNode.addContent(yNode);
					lampsNode.addContent(lampNode);
				}
				raumNode.addContent(lampsNode);
			}
			
			XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
			try {
				xmlOutputter.output(outDocument, fos);
			} catch (IOException e) {
				throw new PersistenceException(e);
			}
			
			
		} catch(IOException ioe) {
			throw new PersistenceException(ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch(IOException e) {
					throw new PersistenceException(e);
				}
			}
		}
		
	}
	
	
	private List<Lamp> getLamps(Element raumNode, List<LineSegment> walls) throws IOException {
		Element lampsNode = raumNode.getChild("lampen");
		List<Lamp> lamps = new LinkedList<Lamp>();
		if(lampsNode != null) { // contains lamps
			List<Element> lampNodes = lampsNode.getChildren("Lampe");	
			for (Element lampNode: lampNodes) {
				Lamp tmpLamp = new Lamp(Double.parseDouble(lampNode.getChildText("x")), Double.parseDouble(lampNode.getChildText("y")));				
				if (tmpLamp.isInsidePolygon(walls)) {
					//tmpLamp.turnOn();
					lamps.add(tmpLamp);
				} else {
					throw new IOException("Not all lamps are actually inside the room. Please provide a valid room layout");
				}	
			}
		}
		return lamps;
	}

	private StringBuilder insertDTDForValidation(InputStreamReader isr) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		int c = -1;
		while((c = isr.read()) != -1) {
			sb.append((char) c);
			if (c == 62) {
				break;
			}
		}
		
		sb.append(System.getProperty("line.separator"));
		sb.append(readDTDFile());
		sb.append(System.getProperty("line.separator"));
		while((c = isr.read()) != -1) {
			sb.append((char) c);
		}
		return sb;
	}
	
	
	private static void checkFileAvailability(File xmlFile) throws FileNotFoundException {
		if (!xmlFile.exists()) {
			throw new FileNotFoundException("File not found at \"" +  xmlFile + "\". Enter a valid file path.");
		}
		if(!xmlFile.isFile()) {
			throw new FileNotFoundException("Path does not point to a file. Enter a valid file path.");
		}
	}
	
	static void testAndAddWallToWalls(LineSegment newWall, List<LineSegment> walls) throws PersistenceException {
		//checks intersections and perpendicularity
		if (walls.isEmpty()) {
			walls.add(newWall);
			return;
		}
		if (!newWall.penetratesLineSegments(walls)) {
			if (newWall.perpendicular(walls.get(walls.size()-1))) {
				walls.add(newWall);
			} else {
				throw new PersistenceException("Sucessive walls are not perpendicular. Please provide a valid room layout!");
			}	
		} else {
			throw new PersistenceException("Walls intersect. Please provide a valid room layout!");
		}
	}
	
	private String readDTDFile() throws IOException{
		InputStreamReader isr = null; 
		
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE Raum [");
		
		try {
			InputStream inputStream = getClass().getResourceAsStream(System.getProperty("file.separator") + DTDFileName);
			isr = new InputStreamReader(inputStream);
			boolean firstTagRead = false;
			int c = -1;
			while((c=isr.read())!=-1) {
				if(firstTagRead) {
					sb.append((char) c);
				} 
				else {
					if(c==62) {
						firstTagRead = true;
					}
				}	
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch(IOException e) {
					throw new IOException(e);
				}
			}
		}
		sb.append(System.getProperty("line.separator"));
		sb.append("]>");
		return sb.toString();
	}
	
	
}
