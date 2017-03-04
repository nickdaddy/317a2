package TrivGame;

////////////////////////////////////////////////////////
//
// file:	TrivGame.java
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

/** The class defines a "game" that can be expressed as an
 * explicit tree, for testing purposes.  The represetnation
 * allows one to identify a start state, the branching of the
 * tree (as a 2D array of integers) and the terminal nodes,
 * and their utilities.  It's not a wonderful design, but it
 * is good enough for testing.  
 *
 * Example: suppose we had a two player game in which
 * only Max gets to move.  
 *
 *    0
 *   / \
 *  1   2 
 * [7] [3] 
 *
 * (Okay, it's not much of a game, but it is very small.)
 *
 * The start state is 0
 * The successor array is {{1,2}}  - only one node has successors
 * The terminal states start at 1
 * The array of utilities of the terminal states is {7,3}
 *  
 * This is a simple way to specify game trees that you can construct
 * by hand, and are small enough to test and trace through.
 *
 * See the Example files for more examples.
 *
 */

public class TrivGame implements Game {

  // first, represent the states of the game

  class TrivState implements GameState {

    int m_state;

    TrivState(int state) {
      m_state = state;
    }

    /** Does this state equal a given other state?
     * */
    public boolean equals(GameState o) {
      TrivState s = (TrivState) o;
      return s.m_state == m_state;
    }

    /** Display the search state.
     * */
    public void display() {
      System.out.println("state_" + m_state);
    }

  }

  // data members for TrivGame

  int      m_INITIAL;    // the initial state, usually 0, but flexible
  int[][]  m_SUCCESSORS; // an array of arrays
  int      m_TERMINAL;   // the index of the first terminal state
                         // assumed that all following states are terminal
  double[] m_UTILITY;    // the array of utilities for terminal states
                         // the indexing starts at 0


  public TrivGame(int initial, int[][] successors, double[] utility, int terminal) {
    m_INITIAL = initial;
    m_SUCCESSORS = successors;
    m_UTILITY = utility;
    m_TERMINAL = terminal;
  }


  /** Nothing much to initialize, just return the initial state.
   * */

  public GameState Initialize() {
    return new TrivState(m_INITIAL);
  }


  /** Recognize a terminal state (win, lose, draw, or cut-off).
   *
   * @param GameState state
   *
   *  GameTree.Search() uses this to know when to stop.
   * */
  public boolean TerminalState(GameState s) {
    TrivState t = (TrivState) s;
    return t.m_state >= m_TERMINAL;
  }

  /** Return a list of GameStates that are 
   *  immediate successors of the given GameState.
   *  */
  public LinkedList<GameState>   Successors(GameState p) {
    TrivState t = (TrivState) p;
    LinkedList<GameState> successors = new LinkedList<GameState>();
    for (int s : m_SUCCESSORS[t.m_state]) {
      successors.addLast(new TrivState(s));
    }
    return successors;
  }

  /** Return the final value of the given terminal GameState.
   *
   * @param GameState state
   *
   * */
  public double Utility(GameState s) {
    TrivState t = (TrivState) s;
    System.out.println(m_UTILITY[t.m_state-m_TERMINAL]);
    return m_UTILITY[t.m_state-m_TERMINAL]; 
  }

}
// eof
