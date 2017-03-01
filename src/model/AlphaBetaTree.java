package model;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import model.Unit.UnitType;

public class AlphaBetaTree {
	public ABState root;
	private int maxDepth;
	public int count;
	
	public AlphaBetaTree(Board board, int maxdepth, boolean kingsturn){
		root = new ABState(null, board, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
		if(st.board.isWinningState() || depth>=maxDepth){
			return st;
		}
		
		List<Move> moves = st.board.allPossibleMoves();
		
		Move bestmove = null;
		int best = st.alpha;
		for (Move move: moves){
			if( move.toMove.type== UnitType.DRAGON){
				continue;
			}
			count++;
			ABState state = new ABState(move, st.board.deepCloneAndMove(move), st.alpha, st.beta);
			
			state = Beta(state, depth+1);
			
			if (state.utility>best){
				best = state.utility;
				st.alpha = state.utility;
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
	
	public ABState Beta(ABState st, int depth){
		if(st.board.isWinningState() || depth>=maxDepth){
			return st;
		}
		
		List<Move> moves = st.board.allPossibleMoves();
		
		Move bestmove = null;
		int best = st.beta;
		for (Move move: moves){
			if( move.toMove.type!= UnitType.DRAGON){
				continue;
			}
			ABState state = new ABState(move, st.board.deepCloneAndMove(move), st.alpha, st.beta);
			count++;
			state = Alpha(state, depth+1);
			if (state.utility<best){
				best = state.utility;
				st.beta= state.utility;
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
		System.out.println("Looked at :"+ tree.count + "Nodes");
		board.Move(tree.root.move);
		board.DisplayBoard();
		
	}
}