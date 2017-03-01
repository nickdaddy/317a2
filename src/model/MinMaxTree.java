package model;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import model.Unit.UnitType;

public class MinMaxTree {

	public List<State> firstLevel;
	
	/**
	 * Takes a state and generates it's children. If the given state is a winning state, returns an empty list.
	 * @param state The state to make children for
	 * @param depth The depth of the given state
	 * @return The list of child states
	 */
	public static List<State> generateChildStates(State state, int depth){
		
		List<State> children = new LinkedList<State>();
		
		List<Move> moves = state.board.allPossibleMoves();
		
		if(moves.size() == 0){
			return children;
		}
		
		for( Move move : moves){
			if(state.board.kingsTurn && (move.toMove.type == UnitType.KING || move.toMove.type == UnitType.GUARD))
				children.add(new State(move, state.board.deepCloneAndMove(move)));
			
			if(!state.board.kingsTurn && move.toMove.type == UnitType.DRAGON)
				children.add(new State(move, state.board.deepCloneAndMove(move)));
		}
		
		return children;
	}
	
	/**
	 * Takes a state and generates it's children. If the given state is a winning state, returns an empty list.
	 * @param state The state to make children for
	 * @param depth The depth of the given state
	 * @return The list of child states
	 */
	public List<State> generateChildStates(Board board){
		
		List<State> children = new LinkedList<State>();
		
		for( Move move : board.allPossibleMoves()){
			if(board.kingsTurn && (move.toMove.type == UnitType.KING || move.toMove.type == UnitType.GUARD))
				children.add(new State(move, board.deepCloneAndMove(move)));
			
			if(!board.kingsTurn && move.toMove.type == UnitType.DRAGON)
				children.add(new State(move, board.deepCloneAndMove(move)));
		}
	
		return children;
	}
	
	/**
	 * Creates a new min-max tree based on the given board
	 * @param board The board to create a tree for
	 */
	public MinMaxTree(Board board, int depth){
		firstLevel = this.generateChildStates(board);
		
		System.out.println("Level 0 contains " + firstLevel.size() + " states.");
		
		List<State> currentLevel = new LinkedList<State>();
		List<State> nextLevel = new LinkedList<State>();
		
		currentLevel.addAll(firstLevel);
		
		for(int x = 1; x < depth; x++){
			for (State curState : currentLevel){
				curState.childStates = generateChildStates(curState, x);
				nextLevel.addAll(curState.childStates);
			}
			
			System.out.println("Level " + x + " contains " + nextLevel.size() + " states.");
			
			currentLevel.clear();
			currentLevel.addAll(nextLevel);
			nextLevel.clear();
		}
	}
	
	/** For testing**/
	public static void main(String[] args){
		Board board = new Board();
		board.Initialize();
		board.DisplayBoard();
		
		List<State> firstLevel = new MinMaxTree(board, Controller.maxDepth).firstLevel;

	}
}
