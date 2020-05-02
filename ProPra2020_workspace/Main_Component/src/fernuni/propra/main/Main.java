package fernuni.propra.main;

import java.util.Iterator;
import java.util.StringTokenizer;

import fernuni.propra.algorithm.UserSolveAAS;
import fernuni.propra.algorithm.UserSolveAASException;
import fernuni.propra.algorithm.UserValidateAAS;
import fernuni.propra.algorithm.UserValidateAASException;
import fernuni.propra.file_processing.UserReadInputWriteOutputAAS;
import fernuni.propra.file_processing.UserReadInputWriteOutputException;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;
import fernuni.propra.internal_data_model.Point;
import fernuni.propra.user_interface.UserDisplayAAS;

/**
 * Haupteinstiegspunkt der Anwendung.
 *
 * In der Main-Komponente m&uumlssen unter anderem die Eingabeparameter verarbeitet werden.</br>
 * 
 * f&uumlr den Ablaufparameter "r" wird folgende Festlegung getroffen:
 * <ul>
 * <li>"s" (solve): f&uumlr die durch die XML-Datei beschriebene Probleminstanz wird eine L&oumlsung ermittelt. Die Positionen der Lampen werden in der angegebenen XML-Datei gespeichert. Wenn in der XML-Datei bereits eine L&oumlsung enthalten ist, so ist diese zu &uumlberschreiben.</li>
 * <li>"sd" (solve & display): wie "s", nur dass der Raum sowie die ermittelten Positionen der Lampen zus&aumltzlich in der grafischen Oberfl&aumlche gezeigt werden.</li>
 * <li>"v" (validate): durch diese Option wird gepr&uumlft, ob der in der angegebenen XML-Datei enthaltene Raum durch die ebenso dort angegebenen Lampen vollst&aumlndig ausgeleuchtet ist. Das Ergebnis der Pr&uumlfung sowie die Anzahl und Positionen der Lampen werden ausgegeben. Falls die angegebene XML-Datei keinen zul&aumlssigen Raum enth&aumllt, wird eine Fehlermeldung ausgegeben. Die Ausgabe erfolgt in der Kommandozeile.</li>
 * <li>"vd" (validate & display): wie "v", nur dass der Raum und die Lampen nach der Validierung zus&aumltzlich in der grafischen Oberfl&aumlche angezeigt werden.</li>
 * <li>"d" (display): der in der XML-Datei enthaltene Raum und die Lampen werden in der grafischen Oberfl&aumlche angezeigt. Falls die angegebene XML-Datei keinen zul&aumlssigen Raum enth&aumllt, wird eine Fehlermeldung auf der Kommandozeile ausgegeben.</li>
 * </ul>
 * Der Eingabedateiparameter "if" ist ein String, der den Pfad der Eingabedatei beinhaltet.</br>
 * 
 * Der Parameter f&uumlr ein Zeitlimit "l" ist eine positive nat&uumlrliche Zahl, welche die maximale Rechenzeit in Sekunden angibt.
 */
public class Main {

	/**
	 * Haupteinstiegsfunktion
	 */
	private static final String generalHelpMessage = "Java -jar ProPra.jar r=runParameter if=\"pathToFile\" l=timeLimit \n \n "
			+ "The runParameter specified by r =runParameter is mandatory and must be one of s,sd,v,vd or d .\n "
			+ "The input file parameter is also mandatory. pathToFile specifies the full path to a valid input file. The \" before and after pathToFile are mandatory\n"
			+ "The time limit parameter is optional. For runParameter = s or runParameter = sd. This parameter specifies how long the solution algorithm searches for an optimal lamp layout. Time limit must be a positive integer number.";
	
	
	
