/*
* @author Mahdokht Afravi
* @created 03.18 S
* @modified 03.19 U
*
* Controls the Battleship game play.
*/

package edu.utep.cs.cs4330.battleship;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private Battleship game;
    private BoardView userBV;
    private BoardView compBV;
    private TextView shotsLabel;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_game);

        //set the game
        game = game.getGame();
        userBV = (BoardView) findViewById(R.id.userBV);
        compBV = (BoardView) findViewById(R.id.compBV);
        userBV.setRadius(30);
        compBV.setRadius(25);
        //adds board touch listener to computer Player's Board
        userBV.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                //toast(String.format("Touched: %d, %d", x, y));
                hit(x,y);
            }
        });
        assignBoards();
        setBoards();

        shotsLabel = (TextView) findViewById(R.id.shots);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //overridePendingTransition(R.anim.game_in,R.anim.game_out); //FIXME 3.6-7, 4.6?
    }
    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /* On create, set the Boards to apply the hits to the opponent Player's Boards */
    private void assignBoards() {
        Board computerDesigned = game.computerBoard();
        Board userDesigned = game.userBoard();
        game.setPlayer1Board(null);
        game.setPlayer2Board(null);
        game.setPlayer1Board(computerDesigned);
        game.setPlayer2Board(userDesigned);
    }
    /* Establishes the Players' Boards to their opponents' BoardViews */
    private void setBoards() {
        userBV.setBoard(game.userBoard());
        compBV.setBoard(game.computerBoard());
        //FIXME userBV shows computerBoard (user hits computer's Board)
    }

    /* Dialog confirmation to user on newButton clicked */
    public void newClicked(View v) {
        createDialog();
    }

    /* Show Player played a hit on the Board */
    private void hit(int y, int x) {
        if ( game.playerTurn() )
            game.player1Hit(x,y);
        setBoards();
        while ( game.computerTurn() ) {
            game.player2Hit();
            setBoards();
        }
        updateShots();
        if ( hasWinner() )
            startNew();
    }
    private boolean hasWinner() {
        if ( game.computerWon() ) {
            toast("Computer won!");
            return true;
        } else {
            if ( game.userWon() ) {
                toast("Player won!");
                return true;
            } else return false;
        }
    }
    private void startNew() {
        game.createNewGame();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    private void continueGame() {
        //TODO idk wut to do here...
    }
    private void createDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.newGameRequest);
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startNew();
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        continueGame();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /* Display the number of shots made on the Board by the user Player */
    private void updateShots() {
        shotsLabel.setText(game.userShots() + " Shots");
    }
}
