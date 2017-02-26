package model;

import java.util.LinkedList;
import java.util.List;

/**
 * The singleton board class represents the board in the game, and contains a list of units
 * as well as a 5x5 representation of the current state of the board.
 */
public class Board {

	/** The singleton instance of the board */
	private static Board instance = null;
	
	/** This is the actual board grid */
	public char grid[][];
	
	/** size of the board**/
	private int size = 5;

	/** This is the list of units on the board currently */
	public List<Unit> units;
	
	/**
	 * Creates a new board for the game to be played on
	 */
	protected Board(){
		createGrid();
		spawnUnits();
	}
	
	public static Board getInstance(){
		if(instance == null){
			instance = new Board();
		}
		
		return instance;
	}

	/** Spawns all the units for the game and places them on the grid */
	private void spawnUnits() {
		
		units = new LinkedList<Unit>();
		
		/** Spawn King**/
		King king = new King();
		units.add(king);
		
		/** Spawn Guards**/
		for (int i = 1; i<4; i++){
			Guard guard = new Guard(1, i);
			units.add(guard);
		}
		
		/** Spawn Dragons**/
		for (int i = 0;i<5; i++){
			Dragon dragon = new Dragon(4,i);
			units.add(dragon);
		}
		
		
		
	}
	
	public void recreateBoard(){
		createGrid();
		spawnUnits();
	}

	/** Creates the grid for which the units will be placed on */
	private void createGrid() {
		grid = new char[size][size];
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				grid[x][y] = 'O';
			}
		}
	}
	
	public int getSize() {
		return size;
	}
}
