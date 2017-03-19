/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.18 S
*
* Controls the Players of the 2D Battleship Game.
* If the Player is a Computer, it plays by a
*   goal-winning or random Strategy for Battleship,
*   as determined by the user via UI.
*/

package edu.utep.cs.cs4330.battleship;

public class Player {
    /* This Player's name, number of wins, and number of ships placed so far */
    private String name;
    private int wins;
    private int shipsPlaced;
    /* The computer Player's Strategy */
    private Strategy strategy;
    /* This Player's Board and Ships */
    private Board board;
    private Ship[] ship;
    /* The number of Ships and the Size of the Board */
    private final int SHIPS = 5;
    private final int SIZE = 10;
    /* Track if it is this Player's turn */
    private boolean turn;
    /* Tracking knowledge of what was hit in the opposing Player's Board */
    private Board opponent;

    public Player() {
        wins = 0;
        shipsPlaced = 0;
        initShips();
        initBoard();
        turn = false;
    }

    private void initShips() {
        /* Create the Ships for the game */
        ship = new Ship[SHIPS];
        for ( int i=0 ; i<SHIPS ; i++ )
            ship[i] = new Ship(i);
    }

    private void initBoard() {
        /* Create the Board for the Battleship Game */
        board = new Board(SIZE);
    }

    public void setStrategy(boolean smartPlay) {
        /* Gives a Strategy to this computer Player */
        strategy = new Strategy(smartPlay);
    }

    public void setName(String n) {
        /* Sets the name of this Player */
        name = n;
    }

    public void won() {
        /* This Player has won this game */
        wins++;
    }

    public Boolean lostGame() {
        /* This Player lost the game if all of his/her Ships are sunk */
        for ( int i=0 ; i<SHIPS ; i++ )
            if ( !ship[i].sunk() )
                return false;
        return true;
    }

    public Board placeShips() {
        /* The strategy for the Player (computer) places the ships. */
        return strategy.placeShip(ship,board);
    }

    public Board placeTheShips(Ship s, int x, int y) {
        /* User chose to place a ship at this index */
        board.placeShip(s,x,y,s.isVertical());
        shipsPlaced++;
        return board;
    }

    public Board board() {
        /* Get this Player's board */
        return board;
    }

    public void rotateShip(Ship s, boolean rotate) {
        /* Rotate the direction of the Ship to place */
        s.rotate();
    }

    public boolean canHit() {
        /* Is it this Player's turn to hit? */
        return turn;
    }

    public boolean hit(int x, int y) {
        /* The opposing Player made a hit on this Player's Board.
         * Returns TRUE if the hit was not a miss. */
        return board.hit(x,y);
    }

    public void switchTurn() {
        /* Either start or stop this Player's turn */
        turn = !turn;
    }

    public boolean chooseHit() {
        /* For the computer Player, choose a Place to hit in the Board.
         * Returns TRUE if the hit was not a miss. */
        return board.hit(strategy.chooseHit(board));
    }

    public boolean hasWon() {
        /* Has this Player won the game? */
        return board.shipsSunk();
    }

    public boolean isGameOver() {
        /* Has this Player hit the entire Board? */
        return board.isGameOver();
    }
}