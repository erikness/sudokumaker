package concurrency.sudoku.solvers;

import concurrency.sudoku.Sudoku;

public class OnlyPossibleNumberSolver extends Solver {

	public OnlyPossibleNumberSolver(Solver nextSolver) {
		super(nextSolver);
	}

	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Integer rowKey, Integer columnKey) {
		return nextSolver.confirmSolutionExistsFor(puzzle, rowKey, columnKey);
	}

}
