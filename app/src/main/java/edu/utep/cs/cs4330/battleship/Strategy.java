/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.18 S
*
* Plays the Strategy of the Computer in a BattleShip Game.
*/

package edu.utep.cs.cs4330.battleship;

public class Strategy {
    private java.util.Random random;
    private Smart smart;
    private int boardSize;
    @SuppressWarnings("unused")
	private int ships;

    public Strategy(boolean smartPlay, int b, int s) {
        boardSize = b;
        ships = s;
        if ( smartPlay ) {
            random = null;
            smart = new Smart(b, s);
        } else {
            smart = null;
            random = new java.util.Random();
        }
    }

    public Board placeShip(Ship[] ship, Board board) {
        /* Uses the set Strategy to choose a Place to insert
         * the Ships; placement is irrespective of the type
         * of strategy. */
        java.util.Random r = new java.util.Random();
        for ( int i=0 ; i<ship.length ; i++ ) {
            //choose the direction for each Ship
            ship[i].setDirection(r.nextBoolean());
            //choose the X- and Y- positions for the Ship
            int x = r.nextInt(board.size());
            int y = r.nextInt(board.size());
            if ( !board.placeShip(ship[i],x,y,ship[i].isVertical()) )
                i--;
        }
        return board;
    }

    public Place chooseHit() {
        /* Choose a place to hit as a play in the Game */
        int x, y;
        if ( smart==null ) {
            //randomly chooses a hit
            x = random.nextInt(boardSize);
            y = random.nextInt(boardSize);
            return new Place(x,y);
        } else {
            //smart chooses a hit
            Place p = smart.chooseHit();
            //System.out.println("smart chose " + p.getX()+","+ p.getY());
            return p;
        }
    }
    public void hitGood() {
    	if ( smart!=null )
    		smart.hitGood();
    }

    /* When restoring the state of the game */
    public boolean isSmart() {
        return smart==null;
    }
}