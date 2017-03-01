package controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model.*;
import model.Unit.UnitType;

/**
 * The controller singleton runs the game. It contains all the operations for manipulating
 * the board.
 */
public class Controller {

	/** The board of the game to manipulate */
	public Board board;
	
	/** The maximum depth to explore in the tree */
	public static int maxDepth = 12;
	
	/**
	 * Starts the game by creating a new board and deciding which team goes first.
	 */
	public void StartGame(){
		board = new Board();
		board.Initialize();
		CurrentTurn();
	}
	
	public void CurrentTurn(){
		Scanner scanner = new Scanner(System.in);
		
		boolean gameOver = false;
		
		List<Move> possibleMoves = new LinkedList<Move>();
		
		while(!gameOver){
			
			board.UpdateBoard();
			DisplayBoard();
			
			possibleMoves = board.allPossibleMoves();
			
			boolean kingCanMove = false;
			
			for(Move move : possibleMoves){
				System.out.println(move.toMove.toString() + " at " + convertToChar(move.toMove.x) + (move.toMove.y + 1) + 
						" can move to: " + convertToChar(move.x) + (move.y + 1));
				if(move.toMove.type == UnitType.KING){
					kingCanMove = true;
				}
			}
			
			if(!kingCanMove)
				EndGame();
			
			String command = scanner.nextLine();
			
			int startX = convertToNum(command.charAt(0));
			int startY = convertToNum(command.charAt(1));
			int endX = convertToNum(command.charAt(2));
			int endY = convertToNum(command.charAt(3));
			
			for (Move move: possibleMoves){
				if( startX == move.toMove.x && startY == move.toMove.y && endX == move.x && endY == move.y){
					board.Move(move);
					break;
				}
			}
		}
		
		scanner.close();
	}
	
	/**
	 * Ends the game by announcing the winner and starting a new game.
	 */
	public void EndGame(){}
	
	/**
	 * Displays the current state of the board.
	 */
	public void DisplayBoard(){
		board.DisplayBoard();
	}
	
	/**
	 * Generates the MinMaxTree for the current state of the board
	 * @param root The root state to generate the minmax from
	 */
	public void GenerateMinMaxTree(State root){
		List<Move> moves = root.board.allPossibleMoves();
		
		
		for(Move curMove : moves){
			//root.childStates.add(new State(m));
		}
	}
	
	public static int convertToNum(char toConvert){
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
		case('A'):
			num = 0;
			break;
		case('B'):
			num = 1;
			break;
		case('C'):
			num = 2;
			break;
		case('D'):
			num = 3;
			break;
		case('E'):
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
	
	public static char convertToChar(int toConvert){
		char character = '?';
		
		switch(toConvert){
		case(0):
			character = 'A';
			break;
		case(1):
			character = 'B';
			break;
		case(2):
			character = 'C';
			break;
		case(3):
			character = 'D';
			break;
		case(4):
			character = 'E';
			break;
		}
		
		return character;
	}
	
	public static void main(String[] args){
		new Controller().StartGame();
	}
	
	
}
