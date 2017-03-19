/*
* @author Mahdokht Afravi
* @created 03.18 S
* @modified 03.19 U
*
* Controls the Battleship game play.
*/

package edu.utep.cs.cs4330.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private Battleship game;
    private Board userBoard;
    private BoardView userBV;
    private BoardView compBV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if ( savedInstanceState!=null )
            ;

        setBoards();
    }

    private void setBoards() {
        /* Establishes the Players' Boards to their BoardViews */
        userBoard = game.userBoard();
        userBV = (BoardView) findViewById(R.id.userBV);
        userBV.setBoard(game.userBoard());
        compBV = (BoardView) findViewById(R.id.compBV);
        compBV.setBoard(game.compBoard());

        //adds board touch listener to FIXME who's is who's?
        userBV.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                toast(String.format("Touched: %d, %d", x, y));
            }
        });
    }

    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void newClicked(View v) {
        /* Dialog confirmation to user on newButton clicked */
        toast("new clicked!");
        //TODO add dialog box here
    }
}
