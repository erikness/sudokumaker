package com.erikleeness.concurrency.sudoku.solvers;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;

public class FailureSolver extends Solver 
{

	public FailureSolver(Solver nextSolver)
	{
		super(nextSolver);
	}

	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Pair<Integer, Integer> targetLocation)
	{
		return false;
	}

}
