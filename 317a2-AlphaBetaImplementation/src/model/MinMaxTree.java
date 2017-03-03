package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import model.Unit.UnitType;

public class MinMaxTree {

	public State root;
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
		
		root = new State(null, board);
		
		firstLevel = this.generateChildStates(board);
		
		root.childStates = firstLevel;
		
		System.out.println("Level 0 contains " + firstLevel.size() + " states.");
		
		List<State> currentLevel = new LinkedList<State>();
		List<State> nextLevel = new LinkedList<State>();
		
		currentLevel.addAll(firstLevel);
		
		for(int x = 1; x < depth; x++){
			for (State curState : currentLevel){
				curState.childStates = generateChildStates(curState, x);
				nextLevel.addAll(curState.childStates);
				//curState.board.DisplayBoard();
			}
			
			System.out.println("Level " + x + " contains " + nextLevel.size() + " states.");
			
			currentLevel.clear();
			currentLevel.addAll(nextLevel);
			nextLevel.clear();
		}
	}
	
	public State EvaluateTree(boolean kingsturn){
		
		if (kingsturn){
			//root.move = MaxValue(root).move;
			//return MaxValue(root);
			root = MaxValue(root);
		}
		else{
			//root.move = MinValue(root).move;
			
			//return MinValue(root);
			root = MinValue(root);
		}
		
		for (State child: root.childStates){
			if (root.utility == child.utility){
				root.move = child.move;
			}
		}
		return root;
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
	
	/** For testing**/
	public static void main(String[] args){
		Board board = new Board();
		board.Initialize();
		board.DisplayBoard();
		
		MinMaxTree tree = new MinMaxTree(board, Controller.maxDepth);
		
		State state = tree.EvaluateTree(board.kingsTurn);
		System.out.println(state.utility);
		
		board.Move(state.move);
		board.DisplayBoard();
		
	}
}
