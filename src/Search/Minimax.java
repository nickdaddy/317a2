////////////////////////////////////////////////////////
//
// file:        Minimax.java
// Author:      Michael C. Horsch
//              Department of Computer Science
//              University of Saskatchewan
//
// History:     
//              Created:        2007/09/19
//  
// Notice:      This code is provided without warranty
//              for any purpose except to explore
//              search techniques in the study of AI.
//              Please do not distribute outside the class!
// 
////////////////////////////////////////////////////////

package Search;

import java.util.LinkedList;
import java.util.Iterator;

public class Minimax extends GameTreeSearch {

  public Game m_game;

  public Minimax(Game g) {
    m_game = g;
  }


  protected GameTreeNode MinimaxValue(GameTreeNode s, boolean MaxStarts) { 
    
    if (MaxStarts) {
      return MaxValue(s);
    }
    else {
      return MinValue(s);
    }
  }


  // the two mutually recursive functions
  // only difference is the way the "best" successor
  // is determined

  private GameTreeNode MaxValue(GameTreeNode s) {

    m_countNodesVisited++;
    if (m_game.TerminalState(s.m_state)) {
      s.m_value = m_game.Utility(s.m_state);
      return s;
    }

    LinkedList<GameState> successors = m_game.Successors(s.m_state);
    GameTreeNode best = null;
    double bestValue = Double.NEGATIVE_INFINITY;
    Iterator<GameState> it = successors.iterator();

    while (it.hasNext()) {
      GameState current = it.next();
      GameTreeNode curr = new GameTreeNode(current,0,s.m_depth+1);
      GameTreeNode n = MinValue(curr);
      if (n.m_value > bestValue) {
	bestValue = n.m_value;
	best = curr;
	best.m_value = bestValue;
      }
    }
    return best;
  }

  private GameTreeNode MinValue(GameTreeNode s) {

    m_countNodesVisited++;
    
    if (m_game.TerminalState(s.m_state)) {
      s.m_value = m_game.Utility(s.m_state);
      return s;
    }

    LinkedList<GameState> successors = m_game.Successors(s.m_state);
    GameTreeNode best = null;
    double bestValue = Double.POSITIVE_INFINITY;
    Iterator<GameState> it = successors.iterator();

    while (it.hasNext()) {
      GameState current = it.next();
      GameTreeNode curr = new GameTreeNode(current,0,s.m_depth+1);
      GameTreeNode n = MaxValue(curr);
      if (n.m_value < bestValue) {
	bestValue = n.m_value;
	best = curr;
	best.m_value = bestValue;
      }
    }
    return best;
  }

}

// eof
