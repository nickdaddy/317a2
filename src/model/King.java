package model;

import java.util.LinkedList;
import java.util.List;

public class King extends Unit{

	public King(){
		x = 0;
		y = 2;
		type = UnitType.KING;
	}
	
	/**
	 * Returns a list of possible moves this unit can make 
	 */
	public List<Move> PossibleMoves() {
		List<Move> moves = new LinkedList<Move>();
		
		//Moving up
		Move curMove = new Move(this, x, y + 1);
		if(hasGuard(curMove.x, curMove.y)){
			curMove = new Move(this, x, y + 2);
			if(isValidMove(curMove))
				moves.add(curMove);
		} else if (isValidMove(curMove)){
			moves.add(curMove);
		}
		
		//Moving down
		curMove = new Move(this, x, y - 1);
		if(hasGuard(curMove.x, curMove.y)){
			curMove = new Move(this, x, y - 2);
			if(isValidMove(curMove))
				moves.add(curMove);
		} else if (isValidMove(curMove)){
			moves.add(curMove);
		}
		
		//Moving right
		curMove = new Move(this, x + 1, y);
		if(hasGuard(curMove.x, curMove.y)){
			curMove = new Move(this, x + 2, y);
			if(isValidMove(curMove))
				moves.add(curMove);
		} else if (isValidMove(curMove)){
			moves.add(curMove);
		}
		
		//Moving left
		curMove = new Move(this, x - 1, y);
		if(hasGuard(curMove.x, curMove.y)){
			curMove = new Move(this, x - 2, y);
			if(isValidMove(curMove))
				moves.add(curMove);
		} else if (isValidMove(curMove)){
			moves.add(curMove);
		}
		
		return moves;
	}
	
	/**
	 * This takes a move and returns whether the move is valid or not.
	 * @param move The move to decide is valid or not
	 * @return Whether the move was valid or not
	 */
	@Override
	public boolean isValidMove(Move move) {
		
		if (move.x < 0 || 4 < move.x || move.y < 0 || 4 <  move.y)
			return false;
		
		if(Board.getInstance().grid[move.x][move.y] == 'O'){
			return true;
		}
		
		return false;
	}
	
	/**
	 * This determines whether a coordinate contains a guard or not
	 * 
	 * @param x The x coordinate to check
	 * @param y The y coordinate to check
	 * @return whether the given coordinate contains a guard or not
	 */
	private boolean hasGuard(int x, int y){
		
		if (x < 0 || 4 < x || y < 0 || 4 < y)
			return false;
		
		return (Board.getInstance().grid[x][y] == 'G');
	}
	
	@Override
	public String toString(){
		return "King";
	}
}
