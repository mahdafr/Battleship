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
    /* The computer Player's Strategy */
    private Strategy strategy;
    /* This Player's Board and Ships */
    private Board board;
    private Ship[] ship;
    /* Track if it is this Player's turn */
    private boolean turn;
    /* Keeping track of the Ships sunk by and against this Player */
    private int shipsSunk;

    public Player(int board, int ships) {
        wins = 0;
        shipsSunk = 0;
        initShips(ships);
        initBoard(board);
        turn = false;
    }

    private void initShips(int ships) {
        /* Create the Ships for the game */
        ship = new Ship[ships];
        for ( int i=0 ; i<ships ; i++ )
            ship[i] = new Ship(i);
    }

    private void initBoard(int b) {
        /* Create the Board for the Battleship Game */
        board = new Board(b);
    }

    public void setStrategy(boolean smartPlay) {
        /* Gives a Strategy to this computer Player */
        strategy = new Strategy(smartPlay,board.size(),ship.length);
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
        for ( int i=0 ; i<ship.length ; i++ )
            if ( !ship[i].sunk() )
                return false;
        return true;
    }

    public void placeShips() {
        /* The strategy for the Player (computer) places the ships. */
        board = strategy.placeShip(ship,board);
    }

    public void placeShip(Ship s, int x, int y) {
        /* User chose to place a ship at this index */
        board.placeShip(s, x, y, s.isVertical());
    }

    public Board board() {
        /* Get this Player's board */
        return board;
    }

    public void rotateShip(int i) {
        /* Rotate the direction of the Ship to place */
        ship[i].rotate();
    }

    public boolean canHit() {
        /* Is it this Player's turn to hit? */
        return turn;
    }

    public boolean hit(int x, int y) {
        /* The opposing Player made a hit on this Player's Board.
         * Returns TRUE if the hit was not a miss. */
        if ( board.hit(x,y) ) { //was a ship in this Place of the Board
            //check if the ship was sunk
            for ( int i=0 ; i<ship.length ; i++ )
                if ( board.hasShipSunk(ship[i]) ) {
                    ship[i].sink();
                    shipsSunk++;
                }
            return true;
        }
        return false;
    }

    public void switchTurn() {
        /* Either start or stop this Player's turn */
        turn = !turn;
    }

    public boolean chooseHit() {
        /* For the computer Player, choose a Place to hit in the Board.
         * Returns TRUE if the hit was not a miss. */
        return board.hit(strategy.chooseHit());
    }

    public boolean hasWon() {
        /* Has this Player won the game? */
        return board.shipsSunk();
    }

    public boolean isGameOver() {
        /* Has this Player hit the entire Board? */
        return board.isGameOver();
    }

    public int numberOfShips() {
        /* Returns the number of Ships to be placed in the Board */
        return ship.length;
    }

    /* When restoring the state of the game */
    public void setBoard(Board b) {
        board = b;
    }
    public boolean isSmart() {
        return strategy.isSmart();
    }
    
    public Ship[] ships() {
    	return ship;
    }

    /* Moves Ship i to the Place(x,y) */
    public boolean moveShip(int i, int x, int y) {
        return board.placeShip(ship[i],x,y,ship[i].isVertical());
    }

    /* Gets the Ship at index(x,y) */
    public int getShipAt(int x, int y) {
        for ( int i=0 ; i<ship.length ; i++ )
            if ( ship[i].isAt(x,y) )
                return i;
        return -1;
    }
}