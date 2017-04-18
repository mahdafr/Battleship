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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
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

public class MainActivity extends AppCompatActivity {
    private Battleship game;
    Spinner p2p, ai;
    int conectionType;
    boolean wifiDirectAllowed;
    //WifiDirect functionality
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conectionType = -1;

        createWD(); //set up for wifi direct

        game = game.getGame();
        wifiDirectAllowed = false;
        p2p = (Spinner) findViewById(R.id.P2Pspinner);
        setP2PSpinner();
        ai = (Spinner) findViewById(R.id.AIspinner);
        setAISpinner();
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
                        //startGame();
                        break;
                    case 1: //Random
                        game.addRandomStrategy();
                        //startGame();
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
    }

    /* Wifi Functionality */
    private boolean WFenabled() {
        //checks if WF is on
        WifiManager m = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return m.isWifiEnabled();
    }
    private void turnOnWF() {
        //creates a window alert: user permission to turn on WF
        WifiManager m = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }
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

    /* Creates a dialog for user confirmation */
    private void createTryAgainDialog(String msg, String positive, String negative) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton(positive,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //todo what do when user wants to try connecting again?
                    }
                });
        alertDialogBuilder.setNegativeButton(negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        recreate(); //fixme what do when user doesn't want to connect?
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