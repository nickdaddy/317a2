package model;

/**
 * This class represent a move in the system. A move contains a unit and the 
 * destination to move to on the board.
 */
public class Move {

	/** The unit to move */
	public Unit toMove;
	
	/** The location to move the unit to */
	public int x, y;
	
	/**
	 * Creates a new move with the given unit to move and destination
	 * @param moving The unit to move
	 * @param xDest The x coordinate of the destination
	 * @param yDest The y coordinate of the destination
	 */
	public Move(Unit moving, int xDest, int yDest){
		toMove = moving;
		x = xDest;
		y = yDest;
	}
}
