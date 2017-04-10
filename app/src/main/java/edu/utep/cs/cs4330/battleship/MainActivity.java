package edu.utep.cs.cs4330.battleship;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /* Starts a LOCAL game upon click */
    public void LOCALclicked(View v) {
        toast("playing against local opponent");
        //TODO the activity yo
        //Intent intent = new Intent(getApplicationContext(),LOCALActivity.class);
        //startActivity(intent);
    }

    /* Starts a P2P game upon click */
    public void P2Pclicked(View v) {
        toast("playing against network opponent");
        Intent intent = new Intent(getApplicationContext(),P2PActivity.class);
        startActivity(intent);
    }

    /* Starts a game against the AI upon click */
    public void AIclicked(View v) {
        toast("plaing AI");
        Intent intent = new Intent(getApplicationContext(),AIActivity.class);
        startActivity(intent);
    }
}
