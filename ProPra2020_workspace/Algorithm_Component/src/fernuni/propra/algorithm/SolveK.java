package fernuni.propra.algorithm;

import java.util.List;

import fernuni.propra.algorithm.runtime_information.IRuntimeInformation;
import fernuni.propra.algorithm.runtime_information.RuntimeExceptionLamps;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

// computes best solution in given time limit and replaces lamps with found best solution in room
public class SolveK extends Thread{
	private IRuntimeInformation runTimeInformation;
	private IRoom room;
	private ICandidateSearcher candidateSearcher;
	private IPositionOptimizer positionOptimizer;
	private boolean computationFinished;
	volatile List<Lamp> bestSolution;
	private volatile int numberLampsOnBestSolution;
	private volatile SolveKException exception = null; // to be communicated to main thread
	
	public SolveK(IRoom room, IRuntimeInformation runTimeInformation) {
		this.room = room;
		this.runTimeInformation = runTimeInformation;
		this.candidateSearcher = AbstractAlgorithmFactory.getAlgorithmFactory().createCandidateSearcher();
		this.positionOptimizer = AbstractAlgorithmFactory.getAlgorithmFactory().createPositionOptimizer();
	}
	
	
	public void solve(IRuntimeInformation runTimeInformation, IRoom room) throws SolveKException, InterruptedException{
		List<Lamp> candidates;
		try {
			runTimeInformation.startTimeCandidateSearch();
			try {
				candidates = candidateSearcher.searchCandidates(room, 
						runTimeInformation);
			} catch(InterruptedException ie) {
				runTimeInformation.stopTimeCandidateSearch();
				throw new InterruptedException(ie.getMessage());
			} 
			runTimeInformation.stopTimeCandidateSearch();
			
			System.out.println("Number of candidates found: " + candidates.size());
			
			runTimeInformation.startTimeOptimizePositions();
			try {
				positionOptimizer.optimizePositions(room, 
						candidates, runTimeInformation); 
			} catch (InterruptedException ie) {
				runTimeInformation.stopTimeOptimizePositions();
				throw new InterruptedException(ie.getMessage()); 
			} 
			runTimeInformation.stopTimeOptimizePositions();
			
			
			
			
			
		} catch (CandidateSearcherException e) {
			throw new SolveKException(e);
		} catch (RuntimeExceptionLamps rte) {
			throw new SolveKException(rte);
		}
	}

	@Override
	public void run() {
		
		boolean isSolved = false;
		try {
			solve(runTimeInformation, room);
			isSolved = true;
		} catch(SolveKException e) {
			this.exception = e;
		} catch(InterruptedException ie) {
		}
		
		
		
		
		bestSolution = positionOptimizer.getCurrentBestSolution();
		numberLampsOnBestSolution = positionOptimizer.getNumberOfOnLampsBestSolution();
		if(bestSolution != null) { // null if interrupted or exception at candidate searcher -> no solution available
			room.replaceLamps(bestSolution);
		} else {
			exception = new SolveKException("Not enough time to compute a solution!");
		}
		
		setComputationFinished(true);
		
	}
	
	
	private synchronized void setComputationFinished(boolean computationFinished) {
		this.computationFinished = computationFinished;
		notifyAll();
	}
	
	public synchronized SolveKException testIfComputationFinished() throws InterruptedException{
		while(!computationFinished) {
			wait();
		}
		return exception;
	}
	
	public synchronized int getNumberOfOnLampsBestSolution() {
		return numberLampsOnBestSolution;
	}
	
	
}
