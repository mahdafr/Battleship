/*
 * @modified Mahdokht Afravi on 03.18 S
 *
 * This class models the GUI of a 2D Battleship
 * board game.
 */

package edu.utep.cs.cs4330.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Battleship game; //Controller for Game play
    private Board userBoard;
    private BoardView userBV;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a new Game
        game = new Battleship(10);

        userBoard = game.userBoard();
        userBV = (BoardView) findViewById(R.id.boardView);
        userBV.setBoard(game.userBoard());
        userBV.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                toast(String.format("Touched: %d, %d", x, y));
            }
        });
    }

    private boolean[] convertBoardToBool() {
        /* Converts the 2D user Player's Board with Ships into a Boolean[] for Bundle */
        boolean[] board = new boolean[userBoard.size()*userBoard.size()];
        int index = 0;
        for ( int i=0 ; i<userBoard.size() ; i++ )
            for ( int j=0 ; j<userBoard.size() ; j++ )
                if ( userBoard.hasShip(i,j) )
                    board[index++] = true;
                else
                    board[index++] = false;
        return board;
    }

    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void rotateClicked(View v) {
        /* Rotates the Ship's orientation on the Board */
        toast("rotated ship!");
        //TODO rotate the ship being added
    }

    public void createClicked(View v) {
        /* Proceeds to GameActivity to play the Battleship game */
        toast("create clicked!");
        //save the user Player's Ship placements in the Board to start the game
        bundle = new Bundle();
        bundle.putBooleanArray("board",convertBoardToBool());
        //TODO bundle for next activity
        //TODO jump to game activity

    }
}
