package model;

import java.util.List;

public class King extends Unit{

	public King(){
		x = 0;
		y = 2;
		type = UnitType.KING;
	}
	public List<Move> PossibleMoves(){
		return null;
		
	}
	
	/**
	 * This takes a move and returns whether the move is valid or not.
	 * @param move The move to decide is valid or not
	 * @return Whether the move was valid or not
	 */
	@Override
	public boolean isValidMove(Move move){ return false; }
	
}
