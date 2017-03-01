package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import controller.Controller;

public class MinMaxTree {
	State root;
	int maxDepth;
	/**
	 * Takes a state and generates it's children. If the given state is a winning state, returns an empty list.
	 * @param state The state to make children for
	 * @param depth The depth of the given state
	 * @return The list of child states
	 */
	
	public MinMaxTree(Board board, int maxdepth){
		
		List<Move> moves = Controller.allPossibleMoves(board);
		this.maxDepth = maxdepth;
		
		root = new State();
		
		for(Move curMove : moves){
			State child = new State(curMove);
			root.childStates.add(child);
			GenerateMinMaxTree(child, 0);
		}
		
		
	}
	
	public void GenerateMinMaxTree(State state, int depth){
		Board board = new Board(state.board);
		//apply move to board here. 
		
		
		List<Move> moves = Controller.allPossibleMoves(board);
		
		if (depth>maxDepth || Controller.isWinningState(board)|| moves.size()==0){
			state.utility=  Controller.EvaluateBoard(board);
			return;
		}
		
		
		for(Move curMove : moves){
			State child = new State(curMove);
			state.childStates.add(child);
			GenerateMinMaxTree(child, depth+1);
		}
		
	}
	
	public State EvaluateTree(boolean kingsturn){
		if (kingsturn){
			return MaxValue(root);
		}
		else{
			return MinValue(root);
		}
		
	}
	
	private State MaxValue(State st){
		if (st.childStates == null){
			return st;
		}
		
		Iterator<State> iter = st.childStates.iterator();
		int best = Integer.MIN_VALUE;
		while(iter.hasNext()){
			State curr = iter.next();
			MinValue(curr);
			if (curr.utility>best){
				best = curr.utility;
			}
		}
		st.utility= best;
		return st;
	}
	
	private State MinValue(State st){
		if (st.childStates == null){
			return st;
		}
		
		Iterator<State> iter = st.childStates.iterator();
		int best = Integer.MAX_VALUE;
		while(iter.hasNext()){
			State curr = iter.next();
			MaxValue(curr);
			if (curr.utility<best){
				best = curr.utility;
			}
		}
		st.utility= best;
		return st;
	}
	
	/**
	public List<State> generateChildStates(State state, int depth){
		
		List<State> states = new LinkedList<State>();
		
		List<Move> moves = Controller.allPossibleMoves(state.board);
		
		if(moves.size() == 0 || depth > Controller.maxDepth){
			return states;
		}
		
		return null;
	}
	**/
}
