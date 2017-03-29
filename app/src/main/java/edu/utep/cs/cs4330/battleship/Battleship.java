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
    private static Battleship instance = new Battleship(10,5);

    private Battleship(int b, int s) {
        initPlayers(b,s);
    }

    public static Battleship getGame() {
        if ( instance.equals(null) )
            instance = new Battleship(10,5);
        return instance;
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

    public void createNewGame() {
        initPlayers(10,5);
    }

    public Board userBoard() {
        /* Returns the user's Board */
        return player1.board();
    }
    public Board computerBoard() {
        /* Returns the computer's Board */
        return player2.board();
    }

    public boolean playerTurn() {
        /* Checks if it is user Player's turn */
        return player1.canHit();
    }
    public boolean computerTurn() {
        /* Checks if it is computer Player's turn */
        return player2.canHit();
    }
    private void switchTurns() {
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

    /* Rotates the i'th Ship */
    public boolean rotatePlayer1Ship(int i) {
        return player1.rotateShip(i);
    }
    /* If possible, moves the selected Ship to Place(x,y) */
    public boolean moveShip(int s, int changeX, int changeY) {
        return player1.moveShip(s,changeX,changeY);
    }

    public Ship[] userShips() {
    	return player1.ships();
    }

    /* If the user Player hit a Ship, they can keep hitting */
    public boolean player1Hit(int x, int y) {
    	if ( !player1.hit(x,y) ) {
            switchTurns();
            return false;
        }
        return true;
    }
    /* If the computer Player hit a Ship, they can keep hitting */
    public boolean player2Hit() {
        if ( !player2.chooseHit() ) {
            switchTurns();
            return false;
        }
        return true;
    }
    /* Returns the number of shots made by the user Player */
    public int userShots() {
        return player1.shots();
    }
}