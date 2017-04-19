package edu.utep.cs.cs4330.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

/** Marco Lopez
 * CS 5390 - Mobile Application Development
 * 2/14/2017
 *
 * This is the controller in the MVC
 */
public class HumanDeployActivity extends AppCompatActivity{

    private Battleship game;
    Ship currentShip;
    BoardView humanBoardView;
    Board humanBoard;
    final int boardSize = 10;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Main Activity", "This is the onCreate method");

        setContentView(R.layout.activity_human_deploy);


        game = game.getGame();

        humanBoardView = (BoardView)findViewById(R.id.boardView);
        humanBoard = game.userBoard();
        setBoard();
        initialPlacing();

        spinner = (Spinner) findViewById(R.id.ship_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ships_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    //Aircraft carrier
                    case 0:
                        Toast.makeText(getApplicationContext(), "Selected Aircraft Carrier", Toast.LENGTH_SHORT).show();
                        currentShip = game.userShips()[0];
                        break;

                    //Battleship
                    case 1:
                        Toast.makeText(getApplicationContext(), "Selected Battleship", Toast.LENGTH_SHORT).show();
                        currentShip = game.userShips()[1];
                        break;

                    //Frigate
                    case 2:
                        Toast.makeText(getApplicationContext(), "Selected Frigate", Toast.LENGTH_SHORT).show();
                        currentShip = game.userShips()[2];
                        break;

                    //Submarine
                    case 3:
                        Toast.makeText(getApplicationContext(), "Selected Submarine", Toast.LENGTH_SHORT).show();
                        currentShip = game.userShips()[3];
                        break;

                    //Minesweeper
                    case 4:
                        Toast.makeText(getApplicationContext(), "Selected Mineseeper", Toast.LENGTH_SHORT).show();
                        currentShip = game.userShips()[4];
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Move buttons method
     * @param view - The button pressed
     */
    public void moveShip(View view){
        switch(view.getId()){
            case R.id.move_left:
                moveShip(0,-1);//-1,0
                humanBoardView.invalidate();
                break;

            case R.id.move_right:
                moveShip(0,1);//1,0
                humanBoardView.invalidate();
                break;

            case R.id.move_up:
                moveShip(-1,0);//0,-1
                humanBoardView.invalidate();
                break;

            case R.id.move_down:
                moveShip(1,0);//0,1
                humanBoardView.invalidate();
                break;

            case R.id.rotateLeft:
                rotate(currentShip);
                humanBoardView.invalidate();
                break;

            case R.id.rotateRight:
                rotate(currentShip);
                humanBoardView.invalidate();
                break;
        }
    }

    private boolean moveShip(int rowAmount, int colAmount){
        Place [] placesARRAY = currentShip.inPlaces();
        Place [] newPlaces = new Place[currentShip.inPlaces().length];

        int row;
        int col;
        int index = 0;
        for(Place currentPlace : placesARRAY){
            row = currentPlace.getX();
            col = currentPlace.getY();

            if(row + rowAmount < 0 || row + rowAmount > humanBoard.getSize()-1 || col + colAmount < 0 || col + colAmount > humanBoard.getSize()-1){
                Toast.makeText(getApplicationContext(), "Invalid movement, out of board.", Toast.LENGTH_SHORT).show();
                return false;
            }

            /* If the new place is not part of the current ship but it has a ship, then it means it will collide with another ship*/
            if (!currentShip.isAt(row+rowAmount, col+colAmount) && humanBoard.hasShip(row+rowAmount, col+colAmount)) {
                Toast.makeText(getApplicationContext(), "Invalid movement, other ship in the way.", Toast.LENGTH_SHORT).show();
                return false;
            }

            Place newPlace = new Place(row+rowAmount, col+colAmount);
            newPlace.addShip();
            newPlaces[index] = newPlace;
            index++;
        }

        currentShip.setPlaces(newPlaces);
        humanBoard.clearBoard();
        humanBoard.restoreShips(game.userShips());
        setBoard();
        return true;
    }


    /**
     * Called when user pushes the "DONE" button
     * @param view - The button pressed
     */
    public void placeShips(View view){
        Intent intent = new Intent(getBaseContext(), GameActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Place ships in original position
     */
    private void initialPlacing(){
        int count = 0;
        humanBoard.clearBoard();
        for(Ship currentShip : game.userShips()){
            Place[] currentPlaces = new Place[currentShip.getSize()];
            for(int i = 0; i < currentShip.getSize(); i++) {
                currentPlaces[i] = humanBoard.at(count, i);
            }
            count++;

            currentShip.setDirection(false);
            currentShip.setPlaces(currentPlaces);
        }
        humanBoard.restoreShips(game.userShips());
    }

    /**
     * Change ship orientation
     * @return true if the ship was able to rotate successfully, false otherwise
     */
    private void rotate(Ship rotatingShip){
        if(humanBoard.canRotate(rotatingShip)) {
            rotatingShip.rotate();
        }
        game.userBoard().clearBoard();
        game.userBoard().restoreShips(game.userShips());
    }

    private void setBoard() {
        humanBoardView.setRadius(65);
        humanBoardView.setUserBoard(humanBoard);
    }
}
