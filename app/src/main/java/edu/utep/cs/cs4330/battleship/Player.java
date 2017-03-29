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
    @SuppressWarnings("unused")
	private String name;
    @SuppressWarnings("unused")
	private int wins;
    private int shots;
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
        shots = 0;
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
    public int shots() {
        /* Get this Player's shots */
        return shots;
    }

    public boolean rotateShip(int i) {
        /* Rotate the direction of the Ship */
        ship[i].rotate();
        //Place[] p = ship[i].inPlaces();
    	//System.out.println("attempt rotate from " +p[0].getX()+","+p[0].getY());
        if ( board.canRotate(ship[i]) ) {
        	//System.out.println("can rotate from " +p[0].getX()+","+p[0].getY());
        	board.clearBoard();
        	//board.removeShip(ship[i],p[0].getX(),p[0].getY());
        	//board.placeShip(ship[i],p[0].getX(),p[0].getY(),ship[i].isVertical());
        	board.restoreShips(ship);
        	return true;
        } else return false;
    }

    public boolean canHit() {
        /* Is it this Player's turn to hit? */
        return turn;
    }

    public boolean hit(int x, int y) {
        /* The opposing Player made a hit on this Player's Board.
         * Returns TRUE if the hit was not a miss. */
        shots++;
        return board.hit(x,y);
    }

    public void switchTurn() {
        /* Either start or stop this Player's turn */
        turn = !turn;
    }

    public boolean chooseHit() {
        /* For the computer Player, choose a Place to hit in the Board.
         * Returns TRUE if the hit was not a miss. */
        shots++;
        return board.hit(strategy.chooseHit());
    }

    public boolean hasWon() {
        /* Has this Player won the game? */
        return board.shipsSunk() || shipsSunk==ship.length;
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
    
    /* Returns this Player's Ships */
    public Ship[] ships() {
    	return ship;
    }

    /* Moves Ship i to the Place(x,y) */
    public boolean moveShip(int i, int changeX, int changeY) {
    	//int i = getShipAt(fromX,fromY);
    	int prevX = ship[i].getX();
    	int prevY = ship[i].getY();
        int newX = prevX + changeX;
        int newY = prevY + changeY;
    	if ( board.placeShip(ship[i],newX,newY,ship[i].isVertical()) ) {
    		//board.removeShip(ship[i],prevX,prevY);
            board.clearBoard();
            board.restoreShips(ship);
    		return true;
    	} else {
    		//board.removeShip(ship[i],newX,newY);
            board.clearBoard();
            board.restoreShips(ship);
    		return false;
    	}
    }

    /* Gets the Ship at index(x,y) */
    private int getShipAt(int x, int y) {
        for ( int i=0 ; i<ship.length ; i++ )
            if ( ship[i].isAt(x,y) )
                return i;
        return -1;
    }
}