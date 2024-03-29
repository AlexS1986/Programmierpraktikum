package fernuni.propra.file_processing;

import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.LineSegment;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.internal_data_model.Room;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * A specific provider of persistence for an {@link IRoom} that stores/and reads  the {@link IRoom} 
 * from an xml-file that adheres to the Document Type Definition (DTD) specified in Listing 1 of [1], i.e.
 * 
 * 	01 <?xml version="1.0" encoding="UTF-8"?> 
 *  02 <!ELEMENT Raum (ID, ecken, lampen?)> 
 *  03 <!ELEMENT ID (#PCDATA)>
 *  04 <!ELEMENT ecken (Ecke*)>
 *  05 <!ELEMENT lampen (Lampe*)> 
 *  06 <!ELEMENT Ecke (x, y)>
 *  07 <!ELEMENT Lampe (x, y)> 
 *  08 <!ELEMENT x (#PCDATA)>
 *  09 <!ELEMENT y (#PCDATA)>
 *  <p>
 *  {@link FilePersistence} makes use of 
 *  the JDOM2-library (see http://www.jdom.org/) 
 * 
 * @author alex
 *
 * [1] Aufgabenstellung Programmierpraktikum SS 2020
 */
class FilePersistence implements IPersistence {
	private static final String DTDFileName = "DataModel.dtd";

	// all lamps are turned on initially
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

			// parse xml
			SAXBuilder builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
			document = builder.build(new StringReader(sb.toString()));
			Element roomNode = document.getRootElement();

			String ID = roomNode.getChildText("ID");

			Element cornersNode = roomNode.getChild("ecken");
			if (cornersNode == null) { // if no corners are provided (which is valid according to DTD) an exception is
										// thrown since no computation can be done
				throw new PersistenceException(
						"No corners provided. Cannot compute anything. Please provide an input file with a valid number of Ecken.");
			}
			List<Element> cornerNodes = cornersNode.getChildren("Ecke");
			LinkedList<Point> corners = new LinkedList<Point>();
			List<LineSegment> walls = new ArrayList<LineSegment>();

			// loop over all corners
			for (Element cornerNode : cornerNodes) {
				Point tmpPoint = new Point(Double.parseDouble(cornerNode.getChildText("x")),
						Double.parseDouble(cornerNode.getChildText("y")));
				// add wall
				if (!corners.isEmpty()) {
					LineSegment newWall = new LineSegment(corners.getLast(), tmpPoint);
					testAndAddWallToWalls(newWall, walls);
				}
				// add corner
				corners.add(tmpPoint);
			}
			// add last wall
			LineSegment newWall = new LineSegment(corners.getLast(), corners.getFirst());
			testAndAddWallToWalls(newWall, walls);

			// add lamps
			List<Lamp> lamps = getLamps(roomNode, walls);

			outRoom = new Room(ID, lamps, corners);

		} catch (JDOMException e) {
			throw new PersistenceException("The xml-file could not be read correctly. "
					+ "Please provide a valid file.");
		} catch (NumberFormatException e) {
			throw new PersistenceException("The xml-file could not be read correctly. "
					+ "Please provide a valid file.");
		} catch (IOException e) {
			throw new PersistenceException("The xml-file could not be read correctly. "
					+ "Please provide a valid file.");
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
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

			// build xml structure conforming with DTD definition
			Document outDocument = new Document();

			// root node
			Element roomNode = new Element("Raum");
			outDocument.addContent(roomNode);

			// write ID
			Element ID = new Element("ID");
			ID.addContent(room.getID());
			roomNode.addContent(ID);

			// write corners
			Element cornersNode = new Element("ecken");
			Iterator<Point> cornersOfRoomIterator = room.getCorners();
			while (cornersOfRoomIterator.hasNext()) {
				Point corner = cornersOfRoomIterator.next();
				Element cornerNode = new Element("Ecke");
				// write x,y
				Element xNode = new Element("x");
				Element yNode = new Element("y");
				xNode.addContent(String.valueOf(corner.getX()));
				yNode.addContent(String.valueOf(corner.getY()));
				cornerNode.addContent(xNode);
				cornerNode.addContent(yNode);
				cornersNode.addContent(cornerNode);
			}
			roomNode.addContent(cornersNode);

			Iterator<Lamp> lampIterator = room.getLamps();
			if (lampIterator.hasNext()) {
				Element lampsNode = new Element("lampen");
				while (lampIterator.hasNext()) {
					Lamp lamp = lampIterator.next();
					if (lamp.getOn()) { // lamps are only appended to output if they are turned on in the solution, i.e.
										// they
										// are not only candidates but part of the best solution.
						Element lampNode = new Element("Lampe");
						Element xNode = new Element("x");
						Element yNode = new Element("y");
						xNode.addContent(String.valueOf(lamp.getX()));
						yNode.addContent(String.valueOf(lamp.getY()));
						lampNode.addContent(xNode);
						lampNode.addContent(yNode);
						lampsNode.addContent(lampNode);
					}

				}
				roomNode.addContent(lampsNode);
			}

			XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
			xmlOutputter.getFormat().setEncoding("UTF-8");
			try {
				// actually write output
				xmlOutputter.output(outDocument, fos);
			} catch (IOException e) {
				throw new PersistenceException(e);
			}
		} catch (IOException ioe) {
			throw new PersistenceException(ioe);
		} finally { // clean up
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw new PersistenceException(e);
				}
			}
		}

	}

	/**
	 * Returns a {@link List} of {@link Lamp}s that have been specified in the xml
	 * file. Uses JDOM-2.
	 * 
	 * @param roomNode : The "Raum" xml-Node of this valid inputfile
	 * @param walls
	 * @return
	 * @throws IOException
	 */
	private List<Lamp> getLamps(Element roomNode, List<LineSegment> walls) throws IOException {
		Element lampsNode = roomNode.getChild("lampen");
		List<Lamp> lamps = new LinkedList<Lamp>();
		if (lampsNode != null) { // contains lamps
			List<Element> lampNodes = lampsNode.getChildren("Lampe");
			for (Element lampNode : lampNodes) {
				Lamp tmpLamp = new Lamp(Double.parseDouble(lampNode.getChildText("x")),
						Double.parseDouble(lampNode.getChildText("y")));
				if (tmpLamp.isInsidePolygon(walls)) { // the lamp is actually positioned inside the room
					lamps.add(tmpLamp);
				} else {
					throw new IOException(
							"Not all lamps are actually inside the room. Please provide a valid room layout");
				}
			}
		}
		return lamps;
	}

	/**
	 * Allows for format checking of the file content that is provided by an
	 * {@link InputStreamReader}. Inserts the DTD specification after the first ">"
	 * (ASCII dez = 62) has been read -> works for xml-files. Otherwise the
	 * specification is appended to the end of the file, which will produce nothing
	 * meaning full.
	 * 
	 * @param isr : The {@link InputStreamReader} obtained from an xml file
	 * @return A {@link StringBuilder} that has the DTD-specification added to its
	 *         xml-header.
	 * @throws IOException : If read from the supplied {@link InputStreamReader}
	 *                     fails.
	 */
	private StringBuilder insertDTDForValidation(InputStreamReader isr) throws IOException {
		StringBuilder sb = new StringBuilder();

		int c = -1;
		while ((c = isr.read()) != -1) {
			sb.append((char) c);
			if (c == 62) {
				break;
			}
		}

		sb.append(System.getProperty("line.separator"));
		sb.append(readDTDFile());
		sb.append(System.getProperty("line.separator"));
		while ((c = isr.read()) != -1) {
			sb.append((char) c);
		}
		return sb;
	}

	/**
	 * Checks whether the specified file indeed exists
	 * 
	 * @param xmlFile
	 * @throws FileNotFoundException : thrown if the specified file does not exist.
	 */
	private static void checkFileAvailability(File xmlFile) throws FileNotFoundException {
		if (!xmlFile.exists()) {
			throw new FileNotFoundException("File not found at \"" + xmlFile + "\". Enter a valid file path.");
		}
		if (!xmlFile.isFile()) {
			throw new FileNotFoundException("Path does not point to a file. Enter a valid file path.");
		}
	}

	/**
	 * Checks if the new {@link Wall} intersects any {@link Wall} in the supplied
	 * {@link Wall} set. This would mean that the defined {@link Wall} is not a
	 * valid {@link Wall}. If the wall is valid it is added to the provided set of
	 * {@link Wall}s
	 * 
	 * @param newWall : The new {@link Wall} to add.
	 * @param walls   : The set of already present {@link Wall}s
	 * @throws PersistenceException : if intersection found
	 */
	static void testAndAddWallToWalls(LineSegment newWall, List<LineSegment> walls) throws PersistenceException {
		// checks intersections and perpendicularity
		if (walls.isEmpty()) {
			walls.add(newWall);
			return;
		}
		if (!newWall.penetratesLineSegments(walls)) {
			if (newWall.perpendicular(walls.get(walls.size() - 1))) {
				walls.add(newWall);
			} else {
				throw new PersistenceException(
						"Sucessive walls are not perpendicular. Please provide a valid room layout!");
			}
		} else {
			throw new PersistenceException("Walls intersect. Please provide a valid room layout!");
		}
	}

	/**
	 * Reads the content of the DTD file for use with JDOM xml parser.
	 * 
	 * @return : The content of the DTD file as a String.
	 * @throws IOException
	 */
	private String readDTDFile() throws IOException {
		InputStreamReader isr = null;

		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE Raum [");

		try {
			InputStream inputStream = getClass()
					.getResourceAsStream(System.getProperty("file.separator") + DTDFileName);
			isr = new InputStreamReader(inputStream);
			boolean firstTagRead = false;
			int c = -1;
			while ((c = isr.read()) != -1) {
				if (firstTagRead) { // ignore the content up until the first tag
					sb.append((char) c);
				} else {
					if (c == 62) {
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
				} catch (IOException e) {
					throw new IOException(e);
				}
			}
		}
		sb.append(System.getProperty("line.separator"));
		sb.append("]>");
		return sb.toString();
	}

}