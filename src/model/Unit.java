package model;

import java.util.List;

/**
 * The unit class represents a piece on the game board. It can be either a king, guard, or dragon.
 * It will contain a reference to it's current coordinate
 */
public abstract class Unit {

	/** The x and y coordinate of the unit */
	public int x, y;
	
	/** The type of the unit */
	public UnitType type;
	
	/**
	 * The list of possible moves the piece can make at the current time
	 * @return A list containing all the possible moves the piece can make
	 */
	public abstract List<Move> PossibleMoves();
	
	/** Public enum representing the type of unit */
	public enum UnitType{
	
		/** The king piece */
		KING,
		
		/** The guard piece */
		GUARD,
		
		/** The dragon piece */
		DRAGON
	}
}
