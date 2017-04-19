/*
 * @created Mahdokht Afravi on 04/08 S
 *
 * Starts the Battleship App by asking the user
 *   to choose a method of playing the game:
 *   against a local opponent, a network opponent,
 *   or the computer.
 *
 * @modified Mahdokht Afravi on 04/17 M
 */
package edu.utep.cs.cs4330.battleship;

import android.bluetooth.BluetoothAdapter;
<<<<<<< HEAD
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.SystemClock;
=======
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
>>>>>>> misha
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.util.Log;
<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
=======
>>>>>>> misha

public class MainActivity extends AppCompatActivity {
    private Battleship game;
    Spinner p2p, ai;
    int conectionType;
<<<<<<< HEAD
    boolean connected;
    Socket opponentSocket;
    boolean client;
=======
    boolean wifiDirectAllowed;
    //WifiDirect functionality
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    IntentFilter intentFilter;
>>>>>>> misha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conectionType = -1;

<<<<<<< HEAD
=======
        createWD(); //set up for wifi direct

>>>>>>> misha
        game = game.getGame();
        wifiDirectAllowed = false;
        p2p = (Spinner) findViewById(R.id.P2Pspinner);
        setP2PSpinner();
        ai = (Spinner) findViewById(R.id.AIspinner);
        setAISpinner();
        connected = false;
        client = false;
    }
    /* Register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }
    /* De-register the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /* Sets adapters to the Spinners */
    private void setP2PSpinner() {;
        //P2P Adapter
        ArrayAdapter<CharSequence> p2pAdapter = ArrayAdapter.createFromResource(this,R.array.p2p,android.R.layout.simple_spinner_item);
        p2pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p2p.setAdapter(p2pAdapter);
        p2p.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ( position ) {
<<<<<<< HEAD
                    case 0: //Bluetooth
//                        startBTGame();
                        conectionType = 0;
                        break;
                    case 1: //Wifi Direct
//                        startWFDirectGame();
                        conectionType = 1;
                        break;
                    case 2: //Wifi
//                        startWFGame();
=======
                    case 0: //name
                        conectionType = -1;
                        break;
                    case 1: //Bluetooth
                        startBTGame();
                        conectionType = 0;
                        break;
                    case 2: //Wifi Direct
                        if ( wifiDirectAllowed ) //phone has functionality: wifiDirect?
                            conectionType = 1;
                        else toast ("Phone has no WifiDirect capability");
                        break;
                    case 3: //Wifi
                        startWFGame();
>>>>>>> misha
                        conectionType = 2;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO idk maybe something pretty?
            }
        });
    }
    private void setAISpinner() {
        //AI Adapter
        ArrayAdapter<CharSequence> aiAdapter = ArrayAdapter.createFromResource(this,R.array.ai,android.R.layout.simple_spinner_item);
        aiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ai.setAdapter(aiAdapter);
        ai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ( position ) {
                    case 0: //Smart
                        game.addSmartStrategy();
<<<<<<< HEAD
//                        startGame();
                        break;
                    case 1: //Random
                        game.addRandomStrategy();
//                        startGame();
=======
                        //startGame();
                        break;
                    case 1: //Random
                        game.addRandomStrategy();
                        //startGame();
>>>>>>> misha
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO idk maybe something pretty?
            }
        });
    }

    /* Bluetooth Functionality */
