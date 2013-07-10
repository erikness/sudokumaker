package com.erikleeness.concurrency.sudoku.solvers;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;

public class RecursiveSolver extends Solver {

	public RecursiveSolver(Solver nextSolver) {
		super(nextSolver);
	}

	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Pair<Integer, Integer> targetLocation) {
		return nextSolver.confirmSolutionExistsFor(puzzle, targetLocation);
	}

}
