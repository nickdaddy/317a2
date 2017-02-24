package controller;

import model.*;

/**
 * The controller singleton runs the game. It contains all the operations for manipulating
 * the board.
 */
public class Controller {

	/** The board of the game to manipulate */
	public Board board;
	
	/**
	 * This will perform the given move and return true or false depending on whether it was successful or not
	 * 
	 * @param move The move to perform
	 * @return Whether the move succeeded or not
	 */
	public boolean Move(Move move){ return false; }
	
	/**
	 * Starts the game by creating a new board and deciding which team goes first.
	 */
	public void StartGame(){}
	
	/**
	 * Ends the game by announcing the winner and starting a new game.
	 */
	public void EndGame(){}
}
