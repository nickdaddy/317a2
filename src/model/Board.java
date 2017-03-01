package model;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import model.Unit.UnitType;

/**
 * The singleton board class represents the board in the game, and contains a list of units
 * as well as a 5x5 representation of the current state of the board.
 */
public class Board {
	
	/** This is the actual board grid */
	public char grid[][];
	
	public final char emptyChar = '*';
	
	/** size of the board**/
	private int size = 5;

	/** This is the list of units on the board currently */
	public List<Unit> units;
	
	/** If true, it is the kings turn to play */
	public boolean kingsTurn;
	
	/**
	 * Creates a new board for the game to be played on
	 */
	public Board(){}
	
	/**
	 * Creates a new board for the game to be played on
	 */
	public void Initialize(){
		createGrid();
		spawnUnits();
		kingsTurn = false;
	}
	
	/**
	 * Checks if the given x and y coordinates are within the board size
	 * @param x The x pos to check
	 * @param y The y pos to check
	 * @return Whether the x and y positions are within the board boundaries
	 */
	public boolean inBounds(int x, int y){
		return !(x < 0 || getSize() - 1 < x || y < 0 || getSize() - 1 < y);
	}

	/** Spawns all the units for the game and places them on the grid */
	private void spawnUnits() {
		
		units = new LinkedList<Unit>();
		
		/** Spawn King**/
		King king = new King(this);
		units.add(king);
		
		/** Spawn Guards**/
		for (int i = 1; i<4; i++){
			Guard guard = new Guard(1, i, this);
			units.add(guard);
		}
		
		/** Spawn Dragons**/
		for (int i = 0;i<5; i++){
			Dragon dragon = new Dragon(3, i, this);
			units.add(dragon);
		}
	}
	
