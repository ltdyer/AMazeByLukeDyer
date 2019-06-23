package edu.wm.cs.cs301.amazebylukedyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PlayManuallyActivity extends AppCompatActivity {

    private Button showWallsButton;
    private Button showFullMazeButton;
    private Button showSolutionButton;

    private Button forwardButton;
    private Button backwardButton;
    private Button leftButton;
    private Button rightButton;

    private Button skipButton;

    private int pathLength;

    String skillLevel;
    String generationAlgo;
    String operationMode;

    public boolean visited = false;

    final public static String PATHLENGTH = "PATHLENGTH";
    final public static String MANUAL = "MANUAL";
    final public static String ROBOT = null;
    final public static String RESULT = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmanuallyactivity_main);

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

        forwardButton = (Button) findViewById(R.id.up_button);
        backwardButton = (Button) findViewById(R.id.down_button);
        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);

        skipButton = (Button) findViewById(R.id.skip_button);

        pathLength = 0;


    }

    public void forwardClick(View view) {
        Toast.makeText(getBaseContext(), "Up!", Toast.LENGTH_SHORT).show();
        Log.v("movement: ", "forward");
    }

    public void downClick(View view) {
        Toast.makeText(getBaseContext(), "Backwards!", Toast.LENGTH_SHORT).show();
        Log.v("movement: ", "backwards");
    }


    public void leftClick(View view) {
        Toast.makeText(getBaseContext(), "Left!", Toast.LENGTH_SHORT).show();
        Log.v("movement: ", "left");
    }

    public void rightClick(View view) {
        Toast.makeText(getBaseContext(), "Right!", Toast.LENGTH_SHORT).show();
        Log.v("movement: ", "right");
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
        keyIdentifierMode = "Manual";

        //change keyIdentifierResult when we actually have the ability to win or lose; for now, default to win
        keyIdentifierResult = "Win";


        intent.putExtra(PATHLENGTH, keyIdentifierPathLength);
        intent.putExtra(MANUAL, keyIdentifierMode);
        intent.putExtra(ROBOT, keyIdentifierFakeMode);
        intent.putExtra(RESULT, keyIdentifierResult);
        Log.v("path length: ", keyIdentifierPathLength);
        Log.v("operation mode: ", keyIdentifierMode);


        startActivity(intent);
        finish();

    }


}
