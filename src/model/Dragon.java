package model;

import java.util.LinkedList;
import java.util.List;

public class Dragon extends Unit {

	public Dragon(int x, int y){
		this.x=x;
		this.y=y;
		
		type = UnitType.DRAGON;
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
		
		curMove = new Move(this, x + 1, y + 1);
		if(isValidMove(curMove))
			moves.add(curMove);
		
		curMove = new Move(this, x + 1, y - 1);
		if(isValidMove(curMove))
			moves.add(curMove);
		
		curMove = new Move(this, x - 1, y - 1);
		if(isValidMove(curMove))
			moves.add(curMove);
		
		curMove = new Move(this, x - 1, y + 1);
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
		
		if (move.x < 0 || 4 < move.x || move.y < 0 || 4 <  move.y)
			return false;
		
		if(Board.getInstance().grid[move.x][move.y] == Board.getInstance().emptyChar){
			return true;
		}
		
		return false;
	}
	
	

	@Override
	public String toString(){
		return "Dragon";
	}
	
	@Override
	public boolean isSurrounded(){
		
		int guardCount = 0;
		
		if(Board.getInstance().inBounds(x, y + 1)){
			if(Board.getInstance().grid[x][y + 1] == 'G' || Board.getInstance().grid[x][y + 1] == 'K'){
				guardCount++;
			}
		}

		if(Board.getInstance().inBounds(x, y - 1)){
			if(Board.getInstance().grid[x][y - 1] == 'G' || Board.getInstance().grid[x][y - 1] == 'K'){
				guardCount++;
			}
		}

		if(Board.getInstance().inBounds(x + 1, y)){
			if(Board.getInstance().grid[x + 1][y] == 'G' || Board.getInstance().grid[x + 1][y] == 'K'){
				guardCount++;
			}
		}
		
		if(Board.getInstance().inBounds(x - 1, y)){
			if(Board.getInstance().grid[x - 1][y] == 'G' || Board.getInstance().grid[x - 1][y] == 'K'){
				guardCount++;
			}
		}
		
		if(Board.getInstance().inBounds(x, y)){
			if(Board.getInstance().grid[x][y] == 'G' || Board.getInstance().grid[x][y] == 'K'){
				guardCount++;
			}
		}
			
		return 1 < guardCount;
	}
}
