/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.19 U
*
* Provides the Logic of a Smart Strategy for Battleship.
*/

package edu.utep.cs.cs4330.battleship;

public class Smart {
    /* Knowledge of where was already a hit or was a miss */
    private boolean hit[][];
    private boolean hadShip[][];
    private Place previous;
    private Ship[] sunk;

    public Smart(int size, int ships) {
        sunk = new Ship[ships];
        previous = null;
        hit = new boolean[size][size];
        hadShip = new boolean[size][size];
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

    public Place chooseHit() {
        /* Finds the X and Y- coordinates to Hit using a goal-winning Strategy */
        Place place = new Place();
        int startX, startY;
        if ( previous==null ) //randomize the first hit
            startX = startY = new java.util.Random().nextInt(hit.length);
        else {
            startX = previous.getX();
            startY = previous.getY();
        }
        for ( int i=startX ; i<hit.length ; i++ )
            for ( int j=startY ; j<hit[i].length ; j++ ) {
                if ( hit[i][j] && hadShip[i][j] ) {
                    //TODO the smart choose should be implemented
                    //check if this ship has already been sunk
                    //if ( !sunkShip(i,j) )
                        //return findPlaceToSink(i,j);
                    //if already sunk, choose another place
                    //find the nearest place if it has not been sunk
                }
                place.setIndex(i,j);
            }
        return previous = new Place(place.getX(),place.getY());
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