	/**
	 * Deep clones the board
	 * @return A deep clone of this board
	 */
	public Board deepClone(){
		Board cloneBoard = new Board();
		cloneBoard.units = new LinkedList<Unit>();
		
		//Deep clone the board units
		for (Unit unit : this.units){
			switch (unit.type) {
			case KING:
				cloneBoard.units.add(new King(cloneBoard));
				break;
			case GUARD:
				cloneBoard.units.add(new Guard(unit.x, unit.y, cloneBoard));
				break;
			case DRAGON:
				cloneBoard.units.add(new Dragon(unit.x, unit.y, cloneBoard));
				break;
			default:
				break;
			}
		}
		
		cloneBoard.grid = new char[size][size];
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				cloneBoard.grid[x][y] = this.grid[x][y];
			}
		}
		
		cloneBoard.kingsTurn = this.kingsTurn;
		
		return cloneBoard;
	}
	
	/**
	 * Deep clones the board and applies the given move to the cloned board
	 * @return A deep clone of this board
	 */
	public Board deepCloneAndMove(Move move){
		Board cloneBoard = new Board();
		cloneBoard.units = new LinkedList<Unit>();
		Unit cloned = null;
		//Deep clone the board units
		for (Unit unit : this.units){
			switch (unit.type) {
			case KING:
				cloned = (new King(cloneBoard));
				break;
			case GUARD:
				cloned = (new Guard(unit.x, unit.y, cloneBoard));
				break;
			case DRAGON:
				cloned = (new Dragon(unit.x, unit.y, cloneBoard));
				break;
			default:
				break;
			}
			
			cloneBoard.units.add(cloned);
			
			//If we just cloned the unit we are trying to move, apply the move to the newly cloned board
			if(move.toMove == unit){
				cloneBoard.Move(new Move(cloned, move.x, move.y));
			}
		}
		
		cloneBoard.grid = new char[size][size];
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				cloneBoard.grid[x][y] = this.grid[x][y];
			}
		}
		
		cloneBoard.kingsTurn = this.kingsTurn;
		cloneBoard.UpdateBoard();
		
		return cloneBoard;
	}
	
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
		
		System.out.println(move.toMove.toString() + " at " + Controller.convertToChar(move.toMove.x) + (move.toMove.y + 1) + 
				" moved to " + Controller.convertToChar(move.x) + (move.y + 1));
		
		move.toMove.x = move.x;
		move.toMove.y = move.y;
		kingsTurn = !kingsTurn;
		
		return true;
	}

	/** Creates the grid for which the units will be placed on */
	private void createGrid() {
		grid = new char[size][size];
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				grid[x][y] = emptyChar;
			}
		}
	}
	
	/**
	 * Updates the grid of the board by redrawing the position of each unit
	 */
	public void UpdateGrid(){
		for(int x = 0; x < getSize(); x++){
			for(int y = 0; y < getSize(); y++){
				grid[x][y] = emptyChar;
			}
		}
		
		for (Unit unit : units){
			switch(unit.type){
			case DRAGON:
				/** to prevent guards being overwritten by dragons**/
				if (grid[unit.x][unit.y] != 'G' && grid[unit.x][unit.y]!= 'K')
					grid[unit.x][unit.y] = 'D';
				break;
			case GUARD:
				grid[unit.x][unit.y] = 'G';
				break;
			case KING:
				grid[unit.x][unit.y] = 'K';
				break;
			default:
				break;
			
			}
		}
	}
	
	/**
	 * Gets the list of possible moves on the current board
	 * @return The list of possible moves to be made
	 */
	public List<Move> allPossibleMoves(){
		List<Move> moves = new LinkedList<Move>();
		
		if(isWinningState())
			return moves;
		
		for (Unit unit : units){
			if(unit.PossibleMoves() != null){
				moves.addAll(unit.PossibleMoves());
			}
		}
		
		return moves;
	}
	
	/**
	 * Checks if the board is currently in a state in which a team has won.
	 * @return Whether the board is currently in a winning state or not
	 */
	public boolean isWinningState(){
		for (Unit unit : units){
			if(unit.type == UnitType.KING){
				if(unit.y == getSize() - 1){
					return true;
				} else if (unit.isSurrounded() && unit.PossibleMoves().size() == 0){
					return true;
				}
				
				break;
			}
		}
		
		return false;
	}
	
	/**
	 * Takes a board and evaluates the utility of it
	 * 
	 * @param board The board to evaluate
	 * @return The utility of the board state itself
	 */
	public int EvaluateBoard(){
		int score = 0;
		int c = 2;
		for(Unit unit: units){
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
			score += CheckAdjacent(unit);
		}
		return score;
	}
	
	/**
	 * Calculates a heuristic score based on the units surrounding the given unit
	 * @param unit The unit to check adjacency
	 * @return The score of the unit based on it's surrounding units
	 */
	public int CheckAdjacent(Unit unit){
		int adjacentscore = 0;
		
		if (unit.type == UnitType.GUARD || unit.type == UnitType.KING){
			for (Unit other : units){
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
			for (Unit other : units){
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
	
	/**
	 * Updates the state of the board by updating the grid, updating the capture flags, and removing/adding units
	 * where appropriate
	 */
	public void UpdateBoard(){
		UpdateGrid();
		UpdateFlags();
		
		List<Unit> capturables = new LinkedList<Unit>();
		
		for (Unit unit : units){
			if(unit.canBeCaptured && unit.type == UnitType.GUARD){
				capturables.add(unit);
			} else if(unit.canBeCaptured && unit.type == UnitType.DRAGON){
			for (Unit guard : units){
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
				units.remove(unit);
				units.add(new Dragon(unit.x, unit.y, this));
			} else if(unit.type == UnitType.DRAGON){
				units.remove(unit);
			}
		}
		
		//Updates the grid again to reflect any changes made by removing/adding units
		UpdateGrid();
		UpdateFlags();
		
		System.out.println("Score of this board is: "+ EvaluateBoard());
	}
	
	/** Updates the capture flags of each unit */
	public void UpdateFlags(){
		for(Unit unit : units){
			unit.canBeCaptured = unit.isSurrounded();
		}
	}
	
	public int getSize() {
		return size;
	}
	
	/** For testing**/
	public static void main(String[] args){
		Board board = new Board();
		
	}
}
