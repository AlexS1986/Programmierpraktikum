package fernuni.propra.file_processing;

import fernuni.propra.internal_data_model.IRoom;

public interface IPersistence {
	
	IRoom readInput(String location) throws PersistenceException;
	void writeOutput(IRoom room, String location) throws PersistenceException;

}
