package concurrency.sudoku.solvers;

import concurrency.sudoku.Sudoku;

public class RecursiveSolver extends Solver {

	public RecursiveSolver(Solver nextSolver) {
		super(nextSolver);
	}

	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Integer rowKey, Integer columnKey) {
		return nextSolver.confirmSolutionExistsFor(puzzle, rowKey, columnKey);
	}

}