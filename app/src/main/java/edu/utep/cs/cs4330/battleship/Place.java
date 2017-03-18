/*
 * @author Mahdokht Afravi
 * @created 03.05 U
 * @modified 03.05 U
 *
 * This class models the Places at each index of a 2D
 * board game.
 */

package edu.utep.cs.cs4330.battleship;

public class Place {
    private boolean hasShip;
    private boolean isHit;
    private int y;
    private int x;

    public Place() {
        hasShip = false;
    }

    /* Mutators: establishing index of this place */
    public void setIndex(int i, int j) {
        x = i;
        y = j;
    }

    /* The indices of the current Place */
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    /* Returns false if hit was a miss (no ship here) */
    public boolean hit() {
        isHit = true;
        if ( hasShip )
            return true;
        return false;
    }

    /* Add a ship to this place */
    public void addShip() {
        hasShip = true;
    }

    /* Return whether this Place was hit already */
    public boolean isHit() {
        return isHit;
    }

    /* Return whether this Place has a Ship or not */
    public boolean hasShip() {
        return hasShip;
    }
}