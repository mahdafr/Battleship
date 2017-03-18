/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.05 U
*
* Controls the Players of the 2D Battleship Game.
* If the Player is a Computer, it plays by a
*   user-chosen Strategy.
*/

package edu.utep.cs.cs4330.battleship;

public class Player {
    private String name;
    private int wins;
    private Strategy strategy;

    public Player() {
        wins = 0;
    }

    public void setName(String n) {
        name = n;
    }

    public void won() {
        wins++;
    }

    public void setStrategy(boolean smartPlay) {
        strategy = new Strategy(smartPlay);
    }
}