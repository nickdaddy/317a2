package model;

import java.util.List;

public class ABState {


	/**
	 * 
	 * @param move The move
	 * @param board The board
	 */
	public ABState(Move move, Board board, int alpha, int beta) {
		this.move = move;
		this.board = board;
		utility = board.EvaluateBoard();
		this.alpha = alpha;
		this.beta = beta;
	}

	/** The move this state represents */
	public Move move;
	public int alpha;
	public int beta;
	/** The utility of this state */
	public int utility;
	
	/** The board in the current state */
	public Board board;
	
	/** The list of child states of the board */
	public List<State> childStates;
}
