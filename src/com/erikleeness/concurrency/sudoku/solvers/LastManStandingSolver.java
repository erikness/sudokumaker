package com.erikleeness.concurrency.sudoku.solvers;

import java.util.Collection;
import java.util.Set;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;
import com.erikleeness.concurrency.sudoku.Sudokus;
import com.google.common.base.Optional;


public class LastManStandingSolver extends Solver 
{
	public LastManStandingSolver(Solver nextSolver) {
		super(nextSolver);
	}

	/**
	 * Can confirm a solution to a given square if and only if 
	 * the square is the only unsolved square in its row, box,
	 * or column.
	 * 
	 */
	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Pair<Integer, Integer> targetLocation) {
		
		Sudoku workingPuzzle = puzzle.copy();
		Integer targetRowKey = targetLocation.getLeft();
		Integer targetColumnKey = targetLocation.getRight();
		
		Set<Optional<Integer>> cellsInBox = Sudokus.cellsInBoxOf(workingPuzzle, targetLocation); 
		int cellsInBoxCount =  countFilledCells(cellsInBox);
		
		Collection<Optional<Integer>> cellsInRow;
		cellsInRow = workingPuzzle.row(targetRowKey).values();
		int cellsInRowCount =  countFilledCells(cellsInRow);
		
		Collection<Optional<Integer>> cellsInColumn;
		cellsInColumn = workingPuzzle.column(targetColumnKey).values();
		int cellsInColumnCount =  countFilledCells(cellsInColumn);
		
		
		
		if (cellsInBoxCount == 8
				|| cellsInRowCount == 8
				|| cellsInColumnCount == 8) {
			// The cell in question can be found if it is removed at this point
			return true;
		} else {
			return nextSolver.confirmSolutionExistsFor(puzzle, targetLocation);
		}

	}
	
	/**
	 * Uses global data workingPuzzle
	 * @param key
	 * @return
	 */
	private int countFilledCells(Collection<Optional<Integer>> cells)
	{
		int cellCount =  0;
		for (Optional<Integer> cell : cells) {
			if (cell.isPresent()) {
				cellCount++;
			}
		}
		
		return cellCount;
	}
	
	
	
}
