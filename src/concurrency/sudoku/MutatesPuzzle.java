package concurrency.sudoku;

/**
 * Used to denote methods that mutate a given puzzle parameter.
 * 
 * Methods are otherwise semantically forbidden from mutating a puzzle
 * that is passed to it; they are restricted to read-only operations.
 * Methods will often make their own copy of the puzzle as a "working
 * copy" in order to tentatively change it.
 * 
 * @author ErikAdmin
 *
 */
public @interface MutatesPuzzle {

}
