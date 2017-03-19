/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.19 U
*
* Controls the behavior of the Battleship Game.
*/

package edu.utep.cs.cs4330.battleship;

public class Battleship {
    /* The maximum size of the Board */
    private final int SIZE;
    /* The Players in the Game */
    private Player player1;
    private Player player2;

    public Battleship(int s) {
        SIZE = s;
        initPlayers();
    }

    private void initPlayers() {
        /* Create the Players for the Battleship Game */
        player1 = new Player();
        //The computer player (setting a strategy)
        player2 = new Player();
        player2.setName("Computer");
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

    public Board compBoard() {
        /* Returns the computer's Board */
        return player2.board();
    }

    public boolean hit(int x, int y) {
        /* If it is the user Player's turn to hit, was it a hit or a miss? */
        if ( player1.canHit() )
            //if the hit was a miss, end Player1's turn
            if ( !player2.hit(x,y) ) //hit into Player2's Board
                switchTurns();
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
}