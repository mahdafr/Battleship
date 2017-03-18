/*
* @author Mahdokht Afravi
* @created 03.05 U
* @modified 03.05 U
*
* Plays the Strategy of the Computer in a BattleShip Game.
*/

package edu.utep.cs.cs4330.battleship;

public class Strategy {
    java.util.Random random;
    private Smart smart;
    private int boardSize;
    private int ships;

    public Strategy(boolean smartPlay, int bSize, int ships) {
        boardSize = bSize;
        this.ships = ships;
        if ( smartPlay )
            smart = new Smart(bSize,ships);
        else
            random = new java.util.Random();
    }

    public Board placeShip(Ship[] ship, Board board) {
        /* Uses the set Strategy to choose a Place to insert
         * the Ships; placement is irrespective of the type
          * of strategy. */
        java.util.Random r = new java.util.Random();
        for ( int i=0 ; i<ship.length ; i++ ) {
            //choose the direction for each Ship
            ship[i].setDirection(r.nextBoolean());
            //choose the X- and Y- directions for the Ship
            int x = r.nextInt(board.size());
            int y = r.nextInt(board.size());
            if ( canPlace(ship[i],x,y,board) )
                board.placeShip(ship[i],x,y,ship[i].isVertical());
            else
                i--;
        }
        return board;
    }

    private boolean canPlace(Ship ship, int x, int y, Board board) {
        /* Checks if a Ship can be placed starting at this index */
        Place start = board.at(x,y);

        if ( ship.isVertical() ) {
            //if the ship is vertical can we place it here?
            x += ship.getSize(); //get the end
            if ( x>board.size() )
                x -= (2*ship.getSize());
            if ( x<0 )
                return false;
            //are there ships within these bounds of Places?
            for ( int i=start.getX() ; i<x ; i++ )
                if ( board.at(i,y).hasShip() )
                    return false;
        } else {
            //if the ship is horizontal, can we place it here?
            y += ship.getSize(); //get the end
            if ( y>board.size() )
                y -= (2*ship.getSize());
            if ( y<0 )
                return false;
            //are there ships within these bounds of Places?
            for ( int j=start.getY() ; j<y ; j++ )
                if ( board.at(x,j).hasShip() )
                    return false;
        }
        return true;
    }

    public Place chooseHit(Board b) {
        /* Choose a place to hit as a play in the Game */
        int x, y;
        if ( smart==null ) {
            //randomly chooses a hit
            x = random.nextInt(boardSize);
            y = random.nextInt(boardSize);
        } else {
            //smart chooses a hit
            int[] place = smart.chooseHit();
            x = place[0];
            y = place[1];
        }
        return b.at(x,y);
    }
}