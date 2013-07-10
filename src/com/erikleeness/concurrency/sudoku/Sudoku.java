package com.erikleeness.concurrency.sudoku;


import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;

/**
 * @author ErikAdmin
 *
 */
public class Sudoku 
{
	private ArrayTable<Integer, Integer, Optional<Integer>> matrix;
	
	public Sudoku(ArrayTable<Integer, Integer, Optional<Integer>> configuration)
	{
		matrix = ArrayTable.create(configuration);
	}
	
	public static Sudoku fromIntMatrix(int[][] matrix)
	{
		Iterable<Integer> rowKeys = Sudokus.intSeq(1, 9);
		Iterable<Integer> colKeys = Sudokus.intSeq(1, 9);
		ArrayTable<Integer, Integer, Optional<Integer>> cells;
		cells = ArrayTable.create(rowKeys, colKeys);
		
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				cells.put(i+1, j+1, Optional.of(matrix[i][j]));
			}
		}
		
		return new Sudoku(cells);
	}
	
	public Optional<Integer> get(Integer rowKey, Integer columnKey)
	{
		return matrix.get(rowKey, columnKey);
	}
	
	public Optional<Integer> get(Pair<Integer, Integer> location)
	{
		return get(location.getLeft(), location.getRight());
	}
	
	public void erase(Integer rowKey, Integer columnKey)
	{
		Optional<Integer> blank = Optional.absent();
		matrix.put(rowKey, columnKey, blank);
	}
	
	public void erase(Pair<Integer, Integer> location)
	{
		erase(location.getLeft(), location.getRight());
	}
	
	public Sudoku copy()
	{
		return new Sudoku(ArrayTable.create(matrix));
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		
		for (Integer rowKey : matrix.rowKeySet()) {
			
			for (Integer columnKey : matrix.columnKeySet()) {
				
				Optional<Integer> optionalElement = matrix.get(rowKey, columnKey);
				Integer element = optionalElement.orNull();
				
				if (element == null) {
					result.append(" ");
				} else {
					result.append(element);
				}
				
				result.append(" ");
				if (columnKey % 3 == 0) result.append(" ");
			}
			
			result.append("\n");
			if (rowKey % 3 == 0) result.append("\n");
		}
		
		return result.toString();
	}
	
	public static <T> List<Optional<T>> toOptionalList(List<T> standardList)
	{
		List<Optional<T>> optionalList = Lists.newArrayList();
		
		for (T element : standardList) {
			optionalList.add(Optional.of(element));
		}
		
		return optionalList;
	}

	public void put(int rowKey, int columnKey, Optional<Integer> value)
	{
		matrix.put(rowKey, columnKey, value);
	}
	
	public void put(Pair<Integer, Integer> location, Optional<Integer> value)
	{
		put(location.getLeft(), location.getRight(), value);
	}
	
	public int size()
	{
		/* The matrix.size() and matrix.values().size() methods give you,
		 * as a result, 8. Since Integer is immutable and Optional<Integer> is
		 * also immutable, there are 8 unique values.
		 * 
		 * Semantically, we actually want the number of non-blank cells in this 
		 * table. A completed table should have 9x9=81 as its size().
		 * 
		 * Instead, we simply loop over everything. It may be in our best interest
		 * in the future to keep an instance variable that every state-changing
		 * method is responsible for updating.
		 */
		
		int count = 0;
		for (Map<Integer, Optional<Integer>> row : matrix.rowMap().values()) {
			for (Optional<Integer> cell : row.values()) {
				if (cell.isPresent()) count++;
			}
		}
		
		return count;
	}
	
	public Map<Integer, Optional<Integer>> row(Integer rowKey)
	{
		return matrix.row(rowKey);
	}
	
	public Map<Integer, Optional<Integer>> column(Integer columnKey)
	{
		return matrix.column(columnKey);
	}
}
