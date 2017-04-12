package edu.utep.cs.cs4330.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Battleship game;
    Spinner p2p, ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = game.getGame();
        p2p = (Spinner) findViewById(R.id.P2Pspinner);
        setP2PSpinner();
        ai = (Spinner) findViewById(R.id.AIspinner);
        setAISpinner();
    }

    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /* Sets adapters to the Spinners */
    private void setP2PSpinner() {
        //P2P Adapter
        ArrayAdapter<CharSequence> p2pAdapter = ArrayAdapter.createFromResource(this,R.array.p2p,android.R.layout.simple_spinner_item);
        p2pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p2p.setAdapter(p2pAdapter);
        p2p.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ( position ) {
                    case 0: //name: Play on Network
                        break;
                    case 1: //Bluetooth
                        //TODO turn on if was off through settings and connect to device
                        startGame();
                        break;
                    case 2: //Wifi Direct
                        //TODO dat extra 10 points doe
                        startGame();
                        break;
                    case 3: //Wifi
                        //TODO turn on if was off through settings and connect to network
                        startGame();
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
                    case 0: //name: Play AI
                        break;
                    case 1: //Smart
                        game.addSmartStrategy();
                        startGame();
                        break;
                    case 2: //Random
                        game.addRandomStrategy();
                        startGame();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO idk maybe something pretty?
            }
        });
    }

<<<<<<< HEAD
    /* Starts a game against the AI upon click */
    public void AIclicked(View v) {
        toast("plaing AI");
        Intent intent = new Intent(getApplicationContext(),HumanDeployActivity.class);
        Log.d("MAIN ACT","intent made");
=======
    /* Starts the Battleship Game */
    private void startGame() {
        //TODO set the right activity call boi
        Intent intent = new Intent(getApplicationContext(),HumanDeployActivity.class);
        //Intent intent = new Intent(getApplicationContext(),GameActivity.class);
>>>>>>> master
        startActivity(intent);
    }
}
