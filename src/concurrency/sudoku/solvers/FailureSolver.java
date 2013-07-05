package concurrency.sudoku.solvers;

import concurrency.sudoku.Sudoku;

public class FailureSolver extends Solver 
{

	public FailureSolver(Solver nextSolver)
	{
		super(nextSolver);
	}

	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Integer rowKey, Integer columnKey)
	{
		return false;
	}

}
