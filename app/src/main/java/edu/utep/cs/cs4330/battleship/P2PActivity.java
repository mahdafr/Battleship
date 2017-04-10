package edu.utep.cs.cs4330.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class P2PActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2p);
    }
    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /* Starts a LOCAL game upon click */
    private void LOCALclicked(View v) {
        toast("playing against local opponent");
        //TODO the activity yo
        //Intent intent = new Intent(getApplicationContext(),LOCALActivity.class);
        //startActivity(intent);
    }
}
