package controller;

import java.util.Scanner;

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
	public void StartGame(){
		board = new Board();
		UpdateBoardUnits();
		DisplayBoard();
	}
	
	public void CurrentTurn(){

	}
	
	/**
	 * Ends the game by announcing the winner and starting a new game.
	 */
	public void EndGame(){}
	
	public void UpdateBoardUnits(){
		for(Unit unit : board.units){
			switch(unit.type){
			case DRAGON:
				board.grid[unit.x][unit.y] = 'D';
				break;
			case GUARD:
				board.grid[unit.x][unit.y] = 'G';
				break;
			case KING:
				board.grid[unit.x][unit.y] = 'K';
				break;
			default:
				break;
			
			}
		}
	}
	
	public void DisplayBoard(){
		for(int x = 0; x < board.getSize(); x++){
			for(int y = 0; y < board.getSize(); y++){
				System.out.print(board.grid[x][y]);
			}
			System.out.print("\n");
		}
	}
	
	
	public static void main(String[] args){
		new Controller().StartGame();
	}
}
