/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.19 U
*
* Controls the behavior of the Battleship Game.
*/

package edu.utep.cs.cs4330.battleship;

public class Battleship {
    /* The Players in the Game */
    private Player player1;
    private Player player2;

    public Battleship(int b, int s) {
        initPlayers(b,s);
    }

    private void initPlayers(int b, int s) {
        /* Create the Players for the Battleship Game */
        player1 = new Player(b,s); //user Player
        player1.switchTurn();
        player2 = new Player(b,s); //computer Player
        randomizeShips();
    }

    public void addSmartStrategy() {
        /* Add a Smart Strategy to the computer Player */
        player2.setStrategy(true);
        initComputer();
    }
    public void addRandomStrategy() {
        /* Add a Random Strategy to the computer Player */
        player2.setStrategy(false);
        initComputer();
    }

    private void initComputer() {
        /* The computer Player chooses where to place the Ships in the board */
        player2.setName("Computer");
        player2.placeShips();
    }

    public boolean computerWon() {
        /* Returns whether the computer Player won the game or not */
        return player1.lostGame();
    }

    public boolean userWon() {
        /* Returns whether the user/human Player won the game or not */
        return player2.lostGame();
    }

    public boolean isOver() {
        /* Returns whether there are any more Ships to sink */
        return ( player1.lostGame() || player2.lostGame() );
    }

    public Board userBoard() {
        /* Returns the user's Board */
        return player1.board();
    }

    public Board computerBoard() {
        /* Returns the computer's Board */
        return player2.board();
    }

    public boolean hit(int x, int y) {
        /* If it is the user Player's turn to hit, was it a hit or a miss? */
    	System.out.println("p1 turn: " +player1.canHit());
        if ( player1.canHit() ) {
            //if the hit was a miss, end Player1's turn
            if ( !player2.hit(x,y) ) //hit into Player2's Board
                switchTurns();
        } else 
        if ( player2.canHit() ) {
            while ( !player2.chooseHit() )
                ; //until the hit is a miss, then end Player2's turn
            switchTurns();
        }
        return false;
    }

    public void switchTurns() {
        /* Switch the Player's turns in the game. */
        player1.switchTurn();
        player2.switchTurn();
    }

    private void randomizeShips() {
        /* Randomly places the ships for Player1 */
        player1.setStrategy(false);
        player1.placeShips();
        player2.setStrategy(false);
        player2.placeShips();
    }

    public int numberOfShips() {
        /* Returns the number of Ships each Player must place in the Board */
        return player2.numberOfShips();
    }

    public void rotatePlayer1Ship(int i) {
        /* Rotates the i'th Ship */
        player1.rotateShip(i);
    }

    /* When restoring the state of the game */
    public void setUserBoard(Board b) {
        player1.setBoard(b);
    }
    public void setComputerBoard(Board b) {
        player2.setBoard(b);
    }
    public boolean isHard() {
        return player2.isSmart();
    }
    
    public Ship[] userShips() {
    	return player1.ships();
    }
    
    public boolean player1Hit(int x, int y) {
    	return player1.board().hit(x,y);
    }

    /* If possible, moves the selected Ship to Place(x,y) */
    public boolean moveShipTo(int fromX, int fromY, int toX, int toY) {
        if ( player1.board().hasShip(toX,toY) )
            return false;
        int s = player1.getShipAt(fromX,fromY);
        if ( s>-1 && player1.moveShip(s,toX,toY) )
            return true;
        return false;
    }
}