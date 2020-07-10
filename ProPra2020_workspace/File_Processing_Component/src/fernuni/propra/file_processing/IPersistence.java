package fernuni.propra.file_processing;

import fernuni.propra.internal_data_model.IRoom;

/**
 * A provider of an algorithm that provides persistence to an {@link IRoom}
 * instance.
 * 
 * @author alex
 *
 */
public interface IPersistence {
	/**
	 * Used to read an {@link IRoom} that is present a certain location.
	 * 
	 * @param location : The location at which the {@link IRoom} is stored.
	 * @return : The {@link IRoom}
	 * @throws PersistenceException : thrown if an unexpected error occurred during
	 *                              the reading process.
	 */
	IRoom readInput(String location) throws PersistenceException;

	/**
	 * Used to persistently store the {@link IRoom} at a certain location.
	 * 
	 * @param room     : The {@link IRoom}
	 * @param location : The location at which the {@link IRoom} is stored.
	 * @throws PersistenceException : thrown if an unexpected error occurred during
	 *                              the writing process.
	 */
	void writeOutput(IRoom room, String location) throws PersistenceException;

}
