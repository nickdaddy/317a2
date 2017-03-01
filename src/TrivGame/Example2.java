package TrivGame;

////////////////////////////////////////////////////////
//
// file:	Example2.java
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
 *                                   0
 *                                  / \
 *                   +-------------+   +-------------+  
 *                  /                                 \
 *                 1                                   2
 *                / \                                 / \
 *           +---+   +---+                       +---+   +---+
 *          /             \                     /             \
 *         /               \                   /               \
 *        3                 4                 5                 6
 *      /    \            /   \             /   \             /   \
 *     /      \          /     \           /     \           /     \
 *    7        8        9       10       11       12       13       14
 *   / \      / \      / \      / \      / \      / \      / \      / \
 * 15   16  17   18  19   20  21   22  23   24  25   26  27   28  29   30
  [12]  [4] [2]  [8] [6]  [9] [5]  [8] [2] [13] [4] [11] [7]  [9] [9] [14]
 * 
 * The value of this game is 4, if Max moves first
 * The value of this game is 8, if Min moves first
 * 
 */

import Search.*;

public class Example2 {

    static int INITIAL = 0;
    static int[][] SUCCESSORS =
    {           // successors of: 
      {1,2}     // 0
     ,{3,4}     // 1  
     ,{5,6}     // 2
     ,{7,8}     // 3
     ,{9,10}    // 4
     ,{11,12}   // 5
     ,{13,14}   // 6
     ,{15,16}   // 7
     ,{17,18}   // 8
     ,{19,20}   // 9
     ,{21,22}   // 10
     ,{23,24}   // 11
     ,{25,26}   // 12
     ,{27,28}   // 13
     ,{29,30}   // 14
    };
    static int TERMINAL = 15;

    static double[] UTILITY =
      { 12, 4, 2, 8, 6, 9, 5, 8, 2, 13, 4, 11, 7, 9, 9, 14};

    static public TrivGame createExample() {
      return new TrivGame(Example2.INITIAL,
	                  Example2.SUCCESSORS,
		          Example2.UTILITY,
		          Example2.TERMINAL);
    }

}
// eof
