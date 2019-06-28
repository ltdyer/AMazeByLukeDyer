package edu.wm.cs.cs301.amazebylukedyer.justAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.wm.cs.cs301.amazebylukedyer.R;
import edu.wm.cs.cs301.amazebylukedyer.generation.Cells;
import edu.wm.cs.cs301.amazebylukedyer.generation.MazeConfiguration;
import edu.wm.cs.cs301.amazebylukedyer.generation.Seg;
import edu.wm.cs.cs301.amazebylukedyer.gui.Constants;
import edu.wm.cs.cs301.amazebylukedyer.gui.Controller;
import edu.wm.cs.cs301.amazebylukedyer.gui.DataHolder;
import edu.wm.cs.cs301.amazebylukedyer.gui.FirstPersonDrawer;
import edu.wm.cs.cs301.amazebylukedyer.gui.MapDrawer;
import edu.wm.cs.cs301.amazebylukedyer.gui.MazePanel;

import static edu.wm.cs.cs301.amazebylukedyer.justAndroid.GeneratingActivity.controller;

public class PlayManuallyActivity extends AppCompatActivity {

    private Button showWallsButton;
    private Button showFullMazeButton;
    private Button showSolutionButton;
    private boolean showWalls;
    private boolean showFullMaze;
    private boolean showSolution;

    private Button forwardButton;
    private Button backwardButton;
    private Button leftButton;
    private Button rightButton;

    private Button skipButton;

    private int pathLength;

    MazeConfiguration mazeConfig;
    DataHolder dh;
    MazePanel mazePanel;

    FirstPersonDrawer firstPersonDrawer;
    MapDrawer mapDrawer;
    Seg seg;

    String skillLevel;
    String generationAlgo;
    String operationMode;

    public boolean started = false;

    int angle;
    int walkStep;
    Cells seenCells;

    int px; //position x
    int py; //position y
    int dx; //direction x
    int dy; //direction y

    final public static String PATHLENGTH = "PATHLENGTH";
    final public static String MANUAL = "MANUAL";
    final public static String ROBOT = null;
    final public static String RESULT = "RESULT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmanuallyactivity_main);

        //INTERACTS WITH STATEPLAYING
        //NO CONTROLLER

        started = true;

        mazeConfig = dh.getInstance().getMazeConfiguration();

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
//        forwardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.v("Up", "Moved forward");
//                controller.keyDown()
//
//            }
//        });





        backwardButton = (Button) findViewById(R.id.down_button);
        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);
        skipButton = (Button) findViewById(R.id.skip_button);

        pathLength = 0;


        //Note: MazeConfig is now the entity that contains the numerical information regarding the width, height, size, etc of the maze that we got from generation
        //We will need that to maybe access those kinds of variables
        start(mazePanel);

    }


    public void start(MazePanel mazePanel) {
        started = true;

        this.mazePanel = mazePanel;
        showWalls = false;
        showFullMaze = false;
        showSolution = false;

        seenCells = new Cells(mazeConfig.getWidth()+1, mazeConfig.getHeight()+1);

        setPositionDirectionViewingDirection();

        walkStep = 0;
        startDrawing();


    }

    public void startDrawing() {
        firstPersonDrawer = new FirstPersonDrawer(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Constants.MAP_UNIT, Constants.STEP_SIZE, seenCells, mazeConfig.getRootnode());
        mapDrawer = new MapDrawer(seenCells, 15, mazeConfig);
        draw();
    }


    public void setPositionDirectionViewingDirection() {
        int start[] = mazeConfig.getStartingPosition();
        setCurrentPosition(start[0], start[1]);
        angle = 0;
        setDirectionToMatchCurrentAngle();

    }


    public void setCurrentPosition(int x, int y) {
        px = x;
        py = y;
    }
    private void setCurrentDirection(int x, int y) {
        dx = x ;
        dy = y ;
    }

    public void setDirectionToMatchCurrentAngle() {
        setCurrentDirection((int) Math.cos(radify(angle)), (int) Math.sin(radify(angle))) ;
    }

    final double radify(int x) {

        return x*Math.PI/180;
    }

    public void draw() {
        firstPersonDrawer.draw(mazePanel, px, py, walkStep, angle);
        if (isInShowWallsMode()) {
            mapDrawer.draw(mazePanel, px, py, angle, walkStep, isInShowFullMazeMode(), isInShowSolutionMode());
        }
        mazePanel.update();
    }


    boolean isInShowWallsMode() {
        return showWalls;
    }
    boolean isInShowFullMazeMode() {
        return showFullMaze;
    }
    boolean isInShowSolutionMode() {
        return showSolution;
    }
    /**
     * the following four methods are directional input methods
     * @param view
     */

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


    /**
     * the following three methods are the toggle map, walls, and solution buttons
     * @param view
     */
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

    /**
     * changes the activity from play manually to finish
     * @param view
     */
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
