package fernuni.propra.algorithm;

import fernuni.propra.algorithm.runtime_information.IRuntimeInformation;
import fernuni.propra.algorithm.runtime_information.IRuntimeReader;
import fernuni.propra.algorithm.runtime_information.RuntimeInformation;
import fernuni.propra.internal_data_model.IRoom;

public class UserValidateAAS {
	private ValidateK validateK  = new ValidateK();
	private String resultString;
	IRuntimeInformation runtimeInfo = new RuntimeInformation();
	
	public boolean validate(IRoom room) throws UserValidateAASException{
		//IRuntimeIlluminationTester runtimeInfo = new RuntimeInformation();
		try {
			boolean isIlluminated = validateK.validate(room, runtimeInfo);
			resultString = computeResultString(room, isIlluminated);
			return isIlluminated;
		} catch (ValidateKException e) {
			throw new UserValidateAASException(e);
		}
	}

	private String computeResultString(IRoom room, Boolean isIlluminated) {
		String lineSeparator =  System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder("The room ");
		sb.append(room.getID());
		String illuminatedOrNot = isIlluminated ? " is illuminated. " : " is NOT illuminated. ";
		sb.append(illuminatedOrNot);
		sb.append(lineSeparator);
		sb.append(room.printLampPositions());
		String outString = sb.toString();
		return outString;
	}
	
	public String getResultString() {
		return resultString;
	}
	
	public IRuntimeReader getRuntimeInformation() {
		return runtimeInfo;
	}
	
}
