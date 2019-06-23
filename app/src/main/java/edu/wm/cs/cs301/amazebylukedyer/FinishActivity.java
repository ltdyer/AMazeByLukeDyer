package edu.wm.cs.cs301.amazebylukedyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class FinishActivity extends AppCompatActivity {

    private String pathLength;
    private String manual;
    private String robot;
    private String result;

    private TextView message;
    private TextView pathLengthMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishactivity_main);

        pathLength = "path length";
        manual = "manual";
        robot = "robot";
        result = "result";

        Bundle playActivityIntent = getIntent().getExtras();


        if (playActivityIntent.getString(PlayAnimationActivity.ROBOT) == null) {
            pathLength = playActivityIntent.getString(PlayManuallyActivity.PATHLENGTH);
            result = playActivityIntent.getString(PlayManuallyActivity.RESULT);

            Log.v("Path Length Manual: ", pathLength);
            Log.v("Result Manual: ", result);
        }
        if (playActivityIntent.getString(PlayManuallyActivity.MANUAL) == null) {
            pathLength = playActivityIntent.getString(PlayAnimationActivity.PATHLENGTH);
            result = playActivityIntent.getString(PlayAnimationActivity.RESULT);

            Log.v("Path Length Robot: ", pathLength);
            Log.v("Result Robot: ", result);
        }

        message = (TextView) findViewById(R.id.win_message);
        pathLengthMessage = (TextView) findViewById(R.id.path_length);


        if(result == "win") {
            message.setText("You Win!");
        }
        if(result == "loss") {
            message.setText("Is this Loss?");
        }

        pathLengthMessage.setText("Path Length: " + pathLength);


    }

    /**
     * Overriding the click operation of the the back button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Back Button clicked", Toast.LENGTH_SHORT).show();
                Intent backintent = new Intent(this, AMazeActivity.class);
                startActivity(backintent);
                finish();
                return true;


            default:
                return false;

        }
    }
}