<<<<<<< HEAD
    private boolean BTenabled() {
        //checks if BT is on
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if ( btAdapter!=null && btAdapter.isEnabled() )
            return true;
        return false;
    }

    private void turnOnBT() {
        //creates a window alert: user permission to turn on BT
        Intent intent = new Intent(BluetoothAdapter.getDefaultAdapter().ACTION_REQUEST_ENABLE);
        startActivity(intent);
    }

    private void startBTGame() {
        //activity (settings): connects to bluetooth
        if ( !BTenabled() )
            turnOnBT();
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.startDiscovery();
        startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
        if ( btAdapter.getProfileConnectionState(BluetoothProfile.A2DP)==btAdapter.STATE_CONNECTED )
            startGame();
        else createTryAgainDialog("Bluetooth not connected!","Try Again","Cancel");
=======
    private void startBTGame() {
        //activity (settings): connects to bluetooth
        startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if ( !true ) //todo device not connected to another phone
            createTryAgainDialog("Bluetooth not connected!","Try Again","Cancel");
        //String remote = btAdapter.getAddress(); // format 00:00:00:00:00:00
        //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        //BluetoothServerSocket btSS = btAdapter.listenUsingRfcommWithServiceRecord(remote,uuid);
        //BluetoothDevice btDevice = btAdapter.getRemoteDevice(remote);
        //BluetoothSocket btS = btDevice.createRfcommSocketToServiceRecord(SERIAL_UUID);
        //btS.connect();
>>>>>>> misha
    }

    /* Wifi Functionality */
    private boolean WFenabled() {
        //checks if WF is on
        WifiManager m = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return m.isWifiEnabled();
    }
<<<<<<< HEAD

=======
>>>>>>> misha
    private void turnOnWF() {
        //creates a window alert: user permission to turn on WF
        WifiManager m = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }
<<<<<<< HEAD

=======
>>>>>>> misha
    private void startWFGame() {
        //activity (settings): connects to wifi
        if ( !WFenabled() )
            turnOnWF();
        //fixme it jumps straight to error dialog before turnOnWF()
        /*
        ConnectivityManager m = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = m.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //deprecated
        if ( wifi.isConnected() ) startGame();*/
        // /*
        WifiManager m = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifi = m.getConnectionInfo();
<<<<<<< HEAD

        if ( wifi.getNetworkId()!=-1 ) {
            connectPlayers();

            //Wait for connection thread to be done.
            while(true){
                if(connected){
                    break;
                }
            }


            //IF we are the client of the game
            if(client){
                Battleship.getGame().setIsClient(true);
            }


            //IF we are the server of the game
            else{
                Battleship.getGame().setIsClient(false);
            }


            startGame();
        }

        else createTryAgainDialog("Wifi not connected!","Try Again","Cancel");
    }

    private void connectPlayers(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                try{
                    //Connect to the connector socket
                    Socket connectorSocket = new Socket("172.19.152.51", 8003);

                    //To read from socket
                    BufferedReader in
                            = new BufferedReader(
                            new InputStreamReader(connectorSocket.getInputStream()));

                    //To write to socket
                    PrintWriter out
                            = new PrintWriter(
                            new OutputStreamWriter(connectorSocket.getOutputStream()));

                    //This is the other client's IP
                    String otherIP = in.readLine();


                    //This is the other client's PORT number
                    String otherPORT = in.readLine();

                    //This is stating whether This is the client or the server
                    String clientBoolean = in.readLine();

                    ServerSocket opponentServerSocket;

                    //If we are the client then create a socket to the server
                    if(clientBoolean.equals("true")) {
                        client = true;
                        SystemClock.sleep(1000);
                        opponentSocket = new Socket(otherIP, 2027);
                    }

                    //If we are the server create a server socket
                    else{
                        opponentServerSocket = new ServerSocket(2027);
                        opponentSocket = opponentServerSocket.accept();
                    }

                    Battleship.getGame().initializeAdapter(opponentSocket);

                    connected = true;
                }
                catch(IOException e){
                    e.printStackTrace();
                    Log.d("CONNECTING", e.toString());
                }
            }
        });
    }


    /* Wifi Direct Functionality */
    private void startWFDirectGame() {
        //todo extra credit boi
    }

=======
        if ( wifi.getNetworkId()!=-1 ) startGame(); //*/
        else createTryAgainDialog("Wifi not connected!","Try Again","Cancel");
    }

    /* Wifi Direct Functionality */
    private void createWD() {
        //register Battleship with WifiP2p framework
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WifiBroadcastReceiver(manager, channel, this);
        intentFilter = new IntentFilter();
        //intent filter of intents the broadcast receiver checks for
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }
    private void startWFDirectGame() {
        //uses WifiP2pManager to discover/connect to peers // TODO: 4/17/2017
    }
    protected void wifiDirectEnabled() {
        //wifi direct is enabled on this phone, can play!
        wifiDirectAllowed = true;
    }
    protected void wifiDirectDisabled() {
        //wifi direct is not allowed on this phone, cannot play!
        wifiDirectAllowed = false;
    }

>>>>>>> misha
    /* Creates a dialog for user confirmation */
    private void createTryAgainDialog(String msg, String positive, String negative) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(positive,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
<<<<<<< HEAD
                        setP2PSpinner(); //fixme what do when user wants to try connecting again?
=======
                        //todo what do when user wants to try connecting again?
>>>>>>> misha
                    }
                });
        alertDialogBuilder.setNegativeButton(negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
<<<<<<< HEAD
//                        recreate(); //fixme what do when user doesn't want to connect?
=======
                        recreate(); //fixme what do when user doesn't want to connect?
>>>>>>> misha
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();}

    /* Starts the Battleship Game */
    private void startGame() {
        Intent intent = new Intent(getApplicationContext(),HumanDeployActivity.class);
        startActivity(intent);
    }

    public void startAIGame(View view){
        Intent intent = new Intent(getApplicationContext(),HumanDeployActivity.class);
        startActivity(intent);
    }

    public void connectPlayer(View view){
        switch ( conectionType ) {
            case 0: //Bluetooth
                startBTGame();
                break;
            case 1: //Wifi Direct
                startWFDirectGame();
                break;
            case 2: //Wifi
                startWFGame();
                break;
        }
    }
}