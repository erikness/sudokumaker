package concurrency.sudoku.solvers;

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Optional;

import concurrency.sudoku.Sudoku;
import concurrency.sudoku.Sudokus;

public class SimpleEliminationSolver extends Solver
{

	public SimpleEliminationSolver(Solver nextSolver)
	{
		super(nextSolver);
	}

	/**
	 * Can confirm a solution to a given square if and only if 
	 * there is some number N such that ONLY the square in question could
	 * possibly be N.
	 * 
	 * There are 3 different scenarios for this:
	 * - Confirmation that no other square in the box could be N.
	 * - Confirmation that no other square in the row could be N.
	 * - Confirmation that no other square in the column could be N.
	 * 
	 * Any one of these three conditions is sufficient to confirm that the
	 * square can be solved.
	 */
	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Integer rowKey, Integer columnKey)
	{
		Sudoku workingPuzzle = puzzle.copy();
		Pair<Integer, Integer> targetLocation = new Pair<> (rowKey, columnKey);
		
		Set<Optional<Integer>> cellsInBox = Sudokus.cellsInBoxOf(workingPuzzle, rowKey, columnKey);
		
		Collection<Optional<Integer>> cellsInRow = workingPuzzle.row(rowKey).values();
		
		Collection<Optional<Integer>> cellsInColumn = workingPuzzle.column(columnKey).values();
		
		for (Integer n : Sudokus.intSeq(1, 9)) {
			
			Set< Pair<Integer, Integer> > possibleLocations = possibleRowLocationsFor(workingPuzzle, n, rowKey);
			
			if (possibleLocations.size() == 1 && 
					possibleLocations.contains( targetLocation )) {
				// The target location is the only possible location for N in its row, so its solution must be N
				return true;
			} else {
				continue;
			}
			
			
		}

		return nextSolver.confirmSolutionExistsFor(puzzle, rowKey, columnKey);
	}
	
	private Set< Pair<Integer, Integer> > possibleRowLocationsFor(
			Sudoku puzzle, Integer n, Integer rowKey)
	{
		for (Integer otherRowKey : Sudokus.intSeq(1, 9)) {
			
		}
	}
}
