package com.erikleeness.concurrency.sudoku.solvers;

import com.erikleeness.concurrency.sudoku.Sudoku;

public class SingleCellSolver
{
	private Solver nextSolver;
	
	public SingleCellSolver()
	{
		nextSolver = new LastManStandingSolver(
				new SimpleEliminationSolver(
				new OnlyPossibleNumberSolver(
				new RecursiveSolver(
				new FailureSolver(null)))));
	}
	
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Integer rowKey, Integer columnKey) {
		return nextSolver.confirmSolutionExistsFor(puzzle, rowKey, columnKey);
	}

}
