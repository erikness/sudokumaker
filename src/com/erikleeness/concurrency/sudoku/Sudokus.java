package com.erikleeness.concurrency.sudoku;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;


public class Sudokus
{
	public static Set<Optional<Integer>> cellsInBoxOf(Sudoku puzzle, Integer rowKey, Integer columnKey)
	{
		int[] rowKeysInBox = boxKeysFromKey(rowKey);
		int[] columnKeysInBox = boxKeysFromKey(columnKey);
		
		Set<Optional<Integer>> cellsInBox = new HashSet<Optional<Integer>>();
		for (int otherRowKey : rowKeysInBox) {
			for (int otherColumnKey : columnKeysInBox) {
				cellsInBox.add(puzzle.get(otherRowKey, otherColumnKey));
			}
		}
		
		return cellsInBox;
	}
	
	public static Set<Optional<Integer>> cellsInBoxOf(Sudoku puzzle, Pair<Integer, Integer> location)
	{
		return cellsInBoxOf(puzzle, location.getLeft(), location.getRight());
	}
	
	public static Iterable< Pair<Integer, Integer> > locationsInBoxOf(
			Sudoku puzzle, Integer rowKey, Integer columnKey)
	{
		int[] rowKeys = boxKeysFromKey(rowKey);
		int[] columnKeys = boxKeysFromKey(columnKey);
		
		return Pair.range(
				Range.closed(rowKeys[0], rowKeys[rowKeys.length - 1]),
				Range.closed(columnKeys[0], columnKeys[columnKeys.length - 1]));
				
	}
	
	public static Iterable< Pair<Integer, Integer> > locationsInBoxOf(
			Sudoku puzzle, Pair<Integer, Integer> location)
	{
		return locationsInBoxOf(puzzle, location.getLeft(), location.getRight());
	}
	
	/**
	 * Returns an iterable of integers from the start to the end
	 * @param start		The start of the sequence, inclusive
	 * @param end		The end of the sequence, exclusive
	 * @return			The set of integers from start to end
	 */
	public static Iterable<Integer> intSeq(int start, int end)
	{
		return ContiguousSet.create(
				Range.closed(start, end), DiscreteDomain.integers());
	}
	
	public static int[] boxKeysFromKey(int key)
	{
		switch(key) {
		case 1:
		case 2:
		case 3:
			return new int[]{1,2,3};
		case 4:
		case 5:
		case 6:
			return new int[]{4,5,6};
		case 7:
		case 8:
		case 9:
			return new int[]{7,8,9};
		default:
			return new int[]{};
		}
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
	public static boolean directConflictExistsBetween(
			Integer n, Pair<Integer, Integer> location, Sudoku puzzle)
	{
		Optional<Integer> cellValue = puzzle.get(location);
		if (cellValue.isPresent()) {
			return !cellValue.get().equals(n);
		}
		
		// Is there an N in the cell's row?
		List< Pair<Integer, Integer> > locationsInRow = 
				Pair.rightRange(location.getLeft(), Range.closed(1, 9));
		
		for (Pair<Integer, Integer> locationInRow : locationsInRow) {
			if (puzzle.get(locationInRow) .equals (Optional.of(n))) {
				return true;
			}
		}
		
		// Is there an N in the cell's column?
		List< Pair<Integer, Integer> > locationsInColumn = 
				Pair.leftRange(Range.closed(1, 9), location.getRight());
		
		for (Pair<Integer, Integer> locationInColumn : locationsInColumn) {
			if (puzzle.get(locationInColumn) .equals (Optional.of(n))) {
				return true;
			}
		}

		// Is there an N in the cell's box?
		Set< Optional<Integer> > locationsInBox = 
				Sudokus.cellsInBoxOf(puzzle, location);
				
		for (Optional<Integer> locationInBox : locationsInBox) {
			if (locationInBox .equals (Optional.of(n))) {
				return true;
			}
		}
		
		
		// We've done all we can; there is nothing immediately stopping "location" from being N.
		return false;
		
	}

}
