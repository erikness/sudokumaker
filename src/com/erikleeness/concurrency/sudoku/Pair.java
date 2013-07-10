package com.erikleeness.concurrency.sudoku;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

public class Pair<L,R> {

	  private final L left;
	  private final R right;

	  public Pair(L left, R right) {
	    this.left = left;
	    this.right = right;
	  }
	  
	  /**
	   * Quick shorthand creator for a pair where generics are not
	   * required in the creation method.
	   * @param left
	   * @param right
	   * @return
	   */
	  public static <L, R> Pair<L, R> of(L left, R right)
	  {
		  return new Pair<L, R>(left, right);
	  }
	  
	  /**
	   * Creates a list of Integer pairs where the right element remains
	   * constant and the left element spans over a range supplied.
	   * @param leftRange	range of Integers that the left element will span over				
	   * @param right		the right element that stays constant throughout every result
	   * @return			list of pairs where the left element varies and the right element is constant
	   */
	  public static List< Pair<Integer, Integer> > leftRange(
			  Range<Integer> leftRange, Integer right)
	  {
		  List< Pair<Integer, Integer> > result = Lists.newArrayList();

		  Iterable<Integer> leftIterable = ContiguousSet.create(
					leftRange, DiscreteDomain.integers());
		  
		  for (Integer left : leftIterable) {
			  result.add( Pair.of(left, right) );
		  }
		  
		  return result;
	  }
	  
	  /**
	   * Creates a list of Integer pairs where the left element remains
	   * constant and the right element spans over a range supplied.
	   * @param left		the left element that stays constant throughout every result
	   * @param rightRange	range of Integers that the right element will span over		
	   * @return			list of pairs where the right element varies and the left element is constant
	   */
	  public static List< Pair<Integer, Integer> > rightRange(
			  Integer left, Range<Integer> rightRange)
	  {
		  List< Pair<Integer, Integer> > result = Lists.newArrayList();

		  Iterable<Integer> rightIterable = ContiguousSet.create(
					rightRange, DiscreteDomain.integers());
		  
		  for (Integer right : rightIterable) {
			  result.add( Pair.of(left, right) );
		  }
		  
		  return result;
	  }
	  
	  /**
	   * Returns a list of pairs in a region defined by the ranges on the right
	   * and left side of the resulting pairs.
	   * 
	   * Though the result is often internally 2-dimensional, the order is not formally
	   * specified, allowing us to iterate over the result in 1-dimension (or so it seems
	   * from the client).
	   * 
	   * Why an iterable instead of a list? This is a 2-dimensional range, which
	   * would otherwise have to fit into a List<List>, which is ugly and not very
	   * decoupled for the original use case: just finding a bunch of cells in an area and 
	   * iterating over them (order not important).
	   * 
	   * An Iterable is better for that, and we don't even have to make an Iterable<Iterable>.
	   * 
	   * @param leftRange
	   * @param rightRange
	   * @return
	   */
	  public static Iterable< Pair<Integer, Integer> > range(
			  Range<Integer> leftRange, Range<Integer> rightRange) {
		  
		  final Iterable<Integer> leftIterable = ContiguousSet.create(
					leftRange, DiscreteDomain.integers());
		  final Iterable<Integer> rightIterable = ContiguousSet.create(
					rightRange, DiscreteDomain.integers());
		  
		  final Iterator< Pair<Integer, Integer> > resultIterator = new Iterator< Pair<Integer, Integer> >() {
			  
			  private Iterator<Integer> leftIterator;
			  private Iterator<Integer> rightIterator;
			  
			  private Integer leftCurrent;
			  
			  {
				  leftIterator = leftIterable.iterator(); 
				  rightIterator = rightIterable.iterator(); 
				  leftCurrent = leftIterator.next();
			  }
			  
			  public boolean hasNext() 
			  {
				  return leftIterator.hasNext() || rightIterator.hasNext();
			  }
			  
			  public Pair<Integer, Integer> next()
			  {
				  if (!rightIterator.hasNext()) {
					  rightIterator = rightIterable.iterator();
					  leftCurrent = leftIterator.next();
				  }
				  
				  return Pair.of(leftCurrent, rightIterator.next());
			  }
			  
			  public void remove()
			  {
				  throw new UnsupportedOperationException();
			  }
			  
		  };
		  
		  // Now that we have a formal iterator...
		  return new Iterable< Pair<Integer, Integer> >() {
			  public Iterator< Pair<Integer, Integer> > iterator()
			  {
				  return resultIterator;
			  }
		  };
		  
	  }

	  public L getLeft() { return left; }
	  public R getRight() { return right; }

	  @Override
	  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

	  @Override
	  public boolean equals(Object other) {
	    if (other == null) return false;
	    if (!(other instanceof Pair)) return false;
	    
	    @SuppressWarnings("rawtypes")
		Pair pairOther = (Pair) other;
	    
	    return this.left.equals(pairOther.getLeft()) &&
	           this.right.equals(pairOther.getRight());
	  }
	  
	  @Override
	  public String toString()
	  {
		  return "(" + left.toString() + ", " + right.toString() + ")";
	  }

}