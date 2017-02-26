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
	
	/** If true, it is the kings turn to play */
	public boolean kingsTurn;
	
	/**
	 * This will perform the given move and return true or false depending on whether it was successful or not
	 * 
	 * @param move The move to perform
	 * @return Whether the move succeeded or not
	 */
	public boolean Move(Move move){ 
		move.toMove.x = move.x;
		move.toMove.y = move.y;
		return true;
	}
	
	/**
	 * Starts the game by creating a new board and deciding which team goes first.
	 */
	public void StartGame(){
		board = new Board();
		kingsTurn = false;
		UpdateBoard();
		DisplayBoard();
		CurrentTurn();
	}
	
	public void CurrentTurn(){
		Scanner scanner = new Scanner(System.in);
		
		boolean gameOver = false;
		
		while(!gameOver){
			String command = scanner.nextLine();
			
			int startX = convertToNum(command.charAt(0));
			int startY = convertToNum(command.charAt(1));
			int endX = convertToNum(command.charAt(2));
			int endY = convertToNum(command.charAt(3));
			
			System.out.println("Input X: " + startX + " Input Y: " + startY);
			
			for (Unit unit : board.units){
				
				System.out.println("Unit X: " + unit.x + " Unit Y: " + unit.y);
				
				//If we find the unit we're telling to move
				if(startX == unit.x && startY == unit.y){
					if(unit.PossibleMoves() != null){
						for(Move move : unit.PossibleMoves()){
							System.out.println("Move X: " + move.x + " Move Y: " + move.y);
							 //If the requested move is possible
							 if(endX == move.x && endY == move.y){
								 
								 Move(move);
							 }
						 }
					}
				}
			}
			
			
			UpdateBoard();
			DisplayBoard();
			
		}
		
		scanner.close();
		
	}
	
	private int convertToNum(char toConvert){
		int num = -1;
		
		switch(toConvert){
		case('a'):
			num = 0;
			break;
		case('b'):
			num = 1;
			break;
		case('c'):
			num = 2;
			break;
		case('d'):
			num = 3;
			break;
		case('e'):
			num = 4;
			break;
		case('1'):
			num = 0;
			break;
		case('2'):
			num = 1;
			break;
		case('3'):
			num = 2;
			break;
		case('4'):
			num = 3;
			break;
		case('5'):
			num = 4;
			break;
		}
		
		return num;
	}
	
	/**
	 * Ends the game by announcing the winner and starting a new game.
	 */
	public void EndGame(){}
	
	public void UpdateBoard(){
		
		for(int x = 0; x < board.getSize(); x++){
			for(int y = 0; y < board.getSize(); y++){
				board.grid[x][y] = 'O';
			}
		}
		
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
