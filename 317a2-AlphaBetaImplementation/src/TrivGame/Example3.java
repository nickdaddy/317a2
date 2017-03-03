package TrivGame;

////////////////////////////////////////////////////////
//
// file:	Example3.java
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

public class Example3 {

    static int INITIAL = 0;
    static int[][] SUCCESSORS =
    {           // successors of: 
      {1,2,3}     // 0
     ,{4,5}     // 1  
     ,{6,7}     // 2
     ,{8,9}     // 3
     ,{10,11}    // 4
     ,{12}   // 5
     ,{13,14}   // 6
     ,{15,16}   // 7
     ,{17}   // 8
     ,{18,19}   // 9
    };
    static int TERMINAL = 10;

    static double[] UTILITY =
      { 3, 1, 5, 7, 0, 2, 1, 5, 1, 5 };

    static public TrivGame createExample() {
      return new TrivGame(Example3.INITIAL,
	                  Example3.SUCCESSORS,
		          Example3.UTILITY,
		          Example3.TERMINAL);
    }

}
// eof
