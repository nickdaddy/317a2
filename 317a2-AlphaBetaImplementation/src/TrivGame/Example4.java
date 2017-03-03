package TrivGame;

////////////////////////////////////////////////////////
//
// file:	Example4.java
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
 */

import Search.*;

public class Example4 {

    static int INITIAL = 0;
    static int[][] SUCCESSORS =
    {           // successors of: 
      {1,2,3,4}     // 0
     ,{5,6,7}     // 1  
     ,{8,9}     // 2
     ,{10,11,12}    // 3
     ,{13,14}   // 4
    };
    static int TERMINAL = 5;

    static double[] UTILITY =
      { 3, 1, 5, 2, 0, 7, 1, 5, 4, 8};

    static public TrivGame createExample() {
      return new TrivGame(Example4.INITIAL,
	                  Example4.SUCCESSORS,
		          Example4.UTILITY,
		          Example4.TERMINAL);
    }

}
// eof
