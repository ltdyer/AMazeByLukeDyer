package edu.wm.cs.cs301.amazebylukedyer.justAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.wm.cs.cs301.amazebylukedyer.R;
import edu.wm.cs.cs301.amazebylukedyer.generation.MazeConfiguration;
import edu.wm.cs.cs301.amazebylukedyer.generation.MazeFactory;
import edu.wm.cs.cs301.amazebylukedyer.generation.Order;
import edu.wm.cs.cs301.amazebylukedyer.gui.DataHolder;
import edu.wm.cs.cs301.amazebylukedyer.gui.MazePanel;
import edu.wm.cs.cs301.amazebylukedyer.gui.RobotDriver;

import static edu.wm.cs.cs301.amazebylukedyer.generation.Order.Builder.DFS;

public class GeneratingActivity extends AppCompatActivity implements Order {


    //INTERACTS WITH MAZEFACTORY


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

    int percentdone = 0;

    String skillLevel;
    String generationAlgo;
    String operationMode;

    public static Handler progressHandler;
    public DataHolder dh;
    public static MazeConfiguration mazeConfig;
    public static MazePanel mp;
    MazeFactory mazefactory;
    Order orderChoice;
    Order.Builder builderChoice;


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
        progressBarString = (TextView) findViewById(R.id.Generating_maze_string);

        mazefactory = new MazeFactory();


        if (operationMode.equals("Manual")) {



            intent = new Intent(this, PlayManuallyActivity.class);

            intent.putExtra(AMazeActivity.GENERATIONALGO, generationAlgo);
            intent.putExtra(AMazeActivity.SKILLLEVEL, skillLevel);
            intent.putExtra(AMazeActivity.OPERATIONMODE, operationMode);

            setMaze(skillLevel, generationAlgo, operationMode);




        }
        else if (operationMode.equals("WallFollower") || operationMode.equals("Wizard")) {



            intent = new Intent(this, PlayAnimationActivity.class);

            intent.putExtra(AMazeActivity.GENERATIONALGO, generationAlgo);
            intent.putExtra(AMazeActivity.SKILLLEVEL, skillLevel);
            intent.putExtra(AMazeActivity.OPERATIONMODE, operationMode);

            setMaze(skillLevel, generationAlgo, operationMode);




        }

    }

    private void setMaze(String skillLevel, String generationAlgo, String operationMode) {
        getSkillLevel();
        getBuilder();


        //also something along the lines of mazeFactory.builder(this)?
        mazefactory.order(this);

    }

//    private void setOperationMode(String operationMode) {
//        if (operationMode.equals("Manual")) {
//            //set Manual
//        }
//        else if(operationMode.equals("WallFollower") || operationMode.equals("Wizard")) {
//            //set Wallfollower
//        }
//    }






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

    @Override
    public int getSkillLevel() {
        return Integer.parseInt(skillLevel);
    }

    @Override
    public Builder getBuilder() {
        if (generationAlgo.equals("Default")) {
            builderChoice = DFS;
            return builderChoice;
        }
        else if (generationAlgo.equals("Kruskal")) {
            builderChoice = Builder.Kruskal;
            return builderChoice;
        }
        else if (generationAlgo.equals("Prim")) {
            builderChoice = Builder.Prim;
            return builderChoice;
        }
        return builderChoice;
    }

    @Override
    public boolean isPerfect() {
        return false;
    }

    @Override
    public void setBuilder(Builder builder) {

    }

    @Override
    public void deliver(MazeConfiguration mazeConfig) {
        //I'm thinking we wont do anything with operation in here, maybe thats for next screen

        //Now that we're getting this mazeConfiguration returned from the MazeBuilder buildOrder thread, we can set this in our data holder

        dh.setMazeConfiguration(mazeConfig);
        startActivity(intent);
        finish();

    }

    @SuppressLint("HandlerLeak")
    @Override
    public void updateProgress(int percentage) {
        progressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what < 100) {
                    progressBar.setProgress(msg.what);
                    progressBarString.setText("Progress: " + msg.what);
                }
            }
        };

    }

    /**
     * Makes the corresponding start button visible based on the mode of operation
     */
//    public void makeOneOfTheStartButtonsVisible() {
//        if (operationMode.equals("Manual")) {
//            startManualMazePlayButton.setVisibility(View.VISIBLE);
//        }
//        if (operationMode.equals("WallFollower") || operationMode.equals("Wizard")) {
//            startAutomaticMazePlayButton.setVisibility(View.VISIBLE);
//        }
//        else {
//            Log.v("no button, opMode is: ", ""+operationMode);
//        }
//    }


    /**
     * These two methods activate the coresponding Play activity base don mode of operation
     * @param view
     */
//    public void changeToPlayManuallyActivity(View view) {
//
//        intent = new Intent(this, PlayManuallyActivity.class);
//
//        intent.putExtra(AMazeActivity.GENERATIONALGO, generationAlgo);
//        intent.putExtra(AMazeActivity.SKILLLEVEL, skillLevel);
//        intent.putExtra(AMazeActivity.OPERATIONMODE, operationMode);
//
//        Log.v("we are in: ", "PlayManually");
//
//        startActivity(intent);
//        finish();
//    }
//
//    public void changeToPlayAutomaticActivity(View view) {
//        intent = new Intent(this, PlayAnimationActivity.class);
//
//        intent.putExtra(AMazeActivity.GENERATIONALGO, generationAlgo);
//        intent.putExtra(AMazeActivity.SKILLLEVEL, skillLevel);
//        intent.putExtra(AMazeActivity.OPERATIONMODE, operationMode);
//
//        Log.v("we are in: ", "PlayAnimation");
//
//        startActivity(intent);
//        finish();
//    }
}
