package concurrency.sudoku;

import java.util.Collection;
import java.util.HashSet;
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
	
	/**
	 * Returns an iterable of integers from the start to the end
	 * @param start		The start of the sequence, inclusive
	 * @param end		The end of the sequence, exclusive
	 * @return			The set of integers from start to end
	 */
	public static Iterable<Integer> intSeq(int start, int end)
	{
		return ContiguousSet.create(
				Range.closed(1, 9), DiscreteDomain.integers());
	}
	
	private static int[] boxKeysFromKey(int key)
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

}
