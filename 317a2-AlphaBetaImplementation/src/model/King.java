package model;

import java.util.LinkedList;
import java.util.List;

public class King extends Unit{

	public King(int xPos, int yPos, Board gameBoard){
		x = xPos;
		y = yPos;
		type = UnitType.KING;
		board = gameBoard;
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
		
		return (board.grid[x][y] == 'G');
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
			
		return 3 <= dragonCount;
	}
	
	
	
	@Override
	public String toString(){
		return "King";
	}
}
