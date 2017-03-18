/*
 * @author Mahdokht Afravi
 * @created 03.05 U
 * @modified 03.05 U
 *
 * Models the Ships in the 2D Battleship board game.
 */

package edu.utep.cs.cs4330.battleship;

public class Ship {
    /* Each Ship has a name, a size, and direction */
    private String name;
    private int length;
    private boolean direction;
    /* Has this Battleship been sunk? */
    private boolean sunk;

    public Ship(int i) {
        name = chooseName(i);
        length = chooseSize(i);
        if ( name.equalsIgnoreCase("NOT A SHIP") || length<0 )
            System.out.println("CAN'T MAKE SHIP" + i);
        sunk = false;
    }

    /* Determine the name of each Ship in Battleship */
    private String chooseName(int i){
        switch ( i ) {
            case 0:
                return "Aircraft Carrier";
            case 1:
                return "Battleship";
            case 2:
                return "Frigate";
            case 3:
                return "Submarine";
            case 4:
                return "Minesweeper";
            default:
                System.out.println("NOT A SHIP");
        }
        return "NOT A SHIP";
    }

    /* Determine the size of each Ship in Battleship */
    private int chooseSize(int i) {
        switch ( i ) {
            case 0:
                return 5;
            case 1:
                return 4;
            case 2:
                return 3;
            case 3:
                return 3;
            case 4:
                return 2;
            default:
                System.out.println("NOT A SHIP");
        }
        return -1;
    }

    /* Sink this Ship */
    public void sink() {
        sunk = true;
    }

    /* Get the name of this Ship */
    public String getName() {
        return name;
    }

    /* Get the size of this Ship */
    public int getSize() {
        return length;
    }

    /* Is this ship sunk? */
    public boolean sunk() {
        return sunk;
    }

    /* If TRUE, the direction is vertical */
    public void setDirection(boolean vertical) {
        direction = vertical;
    }

    /* Returns TRUE if the direction is vertical */
    public boolean isVertical() {
        return direction;
    }
}