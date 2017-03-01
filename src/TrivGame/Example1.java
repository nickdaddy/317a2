package TrivGame;

////////////////////////////////////////////////////////
//
// file:	Example1.java
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


/**  A simple example of a game with the following game tree.
 * The nodes are the state numbers, and the numbers in [] are
 * the utility values for the terminal states directly above them.
 *
 *                 0
 *               / | \
 *        +-----+  |  +------+
 *       /         |          \
 *      1          2           3
 *    / | \      / | \       / | \
 *   4  5  6    7  8  9    10 11  12
 * [3][12][8]  [2][4][6]  [14][5] [2]
 * 
 * 
 * The value of this game is 3, if Max moves first
 * The value of this game is 6, if Min moves first
 * 
 */


import Search.*;

public class Example1 {

  static int INITIAL = 0;

  static int[][] SUCCESSORS =
  {
    {1,2,3}    
   ,{4,5,6}    
   ,{7,8,9}    
   ,{10,11,12} 
  };
  static int TERMINAL = 4;

    static double[] UTILITY =
      //4   5   6   7   8   9  10  11  12
      { 3 ,12 , 8 , 2 , 4 , 6 ,14 , 5 , 2 };


    static public TrivGame createExample() {
      return new TrivGame(Example1.INITIAL,
	                  Example1.SUCCESSORS,
		          Example1.UTILITY,
		          Example1.TERMINAL);
    }

}
// eof
