package concurrency.sudoku.solvers;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Range;

import concurrency.sudoku.Pair;
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
		/* As a matter of convention, "target" refers to the cell pointed to by
		 * rowKey and columnKey.
		 */
		Sudoku workingPuzzle = puzzle.copy();
		
		for (Integer n : Sudokus.intSeq(1, 9)) {
			
			boolean targetIsOnlyNPossibleInRow = true;
			boolean targetIsOnlyNPossibleInColumn = true;
			boolean targetIsOnlyNPossibleInBox = true;
			
			// Can any other cells in the row be n?
			List< Pair<Integer, Integer> > locationsInRow = Pair.rightRange( rowKey, Range.closed(1, 9) );
			for (Pair<Integer, Integer> cellLocation : locationsInRow) {
				if (cellCanBeN( workingPuzzle, n, cellLocation )) {
					targetIsOnlyNPossibleInRow = false;
					break;
				}
			}
			
			if (targetIsOnlyNPossibleInRow) {
				// Well fuck yeah, the square can be solved!
				return true;
			}
			
			// Can any other cells in the row be n?
			List< Pair<Integer, Integer> > locationsInColumn = Pair.rightRange( rowKey, Range.closed(1, 9) );
			for (Pair<Integer, Integer> cellLocation : locationsInColumn) {
				if (cellCanBeN( workingPuzzle, n, cellLocation )) {
					targetIsOnlyNPossibleInColumn = false;
					break;
				}
			}
			
			if (targetIsOnlyNPossibleInColumn) {
				// Well fuck yeah, the square can be solved!
				return true;
			}
			
			// Can any other cells in the row be n?
			Iterable< Pair<Integer, Integer> > locationsInBox = Sudokus.locationsInBoxOf(workingPuzzle, rowKey, columnKey);
			for (Pair<Integer, Integer> cellLocation : locationsInBox) {
				if (cellCanBeN( workingPuzzle, n, cellLocation )) {
					targetIsOnlyNPossibleInBox = false;
					break;
				}
			}

			if (targetIsOnlyNPossibleInBox) {
				// Well fuck yeah, the square can be solved!
				return true;
			}
			
		}

		return nextSolver.confirmSolutionExistsFor(puzzle, rowKey, columnKey);
	}
	
	/**
	 * Returns false if it is trivial to see that the square cannot be N.
	 * Returns true if there is a possibility, given what we know with the
	 * supplied puzzle and te simple methods we're using.
	 * 
	 * Performs a number of internal checks and short circuits if any of them
	 * evaluate to false.
	 * 
	 * @param puzzle	
	 * @param n			number for the cell to be checked against
	 * @param location	location of the cell to be checked
	 * @return			false if the square can't be N, true if it might be
	 */
	private boolean cellCanBeN(
			Sudoku puzzle, Integer n, Pair<Integer, Integer> location)
	{
		// Is there an N in the cell's column?
		List< Pair<Integer, Integer> > locationsInColumn = 
				Pair.leftRange(Range.closed(1, 9), location.getRight());
		
		for (Pair<Integer, Integer> locationInColumn : locationsInColumn) {
			if (puzzle.get(locationInColumn) .equals (Optional.of(n))) {
				return false;
			}
		}
		
		// Is there an N in the cell's row?
		List< Pair<Integer, Integer> > locationsInRow = 
				Pair.rightRange(location.getLeft(), Range.closed(1, 9));
		
		for (Pair<Integer, Integer> locationInRow : locationsInRow) {
			if (puzzle.get(locationInRow) .equals (Optional.of(n))) {
				return false;
			}
		}
		
		// Is there an N in the cell's box?
		Set< Optional<Integer> > locationsInBox = 
				Sudokus.cellsInBoxOf(puzzle, location);
				
		for (Optional<Integer> locationInBox : locationsInBox) {
			if (locationInBox .equals (Optional.of(n))) {
				return false;
			}
		}
		
		// We've done all we can
		return true;
		
	}
}
