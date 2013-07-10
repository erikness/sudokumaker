package com.erikleeness.concurrency.sudoku.solvers;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;

public abstract class Solver {

	protected Solver nextSolver;
	
	public Solver(Solver nextSolver)
	{
		this.nextSolver = nextSolver;
	}
	
	public abstract boolean confirmSolutionExistsFor(Sudoku puzzle, Pair<Integer, Integer> targetLocation);
}
