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
	
	/**
	 * Returns a list of possible moves this unit can make 
	 */
	public List<Move> PossibleMoves() {
		List<Move> moves = new LinkedList<Move>();
		
		Move curMove = new Move(this, x, y + 1);
		if(isValidMove(curMove))
			moves.add(curMove);
		
		curMove = new Move(this, x, y - 1);
		if(isValidMove(curMove))
			moves.add(curMove);
		
		curMove = new Move(this, x + 1, y);
		if(isValidMove(curMove))
			moves.add(curMove);
		
		curMove = new Move(this, x - 1, y);
		if(isValidMove(curMove))
			moves.add(curMove);
		
		return moves;
	}
	
	/**
	 * This takes a move and returns whether the move is valid or not.
	 * @param move The move to decide is valid or not
	 * @return Whether the move was valid or not
	 */
	@Override
	public boolean isValidMove(Move move) {
		
		if (move.x < 0 || board.getSize() < move.x || move.y < 0 || board.getSize() <  move.y)
			return false;
		
		if(board.grid[move.x][move.y] == board.emptyChar){
			return true;
		} else if (board.grid[move.x][move.y] == 'D'){
			for(Unit unit : board.units){
				if(move.x == unit.x && move.y == unit.y){
					return unit.canBeCaptured;
				}
			}
		}
		
		return false;
	}

	@Override
	public String toString(){
		return "Guard";
	}
	
	@Override
	public boolean isSurrounded(){
		
		int dragonCount = 0;
		
		if(board.inBounds(x, y + 1)){
			if(board.grid[x][y + 1] == 'D'){
				dragonCount++;
			}
		}

		if(board.inBounds(x, y - 1)){
			if(board.grid[x][y - 1] == 'D'){
				dragonCount++;
			}
		}

		if(board.inBounds(x + 1, y)){
			if(board.grid[x + 1][y] == 'D'){
				dragonCount++;
			}
		}
		
		if(board.inBounds(x - 1, y)){
			if(board.grid[x - 1][y] == 'D'){
				dragonCount++;
			}
		}
			
		return 2 < dragonCount;
	}
}
