////////////////////////////////////////////////////////
//
// file:	GameTreeSearch.java
// Author: 	Michael C. Horsch
//		Department of Computer Science
// 		University of Saskatchewan
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

import java.util.Iterator;
import java.util.LinkedList;

// basic storage for GameTreeSearch nodes
// We need to store:
//   - a GameState, 
//   - the value (if known) of the GameState

class GameTreeNode {
  protected GameState m_state;  
  protected double    m_value;  
  protected int       m_depth;  

  public GameTreeNode(GameState state, double value, int depth) {
    m_state = state;
    m_value = value;
    m_depth = depth;
  }
}


//////////////////////////////////////////////////////////
abstract public class GameTreeSearch {

  protected long m_countNodesVisited;
  
  // Build a search engine for the given game

  public GameTreeSearch() {
    m_countNodesVisited = 0;
  }

  public GameState Search(GameState s, boolean MaxMovesFirst) {
    
    m_countNodesVisited = 0;
    GameTreeNode n = MinimaxValue(new GameTreeNode(s,0,0), MaxMovesFirst);  
    System.out.println("Best value: " + n.m_value);
    System.out.println("Visited " + m_countNodesVisited + " nodes.");
    return n.m_state;
  }

  abstract protected GameTreeNode MinimaxValue(GameTreeNode s, boolean MaxMovesFirst);

}


// eof
