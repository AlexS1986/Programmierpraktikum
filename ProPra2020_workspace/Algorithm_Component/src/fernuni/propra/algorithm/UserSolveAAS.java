package fernuni.propra.algorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import fernuni.propra.algorithm.runtime_information.IRuntimeInformation;
import fernuni.propra.algorithm.runtime_information.IRuntimeReader;
import fernuni.propra.algorithm.runtime_information.RuntimeExceptionLamps;
import fernuni.propra.algorithm.runtime_information.RuntimeInformation;
import fernuni.propra.internal_data_model.IRoom;

/**
 * Use case that provides access to the solution algorithm, which allows to
 * compute the optimal {@link Lamp} positions for a given {@link IRoom} instance
 * and a given time limit which has to be specified as an integer number
 * representing the seconds a solution is allowed to take.
 *
 * @author alex
 *
 */
public class UserSolveAAS {
	IRuntimeInformation runTimeInformation = new RuntimeInformation();

	/**
	 * The interface to the solution algorithm. A separate thread is started to
	 * handle the algorithm that is interrupted after the time limit has been
	 * reached.
	 * <p>
	 * The solving is delegated to an instance of {@link SolveK} that controls the
	 * execution of the algorithm and makes the results available.
	 * <p>
	 * 
	 * @param room : {@link IRoom} instance for which the optimal {@link Lamp}
	 *             positions have to be found.
	 * @param time : The time limit in seconds as an integer number. Negative
	 *             numbers are treated as infinite time limits.
	 * @return The number of {@link Lamp}s that are turned on in the best solution.
	 * @throws UserSolveAASException
	 */
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

			// exception from other thread
			if (solveException != null) {
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

	/**
	 * Provides access to runtime information.
	 * 
	 * @return A data structure of type {@link IRuntimeReader} that allows to obtain
	 *         run time information.
	 */
	public IRuntimeReader getRuntimeInformation() {
		return runTimeInformation;
	}

}
