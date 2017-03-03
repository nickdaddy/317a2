package model;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import model.Unit.UnitType;


public class AlphaBetaTree {
	public ABState root;
	private int maxDepth;
	public int count;
	public Move last4thMove;
	public int minMaxDepth;

	public AlphaBetaTree(Board board, int maxdepth, boolean kingsturn){
		root = new ABState(null, board, Integer.MIN_VALUE, Integer.MAX_VALUE, null);
		maxDepth = maxdepth;
		count = 0;

		if (kingsturn){
			root = Alpha(root, 0);
		}
		else{
			root = Beta(root, 0);
		}
	}
	
	public ABState Alpha(ABState st, int depth){
		
		//If the board is in a winning state or we have reached the maximum depth
		//return the state
		if(st.board.isWinningState() || depth>=maxDepth){
			
			// Makes the king choose the earliest depth move when there is a tie in utility values.
			st.utility -= minMaxDepth;
			
			return st;
		}
		
		//Create a list of all possible moves
		//Repetition check 
		List<Move> moves;
		if (st.parent!=null && st.parent.parent != null && st.parent.parent.parent!=null && st.parent.parent.parent.parent!=null ){

			moves = st.board.allPossibleMoves(st.parent.parent.parent.parent.move);
		}
		else{
			moves = st.board.allPossibleMoves();
		}
		

		
		//Initialize best move and current best utility
		Move bestmove =st.move;
		int best = st.alpha;
		
		//For each move
		for (Move move: moves){
			
			// Skip branch where the next move from the root is a repeated move.
			if(Controller.moveList.size() > 4 ){
				
				int oldMoveID = Controller.moveList.get(Controller.turnCounter - 5).toMove.ID;
				int newMoveID =  move.toMove.ID;
				// Check if root and move unit ID are the same.
				if(st == root && oldMoveID == newMoveID){
					// Check for same source.
					if (Controller.moveList.get(Controller.turnCounter - 5).toMove.x == move.toMove.x && Controller.moveList.get(Controller.turnCounter - 5).toMove.y == move.toMove.y) {
						// Check for same destination.
						if (Controller.moveList.get(Controller.turnCounter - 5).x == move.x && Controller.moveList.get(Controller.turnCounter - 5).y == move.y) {
							continue;
						}
					}
				}
			}

			//If the move is a dragon, ignore this
			if( move.toMove.type == UnitType.DRAGON){
				continue;
			}
			
			//Now dealing with only guards/the king
			
			//Increment the node count
			count++;
			
			//Create the current alpha-beta state
			ABState state = new ABState(move, st.board.deepCloneAndMove(move), st.alpha, st.beta, st);
			
			//Recursively calls Beta to go to the next level on state
			state = Beta(state, depth+1);
			
			
			if (st == root)
				System.out.println(state.utility);
			
			
			//If we've found a state with better utility, update the current best
			if (state.utility > best){
			
				best = state.utility;
				st.alpha = state.utility;
				bestmove = state.move;
				
			}
			
			//If alpha is better than beta, break
			if (st.alpha > st.beta){
				break;
			}
			
			
		}
		
		
		if (st == root){
			st.move = bestmove;
		}
		
		
		st.utility = best;
		
		return st;
		
	}
	
	public ABState Beta(ABState st, int depth){
		if(st.board.isWinningState() || depth>=maxDepth){
			return st;
		}
		
		List<Move> moves;
		if (st.parent!=null && st.parent.parent != null && st.parent.parent.parent!=null && st.parent.parent.parent.parent!=null ){

			moves = st.board.allPossibleMoves(st.parent.parent.parent.parent.move);
		}
		else{
			moves = st.board.allPossibleMoves();
		}
		
		Move bestmove = st.move;
		int best = st.beta;
		for (Move move: moves){
			
			// Skip branch where the next move from the root is a repeated move.
			if(Controller.moveList.size() > 4 ){
				
				int oldMoveID = Controller.moveList.get(Controller.turnCounter - 5).toMove.ID;
				int newMoveID =  move.toMove.ID;
				// Check if root and move unit ID are the same.
				if(st == root && oldMoveID == newMoveID){
					// Check for same source.
					if (Controller.moveList.get(Controller.turnCounter - 5).toMove.x == move.toMove.x && Controller.moveList.get(Controller.turnCounter - 5).toMove.y == move.toMove.y) {
						// Check for same destination.
						if (Controller.moveList.get(Controller.turnCounter - 5).x == move.x && Controller.moveList.get(Controller.turnCounter - 5).y == move.y) {
							continue;
						}
					}
				}
			}
			
			if( move.toMove.type != UnitType.DRAGON){
				continue;
			}
			
			ABState state = new ABState(move, st.board.deepCloneAndMove(move), st.alpha, st.beta, st);
			
			count++;
			
			state = Alpha(state, depth+1);
			
			if (st == root)
				System.out.println(state.utility);

			if (state.utility<best){
				best = state.utility;
				st.beta = state.utility;
				bestmove = state.move;

			}
			
			if (st.alpha>=st.beta){
				break;
			}
			
			
		}
		if (st == root){
			st.move = bestmove;
		}
		st.utility = best;
		
		return st;
		
	}
	
	public static void main(String[] args){
		Board board = new Board();
		board.Initialize();
		board.DisplayBoard();
		
		AlphaBetaTree tree = new AlphaBetaTree(board, Controller.maxDepth, false);
		

		System.out.println(tree.root.utility);
		System.out.println("Looked at :"+ tree.count + " Nodes");
		board.Move(tree.root.move);
		board.DisplayBoard();
		
	}
}
