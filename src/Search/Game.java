////////////////////////////////////////////////////////
//
// file:	Game.java
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

package Search;

import java.util.LinkedList;

/** This interface indicates the functionality required of any
 *  search problem implementation for use with SearchGeneric.
 *
 */

public interface Game {

  /** Initialize the problem (domain dependent);
   *  initialization is not always necessary, but we 
   *  allow for the possibility.
   *  */
  public GameState Initialize();

  /** Recognize a terminal state (win, lose, draw, or cut-off).
   *
   * @param GameState state
   *
   *  GameTree.Search() uses this to know when to stop.
   * */
  public boolean TerminalState(GameState s);

  /** Return a list of GameStates that are 
   *  immediate successors of the given GameState.
   *  */
  public LinkedList<GameState>   Successors(GameState p);

  /** Return the final value of the given GameState.
   *
   * @param GameState state
   *
   * */
  public double Utility(GameState s);

}
// eof
