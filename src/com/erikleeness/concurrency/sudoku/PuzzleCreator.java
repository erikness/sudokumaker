package com.erikleeness.concurrency.sudoku;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.erikleeness.concurrency.sudoku.solvers.SingleCellSolver;
import com.google.common.base.Optional;
import com.google.common.collect.ArrayTable;


public class PuzzleCreator
{
	public static void main(String[] args)
	{
		Iterable<Integer> rowKeys = Sudokus.intSeq(1, 9);
		Iterable<Integer> colKeys = Sudokus.intSeq(1, 9);
		ArrayTable<Integer, Integer, Optional<Integer>> cells;
		cells = ArrayTable.create(rowKeys, colKeys);
		
		int[][] temp_matrix = new int[][]{
				{4, 9, 8,   7, 1, 6,   5, 2, 3},
				{2, 7, 5,   3, 4, 8,   9, 6, 1},
				{6, 3, 1,   9, 2, 5,   4, 8, 7},
				
				{7, 5, 2,   6, 8, 9,   1, 3, 4},
				{3, 8, 9,   4, 5, 1,   6, 7, 2},
				{1, 6, 4,   2, 3, 7,   8, 5, 9},
				
				{5, 2, 3,   8, 9, 4,   7, 1, 6},
				{8, 4, 7,   1, 6, 2,   3, 9, 5},
				{9, 1, 6,   5, 7, 3,   2, 4, 8},
		};
		
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				cells.put(i+1, j+1, Optional.of(temp_matrix[i][j]));
			}
		}
		
		Sudoku puzzle1 = new Sudoku(cells);
		System.out.println(puzzle1);
		PuzzleCreator puzzleCreator = new PuzzleCreator();
		Sudoku puzzle1_unfinished = puzzleCreator.createPuzzleFromSolution(puzzle1);
		System.out.println(puzzle1_unfinished);
	}
	
	public Sudoku createPuzzleFromSolution(Sudoku solution)
	{
		Sudoku workingPuzzle = solution.copy();
		
		/* checkedCells contains all cells that were either already erased or
		 * could not be solved for when eliminated. */
		Set<Pair<Integer, Integer>> checkedCells = new HashSet<Pair<Integer, Integer>>();
		
		while (workingPuzzle.size() != checkedCells.size()) {
			
			Random randomGenerator = new Random();
			int targetRowKey = randomGenerator.nextInt(9) + 1;
			int targetColumnKey = randomGenerator.nextInt(9) + 1;
			Pair<Integer, Integer> targetLocation = Pair.of(targetRowKey, targetColumnKey);
			
			if (checkedCells.contains( targetLocation )) {
				continue;
			}
			
			Optional<Integer> candidate = workingPuzzle.get(targetLocation);
			
			if (candidate.isPresent()) {
				
				workingPuzzle.erase(targetLocation);
				
				SingleCellSolver solver = new SingleCellSolver();
				boolean cellCanBeSolved = solver.confirmSolutionExistsFor(workingPuzzle, targetLocation);
				
				if (!cellCanBeSolved) {
					// We must put the value back
					workingPuzzle.put(targetLocation, candidate);
				}
				
				checkedCells.add(targetLocation);
			}
		}
		
		return workingPuzzle;
	}
}
