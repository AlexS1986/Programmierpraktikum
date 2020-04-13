package fernuni.propra.file_processing;

import fernuni.propra.internal_data_model.IRoom;

public class UserReadInputWriteOutputAAS {
	private final String location;
	private PersistenceAbstract persistence;
	
	public UserReadInputWriteOutputAAS(String location) {
		this.location = location;
	}
	
	public IRoom readInput() throws UserReadInputWriteOutputException {
		try {
			return persistence.readInput(location);
		} catch (PersistenceException e) {
			throw new UserReadInputWriteOutputException(e);
		}
	}
	
	public void writeOutput(IRoom room) throws UserReadInputWriteOutputException {
		try {
			persistence.writeOutput(room, location);
		} catch (PersistenceException e) {
			throw new UserReadInputWriteOutputException(e);
		}
	}

}
