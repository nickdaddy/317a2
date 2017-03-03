////////////////////////////////////////////////////////
//
// file:	BigGame.java
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

public interface BigGame extends Game {

  /** Return the estimate of final value of the given GameState.
   *
   * @param GameState state
   *
   * */
  public double Estimate(GameState s);

}
// eof
