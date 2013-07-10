package com.erikleeness.concurrency.sudoku.solvers;

import com.erikleeness.concurrency.sudoku.Pair;
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
	
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Pair<Integer, Integer> targetLocation) {
		return nextSolver.confirmSolutionExistsFor(puzzle, targetLocation);
	}

}
