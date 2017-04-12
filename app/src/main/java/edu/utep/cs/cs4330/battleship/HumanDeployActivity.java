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

    Ship currentShip;
    Player humanPlayer;
    BoardView humanBoardView;
    Board humanBoard;
    final int boardSize = 10;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Main Activity", "This is the onCreate method");

        setContentView(R.layout.activity_main);


        humanPlayer = new Player(10, 5);
        humanBoard = humanPlayer.getBoard();

//        humanPlayer.setMyBoard(humanBoard);

        humanBoardView = (BoardView) findViewById(R.id.boardView);
        humanBoardView.setBoard(humanBoard);
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
                        currentShip = humanPlayer.getShips()[0];
                        break;

                    //Battleship
                    case 1:
                        Toast.makeText(getApplicationContext(), "Selected Battleship", Toast.LENGTH_SHORT).show();
                        currentShip = humanPlayer.getShips()[1];
                        break;

                    //Frigate
                    case 2:
                        Toast.makeText(getApplicationContext(), "Selected Frigate", Toast.LENGTH_SHORT).show();
                        currentShip = humanPlayer.getShips()[2];
                        break;

                    //Submarine
                    case 3:
                        Toast.makeText(getApplicationContext(), "Selected Submarine", Toast.LENGTH_SHORT).show();
                        currentShip = humanPlayer.getShips()[3];
                        break;

                    //Minesweeper
                    case 4:
                        Toast.makeText(getApplicationContext(), "Selected Mineseeper", Toast.LENGTH_SHORT).show();
                        currentShip = humanPlayer.getShips()[4];
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
                moveShip(-1,0);
                humanBoardView.invalidate();
                break;

            case R.id.move_right:
                moveShip(1,0);
                humanBoardView.invalidate();
                break;

            case R.id.move_up:
                moveShip(0,-1);
                humanBoardView.invalidate();
                break;

            case R.id.move_down:
                moveShip(0,1);
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
        Place [] placesARRAY = new Place [currentShip.getSize()];

        ArrayList<Place> places = currentShip.getLocation();
        int row;
        int col;
        ArrayList<Place> newLocation = new ArrayList<Place>();
        for(Place currentPlace : places){
            row = currentPlace.getX();
            col = currentPlace.getY();

            if(row + rowAmount < 0 || row + rowAmount > humanBoard.getSize()-1 || col + colAmount < 0 || col + colAmount > humanBoard.getSize()-1){
                Toast.makeText(getApplicationContext(), "Invalid movement, out of board.", Toast.LENGTH_SHORT).show();
                return false;
            }

            Place newPlace = new Place();
            newPlace = humanBoard.at(row+rowAmount, col+colAmount);

            if (!currentShip.getLocation().contains(newPlace) && newPlace.hasShip()) {
                Toast.makeText(getApplicationContext(), "Invalid movement, other ship in the way.", Toast.LENGTH_SHORT).show();
                return false;
            }

            newLocation.add(newPlace);
        }

        for(Place currentPlace : places){
            row = currentPlace.getX();
            col = currentPlace.getY();

            if(!newLocation.contains(currentPlace)) {
                currentPlace.removeShip();
            }

            Place newPlace = new Place();
            newPlace = humanBoard.at(row+rowAmount, col+colAmount);
            newPlace.setShip(true);
        }

        currentShip.setLocation(newLocation);

        return true;
    }


    /**
     * Called when user pushes the "DONE" button
     * @param view - The button pressed
     */
    public void placeShips(View view){
        Intent intent = new Intent(getBaseContext(), GameWindow.class);
        startActivity(intent);
        finish();
    }

    /**
     * Place ships in original position
     */
    private void initialPlacing(){
        int count = 0;
        humanBoard.clearBoard();
        for(Ship currentShip : humanPlayer.getShips()){
            Place[] currentPlaces = new Place[currentShip.getSize()];
            for(int i = 0; i < currentShip.getSize(); i++) {
                currentPlaces[i] = humanBoard.at(i, count);
            }
            currentShip.setDirection(false);
            currentShip.setPlaces(currentPlaces);
            count++;
        }
        humanBoard.restoreShips(humanPlayer.getShips());
    }

    /**
     * Change ship orientation
     * @return true if the ship was able to rotate successfully, false otherwise
     */
    private boolean rotate(Ship rotatingShip){

        int amount = rotatingShip.getSize()/2;

        //If the ship is horizontal then we will add the amount to the places of the ship
        //Otherwise we will subtract it
        if(rotatingShip.isVertical()){
            amount = -amount;
        }

        //Traverse each place checking whether or not each place will go out of bounds from the board
        for(Place currentPlace : rotatingShip.inPlaces()){

            //Get current coordinates in order to increase or decrease them depending on <amount>
            int row = currentPlace.getX();
            int col = currentPlace.getY();

            //If the new coordinates for the place go out of bounds return false and display a message
            if(row + amount > humanBoard.getSize()-1 || row + amount < 0 || col + amount > humanBoard.getSize()-1 || col + amount < 0) {
                Toast.makeText(getApplicationContext(), "Invalid Rotation, out of board.", Toast.LENGTH_SHORT).show();
                return false;
            }

            //Check to see if the new place has a colliding ship meaning it cannot be rotated that way.
            //Don't check when amount is 0 though because it would say that it collided with itself.
            else if(amount != 0) {
                if (humanBoard.at(row + amount, col + amount).hasShip()) {
                    Toast.makeText(getApplicationContext(), "Invalid Rotation, other ship in the way.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            if (!rotatingShip.isVertical())
                amount--;
            else
                amount++;
        }


        //Actually change the places
        amount = rotatingShip.getSize()/2;
        if(rotatingShip.isVertical()){
            amount = -amount;
        }
        ArrayList<Place> newLocations = new ArrayList<>();

        for(Place currentPlace : rotatingShip.inPlaces()){
            currentPlace.removeShip();
            Place newPlace = humanBoard.at(currentPlace.getX() + amount, currentPlace.getY() + amount);
            newPlace.addShip();
            newLocations.add(newPlace);
            if(!rotatingShip.isVertical())
                amount--;

            else
                amount++;
        }

        Place [] newPlacesArray = new Place[newLocations.size()];
        newPlacesArray = newLocations.toArray(newPlacesArray);
        currentShip.setPlaces(newPlacesArray);
        rotatingShip.setDirection(!rotatingShip.isVertical());
        return true;
    }
}
