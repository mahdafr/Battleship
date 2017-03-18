/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.05 U
*
* Controls the behavior of the Battleship Game.
*/

package edu.utep.cs.cs4330.battleship;

public class Battleship {
    /* The Board in the Game */
    private Board board;
    /* The Ships in the Game */
    private final int SHIPS = 5;
    private Ship[] ship;
    /* The Players in the Game */
    private Player player1;
    private Player player2;
    /* Game's Board size */
    private int size;

    public Battleship(int s) {
        size = s;
        createShips();
        createBoard();
        createPlayers();
    }

    private void createShips() {
        /* Create the Ships for the Battleship Game */
        ship = new Ship[SHIPS];
        for ( int i=0 ; i<SHIPS ; i++ )
            ship[i] = new Ship(i);
    }

    private void createBoard() {
        /* Create the Board for the Battleship Game */
        board = new Board(size);
    }

    private void createPlayers() {
        /* Create the Players for the Battleship Game */
        player1 = new Player();
        player2 = new Player();
    }

    public void addSmartStrategy() {
        /* Add a Smart Strategy to the Computer Player */
        player2.setStrategy(true);
    }

    public void addRandomStrategy() {
        /* Add a Random Strategy to the Computer Player */
        player2.setStrategy(false);
    }

    public Board board() {
        /* Returns this Game's Board */
        return board;
    }

    public void setBoard(Board b) {
        /* Re-establish the Game's Board */
        board = b;
    }

    public Ship[] ships() {
        /* Returns this Game's Ships */
        return ship;
    }
}