package com.erikleeness.concurrency.sudoku.solvers;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;
import com.erikleeness.concurrency.sudoku.Sudokus;
import com.google.common.collect.Range;


public class SimpleEliminationSolver extends Solver
{
	private Sudoku workingPuzzle;
	private Pair<Integer, Integer> targetLocation;
	
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
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Pair<Integer, Integer> targetLocation)
	{
		/* As a matter of convention, "target" refers to the cell pointed to by
		 * rowKey and columnKey.
		 */
		this.targetLocation = targetLocation;
		Integer targetRowKey = targetLocation.getLeft();
		Integer targetColumnKey = targetLocation.getRight();
		
		workingPuzzle = puzzle.copy();
		
		for (Integer n : Sudokus.intSeq(1, 9)) {
			
			Iterable< Pair<Integer, Integer> > locationsInRow = 
					Pair.rightRange( targetRowKey, Range.closed(1, 9));
			Iterable< Pair<Integer, Integer> > locationsInColumn = 
					Pair.leftRange( Range.closed(1, 9), targetColumnKey);
			Iterable< Pair<Integer, Integer> > locationsInBox = 
					Sudokus.locationsInBoxOf(workingPuzzle, targetRowKey, targetColumnKey);
			
			if ( ! Sudokus.directConflictExistsBetween(n, targetLocation, puzzle)) {
				
				// If there IS a direct conflict between the target and N, then we shouldn't even
				// bother checking if it's the only possible N (because of course it's not)
				
				if (!anyOthersCanBeN( n, locationsInRow )) {
					return true;
				}

				if (!anyOthersCanBeN( n, locationsInColumn )) {
					return true;
				}

				if (!anyOthersCanBeN( n, locationsInBox )) {
					return true;
				}
			}
		}

		return nextSolver.confirmSolutionExistsFor(puzzle, targetLocation);
	}
	
	/**
	 * Returns true if any of the cells in the locations provided can possibly
	 * be N (see cellCanBeN for specific criteria).
	 * 
	 * Returns false if none of them can possible be N.
	 * 
	 * @param locations		iterable of locations to check against
	 * @param n				number to check for
	 * @return				true if any cell in locations can be n 
	 */
	public boolean anyOthersCanBeN(Integer n,
			Iterable< Pair<Integer, Integer> > locations)
	{
		// Can any non-target cells in "locations" be n?
		for (Pair<Integer, Integer> cellLocation : locations) {
			if ( ! cellLocation.equals(targetLocation) &&
				 ! Sudokus.directConflictExistsBetween( n, cellLocation, workingPuzzle )) {
				return true;
				
			}
		}
		
		// All items in locations have a direct conflict with n EXCEPT the target,
		// therefore the target can be solved
		return false;
	}
}
