package model;

import java.util.LinkedList;
import java.util.List;

/** The guard class*/
public class Guard extends Unit {

	public Guard(int x, int y){
		this.x=x;
		this.y=y;
		type = UnitType.GUARD;
		
	}
	
	
	public List<Move> PossibleMoves() {
		List<Move> moves = new LinkedList<Move>();
		
		moves.add(new Move(this, x, y + 1));
		moves.add(new Move(this, x, y - 1));
		moves.add(new Move(this, x + 1, y));
		moves.add(new Move(this, x - 1, y));
		return moves;
	}
	
	/**
	 * This takes a move and returns whether the move is valid or not.
	 * @param move The move to decide is valid or not
	 * @return Whether the move was valid or not
	 */
	@Override
	public boolean isValidMove(Move move) {
		
		//if (move.x < 0 || 4 < move.x || move.y < 0 || 4 <  move.y)
		//	return false;
		
		return true;
	}

}
