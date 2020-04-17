package fernuni.propra.algorithm;

import fernuni.propra.internal_data_model.IRoom;

public class UserValidateAAS {
	private ValidateKAbstract validateK;
	
	public boolean validate(IRoom room) {
		validateK = new ValidateK();
		IRuntimeIlluminationTester runtimeInfo = new RuntimeInformation();
		return validateK.validate(room, runtimeInfo);
	}

}
