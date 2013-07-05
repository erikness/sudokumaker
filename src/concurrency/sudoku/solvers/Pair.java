package concurrency.sudoku.solvers;

public class Pair<L,R> {

	  private final L left;
	  private final R right;

	  public Pair(L left, R right) {
	    this.left = left;
	    this.right = right;
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