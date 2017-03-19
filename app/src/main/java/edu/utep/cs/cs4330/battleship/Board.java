/*
 * @modified Mahdokht Afravi on 03.05 U
 *
 * This class models the Board of a 2D Battleship
 * board game.
 */

package edu.utep.cs.cs4330.battleship;

/**
 * A game board consisting of <code>size</code> * <code>size</code> places
 * where battleships can be placed. A place of the board is denoted
 * by a pair of 0-based indices (x, y), where x is a column index
 * and y is a row index. A place of the board can be shot at, resulting
 * in either a hit or miss.
 */
public class Board {
    private Place[][] board; //@modified Mahdokht Afravi on 03.05 U

    /**
     * Size of this board. This board has
     * <code>size*size </code> places.
     */
    private final int size;

    /** Create a new board of the given size. */
    public Board(int size) {
        this.size = size;
        board = new Place[size][size];
        numHits = 0; //@modified Mahdokht Afravi on 03.05 U
    }

    /** Return the size of this board. */
    public int size() {
        return size;
    }

    // Suggestions:
    // 1. Consider using the Observer design pattern so that a client,
    //    say a BoardView, can observe changes on a board, e.g.,
    //    hitting a place, sinking a ship, and game over.
    //
    // 2. Introduce methods including the following:
    //    public boolean placeShip(Ship ship, int x, int y, boolean dir)
    //    public void hit(Place place)
    //    public Place at(int x, int y)
    //    public Place[] places()
    //    public int numOfShots()
    //    public boolean isGameOver()
    //    ...?
    /*
     * @author Mahdokht Afravi
     * @modified 03.05 U
     */
    private int numHits;

    public boolean placeShip(Ship s, int x, int y, boolean dir) {
        /* Sets each Place starting from (x,y) to contain a Ship
         * depending on the dir (ship.isVertical()) of the Ship */
        if ( dir ) { //vertical placement
            if ( canPlace(s,x,y) ) {
                for (int i = x; i < x + s.getSize(); i++)
                    board[i][y].addShip();
            } else return false;
        } else { //horizontal placement
            if ( canPlace(s,x,y) ) {
                for (int j = y; j < y + s.getSize(); j++)
                    board[x][j].addShip();
            } else return false;
        }
        return true;
    }

    private boolean canPlace(Ship ship, int x, int y) {
        /* Checks if a Ship can be placed starting at this index */
        //if initial x or y is out of bounds
        if ( x<0 || y<0 || x>size || y>size )
            return false;
        Place start = at(x,y);
        if ( ship.isVertical() ) {
            //if the ship is vertical can we place it here?
            x += ship.getSize(); //get the end
            if ( x>size )
                return false;
            //are there ships within these bounds of Places?
            for ( int i=start.getX() ; i<x ; i++ )
                if ( at(i,y).hasShip() )
                    return false;
        } else {
            //if the ship is horizontal, can we place it here?
            y += ship.getSize(); //get the end
            if ( y>size )
                return false;
            //are there ships within these bounds of Places?
            for ( int j=start.getY() ; j<y ; j++ )
                if ( at(x,j).hasShip() )
                    return false;
        }
        return true;
    }

    public boolean hit(int x, int y) {
        /* Mark this index as hit */
        return board[x][y].hit();
    }

    public boolean hit(Place place) {
        /* Mark this Place as hit */
        return hit(place.getX(),place.getY());
    }

    public Place at(int x, int y) {
        /* Get the place at this index (X,Y) */
        return board[x][y];
    }

    public boolean isHit(int x, int y) {
        /* Check if this Place in the Board is already hit */
        return board[x][y].isHit();
    }

    public boolean hasShip(int x, int y) {
        /* Check if this Place in the Board has a Ship */
        return board[x][y].hasShip();
    }

    public boolean isGameOver() {
        /* Returns TRUE if all spaces are hit */
        return numHits==size*size;
    }

    public boolean shipsSunk() {
        /* Returns TRUE if all Places with Ships have been hit. */
        for ( int i=0 ; i<size ; i++ )
            for ( int j=0 ; j<size ; j++ )
                if ( board[i][j].hasShip() && !board[i][j].isHit() )
                    return false;
        return true;
    }
}
