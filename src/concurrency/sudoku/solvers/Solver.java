package concurrency.sudoku.solvers;

import concurrency.sudoku.Sudoku;

public abstract class Solver {

	protected Solver nextSolver;
	
	public Solver(Solver nextSolver)
	{
		this.nextSolver = nextSolver;
	}
	
	public abstract boolean confirmSolutionExistsFor(Sudoku puzzle, Integer rowKey, Integer columnKey);
}
