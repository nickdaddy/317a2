package model;

import java.util.List;
/** The gaurd class*/
public class Gaurd extends Unit {

	public Gaurd(int x, int y){
		this.x=x;
		this.y=y;
		type = UnitType.GUARD;
		
	}
	
	
	public List<Move> PossibleMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}
