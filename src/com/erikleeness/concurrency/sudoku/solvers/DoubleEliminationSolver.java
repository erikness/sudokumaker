package com.erikleeness.concurrency.sudoku.solvers;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;

public class DoubleEliminationSolver extends Solver
{

	public DoubleEliminationSolver(Solver nextSolver)
	{
		super(nextSolver);
	}

	/**
	 * Solves based on a special type of elimination, where you have
	 * 2 cells in a row or column in the same box, which for whatever reason
	 * are the only two in that box that can be some N. No matter which is
	 * actually N, the result will become a conflict to any potential N in that row/column, 
	 * and thus we can treat it as a conflictor without knowing which is actually N. 
	 */
	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle,
			Pair<Integer, Integer> targetLocation)
	{
		return nextSolver.confirmSolutionExistsFor(puzzle, targetLocation);
	}

}
