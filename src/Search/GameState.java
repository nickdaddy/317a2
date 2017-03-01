////////////////////////////////////////////////////////
//
// file:	GameState.java
// Author: 	Michael C. Horsch
//		Department of Computer Science
// 		University of Saskatchewan
// History:
// 		Created:	2007/09/06
//              (adapted from CMPT.ThreeOneSeven.Search.Core.SearchState)
//
// Notice:      This code is provided without warranty
//              for any purpose except to explore
//              search techniques in the study of AI.
//              Please do not distribute outside the class!
// 
////////////////////////////////////////////////////////

package Search;


/** This interface indicates the functionality required of any
 *  search state for use with GameTreeSearch.
 *
 */

public interface GameState {

  /** Does this state equal a given other state?
   * */
  public boolean equals(GameState o);

  /** Display the search state.
   * */
  public void display();

}

// eof
