package fernuni.propra.algorithm;

import fernuni.propra.internal_data_model.IRoom;

public class UserValidateAAS {
	private ValidateK validateK  = new ValidateK();
	
	public boolean validate(IRoom room) throws UserValidateAASException{
		IRuntimeIlluminationTester runtimeInfo = new RuntimeInformation();
		try {
			return validateK.validate(room, runtimeInfo);
		} catch (IlluminationTesterException e) {
			throw new UserValidateAASException(e);
		}
	}

}
