package com.erikleeness.concurrency.sudoku.tests;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;
import com.erikleeness.concurrency.sudoku.solvers.FailureSolver;
import com.erikleeness.concurrency.sudoku.solvers.SimpleEliminationSolver;
import com.erikleeness.concurrency.sudoku.solvers.Solver;

public class HardPuzzleTest
{
	public static void main(String[] args)
	{
		int[][] temp_matrix = new int[][]{
				{6, 0, 0,   0, 3, 0,   0, 0, 0},
				{6, 7, 2,   0, 6, 0,   7, 8, 4},
				{0, 5, 0,   1, 0, 7,   0, 0, 9},
				
				{7, 0, 3,   0, 0, 0,   0, 5, 0},
				{0, 0, 0,   0, 0, 0,   0, 0, 0},
				{0, 6, 0,   0, 0, 0,   4, 0, 7},
				
				{8, 0, 0,   5, 0, 1,   0, 4, 0},
				{9, 7, 4,   0, 2, 0,   0, 0, 3},
				{0, 0, 0,   0, 9, 0,   0, 0, 2},
		};
		
		Sudoku hardPuzzle = Sudoku.fromIntMatrix(temp_matrix);
		System.out.println(hardPuzzle);
		
		Solver solver = new SimpleEliminationSolver( new FailureSolver(null) );
		boolean cellCanBeSolved = solver.confirmSolutionExistsFor(hardPuzzle, Pair.of(7, 7));
		System.out.println(cellCanBeSolved);
	}
}
