package com.erikleeness.concurrency.sudoku.solvers;

import java.util.Set;

import com.erikleeness.concurrency.sudoku.Pair;
import com.erikleeness.concurrency.sudoku.Sudoku;
import com.erikleeness.concurrency.sudoku.Sudokus;
import com.google.common.collect.Sets;

public class OnlyPossibleNumberSolver extends Solver
{
	private Sudoku workingPuzzle;
	
	public OnlyPossibleNumberSolver(Solver nextSolver)
	{
		super(nextSolver);
	}

	@Override
	public boolean confirmSolutionExistsFor(Sudoku puzzle, Pair<Integer, Integer> targetLocation)
	{
		workingPuzzle = puzzle.copy();
		
		Set<Integer> conflictsWithTarget = Sets.newHashSet();
		
		for (Integer n : Sudokus.intSeq(1, 9)) {
			if (Sudokus.directConflictExistsBetween( n, targetLocation, workingPuzzle )) {
				conflictsWithTarget.add(n);
			}
		}
		
		if (conflictsWithTarget.size() == 8) {
			// There's only one it could possibly be!
			return true;
		}
		
		return nextSolver.confirmSolutionExistsFor(puzzle, targetLocation);
	}

}
