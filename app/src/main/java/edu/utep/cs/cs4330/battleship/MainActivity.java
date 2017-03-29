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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Battleship game; //Controller for Game play
    private BoardView userBV;
    private boolean isSmart;
    private Button strategyButton;
    private int ship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a new Game
        game = game.getGame();
        isSmart = true;
        userBV = (BoardView) findViewById(R.id.boardView);
        userBV.setRadius(45);
        userBV.setUserBoard(game.userBoard());
    }
    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /* Moves/Rotates the Ships on the Board by the user input */
    public void rotateShip(View v) {
        if ( game.rotatePlayer1Ship(ship) )
            userBV.setUserBoard(game.userBoard());
        else
            toast("Cannot rotate ship");
    }
    public void moveUp(View v) {
        move(-1,0);
    }
    public void moveLeft(View v) {
        move(0,-1);
    }
    public void moveRight(View v) {
        move(0,1);
    }
    public void moveDown(View v) {
        move(1,0);
    }
    public void nextShip(View v) {
        toast("Stored ship " +(ship+1));
        ship++;
    }
    private void move(int x, int y) {
        if ( ship>=4 )
            endCustomize();
        if ( game.moveShip(ship,x,y) )
            userBV.setUserBoard(game.userBoard());
        else
            toast("Cannot move ship");
    }
    private void endCustomize() {
        toast("Stored ships");
        Button[] butt = new Button[6];
        butt[0] = (Button) findViewById(R.id.rotateShip);
        butt[1] = (Button) findViewById(R.id.shipDown);
        butt[2] = (Button) findViewById(R.id.shipLeft);
        butt[3] = (Button) findViewById(R.id.shipRight);
        butt[4] = (Button) findViewById(R.id.shipUp);
        butt[5] = (Button) findViewById(R.id.nextShip);
        for ( int i=0 ; i<butt.length ; i++ )
            butt[i].setVisibility(View.INVISIBLE);
    }

    /* Sets the strategy of the game to Random or Smart */
    public void strategyClicked(View v) {
        if ( isSmart ) {
            strategyButton.setText(R.string.random);
            game.addRandomStrategy();
        } else {
            strategyButton.setText(R.string.smart);
            game.addSmartStrategy();
        }
        isSmart = !isSmart;
    }

    /* Proceeds to GameActivity to play the Battleship game */
    public void createClicked(View v) {
        Intent intent = new Intent(getApplicationContext(),GameActivity.class);
        startActivity(intent);
    }
}
