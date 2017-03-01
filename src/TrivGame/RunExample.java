package TrivGame;

////////////////////////////////////////////////////////
//
// file:	RunExample.java
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

import java.util.Random;

public class RunExample {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.out.println("usage: java RunExample <e> <a>\n"
	  + "  where <e> is the example and <a> is the algorithm");
      System.exit(0);
    }

    int argc = 0;
    int ex = Integer.parseInt(args[argc++]);
    int alg = Integer.parseInt(args[argc++]);

    Game game;
    GameTreeSearch searcher;

    switch (ex) {
      case 1:
      default:
	game = Example1.createExample();
	break;
      case 2:
	game = Example2.createExample();
	break;
      case 3:
	game = Example3.createExample();
	break;
      case 4:
	game = Example4.createExample();
	break;
    }

    switch (alg) {
      case 1: 
      default: 
	searcher = new Minimax(game);
	break;
    /**  case 2: 
	searcher = new AlphaBeta(game);
	break;**/
    }

    GameState move = searcher.Search(game.Initialize(),true);

    System.out.println("next state: ");
    move.display();
  }
}
// eof
