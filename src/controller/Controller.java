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
	
	/** If true, it is the kings turn to play */
	public boolean kingsTurn;
	
	/**
	 * This will perform the given move and return true or false depending on whether it was successful or not
	 * 
	 * @param move The move to perform
	 * @precondition move must be valid, not filled and piece moving must be on its turn
	 * @return Whether the move succeeded or not
	 */
	public boolean Move(Move move){
		
		/**check for turn **/
		if ((move.toMove.type == UnitType.GUARD || move.toMove.type == UnitType.KING) && !kingsTurn){
			System.out.println("Silly King its not your turn");
			return false;
		}
		else if((move.toMove.type == UnitType.DRAGON) && kingsTurn){
			System.out.println("Silly Dragon its not your turn");
			return false;

		}
		
		System.out.println(move.toMove.toString() + " at " + convertToChar(move.toMove.x) + (move.toMove.y + 1) + 
				" moved to " + convertToChar(move.x) + (move.y + 1));
		
		move.toMove.x = move.x;
		move.toMove.y = move.y;
		
		
		return true;
	}
	
	/**
	 * Starts the game by creating a new board and deciding which team goes first.
	 */
	public void StartGame(){
		board = Board.getInstance();
		kingsTurn = false;
		CurrentTurn();
	}
	
	
	
	public void CurrentTurn(){
		Scanner scanner = new Scanner(System.in);
		
		boolean gameOver = false;
		
		List<Move> possibleMoves = new LinkedList<Move>();
		
		while(!gameOver){
			
			UpdateBoard();
			DisplayBoard();
			
			possibleMoves = allPossibleMoves();
			
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
					if (Move(move)){
						kingsTurn = !kingsTurn;
					}
					break;
					
				}
			}
		}
		
		scanner.close();
		
	}
	
	private void UpdateFlags(){
		for(Unit unit : board.units){
			unit.canBeCaptured = unit.isSurrounded();
		}
	}
	
	private List<Move> allPossibleMoves(){
		List<Move> moves = new LinkedList<Move>();
		
		for (Unit unit : board.units){
			if(unit.PossibleMoves() != null){
				moves.addAll(unit.PossibleMoves());
			}
		}
		
		return moves;
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
	
	private char convertToChar(int toConvert){
		char character = board.getInstance().emptyChar;
		
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
	
	/**
	 * Ends the game by announcing the winner and starting a new game.
	 */
	public void EndGame(){}
	
	public void UpdateBoard(){
		DrawBoard();
		UpdateFlags();
		
		List<Unit> capturables = new LinkedList<Unit>();
		
		for (Unit unit : board.units){
			if(unit.canBeCaptured && unit.type == UnitType.GUARD){
				capturables.add(unit);
			} else if(unit.canBeCaptured && unit.type == UnitType.DRAGON){
			for (Unit guard : board.units){
					if(guard.type == UnitType.GUARD || guard.type == UnitType.KING){
						if(guard.x == unit.x && guard.y == unit.y){
							capturables.add(unit);
						}
					}
				}
			}
		}
		
		for (Unit unit : capturables){
			if(unit.type == UnitType.GUARD){
				board.units.remove(unit);
				board.units.add(new Dragon(unit.x, unit.y));
			} else if(unit.type == UnitType.DRAGON){
				board.units.remove(unit);
			}
		}
		
		DrawBoard();
		System.out.println("Score of this board is: "+EvaluateBoard(board));
	}
	
	public void DrawBoard(){
		for(int x = 0; x < board.getSize(); x++){
			for(int y = 0; y < board.getSize(); y++){
				board.grid[x][y] = board.getInstance().emptyChar;
			}
		}
		
		for (Unit unit : board.units){
			switch(unit.type){
			case DRAGON:
				/** to prevent gaurds being overwritten by dragons**/
				if (board.grid[unit.x][unit.y] != 'G' && board.grid[unit.x][unit.y]!= 'K')board.grid[unit.x][unit.y] = 'D';
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
	
	
	
	public int CheckAdjacent(Unit unit, Board board){
		int adjacentscore = 0;
		
		if (unit.type == UnitType.GUARD || unit.type == UnitType.KING){
			for (Unit other:board.units){
				if (unit == other){
					continue;
				}
				else if(other.type == UnitType.GUARD || other.type == UnitType.KING){
					/** At this point im not sure if only horizontal checks are required. Is it even good to have a vertical check in the game? Maybe just for the king?**/
					/**Horizontal check**/
					if ((other.y == unit.y-1 && other.x == unit.x)|| (other.y == unit.y+1 && other.x == unit.x)){
						adjacentscore++;
					}
				}
				/** close to a dragon**/
				else{
					if (Math.abs(other.x-unit.x)<=1 && Math.abs(other.y-unit.y)<=1){
						adjacentscore--;
					}
				}	
			}
		}
		/** is dragon**/
		else{
			for (Unit other:board.units){
				if (unit == other){
					continue;
				}
				else if(other.type == UnitType.DRAGON){
					/** check all around including diagonal**/
					if (Math.abs(other.x-unit.x)<=1 && Math.abs(other.y-unit.y)<=1){
						adjacentscore--;
					}
				}
				else{
					if (Math.abs(other.x-unit.x)<=1 && Math.abs(other.y-unit.y)<=1){
						adjacentscore++;
					}
				}
			}
		}
		
		return adjacentscore;
	}
	public int EvaluateBoard(Board board){
		int score = 0;
		int c = 2;
		for(Unit unit: board.units){
			switch(unit.type){
				case GUARD:	
					score+=c; 
					
					break;
							
				case KING: 
					score+=c;
					break;
							
				case DRAGON: 
					score-=c;
					break;
			}
			score+=CheckAdjacent(unit, board);
		}
		return score;
	}
	
	
	public static void main(String[] args){
		new Controller().StartGame();
	}
	
	
}
