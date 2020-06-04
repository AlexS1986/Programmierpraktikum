package fernuni.propra.algorithm;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import fernuni.propra.algorithm.runtime_information.IRuntimeInformation;
import fernuni.propra.algorithm.runtime_information.IRuntimeReader;
import fernuni.propra.algorithm.runtime_information.RuntimeExceptionLamps;
import fernuni.propra.algorithm.runtime_information.RuntimeInformation;
import fernuni.propra.internal_data_model.IRoom;

public class UserSolveAAS {
	IRuntimeInformation runTimeInformation = new RuntimeInformation();
	
	public int solve(IRoom room, int time) throws UserSolveAASException {
		SolveK solveControl = new SolveK(room, runTimeInformation);
		try {
			runTimeInformation.startTime();
			
			if (time > 0) { // if time argument is smaller than zero time limit is ignored
				Timer timer = new Timer(time * 1000, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						solveControl.interrupt();	
					}
				});		
				timer.start();
			} 
			
			solveControl.start();
	
			SolveKException solveException = solveControl.testIfComputationFinished();

			//exception from other thread
			if(solveException != null) {
				throw new UserSolveAASException(solveException);
			}
			
			int numberOfOnLampsBestSolution = solveControl.getNumberOfOnLampsBestSolution();
			runTimeInformation.stopTime();
			return numberOfOnLampsBestSolution;
			
		} catch (InterruptedException ie) {
			throw new UserSolveAASException(ie);
		} catch (RuntimeExceptionLamps rte) {
			throw new UserSolveAASException(rte);
		} finally {
			solveControl.interrupt(); // stop solveControl thread
		}
	}
	
	public IRuntimeReader getRuntimeInformation() {
		return runTimeInformation;
	}

}