	public static void main(String[] args) {

		ParameterSet parameterSet = new ParameterSet();
		try {
			for (String paramString : args) {
				StringTokenizer st = new StringTokenizer(paramString, "=");
				if(st.countTokens()==2) {
					String parameterKey = st.nextToken().trim();
					String parameterValue = st.nextToken().trim();
					switch(parameterKey) {	
					case "r":
						parameterSet.setRunParameter(parameterValue);
						break;
					case "if":
						//parameterValue = parameterValue.replace("\"", "");
						parameterSet.setInputFile(parameterValue);
						break;
					case "l":
						parameterSet.setTimeLimit(Integer.parseInt(parameterValue));
						break;
					default:
						throw new GeneralException();
						
					}
					
				} else {
					throw new GeneralException();
				}
			}
			
			if (parameterSet.isValidParameterSet() ) {
				IRoom room;
				UserReadInputWriteOutputAAS userReadWriteAAS;
				UserDisplayAAS userDisplayAAS;
				UserValidateAAS userValidateAAS;
				UserSolveAAS userSolveAAS;
				int numberOfLampsInSolution;
				
				printMessageToConsole("Starting computation ...");
				
				switch(parameterSet.getRunParameter()) {
				case "s":
					userReadWriteAAS = new UserReadInputWriteOutputAAS(parameterSet.getInputFile());
					room = userReadWriteAAS.readInput();
					userSolveAAS = new UserSolveAAS();
					numberOfLampsInSolution = userSolveAAS.solve(room, parameterSet.getTimeLimit());
					userReadWriteAAS.writeOutput(room);
					printMessageToConsole("Computation finished ...");
					break;
				case "sd":
					userReadWriteAAS = new UserReadInputWriteOutputAAS(parameterSet.getInputFile());
					room = userReadWriteAAS.readInput();
					userSolveAAS = new UserSolveAAS();
					numberOfLampsInSolution = userSolveAAS.solve(room, parameterSet.getTimeLimit());
					userReadWriteAAS.writeOutput(room);
					userDisplayAAS = new UserDisplayAAS();
					userDisplayAAS.display(room);
					printMessageToConsole("Computation finished ...");
					printMessageToConsole("Number of lamps necessary:" + numberOfLampsInSolution); // TODO
					printMessageToConsole(userSolveAAS.getRuntimeInformation().toString());
					break;
				case "v":
					userReadWriteAAS = new UserReadInputWriteOutputAAS(parameterSet.getInputFile());
					room = userReadWriteAAS.readInput();
					userValidateAAS = new UserValidateAAS();
					userValidateAAS.validate(room);
					printMessageToConsole(userValidateAAS.getResultString());
					break;
				case "vd":
					userReadWriteAAS = new UserReadInputWriteOutputAAS(parameterSet.getInputFile());
					room = userReadWriteAAS.readInput();
					userValidateAAS = new UserValidateAAS();
					userValidateAAS.validate(room);
					//System.out.println(userValidateAAS.getResultString());
					printMessageToConsole(userValidateAAS.getResultString());
					userDisplayAAS = new UserDisplayAAS();
					userDisplayAAS.display(room);
					break;
				case "d":
					userReadWriteAAS = new UserReadInputWriteOutputAAS(parameterSet.getInputFile());
					room = userReadWriteAAS.readInput();
					userDisplayAAS = new UserDisplayAAS();
					userDisplayAAS.display(room);
					break;
				default:
					
				}
						
			} else {
				throw new GeneralException();
			}
			
			
			
		}catch (ParameterSetException e) {
			printMessageToConsole(e.getMessage());
			System.exit(0);
		} catch(NumberFormatException nfe) {
			printMessageToConsole("The timeLimit parameter specified by l=timeLimit is not an integer number");
		} catch(GeneralException ge) {
			printMessageToConsole(generalHelpMessage);
			System.exit(0); 
		} catch (UserReadInputWriteOutputException e) {
			printMessageToConsole(e.getMessage());
			System.exit(0);
		} catch (UserValidateAASException e) {
			printMessageToConsole(e.getMessage());
			System.exit(0);
		} catch (UserSolveAASException e) {
			printMessageToConsole(e.getMessage());
			System.exit(0);
		}
			
	}
	
	private static void printMessageToConsole(String message) {
		System.out.println(message);
	}
}
