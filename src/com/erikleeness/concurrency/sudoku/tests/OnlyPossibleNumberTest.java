package com.erikleeness.concurrency.sudoku.tests;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;
import com.erikleeness.concurrency.sudoku.solvers.FailureSolver;
import com.erikleeness.concurrency.sudoku.solvers.OnlyPossibleNumberSolver;
import com.erikleeness.concurrency.sudoku.solvers.Solver;
import com.google.common.base.Optional;

public class OnlyPossibleNumberTest
{
	public static void main(String[] args)
	{
		int[][] temp_matrix = new int[][]{
				{5, 3, 4,   6, 7, 8,   9, 1, 2},
				{6, 7, 2,   1, 9, 5,   3, 4, 8},
				{1, 9, 8,   3, 4, 2,   5, 6, 7},
				
				{8, 5, 9,   7, 6, 1,   4, 2, 3},
				{4, 2, 6,   8, 5, 3,   7, 9, 1},
				{7, 1, 3,   9, 2, 4,   8, 5, 6},
				
				{9, 6, 1,   5, 3, 7,   2, 8, 4},
				{2, 8, 7,   4, 1, 9,   6, 3, 5},
				{3, 4, 5,   2, 8, 6,   1, 7, 9},
		};
		
		Sudoku solution = Sudoku.fromIntMatrix(temp_matrix);
		System.out.println(solution);
		
		// Remove 3's from elimination
		solution.erase(Pair.of(5, 6));
		solution.erase(Pair.of(7, 5));
		
		Sudoku modified = testRemoval(solution);
		System.out.println(modified);
	}
	
	public static Sudoku testRemoval(Sudoku solution)
	{
		Sudoku workingPuzzle = solution.copy();
		
		/* checkedCells contains all cells that were either already erased or
		 * could not be solved for when eliminated. */
			
		int targetRowKey = 5;
		int targetColumnKey = 5;
		Pair<Integer, Integer> targetLocation = Pair.of(targetRowKey, targetColumnKey);
		
		Optional<Integer> candidate = workingPuzzle.get(targetLocation);
		
		if (candidate.isPresent()) {
			
			workingPuzzle.erase(targetLocation);
			
			Solver solver = new OnlyPossibleNumberSolver( new FailureSolver(null) );
			boolean cellCanBeSolved = solver.confirmSolutionExistsFor(workingPuzzle, targetLocation);
			
			if (!cellCanBeSolved) {
				// We must put the value back
				workingPuzzle.put(targetLocation, candidate);
				System.out.println(targetLocation.toString() + " was not successfully removed.");
			} else {
				System.out.println(targetLocation.toString() + " WAS successfully removed.");
			}
			
		}
		
		return workingPuzzle;
	}
}
