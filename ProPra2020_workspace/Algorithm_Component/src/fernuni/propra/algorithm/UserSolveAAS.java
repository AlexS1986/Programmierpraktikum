package fernuni.propra.algorithm;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import fernuni.propra.internal_data_model.IRoom;

public class UserSolveAAS {
	
	public int solve(IRoom room, int time) throws UserSolveAASException {
		
		IRuntimeInformation runTimeInformation = new RuntimeInformation();
		SolveK solveControl = new SolveK(room, runTimeInformation);
		
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
		
		SolveKException solveException;
		try {
			solveException = solveControl.testIfComputationFinished();
		} catch (InterruptedException e1) { // interrupted from wait() -> error
			throw new UserSolveAASException(e1);
		}
		
		//exception from other thread
		if(solveException != null) {
			throw new UserSolveAASException(solveException);
		}
		
		int numberOfOnLampsBestSolution = solveControl.getNumberOfOnLampsBestSolution();
		return numberOfOnLampsBestSolution;
		

	}

}
