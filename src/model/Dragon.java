package model;

import java.util.List;

public class Dragon extends Unit {

	public Dragon(int x, int y){
		this.x=x;
		this.y=y;
		
		type = UnitType.DRAGON;
	}
	
	@Override
	public List<Move> PossibleMoves() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * This takes a move and returns whether the move is valid or not.
	 * @param move The move to decide is valid or not
	 * @return Whether the move was valid or not
	 */
	@Override
	public boolean isValidMove(Move move){ return false; }

	@Override
	public String toString(){
		return "Dragon";
	}
}
