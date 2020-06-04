package fernuni.propra.main;


public class ParameterSet {
	private String runParameter;
	private String inputFile;
	private Integer timeLimit;
	private static String[] validRunParameters = {"s","sd","v","vd", "d"};
	private static final String formInputFileParameter = " if=\"pathToFile\"";
	private static final String formTimeLimitParameter = "l=timeLimit , where timeLimit is a positive Integer number";
	private static final String formRunParameter = "r=parameter";
	
	
	public ParameterSet() {
		
	}
	
	void setRunParameter(String runParameter) throws ParameterSetException{
		if (this.runParameter != null) {
			throw new ParameterSetException("Run parameter is already set. Please provide only one run parameter specification.");
		} else if (!isValidRunParameter(runParameter)) {
			String message = "Run parameter is not valid. Please provide a valid run parameter in the form " + formRunParameter+ ", where parameter is one of: ";
			for (String validParameter : validRunParameters) {
				message = message + validParameter;
				message = message + " ";	
			}
			throw new ParameterSetException(message);
		} else {
			this.runParameter = runParameter;
		}
	}
	
	void setInputFile(String inputFile) throws ParameterSetException {
		if (this.inputFile != null) {
			throw new ParameterSetException("Path to input file is already set. Please provide only one input file specification.");
		} /*else if (!inputFile.startsWith("\"") || !inputFile.endsWith("\"")) {
			throw new ParameterSetException("The path to the input file you entered is: " + inputFile +System.getProperty("line.separator") + " The input file parameter needs to start and end with  \" . "
					+ "Please supply the path to the input file in the form: if=\"pathToFile\"");
		} */
		else {
			this.inputFile = inputFile.replace("\"", "");
			
		} 
	}
	
	void setTimeLimit(int timeLimit) throws ParameterSetException {
		if (this.timeLimit != null) {
			throw new ParameterSetException("Time limit is already set. Please provide only one time limit specification.");
		} else {
			this.timeLimit = timeLimit;
		}
	}
	
	boolean isValidParameterSet() throws ParameterSetException {
		if(runParameter == null) {
			String message = "No run parameter provided. Please provide a valid run parameter in the form " + formRunParameter+ ", where parameter is one of: ";
			for (String validParameter : validRunParameters) {
				message = message + validParameter;
				message = message + " ";	
			}
			throw new ParameterSetException(message);
		}
		if (runParameter.equals("v") || runParameter.equals("vd") || runParameter.equals("d")) {
			if (inputFile != null) {
				return true;
			} else {
				throw new ParameterSetException("No path to the input file is specified. Please provide the path to the input file in the form:" + formInputFileParameter);
			} 
		} else if (runParameter.equals("s") || runParameter.equals("sd")) {
			if (inputFile == null ) {
				throw new ParameterSetException("No path to the input file is specified. Please provide the path to the input file in the form:" + formInputFileParameter);
			} else if (timeLimit == null) {
				throw new ParameterSetException("No time limit is specified. Please provide a time limit in the form:" + formTimeLimitParameter);
			} else {
				return true;
			}
		} else {
			String message = "Run parameter is not valid. Please provide a valid run parameter in the form " + formRunParameter+ ", where parameter is one of: ";
			for (String validParameter : validRunParameters) {
				message = message + validParameter;
				message = message + " ";
			}
			throw new ParameterSetException(message);
		}
	
	}
	
	private boolean isValidRunParameter(String runParameter) {
		boolean isValid = false;
		for (String validParameter :  validRunParameters) {
			if (validParameter.equals(runParameter)) {
				isValid  = true;
				break;
			}
		}
		return isValid;
	}
	
	String getRunParameter() {
		return this.runParameter;
	}
	
	String getInputFile() {
		return this.inputFile;
	}
	
	int getTimeLimit() {
		return this.timeLimit;
	}
	
	/*String[] getValidRunParameters() { // TODO
		String[] outStrings = new String[validRunParameters.length];
		for (int i = 0; i< validRunParameters.length; i++) {
			outStrings[i] = validRunParameters[i];
		}
		return outStrings;
	} */

}
