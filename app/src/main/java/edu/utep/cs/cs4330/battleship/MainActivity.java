/*
 * @modified Mahdokht Afravi on 03.18 S
 *
 * This class models the GUI of a 2D Battleship
 *  board game. It accepts a user's input to design
 *  the layout of the Ships on the Board.
 */

package edu.utep.cs.cs4330.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Battleship game; //Controller for Game play
    private BoardView userBV;
    private boolean isSmart;
    private Button strategyButton;
    private Button[] ship = new Button[5];
    private int fromX;
    private int fromY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isSmart = true;

        //create a new Game
        game = new Battleship(10,5);
        initButtons();

        userBV = (BoardView) findViewById(R.id.boardView);
        userBV.setUserBoard(game.userBoard());
        userBV.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                toast(String.format("Touched: %d, %d", x, y));
                if ( game.userBoard().hasShip(x,y) ) //game.moveShipTo(x,y) )
                    startMove(x,y);
                if ( x!=fromX && y!=fromY )
                    finishMove(x,y);
            }
        });
    }

    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initButtons() {
        strategyButton = (Button) findViewById(R.id.strategyButton);
        ship[0] = (Button) findViewById(R.id.ship1);
        ship[1] = (Button) findViewById(R.id.ship2);
        ship[2] = (Button) findViewById(R.id.ship3);
        ship[3] = (Button) findViewById(R.id.ship4);
        ship[4] = (Button) findViewById(R.id.ship5);
    }

    /* The user Player is moving the Ship to a new index(x,y) */
    private void startMove(int x, int y) {
        fromX = x;
        fromY = y;
    }
    private void finishMove(int x, int y) {
        game.moveShipTo(fromX,fromY,x,y);
    }

    /* Stores the Ships on the Board by user input */
    public void rotateFirst(View v) {
        rotate(0);
    }
    public void rotateSecond(View v) {
        rotate(1);
    }
    public void rotateThird(View v) {
        rotate(2);
    }
    public void rotateFourth(View v) {
        rotate(3);
    }
    public void rotateFifth(View v) {
        rotate(4);
    }

    private void rotate(int i) {
        game.rotatePlayer1Ship(i);
        userBV.setUserBoard(game.userBoard());
    }

    public void strategyClicked(View v) {
        /* Sets the strategy of the game to Random or Smart */
        if ( isSmart ) {
            strategyButton.setText(R.string.random);
            game.addRandomStrategy();
        } else {
            strategyButton.setText(R.string.smart);
            game.addSmartStrategy();
        }
        isSmart = !isSmart;
    }

    public void createClicked(View v) {
        /* Proceeds to GameActivity to play the Battleship game */
        Intent intent = new Intent(getApplicationContext(),GameActivity.class);
        intent.putExtra("userBoard",convertBoardToBool());
        intent.putExtra("smart",isSmart);
        startActivity(intent);
    }

    private boolean[] convertBoardToBool() {
        /* Converts the 2D user Player's Board with Ships into a Boolean[] for Bundle */
        int size = game.userBoard().size();
        boolean[] board = new boolean[size*size];
        int index = 0;
        for ( int i=0 ; i<size ; i++ )
            for ( int j=0 ; j<size ; j++ )
                if ( game.userBoard().hasShip(i,j) )
                    board[index++] = true;
                else
                    board[index++] = false;
        return board;
    }
}
