/*
* @author Mahdokht Afravi
* @created 03.18 S
* @modified 03.19 U
*
* Controls the Battleship game play.
*/

package edu.utep.cs.cs4330.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private Battleship game;
    private BoardView userBV;
    private BoardView compBV;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_game);

        game = new Battleship(10,5);

        if ( s!=null ) {
            toast("not empty");
            unbundle(s);
        } else {
            toast("empty");
            unbundle(getIntent().getExtras()); //Bundle b = getIntent().getExtras();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onSaveInstanceState(bundle());
        toast("destroyed)");
    }

    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Bundle bundle() {
        /* Saves the state of the game for screen rotation */
        Bundle b = new Bundle();
        b.putBooleanArray("userBoard",convertBoardToBool(game.userBoard()));
        b.putBooleanArray("computerBoard",convertBoardToBool(game.computerBoard()));
        b.putBoolean("smart",game.isHard());
        return b;
    }

    private boolean[] convertBoardToBool(Board b) {
        /* Converts the 2D Player's Board with Ships into a Boolean[] for Bundle */
        boolean[] board = new boolean[b.size()*b.size()];
        int index = 0;
        for ( int i=0 ; i<b.size() ; i++ )
            for ( int j=0 ; j<b.size() ; j++ )
                if ( b.hasShip(i,j) )
                    board[index++] = true;
                else
                    board[index++] = false;
        return board;
    }

    private void unbundle(Bundle s) {
        /* Unparses the Bundle to restore the state of the game */
        boolean[] b1 = s.getBooleanArray("userBoard");
        boolean[] b2;
        if ( s.containsKey("computerBoard") )
            b2 = s.getBooleanArray("computerBoard");
        else
            b2 = null;
        if ( s.getBoolean("smart") )
            game.addSmartStrategy();
        else
            game.addRandomStrategy();
        setBoards(b1,b2);
    }


    private void setBoards(boolean[] b1, boolean[] b2) {
        /* Establishes the Players' Boards to their BoardViews */
        Board userBoard = unParse(b1);
        game.setUserBoard(userBoard);
        userBV = (BoardView) findViewById(R.id.userBV);

        //is this the first time setting up or are we restoring the state?
        Board compBoard;
        if ( b2!=null ) {
            compBoard = unParse(b2);
        } else {
            compBoard = game.computerBoard();
        }
        game.setComputerBoard(compBoard);
        compBV = (BoardView) findViewById(R.id.compBV);

        compBV.setBoard(game.userBoard());
        userBV.setBoard(game.computerBoard());

        //adds board touch listener to computer Player's Board
        userBV.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                toast(String.format("Touched: %d, %d", x, y));
                hit(true,x,y);
            }
        });
    }

    private void hit(boolean isUser, int x, int y) {
        /* Show Player played a hit on the Board */
        if ( isUser ) {
            game.player1Hit(x,y);
            userBV.setBoard(game.userBoard());
        } else {
            userBV.setBoard(game.computerBoard());
        }
    }

    private Board unParse(boolean[] b) {
        /* Parses the Boolean 1D board to a 2D Battleship Board with the placed Ships */
        Board board = new Board(10);
        int index = 0;
        for ( int i=0 ; i<board.size() ; i++ )
            for ( int j=0 ; j<board.size() ; j++ )
                if ( b[index++] )
                    board.addShip(i,j);
        return board;
    }

    public void newClicked(View v) {
        /* Dialog confirmation to user on newButton clicked */
        toast("new clicked!");
        //TODO add dialog box here
    }
}
