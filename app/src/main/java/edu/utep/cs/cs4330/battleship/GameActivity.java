/*
* @author Mahdokht Afravi
* @created 03.18 S
* @modified 03.19 U
*
* Controls the Battleship game play.
*/

package edu.utep.cs.cs4330.battleship;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class GameActivity extends AppCompatActivity {
    private Battleship game;
    private BoardView userBV;
    private BoardView compBV;
    private TextView shotsLabel;
    private Message receivedMessage;
    private Message lastSentMessage;
    private NetworkAdapter netAdapter;
    private boolean gotMessage;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_game);

        //set the game
        game = game.getGame();
        netAdapter = game.getPlayerConnection();
        gotMessage = false;
        NetworkAdapter netAdapter = game.getPlayerConnection();
        netAdapter.setMessageListener(new NetworkAdapter.MessageListener() {
            public void messageReceived(NetworkAdapter.MessageType type, int x, int y, int[] others) {
                switch (type) {
                    case SHOT:
                        receivedMessage = new Message(Message.MessageType.SHOT, x, y, others);
                        break;

                    case SHOT_ACK:
                        receivedMessage = new Message(Message.MessageType.SHOT_ACK, x, y, others);
                        break;

                    case GAME:
                        receivedMessage = new Message(Message.MessageType.GAME, x, y, others);
                        break;

                    case GAME_ACK:
                        receivedMessage = new Message(Message.MessageType.GAME_ACK, x, y, others);
                        break;

                    case FLEET:
                        Log.d("RECEIVING", "FLEET");
                        receivedMessage = new Message(Message.MessageType.FLEET, x, y, others);
                        break;

                    case FLEET_ACK:
                        receivedMessage = new Message(Message.MessageType.FLEET_ACK, x, y, others);
                        break;

                    case TURN:
                        receivedMessage = new Message(Message.MessageType.TURN, x, y, others);
                        break;

                    case QUIT:
                        receivedMessage = new Message(Message.MessageType.QUIT, x, y, others);
                        break;

                    case CLOSE:
                        receivedMessage = new Message(Message.MessageType.CLOSE, x, y, others);
                        break;

                    case UNKNOWN:
                        receivedMessage = new Message(Message.MessageType.UNKNOWN, x, y, others);
                        break;

                }
            }
        });


        userBV = (BoardView) findViewById(R.id.userBV);
        compBV = (BoardView) findViewById(R.id.compBV);
        userBV.setRadius(30);
        compBV.setRadius(25);
        //adds board touch listener to computer Player's Board
        userBV.addBoardTouchListener(new BoardView.BoardTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                //toast(String.format("Touched: %d, %d", x, y));
                hit(x, y);
            }
        });

        exchangeFleets();
        game.computerBoard().clearBoard();
        game.computerBoard().restoreShips(game.opponentShips());

        //===================================
        assignBoards();
        setBoards();

        shotsLabel = (TextView) findViewById(R.id.shots);
    }

    class SignInAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            gotMessage = false;

            super.onPreExecute();
            // Display a progress bar saying signing in or something
        }

        @Override
        protected String doInBackground(Void... params) {
            // Call your web service
            netAdapter.receiveMessagesAsync();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            gotMessage = true;
            super.onPostExecute(result);
            // Sign in if the credentials are okay, otherwise show some error message to the user
        }
    }

    private void exchangeFleets() {
        int[] player1Fleet = game.makeFleetMessage();

        //If this instance is the client then send his fleet first and wait for a FLEET ACK from the other player through the NetworkAdapter
        if (game.isClient()) {

            //Send our fleet to our opponent (the server)
            netAdapter.writeFleet(player1Fleet);

            //If our fleet was successfully ACK
            if (receiveFleetACK()) {

                //Now that we have successfully sent our FLEET we need to receive opponent's FLEET
                receiveFleetMsg();
            }


            //If we received a message that was not a FLEET_ACK OR our FLEET msg was rejected by opponent
            else {
                //TODO what to do if our FLEET was rejected
            }

        }

        //If we are the server then we wait for the opponent's fleet first, ACK it and then send our own and wait for ACK to that.
        else {

            //Since we are the server, we wait for the opponent's fleet first
            if (receiveFleetMsg()) {

                //Send an FLEET_ACK to the opponent's FLEET message
                netAdapter.writeFleetAck(true);

                //Send our fleet to the opponent
                netAdapter.writeFleet(player1Fleet);
            }

            //We didn't receive a FLEET message
            else {
                //TODO what do when we don't receive a FLEET message
                //TODO reject FLEET message with false FLEET_ACK or send error (UNKNOWN message)?
                netAdapter.writeFleetAck(false);
            }
        }
    }

    public void receiveMessage(){
        SignInAsyncTask receiver = new SignInAsyncTask();
        receiver.execute();
        while(true){
            if(gotMessage == true) break;
            else{
                Log.d("RECEIVING", "FLEET");
            }
        }
    }


    /**
     * Method that receives message from the NetworkAdapter expecting a FLEET_ACK message
     *
     * @return
     */
    private boolean receiveFleetACK() {

        //Wait for the opponent's FLEET_ACK to our FLEET message.
        receiveMessage();

        while(true){
            if(gotMessage) break;
        }

        //If we received an ACK to our fleet
        if (receivedMessage.getType() == Message.MessageType.FLEET_ACK) {
            gotMessage = false;
            //Our fleet message was ACKED and it was accepted
            if (receivedMessage.getX() == 1) {
                return true;
            }

            //Our FLEET message was rejected
            else {
                return false;
                //TODO what if the fleet message is rejected
            }
        }

        //We received a message that was not a FLEET_ACK
        else{
            gotMessage = false;
            return false;
            //TODO what if the next message is not a FLEET_ACK
        }

    }

    /**
     * Calls NetAdapter to receive message expecting a FLEET message
     *
     * @return true - if the message received was a FLEET message
     * false - if the message received was NOT a FLEET message
     * or does not contain the correct length.
     */
    private boolean receiveFleetMsg() {

        receiveMessage();

        while(true){
            if(gotMessage) break;
            else{
                System.out.println("Waiting");
                System.out.println("WAIIIIT");
            }

            System.out.println("Tired of WAITING");
        }

        //We received the opponent's fleet as expected
        if (receivedMessage.getType() == Message.MessageType.FLEET) {
            gotMessage = false;
            //TODO when else to send a rejected (false) FLEET_ACK

            //If the fleet message received was not of the right length, reject it
            if (receivedMessage.getOthers().length != 4 * 5) {
                netAdapter.writeFleetAck(false);
                return false;
            }

            //If the fleet message was of the right length
            else {

                //Set the opponent's board to be the ones in the leet message
                setOpponentShips(receivedMessage.getOthers());

                //Send a FLEET_ACK message
                netAdapter.writeFleetAck(true);
            }

            return true;
        }

        //We received something OTHER than a FLEET message when we are expecting a FLEET message
        else {
            gotMessage = false;
            //TODO what if we didn't receive the opponent's fleet
            return false;
        }
    }

    /**
     * Makes new ships from the fleet message and assigns them to the opponent.
     *
     * @param message
     */
    private boolean setOpponentShips(int[] message) {
        Ship[] opponentShips = game.opponentShips();

        int index = 0;

        int shipCount = 0;
        int nextShipSize = -1;
        int nextShipStartingX = -1;
        int nextShipStartingY = -1;
        boolean isHorizontal = false;

        Ship nextShip = null;

        //Traverse the message
        for (int i : message) {

            //We are looking at the size of a ship
            if (index == 0) {
                //Reset for next ship
                nextShipStartingX = -1;
                nextShipStartingY = -1;
                isHorizontal = false;

                nextShipSize = i;

                //Make a new ship based on the size
                switch (nextShipSize) {
                    case 5:
                        nextShip = new Ship(0);
                        break;
                    case 4:
                        nextShip = new Ship(1);
                        break;
                    case 3:
                        nextShip = new Ship(2);
                        break;
                    case 2:
                        nextShip = new Ship(3);
                        break;

                    default:
                        System.out.println("NOT A SHIP");
                        nextShip = null;
                        return false;
                }

                //Replace previous ship with the new ship
                opponentShips[shipCount] = nextShip;
                shipCount++;

                index = 1;
            }

            //We are looking at the starting X of the ship
            else if (index == 1) {
                nextShipStartingX = i;
                index = 2;
            }

            //We are looking at the starting Y of the ship
            else if (index == 2) {
                nextShipStartingY = i;
                index = 3;
            }

            //We are looking at the direction of a ship
            else if (index == 3) {

                if (i == 1) isHorizontal = true;

                Place[] newPlaces = new Place[nextShipSize];

                //Make new places for the new ship
                for (int j = 0; j < nextShipSize; j++) {
                    Place newPlace;
                    if (isHorizontal) {
                        newPlace = new Place(nextShipStartingX + j, nextShipStartingY);
                    } else {
                        newPlace = new Place(nextShipStartingX, nextShipStartingY + j);
                    }
                    newPlace.addShip();
                    newPlaces[j] = newPlace;
                }

                nextShip.setPlaces(newPlaces);
                nextShip.setDirection(!isHorizontal);

                index = 0;
            }
        }

        return true;
    }

    /**
     * Convert the given boolean value to an int.
     */
    private int toInt(boolean flag) {
        return flag ? 1 : 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //overridePendingTransition(R.anim.game_in,R.anim.game_out); //FIXME 3.6-7, 4.6?
    }

    /**
     * Show a toast message.
     */
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
    }

    /* Dialog confirmation to user on newButton clicked */
    public void newClicked(View v) {
        createDialog();
    }

    /* Show Player played a hit on the Board */
    private void hit(int y, int x) {
        if (game.playerTurn())
            game.player1Hit(x, y);
        setBoards();
        while (game.computerTurn()) {
            game.player2Hit();
            setBoards();
        }
        updateShots();
        if (hasWinner())
            startNew();
    }

    private boolean hasWinner() {
        if (game.computerWon()) {
            toast("Computer won!");
            return true;
        } else {
            if (game.userWon()) {
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
