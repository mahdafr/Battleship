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
    /* @author Mahdokht Afravi
     * @modified 03.05 U
     */

    public boolean placeShip(Ship s, int x, int y, boolean dir) {
        /* Sets each Place starting from (x,y) to contain a Ship
         * depending on the dir (ship.isVertical()) of the Ship */
        if ( dir ) { //vertical placement
            for ( int i=x ; i<x+s.getSize() ; i++ )
                board[i][y].addShip();
        } else { //horizontal placement
            for ( int j=y ; j<y+s.getSize() ; j++ )
                board[x][j].addShip();
        }
        return true;
    }

    public void hit(int x, int y) {
        /* Mark this index as hit */
        board[x][y].hit();
    }

    public void hit(Place place) {
        /* Mark this Place as hit */
        place.hit();
    }

    public Place at(int x, int y) {
        /* Get the place at this index (X,Y) */
        return board[x][y];
    }

    public boolean isHit(int x, int y) {
        /* Check if this Place in the Board is hit */
        return board[x][y].isHit();
    }
}
