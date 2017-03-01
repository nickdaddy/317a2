package model;

import java.util.List;

/**
 * 
 * @author nib851
 *
 */
public class State {

	/**
	 * 
	 * @param move The move
	 * @param board The board
	 */
	public State(Move move, Board board) {
		this.move = move;
		this.board = board;
		utility = board.EvaluateBoard();
	}

	/** The move this state represents */
	public Move move;
	
	/** The utility of this state */
	public int utility;
	
	/** The board in the current state */
	public Board board;
	
	/** The list of child states of the board */
	public List<State> childStates;
}
