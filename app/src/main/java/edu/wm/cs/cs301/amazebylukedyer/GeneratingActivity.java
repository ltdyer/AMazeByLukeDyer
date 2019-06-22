package edu.wm.cs.cs301.amazebylukedyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GeneratingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView progressBarString;
    private Button startMazePlayButton;

    final public static String MANUAL = null;
    final public static String ROBOT = null;
    final public static String WALLFOLLOWER = null;
    final public static String SHOWWALLS = null;
    final public static String SHOWFULLMAZE = null;
    final public static String SHOWSOLUTION = null;
    Intent intent;

    int skillLevel;
    String generationAlgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generatingactivity_main);

        Bundle mainMenuIntent = getIntent().getExtras();
        skillLevel = mainMenuIntent.getInt(AMazeActivity.SKILLLEVEL);
        generationAlgo = mainMenuIntent.getString(AMazeActivity.GENERATIONALGO);


        intent = new Intent(this, PlayManuallyActivity.class);

        Log.v("algorithm: ", ""+skillLevel);
        Log.v("skill level: ", ""+generationAlgo);

        progressBar = (ProgressBar) findViewById(R.id.Generating_maze_progress);
        progressBarString = (TextView) findViewById(R.id.Generating_maze_string);
        startMazePlayButton = (Button) findViewById(R.id.start_button);

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
                Intent intent = new Intent(this, AMazeActivity.class);
                startActivity(intent);
                return true;


            default:
                return false;

        }
    }





    public void changeToPlayManuallyActivity(View view) {
    }
}
