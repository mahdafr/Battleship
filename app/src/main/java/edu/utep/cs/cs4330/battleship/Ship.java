/*
 * @author Mahdokht Afravi
 * @created 03.05 U
 * @modified 03.18 S
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
    /* For determining whether this Ship is sunk */
    private Place[] in;

    public Ship(int i) {
        name = chooseName(i);
        length = chooseSize(i);
        if ( name.equalsIgnoreCase("NOT A SHIP") || length<0 )
            System.out.println("CAN'T MAKE SHIP: " + i);
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

    /* Sets the start index of the Ship's place
    public void setStart(int x, int y) {
        startX = x;
        startY = y;
    }*/

    /* Accessors */
    public int getLength() {
        return length;
    }
    public int getX() {
        return in[0].getX(); //startX;
    }
    public int getY() {
        return in[0].getY(); //startY;
    }
    public String getName() {
        return name;
    }
    public int getSize() {
        return length;
    }
    public boolean sunk() {
        return sunk;
    }

    /* Sink this Ship */
    public void sink() {
        sunk = true;
    }

    /* If TRUE, the direction is vertical */
    public void setDirection(boolean dir) {
        direction = dir;
    }

    /* Returns TRUE if the direction is vertical */
    public boolean isVertical() {
        return direction;
    }

    /* Flips the orientation of the Ship's direction */
    public void rotate() {
        direction = !direction;
    }

    /* Gets where this Ship is on in the Board's Places */
    public Place[] inPlaces() {
        return in;
    }

    /* Changes the Places this Ship is in */
    public void setPlaces(Place[] p) {
        in = p;
    }

    /* Checks if this Ship occupies this index(x,y) */
    public boolean isAt(int x, int y) {
    	//System.out.println("in ship " +name);
        for ( int i=0 ; i<in.length ; i++ ) {
        	//System.out.println("place: " +in[i].getX()+ "," +in[i].getY());
            if ( in[i].getX()==x && in[i].getY()==y )
                return true;
        }
        return false;
    }
}