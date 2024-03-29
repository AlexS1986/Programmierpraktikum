package fernuni.propra.file_processing;

import fernuni.propra.internal_data_model.IRoom;

/**
 * A class that provides access to the use case that allows to perform
 * persistence operations on an {@link IRoom} instance.
 * <p>
 * The actual persistence operations are delegated to an instance of
 * {@link IPersistence}.
 * 
 * @author alex
 *
 */
public class UserReadInputWriteOutputAAS {
	/*
	 * a String defining a location at which the persistent data can be found/
	 * stored
	 */
	private final String location;

	/*
	 * The instance of IPersistence to which operations are delegated
	 */
	private IPersistence persistence;

	/**
	 * Constructor
	 * 
	 * @param location : a String defining a location at which the persistent data
	 *                 can be found/ stored
	 */
	public UserReadInputWriteOutputAAS(String location) {
		this.location = location;
		this.persistence = new FilePersistence();
	}

	/**
	 * Read persistence instance of {@link IRoom}.
	 * 
	 * @return the instance of {@link IRoom}
	 * @throws UserReadInputWriteOutputException
	 */
	public IRoom readInput() throws UserReadInputWriteOutputException {
		try {
			return persistence.readInput(location);
		} catch (PersistenceException e) {
			throw new UserReadInputWriteOutputException(e);
		}
	}

	/**
	 * Store an {@link IRoom} in a persistent fashion.
	 * 
	 * @param room
	 * @throws UserReadInputWriteOutputException
	 */
	public void writeOutput(IRoom room) throws UserReadInputWriteOutputException {
		try {
			persistence.writeOutput(room, location);
		} catch (PersistenceException e) {
			throw new UserReadInputWriteOutputException(e);
		}
	}

}