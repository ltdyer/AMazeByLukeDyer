package edu.wm.cs.cs301.amazebylukedyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    private Button startManualMazePlayButton;
    private Button startAutomaticMazePlayButton;

    final public static String MANUAL = null;
    final public static String ROBOT = null;
    final public static String WALLFOLLOWER = null;
    final public static String SHOWWALLS = null;
    final public static String SHOWFULLMAZE = null;
    final public static String SHOWSOLUTION = null;
    Intent intent;

    int progress = 0;

    String skillLevel;
    String generationAlgo;
    String operationMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generatingactivity_main);

        Bundle mainMenuIntent = getIntent().getExtras();

        skillLevel = "LEVEL";
        generationAlgo = "ALGORITHM";
        operationMode = "OPERATION";

        skillLevel = mainMenuIntent.getString(AMazeActivity.SKILLLEVEL);
        generationAlgo = mainMenuIntent.getString(AMazeActivity.GENERATIONALGO);
        operationMode = mainMenuIntent.getString(AMazeActivity.OPERATIONMODE);



        Log.v("skill level: ", ""+skillLevel);
        Log.v("algorithm: ", ""+generationAlgo);
        Log.v("operation mode: ", ""+operationMode);




        startManualMazePlayButton = (Button) findViewById(R.id.start_button_manual);
        startAutomaticMazePlayButton = (Button) findViewById(R.id.start_button_robot);
        progressBar = (ProgressBar) findViewById(R.id.Generating_maze_progress);
        progressBar.setMax(100);
        Drawable draw=getResources().getDrawable(R.drawable.customprogressbar);
        setProgressValue(progress);
        progressBarString = (TextView) findViewById(R.id.Generating_maze_string);



    }


    /**
     * Utilizes the progressBar and a thread to emulate the maze being generated. In reality, it only is filled up a bit every 1000 ms because there is nothing to
     * generate
     * @param progress
     */
    public void setProgressValue(final int progress) {


        progressBar.setProgress(progress);
        makeOneOfTheStartButtonsVisible();
//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    Thread.sleep(1000);
//
//                }
//                catch (InterruptedException ex) {
//                    //how to deal with exception so thread stops? Code never makes it here
//                    makeOneOfTheStartButtonsVisible();
//
//                }
//
//                setProgressValue(progress + 10);
//                Log.v("progress is: ", "" + progress);
//                if (progress >= 100) {
//                    Log.v("interrupted?", ""+progress);
//                    //even though we make it here, the thread is never stopped, it just keeps going
//                    Thread.interrupted();
//                }
//
//            }
//        });
//        thread.start();


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

    public void makeOneOfTheStartButtonsVisible() {
        if (operationMode.equals("Manual")) {
            startManualMazePlayButton.setVisibility(View.VISIBLE);
        }
        if (operationMode.equals("WallFollower") || operationMode.equals("Wizard")) {
            startAutomaticMazePlayButton.setVisibility(View.VISIBLE);
        }
        else {
            Log.v("no button, opMode is: ", ""+operationMode);
        }
    }





    public void changeToPlayManuallyActivity(View view) {

        intent = new Intent(this, PlayManuallyActivity.class);

        intent.putExtra(AMazeActivity.GENERATIONALGO, generationAlgo);
        intent.putExtra(AMazeActivity.SKILLLEVEL, skillLevel);
        intent.putExtra(AMazeActivity.OPERATIONMODE, operationMode);


        startActivity(intent);
        finish();
    }

    public void changeToPlayAutomaticActivity(View view) {
        intent = new Intent(this, PlayManuallyActivity.class);

        intent.putExtra(AMazeActivity.GENERATIONALGO, generationAlgo);
        intent.putExtra(AMazeActivity.SKILLLEVEL, skillLevel);
        intent.putExtra(AMazeActivity.OPERATIONMODE, operationMode);


        startActivity(intent);
        finish();
    }
}
