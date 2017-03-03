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
	
	/** The character to use as an empty spot on the grid */
	public final char emptyChar = '-';
	
	/** size of the board**/
	private int size = 5;
	
	/** Display whenever a unit moves? */
	private final boolean displayMoves = false;
	
	/** Display the current board score? */
	private final boolean displayScore = false;

	/** This is the list of units on the board currently */
	public List<Unit> units;
	
	/** If true, it is the kings turn to play */
	public boolean kingsTurn;
	
	/********************\
	 * 					 *
	 * PUBLIC FUNCTIONS *
	 * 					 *
	\********************/

	public Board(){}
	
	/**
	 * Creates a new board for the game to be played on
	 */
	public void Initialize(){
		createGrid();
		spawnUnits();
		kingsTurn = false;
		UpdateBoard();
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
				cloneBoard.units.add(new King(unit.x, unit.y, cloneBoard));
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
		
		cloneBoard.grid = new char[size][size];
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				cloneBoard.grid[x][y] = this.grid[x][y];
			}
		}
		
		
		cloneBoard.kingsTurn = this.kingsTurn;
		
		cloneBoard.units = new LinkedList<Unit>();
		
		Unit cloned = null;
		
		//Deep clone the board units
		for (Unit unit : this.units){
			int x = unit.x;
			int y = unit.y;
			
			switch (unit.type) {
			case KING:
				cloned = new King(x, y, cloneBoard);
				break;
			case GUARD:
				cloned = new Guard(x, y, cloneBoard);
				break;
			case DRAGON:
				cloned = new Dragon(x, y, cloneBoard);
				break;
			}
			cloned.ID = unit.ID;

			cloneBoard.units.add(cloned);
			
			//If we just cloned the unit we are trying to move, apply the move to the newly cloned board
			if(move.toMove == unit){
				cloneBoard.Move(new Move(cloned, move.x, move.y));
			}
		}

		cloneBoard.UpdateBoard();
		
		return cloneBoard;
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
		
		if(displayScore)
			System.out.println("Score of this board is: "+ EvaluateBoard());
	}
	
	/** Displays the current state of the board */
	public void DisplayBoard(){
		
		UpdateBoard();
		System.out.print("\n");
		
		for(int x = 0; x < getSize(); x++){
			if (x == 0) {
				System.out.println("   1 2 3 4 5");
				System.out.println(" ___________");
			}
			for(int y = 0; y < getSize(); y++){
				if (y == 0) {
					switch (x) {

					case 0:
						System.out.print("A| ");
						break;
					case 1:
						System.out.print("B| ");
						break;
					case 2:
						System.out.print("C| ");
						break;
					case 3:
						System.out.print("D| ");
						break;
					case 4:
						System.out.print("E| ");
						break;
					}
				}
				System.out.print(grid[x][y] + " ");
			}
			
			System.out.print("\n");
		}
		
		System.out.print("\n");
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
	
	public List<Move> allPossibleMoves(Move moveof4p){
		List<Move> moves = new LinkedList<Move>();
		
		if(isWinningState())
			return moves;
		
		for (Unit unit : units){
			
			if(unit.PossibleMoves() != null){
				
				
				moves.addAll(unit.PossibleMoves());
				/**for (Move move : moves) {
					if ( moves.size()>1 && moveof4p != null && move.x == moveof4p.x && move.y == moveof4p.y && move.toMove.ID == moveof4p.toMove.ID ){
						moves.remove(move);
						break;
					}
				}**/
				
			}
		}
		
		return moves;
	}
	
	/**
	 * Checks if the board is currently in a state in which a team has won.
	 * @return Whether the board is currently in a winning state or not
	 */
	public boolean isWinningState(){
		boolean cannotMove = true;
		for (Unit unit : units){
			
			if (kingsTurn && unit.type == UnitType.GUARD || unit.type == UnitType.KING){
				if (unit.PossibleMoves()!=null){
					cannotMove = false;
				}
			}
			if ( !kingsTurn && unit.type == UnitType.DRAGON){
				if (unit.PossibleMoves()!=null){
					cannotMove = false;
				}
			}
			
			if(unit.type == UnitType.KING){
				if(unit.x == getSize() - 1){
					return true;
				} else if (unit.isSurrounded() && unit.PossibleMoves().size() == 0){
					return true;
				}
				
				break;
			}
			
		}
		
		return cannotMove;
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
		
		if(displayMoves)
			System.out.println(move.toMove.toString() + " at " + Controller.convertToChar(move.toMove.x) + (move.toMove.y + 1) + 
					" moved to " + Controller.convertToChar(move.x) + (move.y + 1));
		
		move.toMove.x = move.x;
		move.toMove.y = move.y;
		
		kingsTurn = !kingsTurn;
		
		return true;
	}
	
	/**
	 * Takes a board and evaluates the utility of it
	 * 
	 * @param board The board to evaluate
	 * @return The utility of the board state itself
	 */
	public int EvaluateBoard(){
		int score = 0;
		int c = 4;
		for(Unit unit: units){
			switch(unit.type){
				case GUARD:	
					score += c; //+ unit.x;
					break;
							
				case KING: 
					score +=c;
					score += unit.x; // Higher score when closer to bottom
					if (unit.x == getSize() - 1) {
						score += 50; // High score for king reaching bottom row.
					}
					// Since the game ends before the king is removed, we check if the king is able to be captured.
					if (unit.isSurrounded() && unit.PossibleMoves().size() == 0) {
						score -= 50;
					}
					break;

				case DRAGON: 
					score -= c+2;
					//score -= unit.x;
					break;
			}
			score += CheckAdjacent(unit);
		}
		return score;
	}
	
	public int EvaluateBoard(Move moveof3p, Move move){
		int score = 0;
		int c = 4;
		/**
		if (moveof3p != null && move.x == moveof3p.x && move.y == moveof3p.y && move.toMove.ID == moveof3p.toMove.ID ){
			if (kingsTurn){
				score+=10;
			}
			else{
				score-=10;
			}
		}**/
		
		for(Unit unit: units){
			switch(unit.type){
				case GUARD:	
					score += c; //+ unit.x;
					break;
							
				case KING: 
					score +=c;
					score += unit.x*2; // Higher score when closer to bottom
					if (unit.x == 4) {
						score += 50; // High score for king reaching bottom row.
					}
					// Since the game ends before the king is removed, we check if the king is able to be captured.
					if (unit.isSurrounded() && unit.PossibleMoves().size() == 0) {
						score -= 50;
					}
					break;

				case DRAGON: 
					score -= c+2;
					//score -= unit.x;
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
				else if(other.type == UnitType.GUARD){
					/** At this point im not sure if only horizontal checks are required. Is it even good to have a vertical check in the game? Maybe just for the king?**/
					/**Horizontal check**/
					if ((other.y == unit.y-1 && other.x == unit.x) || (other.y == unit.y+1 && other.x == unit.x)){
						adjacentscore++;
					}
					/** Vertical check **/
					if ((other.x == unit.x-1 && other.y == unit.y) || (other.x == unit.x+1 && other.y == unit.y)){
						adjacentscore++;
					}
					/**
			
					if ((other.y == unit.y-2 && other.x == unit.x) || (other.y == unit.y+2 && other.x == unit.x)){
						adjacentscore += 3;
					}
					
					if ((other.x == unit.x-2 && other.y == unit.y) || (other.x == unit.x+2 && other.y == unit.y)){
						adjacentscore += 3;
					}
					**/
				}
				else if (other.type == UnitType.KING) {
					/** check all around including diagonal**/
					if (Math.abs(other.x-unit.x)<=1 && Math.abs(other.y-unit.y)<=1){
						adjacentscore++;
					}
					/**
		
					if ((other.y == unit.y-2 && other.x == unit.x) || (other.y == unit.y+2 && other.x == unit.x)){
						adjacentscore += 3;
					}
			
					if ((other.x == unit.x-2 && other.y == unit.y) || (other.x == unit.x+2 && other.y == unit.y)){
						adjacentscore += 3 ;
					}
					**/
					
				}
				/** close to a dragon**/
				else {
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
						adjacentscore++;
					}
				}
				else{
					if (Math.abs(other.x-unit.x)<=1 && Math.abs(other.y-unit.y)<=1){
						adjacentscore--;
					}
				}
				
			}
		}
		
		return adjacentscore;
	}
	
	/** Returns the size of the board */
	public int getSize() {
		return size;
	}
	
	/********************\
	 * 					 *
	 * PRIVATE FUNCTIONS *
	 * 					 *
	\********************/
	
	/** Updates the capture flags of each unit */
	private void UpdateFlags(){
		for(Unit unit : units){
			unit.canBeCaptured = unit.isSurrounded();
		}
	}
	
	/**
	 * Updates the grid of the board by redrawing the position of each unit
	 */
	private void UpdateGrid(){
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
	
	/** Spawns all the units for the game and places them on the grid */
	private void spawnUnits() {
		
		units = new LinkedList<Unit>();
		
		/** Spawn King**/
		King king = new King(0, 2, this);
		king.ID= units.size();
		units.add(king);
		
		/** Spawn Guards**/
		for (int i = 1; i<4; i++){
			Guard guard = new Guard(1, i, this);
			guard.ID = units.size();
			units.add(guard);

		}
		
		/** Spawn Dragons**/
		for (int i = 0;i<5; i++){
			Dragon dragon = new Dragon(3, i, this);
			dragon.ID = units.size();
			units.add(dragon);
			
		}
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
	
	/** For testing**/
	public static void main(String[] args){
		Board board = new Board();
		board.Initialize();
		board.DisplayBoard();
		Board cloned = board.deepCloneAndMove(new Move(board.units.get(5), 2, 1));
		cloned.DisplayBoard();
		cloned = cloned.deepCloneAndMove(new Move(cloned.units.get(2), 2, 2));
		cloned.DisplayBoard();
		cloned = cloned.deepCloneAndMove(new Move(cloned.units.get(7), 2, 3));
		cloned.DisplayBoard();
		MinMaxTree swag = new MinMaxTree(cloned, 3);
	}
}
