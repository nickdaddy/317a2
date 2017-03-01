package model;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;

public class MinMaxTree {

	
	/**
	 * Takes a state and generates it's children. If the given state is a winning state, returns an empty list.
	 * @param state The state to make children for
	 * @param depth The depth of the given state
	 * @return The list of child states
	 */
	public List<State> generateChildStates(State state, int depth){
		
		List<State> states = new LinkedList<State>();
		
		List<Move> moves = Controller.allPossibleMoves(state.board);
		
		if(moves.size() == 0 || depth > Controller.maxDepth){
			return states;
		}
		
		return null;
	}
}
