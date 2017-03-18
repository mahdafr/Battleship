/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.05 U
*
* Provides the Logic of a Smart Strategy for Battleship.
*/

package edu.utep.cs.cs4330.battleship;

public class Smart {
    private boolean board[][];
    private int previousX;
    private int previousY;
    private boolean[] shipSunk;

    public Smart(int size, int ships) {
        shipSunk = new boolean[ships];
        previousX = -1;
        previousY = -1;
        board = new boolean[size][size];
        clearMemory();
    }

    public void clearMemory() {
        /* Erases what is known about Ships hit and hits in Board */
        clearShips();
        clearBoard();
    }

    public void clearShips() {
        /* Clear out the memory of Ships sunk */
        for ( int i=0 ; i<shipSunk.length ; i++ )
            shipSunk[i] = false;
    }

    public void clearBoard() {
        /* Clear out the hits marked on the Board */
        for ( int i=0 ; i<board.length ; i++ )
            for ( int j=0 ; j<board[i].length ; j++ )
                board[i][j] = false;
    }

    public int[] chooseHit() {
        /* Finds the X-coordinate to Hit */
        for ( int i=0 ; i<board.length ; i++ )
            for ( int j=0 ; j<board[i].length ; j++ )
                if ( board[i][j] &&
    }
}