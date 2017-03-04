package TicTacToe;

////////////////////////////////////////////////////////
//
// file:	TicTacToe.java
// Author: 	Michael C. Horsch
//		Department of Computer Science
// 		University of Saskatchewan
//
// History:	
// 		Created:	2007/09/19
//
// Notice:      This code is provided without warranty
//              for any purpose except to explore
//              search techniques in the study of AI.
//              Please do not distribute outside the class!
// 
////////////////////////////////////////////////////////


import Search.*;
import java.util.LinkedList;


public class TicTacToe implements Game {

  static final char Mark_X   = 'X';
  static final char Mark_O   = 'O';
  static final char Unmarked = ' ';

  class TTTState implements GameState {

    char[][] m_board;  // the basic state of the board
    char m_justMoved;  // who just moved
    int m_movesMade;   // a quick count to establish a draw
    boolean m_won;     // remember if it was a win or not

    // basic constructor
    TTTState() {
      m_justMoved = TicTacToe.Mark_O;
      m_movesMade = 0;
      m_won = false;

      m_board = new char[3][3];
      for (int i = 0; i < 3; i++) {
	for (int j = 0; j < 3; j++) {
	  m_board[i][j] = TicTacToe.Unmarked;
	}
      }
    }

    // copy constructor, but alternate who moves, and add to the count
    TTTState(TTTState prev) {
      m_justMoved = alternate(prev.m_justMoved);
      m_movesMade = prev.m_movesMade+1;
      m_won = false;

      m_board = new char[3][3];
      for (int i = 0; i < 3; i++) {
	for (int j = 0; j < 3; j++) {
	  m_board[i][j] = prev.m_board[i][j];
	}
      }
    }

    /** Does this state equal a given other state?
     * */
    public boolean equals(GameState o) {
      TTTState s = (TTTState) o;
      if (m_movesMade != s.m_movesMade) {
	return false;
      }
      for (int i = 0; i < 3; i++) {
	for (int j = 0; j < 3; j++) {
	  if (m_board[i][j] != s.m_board[i][j]) {
	    return false;
	  }
	}
      }
      return true;
    }

    /** Display the game state.
     * */
    public void display() {
      System.out.println(m_board[0][0] + "|" + m_board[0][1] + "|" + m_board[0][2]);
      System.out.println("-+-+-");
      System.out.println(m_board[1][0] + "|" + m_board[1][1] + "|" + m_board[1][2]);
      System.out.println("-+-+-");
      System.out.println(m_board[2][0] + "|" + m_board[2][1] + "|" + m_board[2][2]);
      System.out.println((m_justMoved == TicTacToe.Mark_X ? "O" : "X") + " to move.");
    }

  }

  // return an initial state; nothing else 
  // about the game needs to be initialized
  public GameState Initialize() {
    return new TTTState();
  }


  /** Recognize a terminal state (win, lose, draw, or cut-off).
   *
   * @param GameState state
   *
   *  GameTree.Search() uses this to know when to stop.
   * */
  public boolean TerminalState(GameState s) {
    TTTState t = (TTTState) s;
    if (win(t)) {
      t.m_won = true;  // remember if there was a winner; for use in Utility()
      return true;
    }
    else if (full(t)) {
      return true;    // no winner, but the game's over
    }
    else {
      return false;
    }
  }

  /** Return a list of GameStates that are 
   *  immediate successors of the given GameState.
   *  */
  public LinkedList<GameState>   Successors(GameState p) {
    TTTState t = (TTTState) p;
    LinkedList<GameState> successors = new LinkedList<GameState>();

    // look at each square; if it's empty, 
    // copy it, and put a mark in the empty square

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
	if (t.m_board[i][j] == TicTacToe.Unmarked) {
	  TTTState u = new TTTState(t);
	  u.m_board[i][j] = u.m_justMoved;
	  successors.addLast(u);
	}
      }
    }
    return successors;
  }


  /** Return the final value of the given terminal GameState.
   *
   * @param GameState state
   *
   * Worth noting that this method assumes that TerminalState()
   * halts the game at the right time.  If a win is detected
   * immediately after the winning game, then a win is a win
   * for the player who just moved.  This avoids doing twice 
   * the work of win() to detect if a win in TerminalState()
   * followed by checking win() here again to see who won.
   *
   * */

  public double Utility(GameState s) {
    TTTState t = (TTTState) s;
    if (!t.m_won) {  // a win was recorded in TerminalState()
      return 0;
    }
    else if (t.m_justMoved == TicTacToe.Mark_X) {
      return 1;
    }
    else {
      return -1;
    }
  }

  // If not a win, then if the board is full, it's a draw
  // assumes that a win() is checked first
  boolean full(TTTState s) {
    boolean draw = s.m_movesMade == 9;
    return draw;
  }

  // look for a win by the player who just moved
  boolean win(TTTState s) {
    return threeInARow(s) || threeInAColumn(s) || threeDiagonally(s);
  }

  boolean threeInARow(TTTState s) {
    for (int i = 0; i < 3 ; i++) {
      if (
	  s.m_board[i][0] == s.m_justMoved &&
	  s.m_board[i][1] == s.m_justMoved &&
	  s.m_board[i][2] == s.m_justMoved
	  ) {
	return true;
      }
    }
    return false;
  }

  boolean threeInAColumn(TTTState s ) {
    for (int j = 0; j < 3 ; j++) {
      if (
	  s.m_board[0][j] == s.m_justMoved &&
	  s.m_board[1][j] == s.m_justMoved &&
	  s.m_board[2][j] == s.m_justMoved
	  ) {
	return true;
      }
    }
    return false;
  }

  boolean threeDiagonally(TTTState s ) {
    if (s.m_board[1][1] != s.m_justMoved) {
      return false;  // a quick test
    }
    if (s.m_board[0][0] == s.m_justMoved && 
	s.m_board[1][1] == s.m_justMoved &&
	s.m_board[2][2] == s.m_justMoved) {
      return true;
    }
    if (s.m_board[0][2] == s.m_justMoved && 
	s.m_board[1][1] == s.m_justMoved &&
	s.m_board[2][0] == s.m_justMoved) {
      return true;
    }
    return false;
  }

  private char alternate(char move) {
    if (move == TicTacToe.Mark_X) 
      return TicTacToe.Mark_O;
    else
      return TicTacToe.Mark_X;
  }

  public static void main(String[] args) {
    TicTacToe game = new TicTacToe();
    
    GameTreeSearch searcher;
    switch (Integer.parseInt(args[0])) {
      case 1:
      default:
	searcher = new Minimax(game);
	break;
    //  case 2:
	//searcher = new AlphaBeta(game);
	//break;
    }
    GameState move = searcher.Search(game.Initialize(),true);  // Max to move first

    System.out.println("next state: ");
    move.display();
  }
}
// eof
