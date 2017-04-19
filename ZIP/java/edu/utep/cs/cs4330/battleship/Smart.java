/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.19 U
*
* Provides the Logic of a Smart Strategy for Battleship.
*/

package edu.utep.cs.cs4330.battleship;

import android.util.Log;

public class Smart {
    /* Knowledge of where was already a hit or was a miss */
    private boolean hit[][];
    private boolean hadShip[][];
    private Place previous;
    private Ship[] sunk;

    public Smart(int size, int ships) {
        sunk = new Ship[ships];
        hit = new boolean[size][size];
        hadShip = new boolean[size][size];
        previous = randomize();
        clearMemory();
    }

    public void clearMemory() {
        /* Erases what is known about Ships hit and hits in Board */
        clearShips();
        clearBoard();
    }

    public void clearShips() {
        /* Clear out the memory of Ships sunk */
        for ( int i=0 ; i<sunk.length ; i++ )
            sunk[i] = new Ship(i);
    }

    public void clearBoard() {
        /* Clear out the hits marked on the Board */
        for ( int i=0 ; i<hit.length ; i++ )
            for ( int j=0 ; j<hit[i].length ; j++ ) {
                hit[i][j] = false;
                hadShip[i][j] = false;
            }
    }
    
    public void hitGood() {
    	hadShip[previous.getX()][previous.getY()] = true;
    }

    public Place chooseHit() {
        /* Finds the X and Y- coordinates to Hit using a goal-winning Strategy */
        int startX, startY;
        startX = previous.getX();
        startY = previous.getY();
        Log.d("SMART","previous @ " +startX+ ", " +startY);
        if ( hadShip[startX][startY] ) {
            // can go south
            if ( possible(startX+1,startY) && !hit[startX+1][startY] )
                return previous = new Place(startX+1,startY);
            //can go north
            if ( possible(startX-1,startY) && !hit[startX-1][startY] )
                return previous = new Place(startX-1,startY);
            // can go east
            if ( possible(startX,startY+1) && !hit[startX][startY+1] )
                return previous = new Place(startX,startY);
            //can go north
            if ( possible(startX,startY-1) && !hit[startX][startY-1] )
                return previous = new Place(startX,startY-1);
        }
        return randomize();
    }
    private boolean possible(int x, int y) {
    	Log.d("SMART","checking if " +x+ ", " +y+ " be gucci?");
        if ( x<0 || y<0 ) //out of bounds
            return false;
        if ( x>=hit.length || y>=hit.length ) //out of bounds
            return false;
        if ( hit[x][y] ) //already hit
            return false;
        return true;
    }
    private Place randomize() {
    	//System.out.println("randomizing place");
    	int startX = new java.util.Random().nextInt(hit.length);
        int startY = new java.util.Random().nextInt(hit.length);
        return previous = new Place(startX,startY);
    }
    private boolean sunkShip(int x, int y) {
        /* Checks if any of the known Ships have been sunk */
        for ( int i=0 ; i<sunk.length ; i++ )
            ;//if ( sunk[i].sunk() )
        return false;
    }
    private boolean hasBeenSunk(int x, int y) {
        /* Checks to left, right, up, and down if Ship has been discovered */
        for ( int i=0 ; i<sunk.length ; i++ )
            if ( sunk[i].getX()==x && sunk[i].getY()==y )
                ;
        return false;
    }
}