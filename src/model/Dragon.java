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

}
