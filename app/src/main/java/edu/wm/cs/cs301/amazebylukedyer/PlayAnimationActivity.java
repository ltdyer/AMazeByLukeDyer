package edu.wm.cs.cs301.amazebylukedyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PlayAnimationActivity extends AppCompatActivity {

    private Button showWallsButton;
    private Button showFullMazeButton;
    private Button showSolutionButton;

    private Button pausePlayButton;
    private Button skipButton;

    private int pathLength;

    String skillLevel;
    String generationAlgo;
    String operationMode;

    public boolean visited = false;

    public static final String PATHLENGTH = "PATHLENGTH";
    final public static String MANUAL = null;
    public static final String ROBOT = "ROBOT";
    final public static String RESULT = "RESULT";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playanimationactivity_main);

        visited = true;

        Bundle generatingActivityIntent = getIntent().getExtras();

        skillLevel = "LEVEL";
        generationAlgo = "ALGORITHM";
        operationMode = "OPERATION";

        skillLevel = generatingActivityIntent.getString(AMazeActivity.SKILLLEVEL);
        generationAlgo = generatingActivityIntent.getString(AMazeActivity.GENERATIONALGO);
        operationMode = generatingActivityIntent.getString(AMazeActivity.OPERATIONMODE);



        Log.v("skill level: ", ""+skillLevel);
        Log.v("algorithm: ", ""+generationAlgo);
        Log.v("operation mode: ", ""+operationMode);

        showWallsButton = (Button) findViewById(R.id.Show_walls_button);
        showFullMazeButton = (Button) findViewById(R.id.Show_full_maze_button);
        showSolutionButton = (Button) findViewById(R.id.Show_solution_button);

        pausePlayButton = (Button) findViewById(R.id.pause_and_play_button);

        skipButton = (Button) findViewById(R.id.skip_button);

        pathLength = 0;
    }

    public void pausePlayClick(View view) {
        Toast.makeText(getBaseContext(), "Pause!", Toast.LENGTH_SHORT).show();
        Log.v("screen: ", "paused");
    }

    public void showWalls(View view) {
        Toast.makeText(getBaseContext(), "If this wasn't a test, I would be showing WALLS", Toast.LENGTH_SHORT).show();
        Log.v("Showing: ", "walls");
    }

    public void showFullMaze(View view) {
        Toast.makeText(getBaseContext(), "If this wasn't a test, I would be showing FULL MAZE", Toast.LENGTH_SHORT).show();
        Log.v("Showing: ", "full maze");
    }

    public void showSolution(View view) {
        Toast.makeText(getBaseContext(), "If this wasn't a test, I would be showing SOLUTION", Toast.LENGTH_SHORT).show();
        Log.v("Showing: ", "solution");
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

    public void changeToFinishActivity(View view) {

        String keyIdentifierPathLength = null;
        String keyIdentifierMode = null;
        String keyIdentifierFakeMode = null;
        String keyIdentifierResult = null;

        Intent intent = new Intent(this, FinishActivity.class);
        keyIdentifierPathLength = String.valueOf(pathLength);
        keyIdentifierMode = "Robot";

        //change keyIdentifierResult when we actually have the ability to win or lose; for now, default to win
        keyIdentifierResult = "Win";


        intent.putExtra(PATHLENGTH, keyIdentifierPathLength);
        intent.putExtra(MANUAL, keyIdentifierFakeMode);
        intent.putExtra(ROBOT, keyIdentifierMode);
        intent.putExtra(RESULT, keyIdentifierResult);
        Log.v("path length: ", keyIdentifierPathLength);
        Log.v("operation mode: ", keyIdentifierMode);


        startActivity(intent);
        finish();


    }


}
