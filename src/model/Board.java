package model;

import java.util.List;

/**
 * The singleton board class represents the board in the game, and contains a list of units
 * as well as a 5x5 representation of the current state of the board.
 */
public class Board {

	/** This is the actual board grid */
	public int grid[][];
	
	/** This is the list of units on the board currently */
	public List<Unit> units;
	
	/**
	 * Creates a new board for the game to be played on
	 */
	public Board(){
		createGrid();
		spawnUnits();
	}

	/** Spawns all the units for the game and places them on the grid */
	private void spawnUnits() {}

	/** Creates the grid for which the units will be placed on */
	private void createGrid() {}
}
